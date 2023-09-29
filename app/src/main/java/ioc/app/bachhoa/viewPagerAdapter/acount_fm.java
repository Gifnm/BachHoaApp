package ioc.app.bachhoa.viewPagerAdapter;


import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ioc.app.bachhoa.MainActivity;
import ioc.app.bachhoa.R;
import ioc.app.bachhoa.fm.AccountDetail;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link acount_fm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class acount_fm extends Fragment {

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
        anhxa();
        return view;
    }
    private void anhxa() {
        thongTinTaiKhoan = (CardView) view.findViewById(R.id.account_detail);
        final BottomNavigationView navigationView = getActivity().findViewById(R.id.navigation);

        thongTinTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AccountDetail accountDetail = new AccountDetail();
                fragmentTransaction.add(R.id.main_activity, accountDetail);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                // Ẩn BottomNavigationView khi chuyển đổi đến Fragment khác
                navigationView.setVisibility(View.GONE);
            }
        });
    }






}