 package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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

 public class BlueConnectActivity extends AppCompatActivity {
     ImageView imgLArrowBlue;
     Switch switchBlue;

     BluetoothAdapter btAdapter;
     private final static int REQUEST_ENABLE_BT = 1;
     TextView textStatus;
     Button btnParied, btnSearch, btnSend;
     ListView listView;

     Set<BluetoothDevice> pairedDevices;
     ArrayAdapter<String> btArrayAdapter;
     ArrayList<String> deviceAddressArray;
     private OutputStream mOutputStream;
     private InputStream mInputStream;
     private BluetoothDevice mRemoteDevice;
     public ProgressDialog asyncDialog;
     public boolean onBT = false;
     public byte[] sendByte = new byte[4];
     private BluetoothSocket btSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_connect);

        // variables blue
        textStatus = (TextView) findViewById(R.id.text_status);
        btnParied = (Button) findViewById(R.id.btn_paired);
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnSend = (Button) findViewById(R.id.btn_send);
        listView = (ListView) findViewById(R.id.listview);

        //listView.setOnItemClickListener(new myOnItemClickListener());

        imgLArrowBlue = findViewById(R.id.imgLArrowBlue);
        imgLArrowBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 블루투스 on, off 스위치
        switchBlue = findViewById(R.id.switchBlue);
//        switchBlue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

        // show paired devices
        btArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        deviceAddressArray = new ArrayList<>();
        listView.setAdapter(btArrayAdapter);

    } /* onCreate */

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

     public void onClickButtonSearch(View view){
         // Check if the device is already discovering
         if(btAdapter.isDiscovering()){
             btAdapter.cancelDiscovery();
         } else {
             if (btAdapter.isEnabled()) {
                 btAdapter.startDiscovery();
                 btArrayAdapter.clear();
                 if (deviceAddressArray != null && !deviceAddressArray.isEmpty()) {
                     deviceAddressArray.clear();
                 }
                 IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                 registerReceiver(receiver, filter);
             } else {
                 Toast.makeText(getApplicationContext(), "bluetooth not on", Toast.LENGTH_SHORT).show();
             }
         }
     }

     // Create a BroadcastReceiver for ACTION_FOUND.
     private final BroadcastReceiver receiver = new BroadcastReceiver() {
         public void onReceive(Context context, Intent intent) {
             String action = intent.getAction();
             if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                 // Discovery has found a device. Get the BluetoothDevice
                 // object and its info from the Intent.
                 BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                 String deviceName = device.getName();
                 String deviceHardwareAddress = device.getAddress(); // MAC address
                 btArrayAdapter.add(deviceName);
                 deviceAddressArray.add(deviceHardwareAddress);
                 btArrayAdapter.notifyDataSetChanged();
             }
         }
     };

     @Override
     protected void onDestroy() {
         super.onDestroy();

         // Don't forget to unregister the ACTION_FOUND receiver.
         unregisterReceiver(receiver);
     }

     //public class myOnItemClickListener implements AdapterView.OnItemClickListener {

      //   @Override
//         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//             Toast.makeText(getApplicationContext(), btArrayAdapter.getItem(position), Toast.LENGTH_SHORT).show();
//
//             textStatus.setText("try...");
//
//             final String name = btArrayAdapter.getItem(position); // get name
//             final String address = deviceAddressArray.get(position); // get address
//             boolean flag = true;
//
//             BluetoothDevice device = btAdapter.getRemoteDevice(address);
//
//             // create & connect socket
//             try {
//                 btSocket = createBluetoothSocket(device);
//                 btSocket.connect();
//             } catch (IOException e) {
//                 flag = false;
//                 textStatus.setText("connection failed!");
//                 e.printStackTrace();
//             }
//
//             if(flag){
//                 textStatus.setText("connected to "+name);
//                 connectedThread = new ConnectedThread(btSocket);
//                 connectedThread.start();
//             }
//
         //}
     }
