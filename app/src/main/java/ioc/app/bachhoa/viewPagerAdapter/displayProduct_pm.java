package ioc.app.bachhoa.viewPagerAdapter;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import ioc.app.bachhoa.PrintPriceTagActivity;
import ioc.app.bachhoa.R;
import ioc.app.bachhoa.api.PrinterAPI;
import ioc.app.bachhoa.fm.PrinterBottomSheetFragment;
import ioc.app.bachhoa.model.Printers;
import ioc.app.bachhoa.tapAdapter.viewShelf_fm;
import ioc.app.bachhoa.ultil.ItemClick;
import ioc.app.bachhoa.ultil.UserManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link displayProduct_pm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class displayProduct_pm extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TapAdapter tapAdapter;
    private View view;
    private ImageView choosePrinter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public displayProduct_pm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment displayProduct_pm.
     */
    // TODO: Rename and change types and number of parameters
    public static displayProduct_pm newInstance(String param1, String param2) {
        displayProduct_pm fragment = new displayProduct_pm();
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

        view = inflater.inflate(R.layout.fragment_display_product_pm, container, false);
        anhxa(); // Gọi phương thức anhxa() sau khi đã gán giá trị cho view
        return view;
    }


    private void anhxa() {
        viewPager = (ViewPager) view.findViewById(R.id.tap_viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tap);
        choosePrinter = view.findViewById(R.id.fdp_choose_printer);
        setTabDividers();
        tapAdapter = new TapAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(tapAdapter);
        tabLayout.setupWithViewPager(viewPager);
        addEvent();
    }

    private void setTabDividers() {
        View root = tabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setSize(2, 1);
            drawable.setColor(Color.GRAY);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);

        }

    }

    private void addEvent() {
        choosePrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrinterAPI.apiService.getAll(UserManager.getInstance().getUser().getStore().getStoreID()).enqueue(new Callback<List<Printers>>() {
                    @Override
                    public void onResponse(Call<List<Printers>> call, Response<List<Printers>> response) {
                        if (response.isSuccessful()) {
                            PrinterBottomSheetFragment printerBottomSheetFragment = new PrinterBottomSheetFragment(getActivity(), response.body(), new ItemClick() {
                                @Override
                                public void clickItem(Printers printers) {
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("printerip", getActivity().MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("printerip", printers.getIpAddress());
                                    editor.apply();
                                }
                            });
                            printerBottomSheetFragment.show(getActivity().getSupportFragmentManager(), printerBottomSheetFragment.getTag());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Printers>> call, Throwable t) {

                    }
                });
            }
        });
    }

}