package com.youmi.meizi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.id_tv_res);
    }

    public void doGet(View view) {
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url("http://gank.io/api/data/福利/10/1").build();


        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("onFailure = " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("onResponse = ");
                final String resStr = response.body().string();
                L.e(resStr);
                Gson gson = new Gson();
                MeiziBean meizi = gson.fromJson(resStr, MeiziBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText(resStr);
                    }
                });
            }
        });
    }
}
