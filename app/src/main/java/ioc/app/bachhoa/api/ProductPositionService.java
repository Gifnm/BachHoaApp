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

    /*
     * Lấy vị trí của sản phẩm bằng mã sản phẩm
     * Kết quả trả về là ProductPositioning hoặc NULL trong trường hợp sản phẩm không có vị trí
     * Phương thức là @GET
     * */
    @GET("productPosition/findByID/{id}/{storeID}")
    Call<ProductPositioning> findByProductID(@Path("id") String id, @Path("storeID") int storeID);

    /*
     * Lấy danh sách sản phẩm của một kệ
     * Thâm số, mã kệ, mã mâm, mã cửa hàng
     * */
    @GET("productPositioning/{shelfID}/{platterNb}/{storeID}")
    Call<List<ProductPositioning>> getLitsProductPoiton(@Path("shelfID") int shelfID, @Path("platterNb") int platterID, @Path("storeID") int storeID);

    // Thêm một vị trí mới cho sản phẩm
    @POST("productPositioning/insert")
    Call<ProductPositioning> insertProPosion(@Body ProductPositioning productPositioning);
}
