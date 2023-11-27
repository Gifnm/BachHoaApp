package ioc.app.bachhoa.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ioc.app.bachhoa.DTOEntity.PriceTag;
import ioc.app.bachhoa.model.ProductPositioning;
import ioc.app.bachhoa.ultil.LocalVarible;
import ioc.app.bachhoa.ultil.PrintPriceTag;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductPositionAPI {
    String baseURL = "http:" + LocalVarible.ip + ":" + LocalVarible.serverPort + "/bachhoa/api/productPosition/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ProductPositionAPI apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(ProductPositionAPI.class);

    /**
     * API: Lấy vị trí sản phẩm tại cửa hàng
     *
     * @param id      Mã sản phẩm
     * @param storeID Mã của hàng
     */
    @GET("findByID/{id}/{storeID}")
    Call<ProductPositioning> findByProductID(@Path("id") String id, @Path("storeID") int storeID);

    /**
     * API: Lấy danh sách vị trí tới đơn vị mâm
     *
     * @param shelfID   Mã số kệ
     * @param platterID Mã số mâm
     * @param storeID   Mã cửa hàng
     */
    @GET("{shelfID}/{platterNb}/{storeID}")
    Call<List<ProductPositioning>> getLitsProductPoiton(@Path("shelfID") int shelfID, @Path("platterNb") int platterID, @Path("storeID") int storeID);


    /**
     * API: Thêm một vị trí trưng bày mới
     *
     * @param productPositioning Đối tượng vị trí - model
     */
    @POST("insert")
    Call<ProductPositioning> insertProPosion(@Body ProductPositioning productPositioning);

    /**
     * API: Lấy danh sách tem giá theo kệ
     *
     * @param storeID  Mã cửa hàng
     * @param disSheID Mã số kệ
     */
    @GET("getPosByStoreAndShelf/{storeID}/{disSheID}")
    Call<List<PriceTag>> getPosByStoreAndShelf(@Path("storeID") int storeID, @Path("disSheID") int disSheID);

    /**
     * API: Lấy tem giá 1 sản phẩm
     *
     * @param storeID   Mã cửa hàng
     * @param productID Mã sản phẩm - barcode - sku
     */
    @GET("getPriceTag/{storeID}/{productID}")
    Call<PriceTag> getPriceTag(@Path("storeID") int storeID, @Path("productID") String productID);

    /**
     * API: Lấy danh sách tem giá theo mâm
     *
     * @param shelfID   mã số kệ
     * @param platterID mã số mâm
     * @param storeID   mã số cửa hàng
     */
    @GET("getPricetagByPlatter/{shelfID}/{platterNb}/{storeID}")
    Call<List<PriceTag>> getPriceTagsOnPlatter(@Path("shelfID") int shelfID, @Path("platterNb") int platterID, @Path("storeID") int storeID);
}
