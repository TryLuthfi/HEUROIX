package heuroix.myapps.com.heuroix;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FragmentFour extends Fragment {
    private String id_user, namaa, username, password, userimage, emaill, alamat, notelp,register_date,
            id_content, judull, gambarr, deskripsii, comment, sukaa,date_created ;
    private int suka_status;
    private String JSON_STRING;
    private TextView nama, nama2;
    private ImageView gambar, gambar2;
    private RelativeLayout linear;
    private ProgressBar loading, loading2;

    private static final String URL_PRODUCTS = "https://heuroix.000webhostapp.com/contentpreview.php";
    Context context;
    private static final int NUM_COLUMNS = 3;
    List<Content> productList;
    RecyclerView recyclerView;
    View view;


    public FragmentFour() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_four, container, false);

        productList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recylcerView);
        StaggeredRecyclerViewAdapterdua staggeredRecyclerViewAdapterdua =
                new StaggeredRecyclerViewAdapterdua(getActivity(), productList);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(staggeredRecyclerViewAdapterdua);

        loadProducts();

        nama2= view.findViewById(R.id.nama2);
        gambar2 = view.findViewById(R.id.gambar2);
        linear = view.findViewById(R.id.linear);
        loading = view.findViewById(R.id.loading);
        loading2 = view.findViewById(R.id.loading2);

        linear.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);
        loading2.setVisibility(View.VISIBLE);

        getJSON();

        return  view;
    }

    @SuppressLint("SetTextI18n")
    private void showData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                id_user = jo.getString(konfigurasi.id_user);
                namaa = jo.getString(konfigurasi.nama);
                username = jo.getString(konfigurasi.username);
                password = jo.getString(konfigurasi.password);
                userimage = jo.getString(konfigurasi.userimage);
                emaill = jo.getString(konfigurasi.email);
                alamat = jo.getString(konfigurasi.alamat);
                notelp = jo.getString(konfigurasi.notelp);
                register_date = jo.getString(konfigurasi.register_date);

                HashMap<String, String> data = new HashMap<>();
                data.put(konfigurasi.id_user, id_user);

                list.add(data);

                String id_userr = getId_user();
                if (id_userr.equals(jo.getString("id_user"))) {
                    RequestOptions requestOptions = new RequestOptions()
                            .placeholder(R.drawable.ic_launcher_background);
                    if(userimage.equals("empty")){
                        Glide.with(getActivity()).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQiZSMtcZ3iQz-C09z2XAkYukrdsxrXRvrRl6myil68joLMHUM").into(gambar2);
                    } else {
                        Glide.with((Objects.requireNonNull(getActivity()))).load("https://heuroix.000webhostapp.com/userImage/" + userimage).apply(requestOptions).into(gambar2);
                    }
                    nama2.setText(""+namaa);
                }

                linear.setVisibility(View.VISIBLE);
                loading.setVisibility(View.INVISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                showData();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_DATAUSER);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
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
                                            product.getString("userimage")
                                    ));
                                }
                            }

                            StaggeredRecyclerViewAdapterdua adapter = new StaggeredRecyclerViewAdapterdua(getActivity(), productList);

                            if (adapter != null){
                                recyclerView.setAdapter(adapter);

                                loading2.setVisibility(View.INVISIBLE);
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
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private String getId_user(){
        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_user = preferences.getString("id_user", "null");
        return id_user;
    }
}
