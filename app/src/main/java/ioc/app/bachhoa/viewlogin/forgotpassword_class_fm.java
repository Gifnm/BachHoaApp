package ioc.app.bachhoa.viewlogin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ioc.app.bachhoa.R;

public class forgotpassword_class_fm extends Fragment {
    View view;
    TextView dangNhap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgotpassword_fm, container, false);
        anhXa();
        return view;
    }

    public void anhXa() {
        dangNhap = (TextView) view.findViewById(R.id.forgotpassword_login);
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

    }
}