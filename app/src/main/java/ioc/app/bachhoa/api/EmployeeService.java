package ioc.app.bachhoa.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ioc.app.bachhoa.model.Employee;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EmployeeService {
    String baseURL = "http:192.168.1.7:8083/bachhoa/api/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    EmployeeService apiService = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(EmployeeService.class);
// Lấy thông tin nhân viên bằng id
    @GET("findById/{id}")
    Call<Employee> findById(@Path("id") int employeeId);
}
