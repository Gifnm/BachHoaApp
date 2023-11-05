package ioc.app.bachhoa.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ioc.app.bachhoa.model.Store;
import ioc.app.bachhoa.ultil.LocalVarible;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Part;

public interface DeliveryNoteAPI {
    String baseURL = "http:" + LocalVarible.ip + ":8083/bachhoa/api/deliverynotapi/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    DeliveryNoteAPI apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(DeliveryNoteAPI.class);

    /**
     * API: Lấy danh sách phiệu nhập tại cửa hàng
     * Bao gồm các phiếu nhập chưa hoàn tất
     *
     * @param store Object Cửa hàng
     */
    @GET("getall")
    Call<List<DeliveryNoteAPI>> getByStore(@Part("store") Store store);

}
