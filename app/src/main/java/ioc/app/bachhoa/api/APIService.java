package ioc.app.bachhoa.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ioc.app.bachhoa.model.Bill;
import ioc.app.bachhoa.model.BillDetail;
import ioc.app.bachhoa.model.Categories;
import ioc.app.bachhoa.model.DisplayPlatter;
import ioc.app.bachhoa.model.DisplayShelves;
import ioc.app.bachhoa.model.Employee;
import ioc.app.bachhoa.model.Product;
import ioc.app.bachhoa.model.ProductPositioning;
import ioc.app.bachhoa.ultil.LocalVarible;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    String baseURL = "http:"+ LocalVarible.ip +":"+LocalVarible.serverPort+"/bachhoa/api/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    APIService apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(APIService.class);

    // Employee API
    @GET("getembyid")
    Call<Employee> getEmployeeByID(@Query("id") Integer id);

    @GET("login/{passW}/{user}")
    Call<Employee> login(@Path("passW") String passW, @Path("user") int id);

    // create nhan vien
    @POST("insert")
    Call<Employee> createEmployee(@Body Employee employee);

    // Product API


    @Multipart
    @POST("upload")
    Call<Void> uploadSanPhamWithImage(
            @Part("product") Product product,
            @Part MultipartBody.Part image);

    // Categories API
    @GET("categories")
    Call<List<Categories>> getCategories();

    // shelf api
    // Lấy danh sách kệ hiện có tại cửa hàng
    @GET("findAllShelf/{id}")
    Call<List<DisplayShelves>> getshelfs(@Path("id") int id);

    // Thêm một kệ mới vào cửa hàng
    @POST("insertShelf")
    Call<DisplayShelves> insertShelf(@Body DisplayShelves displayShelves);

    // DisplayPlatter API
    // Lấy danh sách mâm hiện có tại cửa hàng
    @GET("platter/getPlatters/{id}")
    Call<List<DisplayPlatter>> getListFlatter(@Path("id") int id);

    // Thêm một mâm mới
    @POST("platter/inserPlatter")
    Call<Void> insertPlatter(@Body DisplayPlatter displayPlatter);

    // BillAPI
    @POST("createBill")
    Call<Void> createBill(@Body Bill bill);

    //BillDetail API
    // Kiểm tra tồn tại sản phẩm và lấy BillDetail
    @GET("billDetail/getproduct/{productID}")
    Call<BillDetail> getProductForBill(@Path("productID") String productID);

    // Create BillDetail, tham số là một danh sách
    @POST("billDetail/create/{listDetail}/{billID}")
    Call<Void> createBillDetail(@Path("listDetail") List<BillDetail> list, @Path("billID") String billID);

    // ProductPositoning API
    @GET("productPositioning/{shelfID}/{platterNb}/{storeID}")
    Call<List<ProductPositioning>> getLitsProductPoiton(@Path("shelfID") int shelfID, @Path("platterNb") int platterID, @Path("storeID") int storeID);

    @POST("productPositioning/insert")
    Call<ProductPositioning> insertProPosion(@Body ProductPositioning productPositioning);
    // 4. Check san pham

}

