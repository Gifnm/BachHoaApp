package ioc.app.bachhoa.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ioc.app.bachhoa.model.DisplayShelves;
import ioc.app.bachhoa.ultil.LocalVarible;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ShelfAPI {
    String baseURL = "http:" + LocalVarible.ip + ":" + LocalVarible.serverPort + "/bachhoa/api/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ShelfAPI apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(ShelfAPI.class);

    /**
     * API: Lấy danh sách kệ trưng bày
     *
     * @param id Mã số kệ
     */
    @GET("findAllShelf/{id}")
    Call<List<DisplayShelves>> getshelfs(@Path("id") int id);
}
