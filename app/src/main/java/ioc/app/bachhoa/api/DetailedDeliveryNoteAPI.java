package ioc.app.bachhoa.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ioc.app.bachhoa.model.DeliveryNote;
import ioc.app.bachhoa.model.DetailedDeliveryNote;
import ioc.app.bachhoa.ultil.LocalVarible;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface DetailedDeliveryNoteAPI {
    String baseURL = "http:" + LocalVarible.ip + ":" + LocalVarible.serverPort + "/bachhoa/api/detaildeliverynote/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    DetailedDeliveryNoteAPI apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(DetailedDeliveryNoteAPI.class);

    /**
     * API: Lấy danh sách nội dung của phiếu nhập
     *
     * @param deliveryNoteId Object Phiếu nhập hàng
     */
    @GET("getall/{deliveryNoteId}")
    Call<List<DetailedDeliveryNote>> getByDeliveryNote(@Path("deliveryNoteId") String deliveryNoteId);

    @POST("save")
    Call<Void> save(@Part("list") List<DetailedDeliveryNote> list, @Part("employeeId") int employeeID);

}
