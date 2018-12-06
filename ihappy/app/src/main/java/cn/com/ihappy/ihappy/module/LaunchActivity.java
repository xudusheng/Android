package cn.com.ihappy.ihappy.module;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.util.concurrent.TimeUnit;

import cn.com.ihappy.ihappy.L;
import cn.com.ihappy.ihappy.MainActivity;
import cn.com.ihappy.ihappy.R;
import cn.com.ihappy.ihappy.beans.ConfigBean;
import cn.com.ihappy.ihappy.manager.ConfigManager;
import cn.com.ihappy.ihappy.network.RetrofitHelper;
import cn.com.ihappy.ihappy.services.ConfigService;
import cn.com.ihappy.ihappy.utils.SystemUiVisibilityUtil;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        SystemUiVisibilityUtil.hideStatusBar(getWindow(), true);

        this.fetchCongiData();
    }

    /**
     * 加载初始化文件
     */
    private void fetchCongiData() {
        ConfigService configService = RetrofitHelper.getConfigApiService();
        Call<ConfigBean> configBeanCall = configService.fetchConfigData();
        configBeanCall.enqueue(new Callback<ConfigBean>() {
            @Override
            public void onResponse(Call<ConfigBean> call, Response<ConfigBean> response) {
                L.e("=======" + response.message());
                ConfigBean configBean = response.body();
                ConfigManager.shareManager().setConfigBean(configBean);
                addTimeCounter();
            }

            @Override
            public void onFailure(Call<ConfigBean> call, Throwable t) {
                L.e("onFailure");
            }
        });
    }

    private void addTimeCounter() {
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Long aLong) {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        L.e("=====onComplete");
                        finishTask();
                    }
                });
    }


    private void finishTask() {
        Intent mainIntent = new Intent(LaunchActivity.this, MainActivity.class);
        startActivity(mainIntent);
        LaunchActivity.this.finish();
    }
}
