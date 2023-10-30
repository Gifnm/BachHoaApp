package ioc.app.bachhoa.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ioc.app.bachhoa.model.DisplayPlatter;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PlatterService {
    String baseURL = "http:192.168.1.6:8083/bachhoa/api/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    PlatterService apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(PlatterService.class);
    @GET("platter/getPlatters/{id}")
    Call<List<DisplayPlatter>> getListFlatter(@Path("id") int id);
}
