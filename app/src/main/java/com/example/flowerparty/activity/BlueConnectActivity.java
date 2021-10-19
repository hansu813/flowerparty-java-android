 package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flowerparty.ConnectedThread;
import com.example.flowerparty.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

 public class BlueConnectActivity extends AppCompatActivity {
     ImageView imgLArrowBlue;
     Switch switchBlue;

     private BluetoothSPP bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_connect);

        // variables blue




        imgLArrowBlue = findViewById(R.id.imgLArrowBlue);
        imgLArrowBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 블루투스 on, off 스위치
        switchBlue = findViewById(R.id.switchBlue);
        switchBlue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            //LinearLayout blueOn = (LinearLayout) findViewById(R.id.blueOn);
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //blueOn.setVisibility(View.VISIBLE);
                    if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                        bt.disconnect();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                        startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                    }
                } else {
                    //blueOn.setVisibility(View.INVISIBLE);
                    bt.disconnect();
                    Log.i("bluetooth", "disconnect");
                }

            }
        });

//        Button btnConnect = findViewById(R.id.btnConnect); //연결시도
//        btnConnect.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
//                    bt.disconnect();
//                } else {
//                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
//                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
//                }
//            }
//        });

        bt = new BluetoothSPP(this); //Initializing

        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
            TextView temp = findViewById(R.id.textViewtest);
            public void onDataReceived(byte[] data, String message) {
                //Toast.makeText(BlueConnectActivity.this, message, Toast.LENGTH_SHORT).show();
                Log.e("blue", message);
                String[] array = message.split(",");
                temp.setText(array[0].concat("C"));
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() { //연결해제
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() { //연결실패
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });





    } /* onCreate */

     public void onDestroy() {
         super.onDestroy();
         bt.stopService(); //블루투스 중지
     }

     public void onStart() {
         super.onStart();
         if (!bt.isBluetoothEnabled()) { //
             Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
             startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
         } else {
             if (!bt.isServiceAvailable()) {
                 bt.setupService();
                 bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
                 setup();
             }
         }
     }

     public void setup() {
         Button btnSend = findViewById(R.id.btnSend); //데이터 전송
         btnSend.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 bt.send("Text", true);
             }
         });
     }


     public void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
             if (resultCode == Activity.RESULT_OK)
                 bt.connect(data);
         } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
             if (resultCode == Activity.RESULT_OK) {
                 bt.setupService();
                 bt.startService(BluetoothState.DEVICE_OTHER);
                 setup();
             } else {
                 Toast.makeText(getApplicationContext()
                         , "Bluetooth was not enabled."
                         , Toast.LENGTH_SHORT).show();
                 finish();
             }
         }
     }


 } /* end */
