package ioc.app.bachhoa.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ioc.app.bachhoa.model.DeliveryNote;
import ioc.app.bachhoa.ultil.LocalVarible;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Part;

public interface DetailedDeliveryNoteAPI {
    String baseURL = "http:" + LocalVarible.ip + ":8083/bachhoa/api/detaildeliverynote/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    DetailedDeliveryNoteAPI apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(DetailedDeliveryNoteAPI.class);

    /**
     * API: Lấy danh sách nội dung của phiếu nhập
     *
     * @param deliveryNote Object Phiếu nhập hàng
     */
    @GET("getall")
    Call<List<DetailedDeliveryNoteAPI>> getByDeliveryNote(@Part("deliveryNote") DeliveryNote deliveryNote);


}
