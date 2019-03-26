package heuroix.myapps.com.heuroix.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import heuroix.myapps.com.heuroix.konfigurasi.konfigurasi;
import heuroix.myapps.com.heuroix.request.RequestHandler;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeProfile extends Fragment {
    private ImageView gambar, gambar2;
    private TextView nama2;
    private EditText nama, username, password, email, alamat, notelp;
    private String id_user, namaa, usernamee, passwordd, userimage, emaill, alamatt, notelpp, register_date;
    private String JSON_STRING;
    private RelativeLayout linear;
    private ProgressBar loading;


    public ChangeProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_profile, container, false);

        gambar2 = view.findViewById(R.id.gambar2);
        nama2 = view.findViewById(R.id.nama2);
        email = view.findViewById(R.id.email);
        alamat = view.findViewById(R.id.alamat);
        notelp = view.findViewById(R.id.notelp);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);

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
                usernamee = jo.getString(konfigurasi.username);
                passwordd = jo.getString(konfigurasi.password);
                userimage = jo.getString(konfigurasi.userimage);
                emaill = jo.getString(konfigurasi.email);
                alamatt = jo.getString(konfigurasi.alamat);
                notelpp = jo.getString(konfigurasi.notelp);
                register_date = jo.getString(konfigurasi.register_date);

                HashMap<String, String> data = new HashMap<>();
                data.put(konfigurasi.id_user, id_user);

                list.add(data);

                String id_user = getId_user();
                if (id_user.equals(jo.getString("id_user"))) {
                    RequestOptions requestOptions = new RequestOptions()
                            .placeholder(R.drawable.ic_launcher_background);

                    if (userimage.equals("empty")) {
                        Glide.with(Objects.requireNonNull(getActivity())).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQiZSMtcZ3iQz-C09z2XAkYukrdsxrXRvrRl6myil68joLMHUM").into(gambar2);
                    } else {
                        Glide.with(Objects.requireNonNull(getActivity())).load("https://heuroix.000webhostapp.com/userImage/" + userimage).into(gambar2);
                    }

                    nama2.setText(namaa);
                    username.setText(usernamee);
                    password.setText(passwordd);
                    email.setText(emaill);
                    alamat.setText(alamatt);
                    notelp.setText(notelpp);
                }
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

    private String getId_user() {
        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_user = preferences.getString("id_user", "null");
        return id_user;
    }

}
