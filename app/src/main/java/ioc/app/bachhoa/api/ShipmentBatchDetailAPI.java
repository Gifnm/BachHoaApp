package ioc.app.bachhoa.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ioc.app.bachhoa.DTOEntity.ShipmentBacthDTO;
import ioc.app.bachhoa.model.ProductPositioning;
import ioc.app.bachhoa.model.ShipmentBatchDetail;
import ioc.app.bachhoa.ultil.LocalVarible;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ShipmentBatchDetailAPI {
    String baseURL = "http:"+ LocalVarible.ip +":8083/bachhoa/api/shibatdet";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ShipmentBatchDetailAPI apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(ShipmentBatchDetailAPI.class);

    //1. Lấy danh sách ShipmentBatchDetail theo Ke
    // 2. Lấy danh sách ShipmentBatchDTO theo shiBatID
    @GET("getByshiBatID/{shiBatID}")
    Call<List<ShipmentBacthDTO>> getByshiBatID(@Path("shiBatID") String shiBatID);

    /**
     * API: Chuyển trạng thái sản phẩm châm
     *
     * @param shipmentBatchDetail Đối tượng
     */
    @PUT("setStatus")
    Call<Void> setStatus(@Part("ShipmentBatchDetail") ShipmentBatchDetail shipmentBatchDetail);

    /**
     * API: Kiểm tra sản phẩm đã có trong đợt châm hàng khác hay chưa
     *
     * @param productID Mã sản phẩm
     * @param storeID   Mã cửa hàng
     */
    @GET("check/{productID}/{storeID}")
    Call<ProductPositioning> checkScan(@Path("productID") String productID, @Path("storeID") int storeID);
}
