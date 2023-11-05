package ioc.app.bachhoa.tapAdapter;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ioc.app.bachhoa.Apdapter.PostionViewAdapter;
import ioc.app.bachhoa.Apdapter.ShelfApdapter;
import ioc.app.bachhoa.R;
import ioc.app.bachhoa.api.PlatterService;
import ioc.app.bachhoa.api.ProductPositionService;
import ioc.app.bachhoa.api.ShelfService;
import ioc.app.bachhoa.model.DisplayPlatter;
import ioc.app.bachhoa.model.DisplayShelves;
import ioc.app.bachhoa.model.Product;
import ioc.app.bachhoa.model.ProductPositioning;
import ioc.app.bachhoa.model.Store;
import ioc.app.bachhoa.ultil.PrintPriceTag;
import ioc.app.bachhoa.ultil.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link viewShelf_fm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class viewShelf_fm extends Fragment {
    // Khai bao cac bien anh xa cac thanh phan giao dien
    View view;
    Spinner shelfSpinner;
    ImageButton nextPlatter, prevousPlater;
    EditText searchShelf;
    TextView platterNumber;
    Button printShlefNumber, printPriceTagOnShlef, printPricetagsOnPlatter;
    RecyclerView posttionRecyCle;
    List<DisplayShelves> shelvesList = new ArrayList<>();
    int indexShelf = 0;
    List<DisplayPlatter> platterList = new ArrayList<>();
    int indexPlatter = 0;
    List<ProductPositioning> proPosList = new ArrayList<>();
    //
    PostionViewAdapter postionViewAdapter = new PostionViewAdapter(getContext());
    ShelfApdapter shelfApdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public viewShelf_fm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment xemKe_fm.
     */
    // TODO: Rename and change types and number of parameters
    public static viewShelf_fm newInstance(String param1, String param2) {
        viewShelf_fm fragment = new viewShelf_fm();
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
        // Tạo biến view để ánh xạ layout
        view = inflater.inflate(R.layout.view_shelf_fm, container, false);
        uiInit();
        addEvent();
        return view;
    }

    private void uiInit() {
        searchShelf = view.findViewById(R.id.sv_search_shelf);
        shelfSpinner = view.findViewById(R.id.sv_spinner);
        prevousPlater = view.findViewById(R.id.sv_before_platter);
        platterNumber = view.findViewById(R.id.sv_platter_number);
        nextPlatter = view.findViewById(R.id.sv_next_platter);
        posttionRecyCle = view.findViewById(R.id.sv_list_position);
        printShlefNumber = view.findViewById(R.id.vsf_printShelfPosi);
        printPriceTagOnShlef = view.findViewById(R.id.vsf_printPricetagsOnShlef);
        printPricetagsOnPlatter = view.findViewById(R.id.vsf_printPriceTagOnPlatter);
        getListShelf();
        getListPlatter();
        // Set Adapter cho Spinner
        shelfSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indexShelf = position;
                getListPosion();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Set Apdapter cho RecycelView

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        posttionRecyCle.setLayoutManager(linearLayoutManager);
        posttionRecyCle.setAdapter(postionViewAdapter);

    }

    private void addEvent() {
        prevousPlater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPrevousePlatter();
            }
        });
        nextPlatter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNextPlatter();
            }
        });
        // Su kien in ma so ke
        printShlefNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // Su kien in tem gia mam
        printPricetagsOnPlatter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printPricetagsOnPlatter(proPosList);
            }
        });
        // Su kien in tem gia ke
        printPriceTagOnShlef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        ProductPositionService.apiService.getPosByStoreAndShelf(User.employee.getStore().getStoreID(),indexShelf).enqueue(new Callback<List<ProductPositioning>>() {
            @Override
            public void onResponse(Call<List<ProductPositioning>> call, Response<List<ProductPositioning>> response) {
                PrintPriceTag printPriceTag = new PrintPriceTag(getContext());
                ArrayList<Bitmap> arrayList = printPriceTag.generatePriceTags(response.body());
                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                printPriceTag.printPriceTags(arrayList);
            }

            @Override
            public void onFailure(Call<List<ProductPositioning>> call, Throwable t) {

            }
        });
            }
        });
    }

    private void getListPosion() {
        if (indexShelf != 0) {
            ProductPositionService.apiService.getLitsProductPoiton(shelvesList.get(indexShelf).getDisSheID(), platterList.get(indexPlatter).getDisPlaID(), User.employee.getStore().getStoreID()).enqueue(new Callback<List<ProductPositioning>>() {
                @Override
                public void onResponse(Call<List<ProductPositioning>> call, Response<List<ProductPositioning>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            setData(response.body());
                            postionViewAdapter.setData(response.body());
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ProductPositioning>> call, Throwable t) {

                }
            });
        }
    }

    private void getListShelf() {
        ShelfService.apiService.getshelfs(User.employee.getStore().getStoreID()).enqueue(new Callback<List<DisplayShelves>>() {
            @Override
            public void onResponse(Call<List<DisplayShelves>> call, Response<List<DisplayShelves>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<DisplayShelves> list = response.body();
                        list.add(0, new DisplayShelves(0, "Chọn kệ", null));
                        shelvesList = list;
                        shelfApdapter = new ShelfApdapter(getContext(), R.id.sv_spinner, response.body());
                        shelfSpinner.setAdapter(shelfApdapter);
                        shelfApdapter.setChangeData(shelvesList);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<DisplayShelves>> call, Throwable t) {
                shelvesList.add(new DisplayShelves(0, "Chọn kệ", new Store(1)));
                shelfApdapter = new ShelfApdapter(getContext(), R.id.sv_spinner, shelvesList);
                shelfSpinner.setAdapter(shelfApdapter);
                shelfApdapter.setChangeData(shelvesList);
            }
        });
    }

    private void getListPlatter() {
        PlatterService.apiService.getListFlatter(User.employee.getStore().getStoreID()).enqueue(new Callback<List<DisplayPlatter>>() {
            @Override
            public void onResponse(Call<List<DisplayPlatter>> call, Response<List<DisplayPlatter>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        platterList = response.body();
                        platterNumber.setText("Mâm 1");

                    }

                }
            }

            @Override
            public void onFailure(Call<List<DisplayPlatter>> call, Throwable t) {

            }
        });


    }

    private void toNextPlatter() {
        if (indexPlatter < platterList.size() - 1) {
            indexPlatter++;
            platterNumber.setText(platterList.get(indexPlatter).getRowName());
            getListPosion();
        }

    }

    private void toPrevousePlatter() {
        if (indexPlatter > 0) {
            indexPlatter--;
            platterNumber.setText(platterList.get(indexPlatter).getRowName());
            getListPosion();
        }

    }

    private void printPricetagsOnPlatter(List<ProductPositioning> list) {
        PrintPriceTag printPriceTag = new PrintPriceTag(getContext());
        ArrayList<Bitmap> arrayList = printPriceTag.generatePriceTags(list);

        printPriceTag.printPriceTags(arrayList);

    }

    private void setData(List<ProductPositioning> list) {
        proPosList = list;

    }

}