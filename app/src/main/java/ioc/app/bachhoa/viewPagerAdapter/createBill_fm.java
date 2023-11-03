package ioc.app.bachhoa.viewPagerAdapter;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ioc.app.bachhoa.Make_Bill;
import ioc.app.bachhoa.R;
import ioc.app.bachhoa.ReplenishmentPeriod;
import ioc.app.bachhoa.fm.AddPosition_fm;
import ioc.app.bachhoa.fm.CreateProduct;
import ioc.app.bachhoa.ultil.PrintPriceTag;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link createBill_fm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class createBill_fm extends Fragment {
    CardView createProduct, position, makeBill, create_RP, printer;
    View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public createBill_fm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment createBill_fm.
     */
    // TODO: Rename and change types and number of parameters
    public static createBill_fm newInstance(String param1, String param2) {
        createBill_fm fragment = new createBill_fm();
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
        view = inflater.inflate(R.layout.fragment_home_fm, container, false);
        anhxa();
        addEvent();
        AddEventToMakeBill();
        return view;
    }

    private void anhxa() {
        printer = view.findViewById(R.id.fhf_printer);
        printer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintPriceTag printPriceTag = new PrintPriceTag();
                printPriceTag.printOnePriceTag();

//                PrintImageUsingSocket printImageUsingSocket = new PrintImageUsingSocket();
//                printImageUsingSocket.print();

               Toast.makeText(getContext(), "Print", Toast.LENGTH_SHORT).show();
            }
        });
    create_RP = (CardView) view.findViewById(R.id.home_create_RP);
        makeBill = (CardView) view.findViewById(R.id.home_make_bill);
        final BottomNavigationView navigationView = getActivity().findViewById(R.id.navigation);
        createProduct = (CardView) view.findViewById(R.id.create_product);
        position = (CardView) view.findViewById(R.id.home_position);
        createProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CreateProduct createProduct = new CreateProduct();
                fragmentTransaction.add(R.id.main_activity, createProduct);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                // Ẩn BottomNavigationView khi chuyển đổi đến Fragment khác
                navigationView.setVisibility(View.GONE);
            }
        });

    }

    private void addEvent() {
        position.setOnClickListener(new View.OnClickListener() {
            final BottomNavigationView navigationView = getActivity().findViewById(R.id.navigation);

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddPosition_fm addPosition_fm = new AddPosition_fm();
                fragmentTransaction.add(R.id.main_activity, addPosition_fm);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                // Ẩn BottomNavigationView khi chuyển đổi đến Fragment khác
                navigationView.setVisibility(View.GONE);

            }
        });

        addEventToCreateRP();
    }

    private void AddEventToMakeBill() {
        makeBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Make_Bill
                        .class);
                startActivity(intent);
            }
        });

    }

    private void addEventToCreateRP() {
        create_RP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReplenishmentPeriod.class);
                startActivity(intent);

            }
        });
    }
}