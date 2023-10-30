package ioc.app.bachhoa.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ioc.app.bachhoa.DTOEntity.ShipmentBacthDTO;
import ioc.app.bachhoa.model.ProductPositioning;
import ioc.app.bachhoa.model.ShipmentBatchDetail;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ShipmentBatchDetailAPI {
    String baseURL = "http:192.168.1.6:8083/bachhoa/api/shipbatdet/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ShipmentBatchDetailAPI apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(ShipmentBatchDetailAPI.class);

    //1. Lấy danh sách ShipmentBatchDetail theo Ke
    // 2. Lấy danh sách ShipmentBatchDTO theo shiBatID
    @GET("getByshiBatID/{shiBatID}")
    Call<List<ShipmentBacthDTO>> getByshiBatID(@Path("shiBatID") String shiBatID);

    // 3. Lấy danh sách ShipmentBatchDTO theo storeID
//    @GET("getByStoreID/{storeID}")
//    Call<List<ShipmentBacthDTO>> getBystoreID(@Path("storeID") int storeID);

    // 3. Chuyển trạng thái
    @PUT("setStatus")
    Call<Void> setStatus(@Part("ShipmentBatchDetail") ShipmentBatchDetail shipmentBatchDetail);

    // 4. Check san pham

    @GET("check/{productID}/{storeID}")
    Call<ProductPositioning> checkScan(@Path("productID") String productID, @Path("storeID") int storeID);
}
