package ioc.app.bachhoa.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ioc.app.bachhoa.model.DisplayPlatter;
import ioc.app.bachhoa.ultil.LocalVarible;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PlatterAPI {
    String baseURL = "http:"+ LocalVarible.ip +":8083/bachhoa/api/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    PlatterAPI apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(PlatterAPI.class);

    /**
     * API: Lấy danh sách mâm trưng bày
     *
     * @param id Mã số mâm
     */
    @GET("platter/getPlatters/{id}")
    Call<List<DisplayPlatter>> getListFlatter(@Path("id") int id);
}
