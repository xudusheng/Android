package cn.com.ihappy.ihappy.services;

import cn.com.ihappy.ihappy.beans.SubMenuBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ResponseService {

    @GET("/toutiao/index")
    Call<SubMenuBean.ResponseBean> getDataFromNet(@Query("key") String key);

//    @GET("/toutiao/index")
//    Observable<ResponseBean> getDataFromNet(@Query("key") String key);
}
