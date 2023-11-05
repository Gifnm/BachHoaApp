package ioc.app.bachhoa.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ioc.app.bachhoa.ultil.LocalVarible;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface PurchaseHistoryAPI {
    String baseURL = "http:"+ LocalVarible.ip +":8083/bachhoa/api/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    PurchaseHistoryAPI apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(PurchaseHistoryAPI.class);

}
