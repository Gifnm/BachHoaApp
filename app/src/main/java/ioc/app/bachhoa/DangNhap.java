package ioc.app.bachhoa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import ioc.app.bachhoa.viewlogin.login_fm;

public class DangNhap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        setView();
    }

    private void setView() {
        FragmentManager fragmentMananager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentMananager.beginTransaction();
        login_fm login_fm = new login_fm();
        fragmentTransaction.add(R.id.login_account,login_fm);
        fragmentTransaction.commit();
    }


}