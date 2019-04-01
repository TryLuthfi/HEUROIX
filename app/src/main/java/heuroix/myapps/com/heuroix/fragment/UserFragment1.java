package heuroix.myapps.com.heuroix.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import heuroix.myapps.com.heuroix.json.Content;
import heuroix.myapps.com.heuroix.R;
import heuroix.myapps.com.heuroix.adapter.StaggeredRecyclerViewAdapterdua;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment1 extends Fragment {
    private ProgressBar loading;

    private static final String URL_PRODUCTS = "https://heuroix.000webhostapp.com/contentpreview.php";
    Context context;
    private static final int NUM_COLUMNS = 3;
    List<Content> productList;
    RecyclerView recyclerView;
    View view;
    LinearLayout loadingbackground;


    public UserFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_fragment1, container, false);

        productList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recylcerView);
        StaggeredRecyclerViewAdapterdua staggeredRecyclerViewAdapterdua =
                new StaggeredRecyclerViewAdapterdua(getActivity(), productList);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(staggeredRecyclerViewAdapterdua);

        loading = view.findViewById(R.id.loading);
        loadingbackground = view.findViewById(R.id.loadingbackground);
        loading.setVisibility(View.VISIBLE);
        loadProducts();
        return  view;
    }

    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);


                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                String id_user = getId_user();
                                if (id_user.equals(product.getString("id_user"))) {
                                    productList.add(new Content(
                                            product.getString("id_content"),
                                            product.getString("id_user"),
                                            product.getString("judul"),
                                            product.getString("gambar"),
                                            product.getString("deskripsi"),
                                            product.getString("date_created"),
                                            product.getString("nama"),
                                            product.getString("email"),
                                            product.getString("userimage"),
                                            product.getString("likecontent")
                                    ));
                                }
                            }

                            StaggeredRecyclerViewAdapterdua adapter = new StaggeredRecyclerViewAdapterdua(getActivity(), productList);

                            if (adapter != null){
                                if (!isAdded()) return;
                                recyclerView.setAdapter(adapter);

                                loading.setVisibility(View.INVISIBLE);
                                loadingbackground.setVisibility(View.INVISIBLE);
                            }else {
                                Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
                            }

//                            loading.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(Objects.requireNonNull(getActivity())).add(stringRequest);
    }

    private String getId_user(){
        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_user = preferences.getString("id_user", "null");
        return id_user;
    }

}
