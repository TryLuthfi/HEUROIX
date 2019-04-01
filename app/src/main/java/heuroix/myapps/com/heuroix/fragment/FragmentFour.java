package heuroix.myapps.com.heuroix.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import heuroix.myapps.com.heuroix.R;
import heuroix.myapps.com.heuroix.request.RequestHandler;
import heuroix.myapps.com.heuroix.konfigurasi.konfigurasi;


public class FragmentFour extends Fragment {
    private String id_user, namaa, username, password, userimage, emaill, alamat, notelp,register_date,
            id_content, judull, gambarr, deskripsii, comment, sukaa,date_created ;
    private int suka_status;
    private String JSON_STRING;
    private TextView nama, nama2;
    private ImageView gambar, gambar2;
    private RelativeLayout linear;
    private ProgressBar loading, loading2;

    public FragmentFour() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_four, container, false);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.add(R.id.frame_container, new UserFragment1());
        fragmentTransaction.commit();

        final LinearLayout btn1 = view.findViewById(R.id.btn_fragment1);
        final LinearLayout btn2 = view.findViewById(R.id.btn_fragment2);
        final LinearLayout btn3 = view.findViewById(R.id.btn_fragment3);
        final ImageView icon1 = view.findViewById(R.id.icon1);
        final ImageView icon2 = view.findViewById(R.id.icon2);
        final ImageView icon3 = view.findViewById(R.id.icon3);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icon1.setBackgroundResource(R.drawable.listafter);
                icon2.setBackgroundResource(R.drawable.rectangle);
                icon3.setBackgroundResource(R.drawable.heart);
//                btn2.setTextColor(Color.parseColor("#c23c1b"));
//                btn1.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.colorAccent ));
                FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id. frame_container , new UserFragment1());
                fragmentTransaction.commit();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icon1.setBackgroundResource(R.drawable.list);
                icon2.setBackgroundResource(R.drawable.rectangleafter);
                icon3.setBackgroundResource(R.drawable.heart);
                FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id. frame_container , new UserFragment2());
                fragmentTransaction.commit();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icon1.setBackgroundResource(R.drawable.list);
                icon2.setBackgroundResource(R.drawable.rectangle);
                icon3.setBackgroundResource(R.drawable.heartafter);
                FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id. frame_container , new UserFragment3());
                fragmentTransaction.commit();
            }
        });

        nama2 = view.findViewById(R.id.nama2);
        gambar2 = view.findViewById(R.id.gambar2);
        linear = view.findViewById(R.id.linear);
        loading = view.findViewById(R.id.loading);

        linear.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);

        getJSON();

        return view;
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
                        Glide.with(Objects.requireNonNull(getActivity())).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQiZSMtcZ3iQz-C09z2XAkYukrdsxrXRvrRl6myil68joLMHUM").into(gambar2);
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

    private String getId_user(){
        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_user = preferences.getString("id_user", "null");
        return id_user;
    }
}
