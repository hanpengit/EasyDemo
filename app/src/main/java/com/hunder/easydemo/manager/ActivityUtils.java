package com.hunder.easydemo.manager;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by hp on 2016/11/9.
 */
public class ActivityUtils {

    public static Map<String, Activity> activityMap = new HashMap<>();

    public static void addActivity(Activity activity) {
        if (activity != null && !activityMap.containsKey(activity.getClass().getSimpleName())) {
            activityMap.put(activity.getClass().getSimpleName(), activity);
        }
    }

    public static void removeActivity(Activity activity) {
        if (activityMap.containsKey(activity.getClass().getSimpleName())) {
            activityMap.remove(activity.getClass().getSimpleName());
        }
    }

    public static void finishAll() {
        Set<String> keySet = activityMap.keySet();
        for (String key : keySet) {
            if (!isActivityfinished(key)) {
                activityMap.get(key).finish();
            }
        }
    }

    public static boolean isActivityfinished(Class clazz) {
        return isActivityfinished(clazz.getSimpleName());
    }

    private static boolean isActivityfinished(String activitySimpleName) {
        if (activityMap.get(activitySimpleName) != null) {
            return activityMap.get(activitySimpleName).isFinishing();
        }
        return true;
    }

    public static Activity getActivity(Class clazz) {
        return getActivity(clazz.getSimpleName());
    }

    public static Activity getActivity(String simpleName) {
        if (activityMap.containsKey(simpleName)) {
            return activityMap.get(simpleName);
        }
        return null;
    }

    public static void finishActivity(Class clazz) {
        if (getActivity(clazz) != null && !isActivityfinished(clazz)) {
            getActivity(clazz).finish();
        }
    }

}