package ioc.app.bachhoa.tapAdapter;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ioc.app.bachhoa.Apdapter.PostionViewAdapter;
import ioc.app.bachhoa.Apdapter.ShelfApdapter;
import ioc.app.bachhoa.DTOEntity.PriceTag;
import ioc.app.bachhoa.R;
import ioc.app.bachhoa.api.PlatterAPI;
import ioc.app.bachhoa.api.ProductPositionAPI;
import ioc.app.bachhoa.api.ShelfAPI;
import ioc.app.bachhoa.model.DisplayPlatter;
import ioc.app.bachhoa.model.DisplayShelves;
import ioc.app.bachhoa.model.ProductPositioning;
import ioc.app.bachhoa.model.Store;
import ioc.app.bachhoa.ultil.ALoadingDialog;
import ioc.app.bachhoa.ultil.Message;
import ioc.app.bachhoa.ultil.PrintPriceTag;
import ioc.app.bachhoa.ultil.User;
import ioc.app.bachhoa.ultil.UserManager;
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
    List<String> list2 = new ArrayList<>();
    Spinner shelfSpinner;
    ImageButton nextPlatter, prevousPlater;
    AutoCompleteTextView searchShelf;
    TextView platterNumber;
    Button printShlefNumber, printPriceTagOnShlef, printPricetagsOnPlatter, printShelfPosi;
    RecyclerView posttionRecyCle;
    List<DisplayShelves> shelvesList = new ArrayList<>();
    int indexShelf = 0;
    List<DisplayPlatter> platterList = new ArrayList<>();
    int indexPlatter = 0;
    List<ProductPositioning> proPosList = new ArrayList<>();
    //
    PostionViewAdapter postionViewAdapter = new PostionViewAdapter(getActivity());
    ShelfApdapter shelfApdapter;
    ALoadingDialog aLoadingDialog;

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
        printShelfPosi = view.findViewById(R.id.vsf_printShelfPosi);
        searchShelf = view.findViewById(R.id.sv_search_shelf);
        shelfSpinner = view.findViewById(R.id.sv_spinner);
        prevousPlater = view.findViewById(R.id.sv_before_platter);
        platterNumber = view.findViewById(R.id.sv_platter_number);
        nextPlatter = view.findViewById(R.id.sv_next_platter);
        posttionRecyCle = view.findViewById(R.id.sv_list_position);
        printShlefNumber = view.findViewById(R.id.vsf_printShelfPosi);
        printPriceTagOnShlef = view.findViewById(R.id.vsf_printPricetagsOnShlef);
        printPricetagsOnPlatter = view.findViewById(R.id.vsf_printPriceTagOnPlatter);
        // Set Adapter cho Spinner
        // Set Apdapter cho RecycelView
        loadData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        posttionRecyCle.setLayoutManager(linearLayoutManager);
        posttionRecyCle.setAdapter(postionViewAdapter);

    }

    private void addEvent() {
        printShelfPosi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintPriceTag printPriceTag = new PrintPriceTag(getContext());
                printPriceTag.prinerShelfNumber(shelvesList);
            }
        });
        searchShelf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                list2.clear();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list2.clear(); // Xóa toàn bộ danh sách để cập nhật lại với kết quả tìm kiếm mới
                for (DisplayShelves displayShelves : shelvesList) {
                    if (String.valueOf(displayShelves.getDisSheID()).contains(s.toString())) {
                        list2.add(String.valueOf(displayShelves.getDisSheID()));
                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, list2);
                searchShelf.setAdapter(arrayAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchShelf.setOnItemClickListener((parent, view, position, id) -> {
            getListPosionWithShelfID(Integer.parseInt(searchShelf.getText().toString()));
            // Xử lý khi người dùng chọn một mục từ danh sách gợi ý ở vị trí 'position'
            // Ví dụ: Hiển thị thông tin hoặc thực hiện hành động cụ thể với mục đã chọn.
            // Ở đây, selectedItem chứa giá trị mà người dùng đã chọn.
        });
        shelfSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    proPosList.clear();
                    postionViewAdapter.setData(proPosList);
                } else {
                    indexShelf = position;
                    getListPosion();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                // Toast.makeText(getContext(), "NumerShelf", Toast.LENGTH_SHORT).show();
                PrintPriceTag printPriceTag = new PrintPriceTag(getContext());
                printPriceTag.setView(printShelfPosi);
                printPriceTag.prinerShelfNumber(shelvesList);
            }
        });
        // Su kien in tem gia mam
        printPricetagsOnPlatter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printPricetagsOnPlatter();
            }
        });
        // Su kien in tem gia ke
        printPriceTagOnShlef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductPositionAPI.apiService.getPosByStoreAndShelf(User.employee.getStore().getStoreID(), indexShelf).enqueue(new Callback<List<PriceTag>>() {
                    @Override
                    public void onResponse(Call<List<PriceTag>> call, Response<List<PriceTag>> response) {
                        PrintPriceTag printPriceTag = new PrintPriceTag(getContext());
                        printPriceTag.setView(printPriceTagOnShlef);
                        ArrayList<Bitmap> arrayList = printPriceTag.generatePriceTags(response.body());
                        printPriceTag.printPriceTags(arrayList);
                    }

                    @Override
                    public void onFailure(Call<List<PriceTag>> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void getListPosion() {
        ALoadingDialog aLoadingDialog = new ALoadingDialog(getContext());

        if (indexShelf != 0) {
            ProductPositionAPI.apiService.getLitsProductPoiton(shelvesList.get(indexShelf).getDisSheID(), platterList.get(indexPlatter).getDisPlaID(), UserManager.getInstance().getUser().getStore().getStoreID()).enqueue(new Callback<List<ProductPositioning>>() {
                @Override
                public void onResponse(Call<List<ProductPositioning>> call, Response<List<ProductPositioning>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            setData(response.body());
                        }
                    }

                }

                @Override
                public void onFailure(Call<List<ProductPositioning>> call, Throwable t) {

                }
            });
        }
    }

    private void getListPosionWithShelfID(int id) {
        Toast.makeText(getContext(), "Hallo", Toast.LENGTH_SHORT).show();
        ALoadingDialog aLoadingDialog = new ALoadingDialog(getContext());
        aLoadingDialog.show();

        ProductPositionAPI.apiService.getLitsProductPoiton(id, platterList.get(indexPlatter).getDisPlaID(), UserManager.getInstance().getUser().getStore().getStoreID()).enqueue(new Callback<List<ProductPositioning>>() {
            @Override
            public void onResponse(Call<List<ProductPositioning>> call, Response<List<ProductPositioning>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        setData(response.body());
                        aLoadingDialog.cancel();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<ProductPositioning>> call, Throwable t) {

            }
        });
    }


    private void getListShelf() {
        ShelfAPI.apiService.getshelfs(User.employee.getStore().getStoreID()).enqueue(new Callback<List<DisplayShelves>>() {
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
        PlatterAPI.apiService.getListFlatter(User.employee.getStore().getStoreID()).enqueue(new Callback<List<DisplayPlatter>>() {
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

    private void printPricetagsOnPlatter() {
        if (indexShelf != 0) {
            ProductPositionAPI.apiService.getPriceTagsOnPlatter(indexShelf, ++indexPlatter, UserManager.getInstance().getUser().getStore().getStoreID()).enqueue(new Callback<List<PriceTag>>() {
                @Override
                public void onResponse(Call<List<PriceTag>> call, Response<List<PriceTag>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        PrintPriceTag printPriceTag = new PrintPriceTag(getContext());
                        printPriceTag.setView(printPricetagsOnPlatter);
                        printPriceTag.printPriceTags(printPriceTag.generatePriceTags(response.body()));
                    }
                }

                @Override
                public void onFailure(Call<List<PriceTag>> call, Throwable t) {

                }
            });
        } else {
            Message message = new Message(getContext());
            message.messageFailed(printPricetagsOnPlatter, "Hãy chọn kệ");
        }
    }

    private void setData(List<ProductPositioning> list) {
        this.proPosList = list;
        postionViewAdapter.setData(this.proPosList);

    }

    public void loadData() {
        getListShelf();
        getListPlatter();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}