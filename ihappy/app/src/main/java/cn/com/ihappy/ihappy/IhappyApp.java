package cn.com.ihappy.ihappy;

import android.app.Application;

public class IhappyApp extends Application {
    public static IhappyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();
    }

    private void init() {}

    public static IhappyApp getInstance() {
        return mInstance;
    }
}
