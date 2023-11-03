package ioc.app.bachhoa.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ioc.app.bachhoa.model.ShipmentBatch;
import ioc.app.bachhoa.model.ShipmentBatchDetail;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ShipmentBatchAPI {
    String baseURL = "http:192.168.1.7:8083/bachhoa/api/shibat/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ShipmentBatchAPI apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(ShipmentBatchAPI.class);

    // Danh sach cac API
    // 1. Tao mọt ShipmentBatch
    // 2. Lay danh sach ShipmentBatch
    // 3. Chuyển đổi trạng thái bàn giao ca
    // 1. Tao mọt ShipmentBatch
    @POST("insert")
    Call<Void> insert(@Part("ShipmentBatch") ShipmentBatch shipmentBatch, @Part("ShipmentBatchDetail") List<ShipmentBatchDetail> list);

    // 2. Lay danh sach ShipmentBatch
    @GET("findALL/{storeID}")
    Call<List<ShipmentBatch>> getByStoreID(@Path("storeID") int storeID);

    // 3. Chuyển đổi trạng thái bàn giao ca
    @PUT("setStatus/{shiBatID}")
    Call<Void> setStatus(@Path("shiBatID") String shiBatID);

    Call<Void> checkScan(String contents, int storeID);
}
