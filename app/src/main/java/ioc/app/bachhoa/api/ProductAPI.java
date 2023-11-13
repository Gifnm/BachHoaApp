package ioc.app.bachhoa.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ioc.app.bachhoa.model.Product;
import ioc.app.bachhoa.ultil.LocalVarible;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductAPI {
    String baseURL = "http:"+ LocalVarible.ip +":"+LocalVarible.serverPort+"/bachhoa/api/product";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ProductAPI apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(ProductAPI.class);

    /**
     * API: Lấy sản phẩm tại của hàng
     *
     * @param id      Mã sản phẩm
     * @param storeID Mã của hàng
     */
    @GET("findByID/{id}/{storeID}")
    Call<Product> findByID(@Path("id") String id, @Path("storeID") int storeID);


}
