package com.hunder.easylib.widget;


import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class AppRTCUtils {
    public static void assertIsTrue(boolean paramBoolean) {
        if (!paramBoolean)
            throw new AssertionError("Expected condition to be true");
    }

    public static String getThreadInfo() {
        return "@[name=" + Thread.currentThread().getName() + ", id=" + Thread.currentThread().getId() + "]";
    }

    public static Map<String, Object> jsonToMap(JSONObject paramJSONObject)
            throws JSONException {
        Object localObject = new HashMap();
        if (paramJSONObject != JSONObject.NULL)
            localObject = toMap(paramJSONObject);
        return (Map<String, Object>) localObject;
    }

    public static ConcurrentHashMap<String, Object> jsonToMap1(JSONObject paramJSONObject)
            throws JSONException {
        ConcurrentHashMap localConcurrentHashMap = new ConcurrentHashMap();
        if (paramJSONObject != JSONObject.NULL)
            localConcurrentHashMap = toMap1(paramJSONObject);
        return localConcurrentHashMap;
    }

    public static void logDeviceInfo(String paramString) {
        Log.d(paramString, "Android SDK: " + Build.VERSION.SDK_INT + ", Release: " + Build.VERSION.RELEASE + ", Brand: " + Build.BRAND + ", Device: " + Build.DEVICE + ", Id: " + Build.ID + ", Hardware: " + Build.HARDWARE + ", Manufacturer: " + Build.MANUFACTURER + ", Model: " + Build.MODEL + ", Product: " + Build.PRODUCT);
    }

    public static List<Object> toList(JSONArray paramJSONArray)
            throws JSONException {
        ArrayList localArrayList = new ArrayList();
        int i = 0;
        if (i < paramJSONArray.length()) {
            Object localObject = paramJSONArray.get(i);
            if ((localObject instanceof JSONArray))
                localObject = toList((JSONArray) localObject);
            /*while (true) {
                localArrayList.add(localObject);
                i++;
                break;
                if (!(localObject instanceof JSONObject))
                    continue;
                localObject = toMap((JSONObject) localObject);
            }*/
        }
        return (List<Object>) localArrayList;
    }

    public static Map<String, Object> toMap(JSONObject paramJSONObject)
            throws JSONException {
        HashMap localHashMap = new HashMap();
        Iterator localIterator = paramJSONObject.keys();
        if (localIterator.hasNext()) {
            String str = (String) localIterator.next();
            Object localObject = paramJSONObject.get(str);
            if ((localObject instanceof JSONArray))
                localObject = toList((JSONArray) localObject);
            /*while (true) {
                localHashMap.put(str, localObject);
                break;
                if (!(localObject instanceof JSONObject))
                    continue;
                localObject = toMap((JSONObject) localObject);
            }*/
        }
        return (Map<String, Object>) localHashMap;
    }

    public static ConcurrentHashMap<String, Object> toMap1(JSONObject paramJSONObject)
            throws JSONException {
        ConcurrentHashMap localConcurrentHashMap = new ConcurrentHashMap();
        Iterator localIterator = paramJSONObject.keys();
        if (localIterator.hasNext()) {
            String str = (String) localIterator.next();
            Object localObject = paramJSONObject.get(str);
            if ((localObject instanceof JSONArray))
                localObject = toList((JSONArray) localObject);
            /*while (true) {
                localConcurrentHashMap.put(str, localObject);
                break;
                if (!(localObject instanceof JSONObject))
                    continue;
                localObject = toMap1((JSONObject) localObject);
            }*/
        }
        return (ConcurrentHashMap<String, Object>) localConcurrentHashMap;
    }
}