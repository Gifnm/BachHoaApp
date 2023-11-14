package ioc.app.bachhoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import ioc.app.bachhoa.api.EmployeeAPI;
import ioc.app.bachhoa.model.Employee;
import ioc.app.bachhoa.ultil.Message;
import ioc.app.bachhoa.ultil.User;
import ioc.app.bachhoa.ultil.UserManager;
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
            checkLogin();
        } else {
            Message message = new Message(First_activity.this);
            message.messageFailed(internet_status, "Không có kết nối Internet!");
        }

    }


    private void checkLogin() {

        SharedPreferences sharedPreferences = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        int employeeID = sharedPreferences.getInt("employeeID", 0);
        if (employeeID == 0) {
            toLoginView();
        } else {
            EmployeeAPI.apiService.findById(employeeID).enqueue(new Callback<Employee>() {
                @Override
                public void onResponse(Call<Employee> call, Response<Employee> response) {
                    if (response.code() == 200) {
                        UserManager userManager = UserManager.getInstance();
                        userManager.setUser(response.body());
                        User.employee = response.body();
                        toMain();
                    } else if (response.code() == 401) {

                        Intent intent = new Intent(First_activity.this, DangNhap.class);
                        startActivity(intent);
                    }

                }

                @Override
                public void onFailure(Call<Employee> call, Throwable t) {
                    Message message = new Message(First_activity.this);
                    message.messageFailed(internet_status, "Máy chủ đang bảo trì!");
                }
            });
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

    private void toMain() {
        Intent intent = new Intent(First_activity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void toLoginView() {
        Intent intent = new Intent(First_activity.this, DangNhap.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

}