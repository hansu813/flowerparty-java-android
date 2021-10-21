package com.example.flowerparty.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
//import com.android.volley.error.VolleyError;
//import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.example.flowerparty.R;
import com.example.flowerparty.RbPreference;

public class ProfileSettingActivity extends AppCompatActivity {
    ImageView imgClose;
    ImageView imgUNameEdit, imgEmailEdit;
    ImageView imgProfile;
    TextView changePW, pSetting_name, pSetting_nName, pSetting_email;

    //업로드할 이미지의 절대경로(실제 경로)
    String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        imgProfile = findViewById(R.id.imgProfile);

        imgClose = findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 펜 이미지 클릭 시 사용자 닉네임 변경 액티비티로 이동
        imgUNameEdit = findViewById(R.id.imgUNameEdit);
        imgUNameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileCnameActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);//액티비티 스택제거
                startActivity(intent);
            }
        });

        // 펜 이미지 클릭 시 이메일 수정 액티비티로 이동
        imgEmailEdit = findViewById(R.id.imgEmailEdit);
        imgEmailEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileCEmailActivity.class);
                startActivity(intent);
            }
        });

        // 텍스트 클릭 시 비밀번호 변경 액티비티로 이동
        changePW = findViewById(R.id.ChangePW);
        changePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfilePwAccountActivity.class);
                startActivity(intent);
            }
        });


        //외부 저장소에 권한 필요, 동적 퍼미션
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            int permissionResult= checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult== PackageManager.PERMISSION_DENIED){
                String[] permissions= new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,10);
            }
        }



        pSetting_name = findViewById(R.id.pSetting_name);
        pSetting_nName = findViewById(R.id.pSetting_nName);
        pSetting_email = findViewById(R.id.pSetting_email);

        RbPreference pref = new RbPreference(ProfileSettingActivity.this);
        String userId = pref.getValue(RbPreference.PREF_INTRO_USER_AGREEMENT, "default");
        String userEmail = pref.getValue(RbPreference.PREF_MAIN_VALUE, "default");
        String userName = pref.getValue(RbPreference.PREF_SUB_VALUE, "default");
        pSetting_name.setText(userId);
        pSetting_nName.setText(userName);
        pSetting_email.setText(userEmail);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 10:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "외부 메모리 읽기/쓰기 사용 가능", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "외부 메모리 읽기/쓰기 제한", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void clickImg(View view) {

        //갤러리 or 사진 앱 실행하여 사진을 선택하도록..
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){
                    //선택한 사진의 경로(Uri)객체 얻어오기
                    Uri uri= data.getData();
                    if(uri!=null){
                        imgProfile.setImageURI(uri);
                    }

                }else
                {
                    Toast.makeText(this, "이미지 선택을 하지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }//onActivityResult() ..

    //Uri -- > 절대경로로 바꿔서 리턴시켜주는 메소드
    String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor= loader.loadInBackground();
        int column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result= cursor.getString(column_index);
        cursor.close();
        return  result;
    }

    /*public void clickUpload(View view) {
        //서버로 보낼 데이터
        //String name= etName.getText().toString();
        //String msg= etMsg.getText().toString();

        //안드로이드에서 보낼 데이터를 받을 php 서버 주소
        String serverUrl="http://ci2021flower.dongyangmirae.kr/insertDB.php";

        //Volley plus Library를 이용해서
        //파일 전송하도록..
        //Volley+는 AndroidStudio에서 검색이 안됨 [google 검색 이용]

        //파일 전송 요청 객체 생성[결과를 String으로 받음]

        SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new AlertDialog.Builder(ProfileSettingActivity.this).setMessage("응답:"+response).create().show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileSettingActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        //요청 객체에 보낼 데이터를 추가
        RbPreference pref = new RbPreference(ProfileSettingActivity.this);
        String userId = pref.getValue(RbPreference.PREF_INTRO_USER_AGREEMENT, "default");
        smpr.addStringParam("userId", userId);
        //smpr.addStringParam("msg", msg);
        //이미지 파일 추가
        smpr.addFile("img", imgPath);

        //요청객체를 서버로 보낼 우체통 같은 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(smpr);
    }*/

    public void clickLoad(View view) {
    }


}
