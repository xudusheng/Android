package cn.com.ihappy.ihappy.network;

import cn.com.ihappy.ihappy.services.ConfigService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    static {
    }


    public static ConfigService getConfigApiService() {
        return createApi(ConfigService.class, NetApi.IHAPPY_CONFIG_FILE_URL);
    }

    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    private static <T> T createApi(Class<T> clazz, String baseUrl) {
        //配置BaseUrl
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
}
