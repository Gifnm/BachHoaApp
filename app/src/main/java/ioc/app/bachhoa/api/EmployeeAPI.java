package ioc.app.bachhoa.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ioc.app.bachhoa.model.Employee;
import ioc.app.bachhoa.ultil.LocalVarible;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EmployeeAPI {
    String baseURL = "http:"+ LocalVarible.ip +":"+LocalVarible.serverPort+"/bachhoa/api/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    EmployeeAPI apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(EmployeeAPI.class);
// Lấy thông tin nhân viên bằng id

    /**
     * API: Lấy nhân viên
     *
     * @param employeeId Mã nhân viên
     */
    @GET("findById/{id}")
    Call<Employee> findById(@Path("id") int employeeId);
}
