package com.example.flowerparty.fragment;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.flowerparty.BluetoothTest;
import com.example.flowerparty.GetPlantNickRequest;
import com.example.flowerparty.R;
import com.example.flowerparty.RbPreference;
import com.example.flowerparty.activity.BlueActivity;
import com.example.flowerparty.activity.BlueConnectActivity;
import com.example.flowerparty.activity.PlantsNicknameActivity;
import com.example.flowerparty.activity.homeSettingActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class HomeFragment extends Fragment {
    ImageButton imgBtnManage;
    ImageView imgNickname;
    ImageView imgBlueIcon;
    Switch switchWater;
    private BluetoothSPP bt;
    Context ct;
    TextView textViewTemp, textViewHum, textViewCal;

    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> mPairedDevices;
    List<String> mListPairedDevices;

    Handler mBluetoothHandler;
    ConnectedBluetoothThread mThreadConnectedBluetooth;
    BluetoothDevice mBluetoothDevice;
    BluetoothSocket mBluetoothSocket;
    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    TextView text_pnickHome;
    private RbPreference pref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // 이미지 버튼 객체를 가져오기 위해서 변경
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

//        imgBtnManage = (ImageButton) rootview.findViewById(R.id.imgBtnHomeManage);
//        imgBtnManage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), homeSettingActivity.class);
//                startActivity(intent);
//            }
//        });

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        imgNickname = (ImageView) rootview.findViewById(R.id.imgNickname);
        imgNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlantsNicknameActivity.class);
                startActivity(intent);
            }
        });

        imgBlueIcon = (ImageView) rootview.findViewById(R.id.imgBlueIcon);
//        imgBlueIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), BluetoothTest.class);
//                startActivity(intent);
////                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
////                    //bt.disconnect();
////                } else {
////                    Intent intent = new Intent(ct, DeviceList.class);
////                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
////                }
//            }
//        });
        imgBlueIcon.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                listPairedDevices();
            }
        });

        // bluetooth
        ct = container.getContext();
//        bt = new BluetoothSPP(ct);
//        if(!bt.isBluetoothAvailable()) {
//            Toast.makeText(ct, "Bluetooth is not available"
//                    , Toast.LENGTH_SHORT).show();
//        }

        // bluetooth 로 센서 값 받아서 출력
        textViewCal = (TextView) rootview.findViewById(R.id.textViewCal);
        textViewHum = (TextView) rootview.findViewById(R.id.textViewHum);
        textViewTemp = (TextView) rootview.findViewById(R.id.textViewTemp);
//        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
//            @Override
//            public void onDataReceived(byte[] data, String message) {
//                //Toast.makeText(BlueConnectActivity.this, message, Toast.LENGTH_SHORT).show();
//                Log.e("HomeFBlue", "receive");
//                String[] array = message.split(",");
//                textViewCal.setText(array[0].concat(".jodo"));
//                textViewHum.setText(array[1].concat("%"));
//                textViewTemp.setText(array[2].concat("^C"));
//            }
//        });


//        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
//            public void onDeviceConnected(String name, String address) {
//                Toast.makeText(ct
//                        , "Connected to " + name + "\n" + address
//                        , Toast.LENGTH_SHORT).show();
//            }
//
//            public void onDeviceDisconnected() { //연결해제
//                Toast.makeText(ct
//                        , "Connection lost", Toast.LENGTH_SHORT).show();
//            }
//
//            public void onDeviceConnectionFailed() { //연결실패
//                Toast.makeText(ct
//                        , "Unable to connect", Toast.LENGTH_SHORT).show();
//            }
//        });

        // 닉네임 띄우기
        text_pnickHome = (TextView) rootview.findViewById(R.id.text_pnickHome);
        pref = new RbPreference(ct);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        String pNick = jsonObject.getString("plantNick");
                        text_pnickHome.setText(pNick);
                        text_pnickHome.setTextColor(Color.BLACK);

//                        if (plantNick.equals("null")) {
//                            String dNick = "닉네임";
//                            text_pnickHome.setText(dNick);
//                            text_pnickHome.setTextColor(Color.BLACK);
//                        } else {
//                            text_pnickHome.setText(plantNick);
//                            text_pnickHome.setTextColor(Color.BLACK);
//                        }
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                    Log.e("Error", "Response Error", e);
                }
            }
        };
        String userID = pref.getValue(RbPreference.PREF_INTRO_USER_AGREEMENT, "default");
        GetPlantNickRequest getPlantNickRequest = new GetPlantNickRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ct);
        queue.add(getPlantNickRequest);

        //
        switchWater = (Switch) rootview.findViewById(R.id.switchWater);
        switchWater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    //bt.send("1", true);
                    mThreadConnectedBluetooth.write("0");
                } else {
                    mThreadConnectedBluetooth.write("1");
                }
            }
        });

        mBluetoothHandler = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(msg.what == BT_MESSAGE_READ){
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String[] array = readMessage.split(",");
                    textViewHum.setText(array[1].concat("%"));
                    textViewCal.setText(array[0].concat(".C"));
                    textViewTemp.setText(array[2]);
                }
            }
        };

        return rootview;


    } /* onCreateView */

    void bluetoothOn() {
        if(mBluetoothAdapter == null) {
            Toast.makeText(ct, "블루투스를 지원하지 않는 기기입니다.", Toast.LENGTH_LONG).show();
        }
        else {
            if (mBluetoothAdapter.isEnabled()) {
                Toast.makeText(ct, "블루투스가 이미 활성화 되어 있습니다.", Toast.LENGTH_LONG).show();
                //mTvBluetoothStatus.setText("활성화");
            }
            else {
                Toast.makeText(ct, "블루투스가 활성화 되어 있지 않습니다.", Toast.LENGTH_LONG).show();
                Intent intentBluetoothEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE);
            }
        }
    }
    void bluetoothOff() {
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
            Toast.makeText(ct, "블루투스가 비활성화 되었습니다.", Toast.LENGTH_SHORT).show();
            //mTvBluetoothStatus.setText("비활성화");
        }
        else {
            Toast.makeText(ct, "블루투스가 이미 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }



    void listPairedDevices() {
        if (mBluetoothAdapter.isEnabled()) {
            mPairedDevices = mBluetoothAdapter.getBondedDevices();

            if (mPairedDevices.size() > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ct);
                builder.setTitle("장치 선택");

                mListPairedDevices = new ArrayList<String>();
                for (BluetoothDevice device : mPairedDevices) {
                    mListPairedDevices.add(device.getName());
                    //mListPairedDevices.add(device.getName() + "\n" + device.getAddress());
                }
                final CharSequence[] items = mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);
                mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        connectSelectedDevice(items[item].toString());
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                Toast.makeText(ct, "페어링된 장치가 없습니다.", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(ct, "블루투스가 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    void connectSelectedDevice(String selectedDeviceName) {
        for(BluetoothDevice tempDevice : mPairedDevices) {
            if (selectedDeviceName.equals(tempDevice.getName())) {
                mBluetoothDevice = tempDevice;
                break;
            }
        }
        try {
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(BT_UUID);
            mBluetoothSocket.connect();
            mThreadConnectedBluetooth = new ConnectedBluetoothThread(mBluetoothSocket);
            mThreadConnectedBluetooth.start();
            mBluetoothHandler.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget();
        } catch (IOException e) {
            Toast.makeText(ct, "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
        }
    }

    private class ConnectedBluetoothThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedBluetoothThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Toast.makeText(ct, "소켓 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = mmInStream.available();
                    if (bytes != 0) {
                        SystemClock.sleep(100);
                        bytes = mmInStream.available();
                        bytes = mmInStream.read(buffer, 0, bytes);
                        mBluetoothHandler.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                    }
                } catch (IOException e) {
                    break;
                }
            }
        }
        public void write(String str) {
            byte[] bytes = str.getBytes();
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Toast.makeText(ct, "데이터 전송 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Toast.makeText(ct, "소켓 해제 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }





}