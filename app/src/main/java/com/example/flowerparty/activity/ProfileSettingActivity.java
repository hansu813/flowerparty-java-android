package com.example.flowerparty.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
//import com.android.volley.error.VolleyError;
//import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.example.flowerparty.GetUserRequest;
import com.example.flowerparty.R;
import com.example.flowerparty.RbPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ProfileSettingActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView imgClose;
    ImageView imgUNameEdit, imgEmailEdit;
    ImageView imgProfile;
    TextView changePW, pSetting_name, pSetting_nName, pSetting_email;
    Button btn_set_save;
    private RbPreference pref;

    // 서버로 업로드 할 파일관련 변수
    public String uploadFilePath;
    public String uploadFileName;
    private int REQ_CODE_PICK_PICTURE = 1;

    // 파일을 업로드 하기 위한 변수 선언
    private int serverResponseCode = 0;

    // LOG
    private String TAGLOG = "ProfileSettingLoG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        imgProfile = findViewById(R.id.imgProfile);
        imgProfile.setOnClickListener(this);
        btn_set_save = findViewById(R.id.btn_set_save);
        btn_set_save.setOnClickListener(this);



        // 뒤로가기 버튼
        imgClose = findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
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
                Intent intent = new Intent(getApplicationContext(), ProfilePwChangeActivity.class);
                startActivity(intent);
            }
        });


        //외부 저장소에 권한 필요, 동적 퍼미션
//        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
//            int permissionResult= checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            if(permissionResult== PackageManager.PERMISSION_DENIED){
//                String[] permissions= new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                requestPermissions(permissions,10);
       //     }
        //}

        // userID
        pref = new RbPreference(ProfileSettingActivity.this);

        // 사용자 이름 띄우기
        pSetting_name = findViewById(R.id.pSetting_name);
        String userId = pref.getValue(RbPreference.PREF_INTRO_USER_AGREEMENT, "default");
        pSetting_name.setText(userId);

        // 사용자 닉네임 , 이메일 띄우기
       pSetting_nName = findViewById(R.id.pSetting_nName);
        pSetting_email = findViewById(R.id.pSetting_email);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        String userNick = jsonObject.getString("userName");
                        String userEmail = jsonObject.getString("userEmail");

                        pSetting_nName.setText(userNick);
                        pSetting_nName.setTextColor(Color.WHITE);

                        pSetting_email.setText(userEmail);
                        pSetting_email.setTextColor(Color.WHITE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Error", "Response Error", e);
                }
            }
        };
        String userID = pref.getValue(RbPreference.PREF_INTRO_USER_AGREEMENT, "default");
        GetUserRequest getUserRequest = new GetUserRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ProfileSettingActivity.this);
        queue.add(getUserRequest);






    } /* onCreate */



//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode){
//            case 10:
//                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                    Toast.makeText(this, "외부 메모리 읽기/쓰기 사용 가능", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(this, "외부 메모리 읽기/쓰기 제한", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//    }

//    public void clickImg(View view) {
//
//        //갤러리 or 사진 앱 실행하여 사진을 선택하도록..
//        Intent intent= new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent,10);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_PICK_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                String path = getPath(uri);
                String name = getName(uri);

                uploadFilePath = path;
                uploadFileName = name;

                Log.i(TAGLOG, "[onActivityResult] uploadFilePath:" + uploadFilePath + ", uploadFileName" + uploadFileName);

                Bitmap bit = BitmapFactory.decodeFile(path);
                imgProfile.setImageBitmap(bit);
            }
        }
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode){
//            case 10:
//                if(resultCode==RESULT_OK){
//                    //선택한 사진의 경로(Uri)객체 얻어오기
//                    Uri uri= data.getData();
//                    if(uri!=null){
//                        imgProfile.setImageURI(uri);
//                    }
//
//                }else
//                {
//                    Toast.makeText(this, "이미지 선택을 하지 않았습니다.", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
    }//onActivityResult() ..

    // 실제 경로 찾기
    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    // 파일명 찾기
    private String getName(Uri uri) {
        String[] projection = {MediaStore.Images.ImageColumns.DISPLAY_NAME};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    // uri 아이디 찾기
    private String getUriId(Uri uri) {
        String[] projection = {MediaStore.Images.ImageColumns._ID};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    // ====
    // 사진을 서버에 전송하기 위한 스레드
    private class UploadImageToServer extends AsyncTask<String, String, String> {
        ProgressDialog mProgressDialog;
        String fileName = uploadFilePath;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 10240 * 10240;
        File sourceFile = new File(uploadFilePath);

        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(ProfileSettingActivity.this);
            mProgressDialog.setTitle("Loading...");
            mProgressDialog.setMessage("Image uploading...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... serverUrl) {
            if (!sourceFile.isFile()) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Log.i(TAGLOG, "[UploadImageToServer] Source File not exist :" + uploadFilePath);
                    }
                });
                return null;
            } else {
                try {
                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL(serverUrl[0]);

                    // Open a HTTP  connection to  the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("uploaded_file", fileName);
                    Log.i(TAGLOG, "fileName: " + fileName);

                    dos = new DataOutputStream(conn.getOutputStream());

                    // 사용자 이름으로 폴더를 생성하기 위해 사용자 이름을 서버로 전송한다.
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"data1\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes("newImage");
                    dos.writeBytes(lineEnd);

                    // 이미지 전송
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\"; filename=\"" + fileName + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);

                    // create a buffer of  maximum size
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    // send multipart form data necesssary after file data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    // Responses from the server (code and message)
                    serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();
                    Log.i(TAGLOG, "[UploadImageToServer] HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                    if (serverResponseCode == 200) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(ProfileSettingActivity.this, "File Upload Completed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    //close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(ProfileSettingActivity.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.i(TAGLOG, "[UploadImageToServer] error: " + ex.getMessage(), ex);
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(ProfileSettingActivity.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.i(TAGLOG, "[UploadImageToServer] Upload file to server Exception Exception : " + e.getMessage(), e);
                }
                Log.i(TAGLOG, "[UploadImageToServer] Finish");

                return null;

            } // End else block
        }

        @Override
        protected void onPostExecute(String s) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgProfile:
               Intent i = new Intent(Intent.ACTION_PICK);
               i.setType(MediaStore.Images.Media.CONTENT_TYPE);
                i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // images on the SD card.
               // Intent intent= new Intent(Intent.ACTION_PICK);
               // intent.setType("image/*");
                //startActivityForResult(intent,10);

                // 결과를 리턴하는 Activity 호출
                startActivityForResult(i, REQ_CODE_PICK_PICTURE);
                break;

            case R.id.btn_set_save:
                if (uploadFilePath != null) {
                    UploadImageToServer uploadimagetoserver = new UploadImageToServer();
                    uploadimagetoserver.execute("http://ci2021flower.dongyangmirae.kr/Upload.php");
                } else {
                    Toast.makeText(ProfileSettingActivity.this, "You didn't insert any image", Toast.LENGTH_SHORT).show();
                }

                break;

        }

    }






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
