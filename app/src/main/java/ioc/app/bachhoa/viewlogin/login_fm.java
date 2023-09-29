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
import android.widget.TextView;

import androidx.annotation.Nullable;

import ioc.app.bachhoa.MainActivity;
import ioc.app.bachhoa.R;
import ioc.app.bachhoa.viewlogin.forgotpassword_class_fm;
import ioc.app.bachhoa.viewlogin.register_class_fm;

public class login_fm extends Fragment {
    View view;
    TextView quenMatKhau;
    TextView dangKy;
    Button login;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_fragment, container, false);
        anhxa();
        return view;
    }

    private void anhxa() {
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
        login = (Button) view.findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}

