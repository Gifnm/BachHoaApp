package ioc.app.bachhoa.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ioc.app.bachhoa.model.Product;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {
    String baseURL = "http:192.168.1.7:8083/bachhoa/api/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ProductService apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(ProductService.class);
     /*
     * Tìm kiếm với mã sản phẩm
     * Tham số truyền vào là mã sản phẩm
     * Kết quả có thể nhận được là sản phẩm hoặc giá trị null trong trường hợp có lỗi hoặc mã sản phẩm không hợp lệ
     * Phương thức sử dụng là GET
     * */
    @GET("product/findByID/{id}/{storeID}")
    Call<Product> findByID(@Path("id") String id, @Path("storeID") int storeID);


}
