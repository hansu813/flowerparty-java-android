 package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.flowerparty.R;

import java.util.ArrayList;
import java.util.Set;

 public class BlueConnectActivity extends AppCompatActivity {
     ImageView imgLArrowBlue;
     Switch switchBlue;

     BluetoothAdapter btAdapter;
     private final static int REQUEST_ENABLE_BT = 1;

     Set<BluetoothDevice> pairedDevices;
     ArrayAdapter<String> btArrayAdapter;
     ArrayList<String> deviceAddressArray;
     TextView textStatus;
     Button btnParied, btnSearch, btnSend;
     ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_connect);

        // Get permission
        String[] permission_list = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(BlueConnectActivity.this, permission_list,  1);

        // Enable bluetooth
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        // variables
        textStatus = (TextView) findViewById(R.id.textViewStatus);
        btnParied = (Button) findViewById(R.id.btnPaired);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSend = (Button) findViewById(R.id.btnSend);
        listView = (ListView) findViewById(R.id.listviewBT);

        // show paired devices
        btArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        deviceAddressArray = new ArrayList<>();
        listView.setAdapter(btArrayAdapter);



        imgLArrowBlue = findViewById(R.id.imgLArrowBlue);
        imgLArrowBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 블루투스 on, off 스위치
        switchBlue = findViewById(R.id.switchBlue);
  //      switchBlue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            LinearLayout blueOn = (LinearLayout) findViewById(R.id.blueOn);
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    blueOn.setVisibility(View.VISIBLE);
//                } else {
//                    blueOn.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
    }

     public void onClickButtonPaired(View view){
         btArrayAdapter.clear();
         if(deviceAddressArray!=null && !deviceAddressArray.isEmpty()){ deviceAddressArray.clear(); }
         pairedDevices = btAdapter.getBondedDevices();
         if (pairedDevices.size() > 0) {
             // There are paired devices. Get the name and address of each paired device.
             for (BluetoothDevice device : pairedDevices) {
                 String deviceName = device.getName();
                 String deviceHardwareAddress = device.getAddress(); // MAC address
                 btArrayAdapter.add(deviceName);
                 deviceAddressArray.add(deviceHardwareAddress);
             }
         }
     }
}