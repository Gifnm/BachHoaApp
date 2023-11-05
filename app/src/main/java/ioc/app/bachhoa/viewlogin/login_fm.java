package ioc.app.bachhoa.viewlogin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

import ioc.app.bachhoa.MainActivity;
import ioc.app.bachhoa.R;
import ioc.app.bachhoa.api.APIService;
import ioc.app.bachhoa.model.Employee;
import ioc.app.bachhoa.ultil.PrintPriceTag;
import ioc.app.bachhoa.ultil.User;
import ioc.app.bachhoa.viewlogin.forgotpassword_class_fm;
import ioc.app.bachhoa.viewlogin.register_class_fm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login_fm extends Fragment {
    View view;
    TextView quenMatKhau, dangKy, userLogin, userPass, loginErrol, login;
    //Button login;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_fragment,container,false);
        anhxa();
        return view;
    }

    private void anhxa() {
        loginErrol = (TextView) view.findViewById(R.id.lg_errol);
        userLogin = (EditText) view.findViewById(R.id.lg_username);
        userPass = (EditText) view.findViewById(R.id.lg_password);
        login = (TextView) view.findViewById(R.id.lg_loginButton);
        quenMatKhau = (TextView) view.findViewById(R.id.login_sigup);
        dangKy = (TextView) view.findViewById(R.id.login_forgotpassword);
        quenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentMananager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentMananager.beginTransaction();
                register_class_fm register_class_fm = new register_class_fm();
                fragmentTransaction.replace(R.id.login_account, register_class_fm);
                fragmentTransaction.commit();
            }
        });
        dangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentMananager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentMananager.beginTransaction();
                forgotpassword_class_fm forgotpassword_class_fm = new forgotpassword_class_fm();

                fragmentTransaction.replace(R.id.login_account, forgotpassword_class_fm);
                fragmentTransaction.commit();
            }
        });
        login = view.findViewById(R.id.lg_loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAcoount();
            }
        });

    }


    private void loginAcoount() {
        int user = Integer.parseInt(userLogin.getText().toString());
        String passW = userPass.getText().toString();
        APIService.apiService.login(passW, user).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        User.employee = response.body();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        // Khởi tạo SharedPreferences
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("employeeID", response.body().getEmployeeID());
                        editor.apply();
                    } else {
                        loginErrol.setText("Kiểm tra tài khoản và mật khẩu!");

                    }

                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                loginErrol.setText("Tài khoản và mật khẩu chưa chính xác!");
            }
        });

    }

    private void sharedPNG(Bitmap bitmaps) {
        // Tạo một Bitmap bạn muốn chia sẻ (ví dụ: bitmap)
        Bitmap bitmap = bitmaps; // Khởi tạo hoặc lấy Bitmap từ nguồn dữ liệu

// Tạo một Intent với hành động ACTION_SEND
        Intent shareIntent = new Intent(Intent.ACTION_SEND);

// Thiết lập kiểu dữ liệu của dữ liệu bạn đang chia sẻ (ở đây là hình ảnh)
        shareIntent.setType("image/*");

// Chuyển đổi Bitmap thành một mảng byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapByteArray = stream.toByteArray();

// Đặt dữ liệu để chia sẻ (mảng byte của hình ảnh)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shareIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(getContext(), bitmap));
        }


// Khởi động hoạt động chia sẻ và hiển thị danh sách các ứng dụng có thể chia sẻ
        startActivity(Intent.createChooser(shareIntent, "Chia sẻ qua"));

    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

}

