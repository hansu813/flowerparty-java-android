package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.flowerparty.NetworkThread;
import com.example.flowerparty.PlantItem;
import com.example.flowerparty.R;
import com.example.flowerparty.RegisterActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class PlantsChooseActivity extends AppCompatActivity {

    // json 관련
    private static String TAG = "phptest_PlantChooseActivity";
    private static final String TAG_JSON="webnautes";
    private static final String TAG_ID = "idx";
    private static final String TAG_NO = "cntntsNo";
    private static final String TAG_NAME = "cntntsSj";

    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;
    Button btnSelect;





    private NetworkThread thread;
    //String apiKey = "20210908LGXOY6G03MU6JAYF22EEQ";
    String address = "http://api.nongsaro.go.kr/service/garden/gardenList?numOfRows=2&apiKey=20210908LGXOY6G03MU6JAYF22EEQ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants_choose_list);

        //mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mlistView = (ListView) findViewById(R.id.listVIew_main_list);
        btnSelect = findViewById(R.id.btn_plant_select);
        mArrayList = new ArrayList<>();

        GetData task = new GetData();
        task.execute("http://flowerparty.dothome.co.kr/PlantJson.php");

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlantsChooseActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }



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
            //mTextViewResult.setText(s);
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

                String idx = item.getString(TAG_ID);
                String no = item.getString(TAG_NO);
                String name = item.getString(TAG_NAME);

                HashMap<String, String>hashMap = new HashMap<>();

                hashMap.put(TAG_ID, idx);
                hashMap.put(TAG_NO, no);
                hashMap.put(TAG_NAME, name);

                mArrayList.add(hashMap);
            }
            ListAdapter adapter = new SimpleAdapter(
              PlantsChooseActivity.this, mArrayList, R.layout.listview_item,
                    new String[]{TAG_ID, TAG_NAME, TAG_NO},
            new int[]{R.id.textView_list_id, R.id.textView_list_name, R.id.textView_list_no}
            );
            mlistView.setAdapter(adapter);
        } catch (JSONException e) {
            Log.d(TAG, "showResult: ", e);
        }
    }
        // textPlant = findViewById(R.id.txtPlant); // test
        //textPlant1 = findViewById(R.id.textPlant1);
        //new GetXMLTask().execute();




        // adapt data
        //mPlantItems = new ArrayList<>();
//        for ( int i = 1; i <= 10; i++ ) {
//               mPlantItems.add(new PlantItem("번째"));
//
//        }*/

/*
        new Thread(new Runnable() {
            @Override
            public void run() {
                txtPlant = getPlantData();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //mPlantItems.add(new PlantItem(txtPlant));
                        textPlant1.setText(txtPlant);
          //              Log.e("Error", "리스트값" + mPlantItems);
                    }

                });
            }
        }).start();*/

        //myRecyclerAdapter.setPlantList(mPlantItems);

    }

  /*  String getPlantData() {
        StringBuffer buffer = new StringBuffer();

        String apiKey = "20210908LGXOY6G03MU6JAYF22EEQ";
        String numOfRows = "2";

        String queryUrl = "http://api.nongsaro.go.kr/service/garden/gardenList?apikey="
                + apiKey + "&numOfRows=" + numOfRows;

        try {
            URL url = new URL(queryUrl); // 문자열로 된 요청 url 을 URL 객체로 생성
            InputStream is = url.openStream(); // url 위치로 입력 스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); // inputStream 으로부터 xml 입력 받기

            String tag;
            int i = 0;
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); // 태그이름 얻어오기

                        if (tag.equals("item")); // 첫번째 검색 결과
                        else if (tag.equals("cntntsSj")) {
                            i++;
                            if (i == 1) {
                                buffer.append("식물");
                                xpp.next();
                                buffer.append(xpp.getText());
                                buffer.append("\n");
                            }
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;



                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); // 태그 이름 얻어오기
                        if (tag.equals("item")) buffer.append("\n");
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("Error", "Response Error", e);
        }



        return buffer.toString(); // StringBuffer 문자열 객체 반환
    }*/






/*
    private class GetXMLTask extends AsyncTask<String, Void, Document> {
        Document doc;

        String apiKey = "20210908LGXOY6G03MU6JAYF22EEQ";
        String numOfRows = "2";

        String queryUrl = "http://api.nongsaro.go.kr/service/garden/gardenList?apikey="
                + apiKey + "&numOfRows=" + numOfRows;


        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            int pageNo = 217;
            try {
                url = new URL("http://api.nongsaro.go.kr/service/garden/gardenList?apiKey=20210908LGXOY6G03MU6JAYF22EEQ"+ "&numOfRows=" + pageNo);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();
                Log.e("query", queryUrl);

            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {

            String s = "";
            NodeList nodeList = doc.getElementsByTagName("item");

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;

               // NodeList cntntsNo = fstElmnt.getElementsByTagName("cntntsNo");
                //s += "cntntsNo = " + cntntsNo.item(0).getChildNodes().item(0).getNodeValue() + "\n";

                NodeList cntntsSj = fstElmnt.getElementsByTagName("cntntsSj");
                s += "cntntsSj = " + cntntsSj.item(0).getChildNodes().item(0).getNodeValue() + "\n";


                textPlant1.setText(s);

                super.onPostExecute(doc);
            }
        }

    }*/
