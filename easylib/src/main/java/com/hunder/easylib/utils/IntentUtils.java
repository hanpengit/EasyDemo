package com.hunder.easylib.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by hp on 2020/6/13.
 */

public class IntentUtils {

    public static void startActivity(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

}
