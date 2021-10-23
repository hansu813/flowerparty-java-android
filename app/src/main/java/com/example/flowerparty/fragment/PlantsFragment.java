package com.example.flowerparty.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.flowerparty.GetPlantRequest;
import com.example.flowerparty.R;
import com.example.flowerparty.RbPreference;
import com.example.flowerparty.activity.MainActivity;
import com.example.flowerparty.activity.PlantsNicknameActivity;
import com.example.flowerparty.activity.homeSettingActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class PlantsFragment extends Fragment {
    Fragment plantsManageFragment;
    LinearLayout plantsManage;
    MainActivity mainActivity;
    TextView txt_myplant_name, txt_myplant_nick;
    private RbPreference pref;
    Context ct;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    public static PlantsFragment newInstance() {
        return new PlantsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_plants, container, false);
        ct = container.getContext();
        pref = new RbPreference(ct);


        plantsManage = (LinearLayout) rootView.findViewById(R.id.plantsDetail);
        plantsManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), PlantsManageFragment.class);
//                startActivity(intent);
                ((MainActivity)getActivity()).replaceFragment(PlantsManageFragment.newInstance());

            }
        });

        // 식물 이름, 별명 띄우기
        txt_myplant_name = (TextView)rootView.findViewById(R.id.txt_myplant_name);
        txt_myplant_nick = (TextView)rootView.findViewById(R.id.txt_myplant_nick);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        String plantName = jsonObject.getString("plantName");
                        String plantNick = jsonObject.getString("plantNick");

                        txt_myplant_name.setText(plantName);
                        txt_myplant_name.setTextColor(Color.WHITE);
                        txt_myplant_nick.setText(plantNick);
                        txt_myplant_nick.setTextColor(Color.WHITE);
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

        return rootView;
    }
}