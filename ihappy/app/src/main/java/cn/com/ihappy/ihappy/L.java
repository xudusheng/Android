package cn.com.ihappy.ihappy;

import android.util.Log;

public class L {
    private static boolean DEBUG = true;
    private static String TAG = "ihappy";

    public static void e(String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
        }
    }
}
