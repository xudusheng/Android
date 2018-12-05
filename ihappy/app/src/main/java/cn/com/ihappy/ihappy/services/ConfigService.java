package cn.com.ihappy.ihappy.services;


import cn.com.ihappy.ihappy.beans.ConfigBean;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ConfigService {
    /**
     * 配置文件初始化
     */
    @GET("config/menu_1.0.3.json")
    Call<ConfigBean> fetchConfigData();

}
