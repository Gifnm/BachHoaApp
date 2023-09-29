package ioc.app.bachhoa.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ioc.app.bachhoa.model.Categories;
import ioc.app.bachhoa.model.DisplayPlatter;
import ioc.app.bachhoa.model.DisplayShelves;
import ioc.app.bachhoa.model.Employee;
import ioc.app.bachhoa.model.Product;
import ioc.app.bachhoa.tapAdapter.viewProduct_pm;
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
    String baseURL = "http://192.168.1.5:8081/bachhoa/api/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    APIService apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(APIService.class);

    @GET("getembyid")
    Call<Employee> getEmployeeByID(@Query("id") Integer id);

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
    @GET("findAllShelf/{id}")
    Call<List<DisplayShelves>> getshelfs(@Path("id") int id);

    @POST("insertShelf")
    Call<DisplayShelves> insertShelf(@Body DisplayShelves displayShelves);

    // DisplayPlatter API
    @GET("platter/getPlatters/{id}")
    Call<List<DisplayPlatter>> getListFlatter(@Path("id") int id);
}

