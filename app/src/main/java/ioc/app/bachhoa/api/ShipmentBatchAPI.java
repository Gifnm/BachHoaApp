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
    String baseURL = "http:192.168.1.3:8083/bachhoa/api/shibat/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ShipmentBatchAPI apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(ShipmentBatchAPI.class);

    /**
     * API: Tạo một đợt châm hàng
     *
     * @param shipmentBatch Đợt châm hàng - Object
     * @param list          Danh sách đợt châm hàng chi tiết - List Object
     */
    @POST("insert")
    Call<Void> insert(@Part("ShipmentBatch") ShipmentBatch shipmentBatch, @Part("ShipmentBatchDetail") List<ShipmentBatchDetail> list);

    /**
     * API: Lấy danh sách đợt châm hàng
     *
     * @param storeID Mã cửa hàng
     */
    @GET("findALL/{storeID}")
    Call<List<ShipmentBatch>> getByStoreID(@Path("storeID") int storeID);

    /**
     * API: Chuyển trạng thái đợt châm hàng
     *
     * @param shiBatID Mã đợt châm hàng
     */
    @PUT("setStatus/{shiBatID}")
    Call<Void> setStatus(@Path("shiBatID") String shiBatID);

    Call<Void> checkScan(String contents, int storeID);
}
