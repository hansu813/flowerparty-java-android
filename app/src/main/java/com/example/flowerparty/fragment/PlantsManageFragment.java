package com.example.flowerparty.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.flowerparty.GetPlantRequest;
import com.example.flowerparty.R;
import com.example.flowerparty.RbPreference;
import com.example.flowerparty.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class PlantsManageFragment extends Fragment {
    TextView txt_myplantM_name,txt_myplantM_name2, txt_myplantM_nick;
    TextView txt_myplant_dtl;

    MainActivity mainActivity;
    private RbPreference pref;
    Context ct;

    String apiKey = "20210908LGXOY6G03MU6JAYF22EEQ";
    String data;

    public static PlantsManageFragment newInstance() {
        return new PlantsManageFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_plants_manage, container, false);
        ct = container.getContext();
        pref = new RbPreference(ct);

        // 식물 이름, 별명 띄우기
        txt_myplantM_name = (TextView) rootView.findViewById(R.id.txt_myplantM_name);
        txt_myplantM_name2 = (TextView) rootView.findViewById(R.id.txt_myplantM_name2);
        txt_myplantM_nick = (TextView) rootView.findViewById(R.id.txt_myplantM_nick);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        String plantName = jsonObject.getString("plantName");
                        String plantNick = jsonObject.getString("plantNick");
                        String plantNo = jsonObject.getString("plantNo");

                        txt_myplantM_name.setText(plantName);
                        txt_myplantM_name.setTextColor(Color.WHITE);
                        txt_myplantM_name2.setText(plantName);
                        txt_myplantM_name2.setTextColor(Color.WHITE);
                        txt_myplantM_nick.setText(plantNick);
                        txt_myplantM_nick.setTextColor(Color.WHITE);

                        pref = new RbPreference(ct);
                        pref.putPlantNo(RbPreference.PREF_NO_VALUE, plantNo);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                    Log.e("Error", "Response Error", e);
                }
            }
        };
        String userID = pref.getValue(RbPreference.PREF_INTRO_USER_AGREEMENT, "default");
        GetPlantRequest getPlantRequest = new GetPlantRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ct);
        queue.add(getPlantRequest);

        txt_myplant_dtl = (TextView) rootView.findViewById(R.id.txt_myplant_dtl);
        new Thread(new Runnable() {
            @Override
            public void run() {
                data=getXmlData();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txt_myplant_dtl.setText(data);
                    }
                });
            }
        }).start();

        return rootView;
    }

    String getXmlData(){
        StringBuffer buffer=new StringBuffer();
        String str= txt_myplantM_name.getText().toString();// 작성된 Text얻어오기
        String location = URLEncoder.encode(str);
        String query="%EC%A0%84%EB%A0%A5%EB%A1%9C";
        String cntntsNo = pref.getValue(RbPreference.PREF_NO_VALUE, "default");
        System.out.println(cntntsNo);

        String address = "http://api.nongsaro.go.kr/service/garden/gardenDtl?cntntsNo="+cntntsNo+"&apiKey=20210908LGXOY6G03MU6JAYF22EEQ";
        //String queryUrl="http://openapi.kepco.co.kr/service/EvInfoServiceV2/getEvSearchList?addr="+location+"&pageNo=1&numOfRows=10&ServiceKey=자신의 서비스키";
        try{
            URL url= new URL(address);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();//xml파싱을 위한
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("plntbneNm")){
                            buffer.append("식물학 명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("adviseInfo")){
                            buffer.append("*** ");
                            xpp.next();
                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append(" ***");
                            buffer.append("\n\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("distbNm")){
                            buffer.append("유통명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n");//줄바꿈 문자 추가
                        }

                        else if(tag.equals("fmlCodeNm")){
                            buffer.append("과 명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//telephone 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("orgplceInfo")){
                            buffer.append("원산지 정보 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//address 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("frtlzrInfo")){
                            buffer.append("비료 정보 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//mapx 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("hdCodeNm")){
                            buffer.append("습도 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("soilInfo")){
                            buffer.append("토양 정보 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("watercycleSprngCodeNm")){
                            buffer.append("물주기(봄) : ");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("watercycleSummerCodeNm")){
                            buffer.append("물주기(여름) : ");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("watercycleAutumnCodeNm")){
                            buffer.append("물주기(가을) : ");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("watercycleWinterCodeNm")){
                            buffer.append("물주기(겨울) : ");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("speclmanageInfo")){
                            buffer.append("특별 관리 정보 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("fncltyInfo")){
                            buffer.append("기능성 정보 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("grwhTpCodeNm")){
                            buffer.append("생육 온도 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("winterLwetTpCodeNm")){
                            buffer.append("겨울 최저 온도 코드명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("toxctyInfo")){
                            buffer.append("독성정보 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n"); //줄바꿈 문자 추가
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        //buffer.append("파싱 끝\n");
        return buffer.toString();//StringBuffer 문자열 객체 반환

    }//getXmlData method....



}