package com.example.flowerparty;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UserChangeHavePlantRequest extends StringRequest {
    // Setting Server URL (php)
    final static private String URL = "http://ci2021flower.dongyangmirae.kr/UserChangeHavePlant.php";
    private Map<String, String> map;

    public UserChangeHavePlantRequest(String userID, String havePlant, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("havePlant", havePlant);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
