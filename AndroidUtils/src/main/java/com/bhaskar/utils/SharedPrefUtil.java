package com.bhaskar.utils;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by saurabh_jain on 10/11/16.
 */
public class SharedPrefUtil {

    private static final String TAG = SharedPrefUtil.class.getSimpleName();

    /**
     * @param sp
     * @param key
     * @return
     */
    public static boolean isKeyAvailable(SharedPreferences sp, String key) {
        return sp.contains(key);
    }

    /**
     * @param sp
     * @param key
     * @param value
     */
    public static void addString(SharedPreferences sp, String key, String value) {
        if (sp != null) {
            SharedPreferences.Editor ed = sp.edit();
            ed.putString(key, value);
            ed.commit();
        }
    }

    public static String getString(SharedPreferences sp, String key, String defaultValue) {

        if (sp != null) {
            return sp.getString(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * @param sp
     * @param key
     * @param inputMap
     */
    public static void addHashMap(SharedPreferences sp, String key, Map<String, String> inputMap) {

        JSONObject jsonObject = new JSONObject(inputMap);
        String jsonString = jsonObject.toString();
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(key, jsonString);
        ed.apply();
    }

    /**
     * @param sp
     * @param key
     * @return
     */
    public static Map<String, String> getHashMap(SharedPreferences sp, String key) {
        Map<String, String> outputMap = new HashMap<String, String>();

        String jsonString = sp.getString(key, (new JSONObject()).toString());
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> keysItr = jsonObject.keys();
        while (keysItr.hasNext()) {
            String k = keysItr.next();
            String v = null;
            try {
                v = (String) jsonObject.get(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            outputMap.put(k, v);
        }
        return outputMap;
    }

    /**
     * @param sp
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(SharedPreferences sp, String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    /**
     * @param sp
     * @param key
     * @param value
     */
    public static void addInt(SharedPreferences sp, String key, int value) {
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt(key, value);
        ed.commit();

    }

    /**
     * @param sp
     * @param key
     * @param defaultValue
     * @return
     */
    public static long getLong(SharedPreferences sp, String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    /**
     * @param sp
     * @param key
     * @param value
     */
    public static void addLong(SharedPreferences sp, String key, long value) {
        SharedPreferences.Editor ed = sp.edit();
        ed.putLong(key, value);
        ed.commit();

    }

    /**
     * @param sp
     * @param key
     * @param value
     */
    public static void addBoolean(SharedPreferences sp, String key, boolean value) {
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean(key, value);
        ed.commit();
    }

    /***
     *
     * @param sp
     * @param key
     */
    public static void removeKeyValue(SharedPreferences sp, String key) {
        SharedPreferences.Editor ed = sp.edit();
        ed.remove(key);
        ed.apply();
    }

    /**
     * @param sp
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(SharedPreferences sp, String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * @param sp
     * @param key
     * @param object
     * @param objectClass
     */
    public static void addObjectInGsonString(SharedPreferences sp, String key, Object object, Class objectClass) {
        if (object != null) {
            String gsonString = new GsonBuilder().create().toJson(object, objectClass);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString(key, gsonString);
            ed.apply();
        }
    }

    /**
     * @param sp
     * @param key
     * @param object
     * @param objectClass
     */
    public static void addCampaignObjectInGsonString(SharedPreferences sp, String key, Object object, Class objectClass) {
        try {
            String gsonString = new GsonBuilder().create().toJson(object, objectClass);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString(key, gsonString);
            ed.apply();
        } catch (Exception e) {
            Log.e(TAG, "addCampaignObjectInGsonString: " + e.getMessage());
        }
    }


    /**
     * @param sp
     * @param key
     * @param object
     * @param objectClass
     */
    public static void addObjectInGsonStringCommit(SharedPreferences sp, String key, Object object, Class objectClass) {
        if (object != null) {
            String gsonString = new GsonBuilder().create().toJson(object, objectClass);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString(key, gsonString);
            ed.commit();
        }
    }

    /**
     * @param sp
     * @param key
     * @param objectClass
     * @param <T>
     * @return
     */
    public static <T extends Object> T getObjectFromGsonString(SharedPreferences sp, String key, Class<T> objectClass) {
        try {
            String gsonString = sp.getString(key, "");
            if (TextUtils.isEmpty(gsonString)) {
                return null;
            } else {
                return new GsonBuilder().create().fromJson(gsonString, objectClass);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() + "");
            return null;
        }
    }

    /**
     * @param sp
     */
    public static void clearSharedPreference(SharedPreferences sp) {
        sp.edit().clear().apply();
    }
}