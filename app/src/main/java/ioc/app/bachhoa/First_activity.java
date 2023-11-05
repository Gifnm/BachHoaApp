package ioc.app.bachhoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import ioc.app.bachhoa.api.EmployeeAPI;
import ioc.app.bachhoa.model.Employee;
import ioc.app.bachhoa.ultil.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class First_activity extends AppCompatActivity {
    TextView internet_status;
    Boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        internet_status = (TextView) findViewById(R.id.main_status);
        if (isInternetAvailable()) {
            toLogin();
        } else {
            internet_status.setText("Kiểm tra kết nối mạng!");
        }

    }

    public void toLogin() {
        if (checkLogin()) {
            Intent intent = new Intent(First_activity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else {

            Intent intent = new Intent(First_activity.this, DangNhap.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }


    private boolean checkLogin() {

        SharedPreferences sharedPreferences = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        int employeeID = sharedPreferences.getInt("employeeID", 0);
        if (employeeID == 0) {
            return false;
        } else {
            EmployeeAPI.apiService.findById(employeeID).enqueue(new Callback<Employee>() {
                @Override
                public void onResponse(Call<Employee> call, Response<Employee> response) {
                    if (response.isSuccessful()) {
                        User.employee = response.body();
                        Toast.makeText(First_activity.this, "Xin chào" + User.employee.getEmployeeName(), Toast.LENGTH_SHORT).show();
                        setCheck(true);
                    } else {
                        Toast.makeText(First_activity.this, "Vui lòng đăng nhập lại " + employeeID, Toast.LENGTH_SHORT).show();
                        setCheck(false);
                        Intent intent = new Intent(First_activity.this, DangNhap.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Employee> call, Throwable t) {
                    internet_status.setText("Bảo trì máy chủ!");
                    setCheck(false);
                }
            });

            return this.check;
        }


    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    private void setCheck(Boolean check) {
        this.check = check;

    }

    ;
}