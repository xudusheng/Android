package cn.com.ihappy.ihappy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import cn.com.ihappy.ihappy.beans.ConfigBean;
import cn.com.ihappy.ihappy.manager.ConfigManager;
import cn.com.ihappy.ihappy.network.RetrofitHelper;
import cn.com.ihappy.ihappy.services.ConfigService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.id_tv_res)
    TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void doGet(View view) {

        ConfigService configService = RetrofitHelper.getConfigApiService();
        Call<ConfigBean> configBeanCall = configService.fetchConfigData();
        configBeanCall.enqueue(new Callback<ConfigBean>() {
            @Override
            public void onResponse(Call<ConfigBean> call, Response<ConfigBean> response) {
                L.e("=======" + response.message());
                ConfigBean configBean = response.body();
                ConfigManager.shareManager().setConfigBean(configBean);

            }

            @Override
            public void onFailure(Call<ConfigBean> call, Throwable t) {
                L.e("onFailure");
            }
        });
    }
}
