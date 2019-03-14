package heuroix.myapps.com.heuroix;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class UserProfile extends AppCompatActivity {
    private ImageView gambar, gambar2;
    private TextView nama, nama2, username, password, email, alamat, notelp;
    private String id_user, namaa, usernamee, passwordd, userimage, emaill, alamatt, notelpp, register_date;
    private String JSON_STRING;
    private RelativeLayout linear;
    private ProgressBar loading;

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
                    RequestOptions requestOptions = new RequestOptions()
                            .placeholder(R.drawable.ic_launcher_background);

                    if (userimage.equals("empty")) {
                        Glide.with(UserProfile.this).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQiZSMtcZ3iQz-C09z2XAkYukrdsxrXRvrRl6myil68joLMHUM").into(gambar);
                        Glide.with(UserProfile.this).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQiZSMtcZ3iQz-C09z2XAkYukrdsxrXRvrRl6myil68joLMHUM").into(gambar2);
                    } else {
                        Glide.with(UserProfile.this).load("https://heuroix.000webhostapp.com/userImage/" + userimage).into(gambar);
                        Glide.with(UserProfile.this).load("https://heuroix.000webhostapp.com/userImage/" + userimage).into(gambar2);
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
}
