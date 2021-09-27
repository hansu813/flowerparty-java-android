package com.example.flowerparty;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class NetworkThread extends Thread{
    @Override
    public void run() {
        try {
            StringBuilder urlBuilder = new StringBuilder("http://api.nongsaro.go.kr/service/garden/gardenList");
            Log.e("MT_TEST", "urlBuilder");
            urlBuilder.append("?" + URLEncoder.encode("apiKey", "UTF-8") + "=20210908LGXOY6G03MU6JAYF22EEQ");
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=1");
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection)  url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            Log.e("PLANT_API_TEST", sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
