package heuroix.myapps.com.heuroix.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import heuroix.myapps.com.heuroix.R;
import heuroix.myapps.com.heuroix.konfigurasi.konfigurasi;
import heuroix.myapps.com.heuroix.request.RequestHandler;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ContentClicked extends AppCompatActivity {

    private ImageView gambar, suka,userImage;
    private TextView jumlahsuka, judul, nama, email, deskripsi, aa;
    private LinearLayout linear, user;
    private ProgressBar loading;

    private String mPostKeyIdContent = null;
    private String mPostKeyJudul = null;
    private String mPostKeyGambar = null;
    private String mPostKeyNama = null;
    private String mPostKeyEmail = null;
    private String mPostKeyUserImage = null;
    private String mPostKeyDeskripsi = null;

    private String JSON_STRING;
    private String id_user, namaa, username, password, userimage, emaill, alamat, notelp,register_date,
    id_content, judull, gambarr, deskripsii,date_created ;
    private int suka_status;

    public static final String KEY_ID_CONTENT = "id_content";
    public static final String URL_LIKEPRESSED = "https://heuroix.000webhostapp.com/likepressed.php";
    public static final String URL_UNLIKEPRESSED = "https://heuroix.000webhostapp.com/unlikepressed.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_clicked);

        judul = findViewById(R.id.judul);
        suka = findViewById(R.id.suka);
        gambar = findViewById(R.id.gambar);
        jumlahsuka = findViewById(R.id.jumlahsuka);
        nama = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        userImage = findViewById(R.id.userImage);
        deskripsi = findViewById(R.id.deskripsi);
        linear = findViewById(R.id.linear);
        user = findViewById(R.id.user);
        loading = findViewById(R.id.loading);


        loading.setVisibility(View.VISIBLE);
        linear.setVisibility(View.INVISIBLE);

        mPostKeyIdContent = getIntent().getExtras().getString("id_content");
        mPostKeyJudul = getIntent().getExtras().getString("judul");
        mPostKeyGambar = getIntent().getExtras().getString("gambar");
        mPostKeyNama = getIntent().getExtras().getString("nama");
        mPostKeyEmail = getIntent().getExtras().getString("email");
        mPostKeyUserImage = getIntent().getExtras().getString("userimage");
        mPostKeyDeskripsi = getIntent().getExtras().getString("deskripsi");

        Glide.with((ContentClicked.this)).load("https://heuroix.000webhostapp.com/image/" + mPostKeyGambar).into(gambar);

        gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gambarPreview();
            }
        });

        getJSON();
    }

    @SuppressLint("SetTextI18n")
    private void showData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY2);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                id_content = jo.getString(konfigurasi.id_content);
                judull = jo.getString(konfigurasi.judul);
                gambarr = jo.getString(konfigurasi.gambar);
                deskripsii = jo.getString(konfigurasi.deskripsi);
                date_created = jo.getString(konfigurasi.date_created);
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
                data.put(konfigurasi.id_content, id_content);

                list.add(data);

                if (mPostKeyIdContent.equals(jo.getString("id_content"))) {
                    judul.setText("" + judull);
                    nama.setText("" + namaa);
                    user.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ContentClicked.this, Profile_Preview.class);
                            intent.putExtra("id_user", id_user);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
                        }
                    });
                    email.setText("" + emaill);
                    deskripsi.setText(""+deskripsii);
                    if(userimage.equals("empty")){
                        Glide.with(ContentClicked.this).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQiZSMtcZ3iQz-C09z2XAkYukrdsxrXRvrRl6myil68joLMHUM").into(userImage);
                    } else {
                        Glide.with(ContentClicked.this).load("https://heuroix.000webhostapp.com/userImage/" + userimage).into(userImage);
                    }
                    Glide.with((ContentClicked.this)).load(R.drawable.likebefore).into(suka);
                    suka.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Glide.with((ContentClicked.this)).load(R.drawable.likeafter).into(suka);
                        }
                    });

                }

                loading.setVisibility(View.INVISIBLE);
                linear.setVisibility(View.VISIBLE);
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
                String s = rh.sendGetRequest(konfigurasi.URL_GET_DATACONTENT);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void gambarPreview() {
        final Dialog dialog = new Dialog(ContentClicked.this);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.custom_dialog);
        ImageView gambar = dialog.findViewById(R.id.gambar);
        Glide.with((ContentClicked.this)).load("https://heuroix.000webhostapp.com/image/" + mPostKeyGambar).into(gambar);
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(gambar);
        photoViewAttacher.update();
        dialog.show();
    }

    private void addData(final String id_content) {
        class AddData extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(KEY_ID_CONTENT, id_content);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(URL_LIKEPRESSED, params);
                return res;
            }
        }

        AddData ae = new AddData();
        ae.execute();
    }

    private void addData2(final String id_content) {
        class AddData extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(KEY_ID_CONTENT, id_content);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(URL_UNLIKEPRESSED, params);
                return res;
            }
        }

        AddData ae = new AddData();
        ae.execute();
    }
}
