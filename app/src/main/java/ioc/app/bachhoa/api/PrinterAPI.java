package ioc.app.bachhoa.api;

import android.util.Printer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ioc.app.bachhoa.model.Printers;
import ioc.app.bachhoa.model.ShipmentBatch;
import ioc.app.bachhoa.model.ShipmentBatchDetail;
import ioc.app.bachhoa.ultil.LocalVarible;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PrinterAPI {
    String baseURL = "http:" + LocalVarible.ip + ":" + LocalVarible.serverPort + "/bachhoa/api/printer/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    PrinterAPI apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(PrinterAPI.class);

    /**
     * API: Lấy danh sách máy in tại cửa hàng
     *
     * @param storeID Mã cửa hàng
     */
    @GET("getAll/{storeID}")
    Call<List<Printers>> getAll(@Path("storeID") int storeID);

    /**
     * API: Thêm mới thông tin một máy in
     *
     * @param printers Đối tượng Printers
     */
    @POST("insert")
    Call<Void> insert(@Body Printers printers);

    /**
     * API: Xóa thông tin một máy in
     *
     * @param printers Mã cửa hàng
     */
    @DELETE("delete")
    Call<Void> delete(@Body Printers printers);

}
