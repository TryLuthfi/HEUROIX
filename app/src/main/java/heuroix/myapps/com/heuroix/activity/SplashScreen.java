package heuroix.myapps.com.heuroix.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import heuroix.myapps.com.heuroix.R;
import heuroix.myapps.com.heuroix.konfigurasi.konfigurasi;
import heuroix.myapps.com.heuroix.request.RequestHandler;

public class SplashScreen extends AppCompatActivity {

    ImageView bg;
    int waktu;
    public static String id_content;
    public static String id_userr;
    private String JSON_STRING;
    int index = 0;
    public static TextView show;
    public static JSONArray result;


    @Override
    protected void onStart() {
        super.onStart();
        getJSON();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        bg = findViewById(R.id.background);
        show = findViewById(R.id.show);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("HH");
        String formattedDate = date.format(c.getTime());
        int waktu = Integer.parseInt(formattedDate);
        backgroundChange(waktu);
        timer();

    }

    private void timer() {
        final Intent i = new Intent(this, Login.class);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }

    private void backgroundChange(final int waktu) {
        bg.setImageResource(R.drawable.splash);
//        int[] images = {R.drawable.c,R.drawable.b,R.drawable.m};
//        Random rand = new Random();
//        bg.setImageResource(images[rand.nextInt(images.length)]);

//        if (waktu >= 4 && waktu < 10) {
//            Glide.with(SplashScreen.this).load(R.drawable.morning).into(bg);
//        }
//        if (waktu >= 10 && waktu < 15) {
//            Glide.with(SplashScreen.this).load(R.drawable.afternoon).into(bg);
//        }
//        if (waktu >= 15 && waktu < 19) {
//            Glide.with(SplashScreen.this).load(R.drawable.evening).into(bg);
//        }
//        if (waktu >= 19) {
//            Glide.with(SplashScreen.this).load(R.drawable.night).into(bg);
//        }

    }

    @SuppressLint("SetTextI18n")
    private void showData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY3);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                id_content = jo.getString(konfigurasi.id_content);
                id_userr = jo.getString(konfigurasi.id_user);

                HashMap<String, String> data = new HashMap<>();
                data.put(konfigurasi.id_content, id_userr);

                list.add(data);

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
                String s = rh.sendGetRequest(konfigurasi.URL_GET_DATALIKE);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
