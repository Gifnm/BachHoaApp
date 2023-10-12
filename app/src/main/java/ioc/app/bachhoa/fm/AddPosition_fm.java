package ioc.app.bachhoa.fm;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;

import ioc.app.bachhoa.Apdapter.PositionAdapter;
import ioc.app.bachhoa.Apdapter.ShelfApdapter;
import ioc.app.bachhoa.CallBack.CallBackBachHoa;
import ioc.app.bachhoa.R;
import ioc.app.bachhoa.api.APIService;
import ioc.app.bachhoa.model.DisplayPlatter;
import ioc.app.bachhoa.model.DisplayShelves;
import ioc.app.bachhoa.model.Product;
import ioc.app.bachhoa.model.ProductPositioning;
import ioc.app.bachhoa.model.Store;
import ioc.app.bachhoa.ultil.CaptureAct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPosition_fm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPosition_fm extends Fragment {
    // Khai báo các biến cục bộ
    View view;
    Spinner shelfSpinner;
    ImageButton addOneShelf, scan, prevous, next, addOnePlatter;
    TextView platterNumber;
    RecyclerView listPositionItem;
    List<DisplayShelves> listShelf;
    List<DisplayPlatter> displayPlatterList;
    List<ProductPositioning> listPossitioning = new ArrayList<>();
    int index = 0;
    int indexShelf = 0;
    ShelfApdapter shelfApdapter;
    PositionAdapter positionAdapter;
    DisplayShelves displShelfIsSelcted;
    EditText indexOfProduct, quantityDi;
    Button saveDia, cancelDia;
    Dialog dialog = null;
    String result;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddPosition_fm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPosition_fm.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPosition_fm newInstance(String param1, String param2) {
        AddPosition_fm fragment = new AddPosition_fm();
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

    //onCreateView hàm khởi tạo giao diện
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Gắn giao diện cho đối tượng view
        view = inflater.inflate(R.layout.fragment_add_position_fm, container, false);
        // Ánh xạ các view trong giao diện
        // Tải dữ liệu ban đâu từ server
        uiInit();


        return view;
    }

    // Ánh xã các biến và đối tượng giao diện
    private void uiInit() {
        shelfSpinner = (Spinner) view.findViewById(R.id.ap_list_shelf);
        scan = (ImageButton) view.findViewById(R.id.ap_scan);
        addOneShelf = (ImageButton) view.findViewById(R.id.ap_add_shelf);
        listPositionItem = (RecyclerView) view.findViewById(R.id.ap_list_posstion);
        prevous = (ImageButton) view.findViewById(R.id.ap_prevous);
        next = (ImageButton) view.findViewById(R.id.ap_next);
        platterNumber = (TextView) view.findViewById(R.id.ap_pallter);
        addOnePlatter = (ImageButton) view.findViewById(R.id.ap_more_platter);
        getListShelf();
        getListPlatterFrom();
        setAdapterList();
        addEvent();
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.index_and_quantity);

        Window window = dialog.getWindow();
        if (window == null) {

            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams winLayoutParams = window.getAttributes();
        winLayoutParams.gravity = Gravity.CENTER;
        window.setAttributes(winLayoutParams);
        dialog.setCancelable(false);
        indexOfProduct = dialog.findViewById(R.id.index_product);
        quantityDi = dialog.findViewById(R.id.quantity_display);
        saveDia = dialog.findViewById(R.id.btn_saveIndex);
        cancelDia = dialog.findViewById(R.id.btn_cancel_index);
        saveDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantityDi.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Nhập số lượng trưng bày", Toast.LENGTH_SHORT).show();
                } else {
                    insertPosition(result);
                    dialog.dismiss();
                }
            }
        });
        cancelDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    // onDestroy: hàm này được gọi khi giao diện bị phá hủy
    @Override
    public void onDestroy() {
        super.onDestroy();
        showBottomNavigationView();
    }

    private void showBottomNavigationView() {
        final BottomNavigationView navigationView = getActivity().findViewById(R.id.navigation);
        if (navigationView != null) {
            navigationView.setVisibility(View.VISIBLE);
        }
    }

    // Thêm một Kệ mới vào cơ sở dữ liệu
    private void insertShelf() {

        addOneShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayShelves displayShelves = new DisplayShelves(listShelf.size(), "Kệ " + listShelf.size(), new Store(1));
                APIService.apiService.insertShelf(displayShelves).enqueue(new Callback<DisplayShelves>() {
                    @Override
                    public void onResponse(Call<DisplayShelves> call, Response<DisplayShelves> response) {

                        listShelf.add(displayShelves);
                        indexShelf = listShelf.size() - 1;
                        shelfSpinner.setSelection(indexShelf);
                        shelfApdapter.setChangeData(listShelf);
                    }

                    @Override
                    public void onFailure(Call<DisplayShelves> call, Throwable t) {

                    }
                });
            }
        });
    }

    // Lấy danh sách kệ hiện có
    private List<DisplayShelves> getListShelf() {
        List<DisplayShelves> shelvesList = null;
        APIService.apiService.getshelfs(1).enqueue(new Callback<List<DisplayShelves>>() {
            @Override
            public void onResponse(Call<List<DisplayShelves>> call, Response<List<DisplayShelves>> response) {
                List<DisplayShelves> list = response.body();
                list.add(0, new DisplayShelves(0, "Chọn kệ", null));
                listShelf = list;
                setShelfSpinner(list);

            }

            @Override
            public void onFailure(Call<List<DisplayShelves>> call, Throwable t) {

            }
        });
        return shelvesList;

    }

    private void getListPlatter(List<DisplayPlatter> list) {
        displayPlatterList = list;
        platterNumber.setText(displayPlatterList.get(index).getRowName());
    }
    // Thêm một kệ mới


    private void getLitsProductPoiton(int shelfid, int platterID, int storeID) {
        APIService.apiService.getLitsProductPoiton(shelfid, platterID, storeID).enqueue(new Callback<List<ProductPositioning>>() {
            @Override
            public void onResponse(Call<List<ProductPositioning>> call, Response<List<ProductPositioning>> response) {
                listPossitioning = response.body();
                positionAdapter.setData(listPossitioning);
            }

            @Override
            public void onFailure(Call<List<ProductPositioning>> call, Throwable t) {

            }
        });

    }


    // Lấy danh sách mâm của cửa hàng
    private void getListPlatterFrom() {
        APIService.apiService.getListFlatter(1).enqueue(new Callback<List<DisplayPlatter>>() {
            @Override
            public void onResponse(Call<List<DisplayPlatter>> call, Response<List<DisplayPlatter>> response) {
                if (response.isSuccessful()) {
                    List<DisplayPlatter> displayPlatterList = response.body();
                    getListPlatter(displayPlatterList);
                } else {
                    // Xử lý trường hợp không thành công (ví dụ: lỗi mạng, lỗi server, ...).
                }
            }

            @Override
            public void onFailure(Call<List<DisplayPlatter>> call, Throwable t) {

            }
        });
    }

    // Gắng danh sách kệ vào Spinner
    private void setShelfSpinner(List<DisplayShelves> displayShelves) {
        shelfApdapter = new ShelfApdapter(getContext(), R.layout.item_shelf_selected, displayShelves);
        shelfSpinner.setAdapter(shelfApdapter);
        shelfSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý khi một mục được chọn
                DisplayShelves displayShelves1 = shelfApdapter.getItem(position);
                displShelfIsSelcted = displayShelves1;
                indexShelf = position;
                if (!displayShelves1.getShelfName().equals("Chọn kệ")) {
                    getLitsProductPoiton(displayShelves1.getDisSheID(), displayPlatterList.get(index).getDisPlaID(), 1);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có mục nào được chọn
            }
        });
    }

    private void AddShelf() {
        addOneShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayShelves displayShelves = new DisplayShelves(listShelf.size(), "Kệ " + listShelf.size(), new Store(1));
                APIService.apiService.insertShelf(displayShelves).enqueue(new Callback<DisplayShelves>() {
                    @Override
                    public void onResponse(Call<DisplayShelves> call, Response<DisplayShelves> response) {
                        listShelf.add(displayShelves);
                        shelfApdapter.setChangeData(listShelf);
                        shelfSpinner.setSelection(listShelf.size());
                    }

                    @Override
                    public void onFailure(Call<DisplayShelves> call, Throwable t) {

                    }
                });

            }
        });

    }

    private void insertPlatter() {
        addOnePlatter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayPlatter displayPlatter = new DisplayPlatter();
                displayPlatter.setDisPlaID(displayPlatterList.size() + 1);
                displayPlatter.setRowName("Mâm " + (displayPlatterList.size() + 1));
                displayPlatter.setStore(new Store(1));
                APIService.apiService.insertPlatter(displayPlatter).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        displayPlatterList.add(displayPlatter);
                        platterNumber.setText(displayPlatter.getRowName());
                        index = displayPlatterList.size() - 1;
                        listPossitioning.clear();
                        positionAdapter.setData(listPossitioning);

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });

    }

    private void addEvent() {
        insertShelf();
        addPositionByScan();
        toNextRow();
        toPrevousRow();
        insertPlatter();
    }

    private void setAdapterList() {
        positionAdapter = new PositionAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        listPositionItem.setLayoutManager(linearLayoutManager);
        listPositionItem.setAdapter(positionAdapter);
    }

    private void addPositionByScan() {
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(indexShelf == 0)) {
                    ScanCode();
                } else {
                    Toast.makeText(getActivity(), "Hãy chọn kệ!", Toast.LENGTH_SHORT).show();

                }
            }


        });
    }

    private void ScanCode() {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Quét mã Barcode và QR code");
        scanOptions.setBeepEnabled(true);
        scanOptions.setOrientationLocked(true);
        scanOptions.setCaptureActivity(CaptureAct.class);
        activityResultLauncher.launch(scanOptions);
    }

    ActivityResultLauncher<ScanOptions> activityResultLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            this.result = result.getContents();
            OpenDialog();
        }
    });

    private void toNextRow() {

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indexShelf != 0) {
                    if (index < displayPlatterList.size()-1) {
                        index++;
                        platterNumber.setText(displayPlatterList.get(index).getRowName());
                        getLitsProductPoiton(displShelfIsSelcted.getDisSheID(), displayPlatterList.get(index).getDisPlaID(), 1);
                    }
                } else {

                    Toast.makeText(getActivity(), "Hãy chọn kệ:", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

    private void toPrevousRow() {
        prevous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indexShelf != 0) {
                    if (index > 0) {
                        index--;
                        platterNumber.setText(displayPlatterList.get(index).getRowName());
                        getLitsProductPoiton(displShelfIsSelcted.getDisSheID(), displayPlatterList.get(index).getDisPlaID(), 1);
                    }
                } else {
                    Toast.makeText(getActivity(), "Hãy chọn kệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void insertPosition(String produtID) {
        ProductPositioning productPositioning = new ProductPositioning(1, displayPlatterList.get(index), listShelf.get(indexShelf), new Product(produtID), Integer.parseInt(quantityDi.getText().toString()), new Store(1));
        APIService.apiService.insertProPosion(productPositioning).enqueue(new Callback<ProductPositioning>() {
            @Override
            public void onResponse(Call<ProductPositioning> call, Response<ProductPositioning> response) {

                listPossitioning.add(response.body());
                positionAdapter.setData(listPossitioning);
            }

            @Override
            public void onFailure(Call<ProductPositioning> call, Throwable t) {

            }
        });

    }

    private void OpenDialog() {
        dialog.show();
    }
}