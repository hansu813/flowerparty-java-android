package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.flowerparty.AddPlantRequest;
import com.example.flowerparty.NetworkThread;
import com.example.flowerparty.R;
import com.example.flowerparty.RbPreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class PlantsChooseActivity extends AppCompatActivity {
    // json
    private static String TAG = "phptest_PlantChooseActivity";
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_ID = "idx";
    private static final String TAG_NO = "cntntsNo";
    private static final String TAG_NAME = "cntntsSj";

    // list
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;

    // button
    Button btnSelect;


    //private NetworkThread thread;
    //String apiKey = "20210908LGXOY6G03MU6JAYF22EEQ";
    //String address = "http://api.nongsaro.go.kr/service/garden/gardenList?numOfRows=2&apiKey=20210908LGXOY6G03MU6JAYF22EEQ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants_choose_list);

        mlistView = (ListView) findViewById(R.id.listVIew_main_list);
        btnSelect = findViewById(R.id.btn_plant_select);
        mArrayList = new ArrayList<>();

        // intent 로 아이디 수신
        /*Intent intent = getIntent();
        String userID = intent.getExtras().getString("userID");*/

        // 로그인 정보 (사용자 아이디)
        RbPreference pref = new RbPreference(PlantsChooseActivity.this);
        String userId = pref.getValue(RbPreference.PREF_INTRO_USER_AGREEMENT, "default");



        // Json 데이터 리스트에 보여줌 GetData()
        GetData task = new GetData();
        task.execute("http://ci2021flower.dongyangmirae.kr/PlantJson.php");



        // 항목 선택 없이 Select 버튼 클릭시 시
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PlantsChooseActivity.this, "식물을 선택하세요.", Toast.LENGTH_SHORT).show();
            }
        });


        // ListView 항목 클릭 시
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int check_position = mlistView.getCheckedItemPosition();   //리스트뷰의 포지션을 가져옴.
                Object vo = (Object) parent.getAdapter().getItem(position); // 리스트뷰의 내용을 가져옴.
                Toast.makeText(PlantsChooseActivity.this, vo.toString(), Toast.LENGTH_SHORT).show();

                // 항목 선택 후 select 버튼 클릭 시
                btnSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if (success) { // 식물 저장
                                        Toast.makeText(getApplicationContext(), "식물이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(PlantsChooseActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "식물 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e("Error", "Response Error", e);
                                }
                            }
                        };
                        // 선택한 포지션의 리스트뷰의 내용: vo && RbPreference 에서 가져온 userId 를 넘겨줌
                        AddPlantRequest addPlantRequest = new AddPlantRequest(vo.toString(), userId, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(PlantsChooseActivity.this);
                        queue.add(addPlantRequest);
                    }
                });
            }
        });

    }

    // Json 데이터 가져오기
    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(PlantsChooseActivity.this, "Please wail", null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressDialog.dismiss();
            Log.d(TAG, "response - " + s);
            mJsonString = s;
            showResult();
        }

        @Override
        protected String doInBackground(String... strings) {
            String serverURL = strings[0];

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error", e);
                errorString = e.toString();

                return null;
            }
        }
    }

    private void showResult() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                //String idx = item.getString(TAG_ID);
                String no = item.getString(TAG_NO);
                String name = item.getString(TAG_NAME);

                HashMap<String, String> hashMap = new HashMap<>();

                //hashMap.put(TAG_ID, idx);
                hashMap.put(TAG_NO, no);
                hashMap.put(TAG_NAME, name);

                mArrayList.add(hashMap);
            }
            ListAdapter adapter = new SimpleAdapter(
                    PlantsChooseActivity.this, mArrayList, R.layout.listview_item,
                    new String[]{TAG_NAME, TAG_NO},
                    new int[]{R.id.textView_list_name, R.id.textView_list_no}
            );
            mlistView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            mlistView.setAdapter(adapter);


        } catch (JSONException e) {
            Log.d(TAG, "showResult: ", e);
        }
    }

}
