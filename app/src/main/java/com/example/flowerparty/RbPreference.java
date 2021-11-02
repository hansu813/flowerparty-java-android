package com.example.flowerparty;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


// SharedPreferences 를 생성(불러옴) - Editor를 통해서 파일에 접근, 값을 넣거나, 가져오거나, 삭제  -  commit으로 반영
public class RbPreference {

    private final String PREF_NAME = "com.rabiaband.pref";

    public final static String PREF_INTRO_USER_AGREEMENT = "PREF_USER_AGREEMENT";
    public final static String PREF_MAIN_VALUE = "PREF_MAIN_VALUE";
    public final static String PREF_SUB_VALUE = "PREF_SUB_VALUE";
    public final static String PREF_NO_VALUE = "PREF_NO_VALUE";
    public final static String PREF_PASS_VALUE = "PREF_PASS_VALUE";

    static Context mContext;

    public RbPreference(Context c) {
        mContext = c;
    }

    public void putId(String key, String value) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE); // 접근 권한: 해당 앱에서만 접근 가능하게 Mode_Private
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(key, value); // 저장하는 값을 나타내는 키 값, 저장할 값
        editor.commit();
    }
    public void putEmail(String key, String value) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE); // 접근 권한: 해당 앱에서만 접근 가능하게 Mode_Private
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(key, value); // 저장하는 값을 나타내는 키 값, 저장할 값
        editor.commit();
    }
    public void putName(String key, String value) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE); // 접근 권한: 해당 앱에서만 접근 가능하게 Mode_Private
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(key, value); // 저장하는 값을 나타내는 키 값, 저장할 값
        editor.commit();
    }
    public void putPlantNo(String key, String value) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE); // 접근 권한: 해당 앱에서만 접근 가능하게 Mode_Private
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(key, value); // 저장하는 값을 나타내는 키 값, 저장할 값
        editor.commit();
    }

    public void putPass(String key, String value) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(key, value);
        editor.commit();
    }

    public void put(String key, boolean value) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(key, value);
        editor.commit();
    }

    public void put(String key, int value) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(key, value);
        editor.commit();
    }

    public String getValue(String key, String dftValue) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);

        try {
            return pref.getString(key, dftValue); // put에서 지정한 키 값, 값이 없을 경우 디폴트 값
        } catch (Exception e) {
            return dftValue;
        }
    }
    public String getEmail(String key, String dftValue) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);

        try {
            return pref.getString(key, dftValue); // put에서 지정한 키 값, 값이 없을 경우 디폴트 값
        } catch (Exception e) {
            return dftValue;
        }
    }


    public int getValue(String key, int dftValue) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);

        try {
            return pref.getInt(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }
    }

    public boolean getValue(String key, boolean dftValue) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);

        try {
            return pref.getBoolean(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }
    }

    // 로그아웃 시 호출
    public void delValue(String key) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }
}
