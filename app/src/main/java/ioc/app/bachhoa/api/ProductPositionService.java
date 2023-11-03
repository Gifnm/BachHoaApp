package ioc.app.bachhoa.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ioc.app.bachhoa.model.ProductPositioning;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductPositionService {
    String baseURL = "http://192.168.1.6:8083/bachhoa/api/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ProductPositionService apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(ProductPositionService.class);

    /**
     * API: Lấy vị trí sản phẩm tại cửa hàng
     *
     * @param id      Mã sản phẩm
     * @param storeID Mã của hàng
     */
    @GET("productPosition/findByID/{id}/{storeID}")
    Call<ProductPositioning> findByProductID(@Path("id") String id, @Path("storeID") int storeID);

    /**
     * API: Lấy danh sách vị trí tới đơn vị mâm
     *
     * @param shelfID   Mã số kệ
     * @param platterID Mã số mâm
     * @param storeID   Mã cửa hàng
     */
    @GET("productPositioning/{shelfID}/{platterNb}/{storeID}")
    Call<List<ProductPositioning>> getLitsProductPoiton(@Path("shelfID") int shelfID, @Path("platterNb") int platterID, @Path("storeID") int storeID);


    /**
     * API: Thêm một vị trí trưng bày mới
     *
     * @param productPositioning Đối tượng vị trí - model
     */
    @POST("productPositioning/insert")
    Call<ProductPositioning> insertProPosion(@Body ProductPositioning productPositioning);

    /**
     * API: Lấy danh sách vị trí theo đơn vị kệ
     *
     * @param storeID  Mã cửa hàng
     * @param disSheID Mã số kệ
     */
    @GET("getPosByStoreAndShelf/{storeID}/{disSheID}")
    Call<List<ProductPositioning>> getPosByStoreAndShelf(@Path("storeID") int storeID, @Path("disSheID") int disSheID);

}
