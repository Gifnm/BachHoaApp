package ioc.app.bachhoa.viewlogin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import ioc.app.bachhoa.MainActivity;
import ioc.app.bachhoa.R;
import ioc.app.bachhoa.api.APIService;
import ioc.app.bachhoa.model.Employee;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class register_class_fm extends Fragment {
    View view;
    TextView dangNhap;
    EditText gmail;
    EditText passWork1;
    EditText passWork2;
    Button register;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register_fm, container, false);
        anhXa();
        return view;
    }

    public void anhXa() {
        // anh xa giao dien
        dangNhap = (TextView) view.findViewById(R.id.register_dangNhap);
        gmail = (EditText) view.findViewById(R.id.register_email);
        passWork1 = (EditText) view.findViewById(R.id.rg_password);
        passWork2 = (EditText) view.findViewById(R.id.rg_passwords);
        register = (Button) view.findViewById(R.id.register_button);
        // Tao su kien cho cac thanh phan giao dien
        setAction();


    }

    private void setAction() {
        // Di chuyen den giao dien dang nhap
        dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentMananager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentMananager.beginTransaction();
                forgotpassword_class_fm forgotpassword_class_fm = new forgotpassword_class_fm();
                login_fm login_fm = new login_fm();
                fragmentTransaction.replace(R.id.login_account, login_fm);
                fragmentTransaction.commit();
            }
        });
        // Dang ky tai khoan
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Employee employee = new Employee();
                employee.setEmail(gmail.getText().toString());
                employee.setPasswork(passWork1.getText().toString());
                APIService.apiService.createEmployee(employee).enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {
                        System.out.println("Hoho" +t);
                        Toast.makeText(getActivity(), "Thất bại", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}

