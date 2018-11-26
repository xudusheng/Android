package com.youmi.meizi;

import android.util.Log;

public class L {
    private static boolean debug = true;
    private static String Tag = "Meizi";

    public static void e(String msg) {
        if (debug) {
            Log.e(Tag, msg);
        }
    }
}
