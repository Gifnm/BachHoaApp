package ioc.app.bachhoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import ioc.app.bachhoa.fm.MakeBill_fm;
import ioc.app.bachhoa.viewlogin.login_fm;

public class Make_Bill extends AppCompatActivity {
ImageButton scan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_bill);
        uiInit();
        openScan();
    }

    private void uiInit(){
        scan = (ImageButton) findViewById(R.id.mb_scan);


    }
    private void openScan(){
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                MakeBill_fm makeBill_fm = new MakeBill_fm();
                fragmentTransaction.replace(R.id.mb_main,makeBill_fm);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

}