package ioc.app.bachhoa.fm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import ioc.app.bachhoa.Apdapter.ShelfApdapter;
import ioc.app.bachhoa.CallBack.CallBackBachHoa;
import ioc.app.bachhoa.R;
import ioc.app.bachhoa.api.APIService;
import ioc.app.bachhoa.model.DisplayPlatter;
import ioc.app.bachhoa.model.DisplayShelves;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPosition_fm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPosition_fm extends Fragment {
    View view;
Spinner shelfSpinner;
ImageButton addOneShelf, scan, prevous, next;
RecyclerView listPositionItem;
List<DisplayShelves> listShelf;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_add_position_fm, container, false);
        uiInit();
        getListShelf();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showBottomNavigationView();
    }
    private void uiInit(){
        shelfSpinner = (Spinner) view.findViewById(R.id.ap_list_shelf);
        scan = (ImageButton) view.findViewById(R.id.ap_scan);
        addOneShelf = (ImageButton) view.findViewById(R.id.ap_add_shelf);
        listPositionItem =(RecyclerView) view.findViewById(R.id.ap_list_posstion);
        prevous = (ImageButton) view.findViewById(R.id.ap_prevous);
        next =(ImageButton)  view.findViewById(R.id.ap_next);

    }
    private void showBottomNavigationView() {
        final BottomNavigationView navigationView = getActivity().findViewById(R.id.navigation);
        if (navigationView != null) {
            navigationView.setVisibility(View.VISIBLE);
        }
    }
    private void insertShelf(){
addOneShelf.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        APIService.apiService.insertShelf(new DisplayShelves()).enqueue(new Callback<DisplayShelves>() {
            @Override
            public void onResponse(Call<DisplayShelves> call, Response<DisplayShelves> response) {

            }

            @Override
            public void onFailure(Call<DisplayShelves> call, Throwable t) {

            }
        });
    }
});

    }
    private List<DisplayShelves> getListShelf(){
        List<DisplayShelves> shelvesList = null;
        APIService.apiService.getshelfs(1).enqueue(new Callback<List<DisplayShelves>>() {
            @Override
            public void onResponse(Call<List<DisplayShelves>> call, Response<List<DisplayShelves>> response) {
List<DisplayShelves> list = response.body();
setShelfSpinner(list);
            }

            @Override
            public void onFailure(Call<List<DisplayShelves>> call, Throwable t) {

            }
        });
        return shelvesList;

    }
    private void setShelfSpinner(List<DisplayShelves> displayShelves){
        ShelfApdapter shelfApdapter = new ShelfApdapter(getContext(),R.layout.item_shelf_selected,displayShelves);
        shelfSpinner.setAdapter(shelfApdapter);
        shelfSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý khi một mục được chọn

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có mục nào được chọn
            }
        });
    }
    private void getListPlatter(final CallBackBachHoa<List<DisplayPlatter>> callback){

        APIService.apiService.getListFlatter(1).enqueue(new Callback<List<DisplayPlatter>>() {
            @Override
            public void onResponse(Call<List<DisplayPlatter>> call, Response<List<DisplayPlatter>> response) {
                if (response.isSuccessful()) {
                    List<DisplayPlatter> displayPlatterList = response.body();

                    // Gọi callback và truyền danh sách DisplayPlatter cho nó
                    callback.onResponseReceived(displayPlatterList);
                } else {
                    // Xử lý trường hợp không thành công (ví dụ: lỗi mạng, lỗi server, ...).
                }
            }

            @Override
            public void onFailure(Call<List<DisplayPlatter>> call, Throwable t) {

            }
        });
    }
}