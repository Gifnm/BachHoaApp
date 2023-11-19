package ioc.app.bachhoa.fm;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import ioc.app.bachhoa.R;
import ioc.app.bachhoa.model.ProductPositioning;
import ioc.app.bachhoa.ultil.PrintPriceTag;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductInfo_fm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductInfo_fm extends Fragment {
    ImageView img;
    TextView shelf, platter, location, quantity, inventory, status, nameOfProd, barcode, form;
    Button print;
    View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductInfo_fm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductInfo_fm.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductInfo_fm newInstance(String param1, String param2) {
        ProductInfo_fm fragment = new ProductInfo_fm();
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
        view = inflater.inflate(R.layout.fragment_product_info_fm, container, false);
        anhXa();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String json = bundle.getString("content");
            Gson gson = new Gson();
            ProductPositioning productPositioning = gson.fromJson(json, ProductPositioning.class);

            shelf.setText("Kệ: " + productPositioning.getDisplayShelves().getDisSheID());
            platter.setText("Mâm: " + productPositioning.getDisplayPlatter().getDisPlaID());
            location.setText("Vị trí " + productPositioning.getId());
            form.setText("Trưng " + bundle.getString("form"));
            quantity.setText("SL " + productPositioning.getDisplayQuantity());
            status.setText(productPositioning.getProduct().getStatus() == true ? "Kinh doanh" : "Ngưng KD");
            barcode.setText("Barcode: " + productPositioning.getProduct().getProductID());
            nameOfProd.setText(productPositioning.getProduct().getProductName());
            inventory.setText("Tồn: " + productPositioning.getProduct().getInventory());
            Glide.with(getContext())
                    .load(bundle.getString("image"))
                    .error(R.drawable.ic_baseline_cloud_download_24)
                    .into(img);
            printPriceTag(productPositioning);
        }

        return view;
    }

    private void anhXa() {
        print = view.findViewById((R.id.vpo_print));
        shelf = (TextView) view.findViewById(R.id.vpo_shelf);
        platter = view.findViewById(R.id.vpo_platter);
        location = view.findViewById(R.id.vpo_location);
        quantity = view.findViewById(R.id.vpo_quantity);
        inventory = view.findViewById(R.id.vpo_inventory);
        nameOfProd = view.findViewById(R.id.vpo_name);
        barcode = view.findViewById(R.id.vpo_barcode);
        img = view.findViewById(R.id.vpo_img);
        status = view.findViewById(R.id.vpo_status);
        form = view.findViewById(R.id.vpo_form);
    }

    private void printPriceTag(ProductPositioning productPositioning) {
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintPriceTag printPriceTag = new PrintPriceTag(getContext());
                //Bitmap bitmap = printPriceTag.generateOnePriceTag(productPositioning);
               // printPriceTag.printOnetag(bitmap);
            }
        });
    }
}