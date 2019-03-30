package heuroix.myapps.com.heuroix.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import heuroix.myapps.com.heuroix.R;
import heuroix.myapps.com.heuroix.konfigurasi.konfigurasi;
import heuroix.myapps.com.heuroix.request.RequestHandler;

public class UserProfile extends AppCompatActivity {
    private ImageView gambar, gambar2;
    private TextView nama, nama2, username, password, email, alamat, notelp;
    public String id_user, namaa = null, usernamee, passwordd, userimage, emaill, alamatt, notelpp, register_date;
    private String JSON_STRING;
    private RelativeLayout linear;
    private ProgressBar loading;
    public String namaIntent, userimageIntent, usernameIntent, passwordIntent, emailIntent, alamatIntent, notelpIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        gambar = findViewById(R.id.gambar);
        gambar2 = findViewById(R.id.gambar2);
        nama = findViewById(R.id.nama);
        nama2 = findViewById(R.id.nama2);
        email = findViewById(R.id.email);
        alamat = findViewById(R.id.alamat);
        notelp = findViewById(R.id.notelp);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        linear = findViewById(R.id.linear);
        loading = findViewById(R.id.loading);

        final CardView btn1 = findViewById(R.id.btn_fragment1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = UserProfile.this.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.sequential_from_bottom,0,0,R.anim.sequental_out_top);
                fragmentTransaction.add(R.id. frame_container , new ChangeProfile());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        final CardView btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                startActivity(new Intent(UserProfile.this, Login.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                editor.clear();
                editor.apply();
                finish();
            }
        });

        linear.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);

        getJSON();
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
                    namaIntent = namaa;
                    userimageIntent = userimage;
                    usernameIntent = usernamee;
                    passwordIntent = passwordd;
                    emailIntent = emaill;
                    alamatIntent = alamatt;
                    notelpIntent = notelpp;

                    RequestOptions requestOptions = new RequestOptions()
                            .placeholder(R.drawable.ic_launcher_background);

                    if (userimage.equals("empty")) {
                        Glide.with(getApplicationContext()).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQiZSMtcZ3iQz-C09z2XAkYukrdsxrXRvrRl6myil68joLMHUM").into(gambar);
                        Glide.with(getApplicationContext()).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQiZSMtcZ3iQz-C09z2XAkYukrdsxrXRvrRl6myil68joLMHUM").into(gambar2);
                    } else {
                        Glide.with(getApplicationContext()).load("https://heuroix.000webhostapp.com/userImage/" + userimage).into(gambar);
                        Glide.with(getApplicationContext()).load("https://heuroix.000webhostapp.com/userImage/" + userimage).into(gambar2);
                    }

                    nama.setText(namaa);
                    nama2.setText(namaa);
                    username.setText(usernamee);
                    password.setText(passwordd);
                    email.setText(emaill);
                    alamat.setText(alamatt);
                    notelp.setText(notelpp);
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

    private String getId_user() {
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_user = preferences.getString("id_user", "null");
        return id_user;
    }

    public void back(View view) {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
