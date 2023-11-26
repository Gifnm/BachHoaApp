package ioc.app.bachhoa.viewPagerAdapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ioc.app.bachhoa.DangNhap;
import ioc.app.bachhoa.MainActivity;
import ioc.app.bachhoa.R;
import ioc.app.bachhoa.fm.AccountDetail;
import ioc.app.bachhoa.ultil.UserManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link acount_fm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class acount_fm extends Fragment {
    private Button btnLogout;
    private TextView storeID, user;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // Tao doi tuong View de anhxa
    View view;
    CardView thongTinTaiKhoan;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public acount_fm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment historyBill_fm.
     */
    // TODO: Rename and change types and number of parameters
    public static acount_fm newInstance(String param1, String param2) {
        acount_fm fragment = new acount_fm();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account_fm, container, false);
        uiInit();
        return view;
    }

    private void uiInit() {
        btnLogout = view.findViewById(R.id.faf_logout);
        storeID = view.findViewById((R.id.faf_store_id));
        user = view.findViewById(R.id.faf_user);
        storeID.setText("Cửa hàng: "+ UserManager.getInstance().getUser().getStore().getStoreID());
        user.setText(UserManager.getInstance().getUser().getEmployeeName() +" - "+ UserManager.getInstance().getUser().getEmployeeID());
       addEvent();
    }
    private void addEvent(){
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(getActivity(), DangNhap.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
}