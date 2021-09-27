package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.flowerparty.NetworkThread;
import com.example.flowerparty.PlantItem;
import com.example.flowerparty.MyRecyclerAdapter;
import com.example.flowerparty.R;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class PlantsChooseActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter myRecyclerAdapter;
    private ArrayList<PlantItem> mPlantItems;
    String txtPlant;
    TextView textPlant, textPlant1;




    private NetworkThread thread;
    //String apiKey = "20210908LGXOY6G03MU6JAYF22EEQ";
    String address = "http://api.nongsaro.go.kr/service/garden/gardenList?numOfRows=2&apiKey=20210908LGXOY6G03MU6JAYF22EEQ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants_choose);

        textPlant = findViewById(R.id.txtPlant); // test
        textPlant1 = findViewById(R.id.textPlant1);
        new GetXMLTask().execute();

  /*      //mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // initiate adapter
        //myRecyclerAdapter = new MyRecyclerAdapter();

        // initiate recyclerview
        //mRecyclerView.setAdapter(myRecyclerAdapter);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // adapt data
        mPlantItems = new ArrayList<>();
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

    }
}