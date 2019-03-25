package heuroix.myapps.com.heuroix;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Home extends AppCompatActivity {
    private Uri contentUri;
    private File f;
    private Bitmap imageUri;
    private LinearLayout bawah, bawah2, aa;
    private TextView tiga, empat;
    private static final int PICK_IMAGE = 100;
    private ImageView foto;
    private String JSON_STRING;
    private String id_user, nama, username, password, userimage, email, alamat, notelp,register_date;
    private int waktu;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tiga = findViewById(R.id.tiga);
        empat = findViewById(R.id.empat);
        aa = findViewById(R.id.aa);
        bawah = findViewById(R.id.bawah);
        bawah2 = findViewById(R.id.bawah2);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("HH");
        String formattedDate = date.format(c.getTime());
        waktu = Integer.parseInt(formattedDate);
        isNetworkConnectionAvailable();

        bawah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bawah2.setVisibility(View.VISIBLE);
                bawah2.startAnimation(AnimationUtils.loadAnimation(Home.this, R.anim.slide_anim_from_bottom));
            }
        });

        bawah2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bawah2.startAnimation(AnimationUtils.loadAnimation(Home.this, R.anim.slide_anim_from_top));
                bawah2.setVisibility(View.GONE);
            }
        });

        getJSON();
    }

    public void checkNetworkConnection(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean isNetworkConnectionAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected) {
            Log.d("Network", "Connected");
            return true;
        }
        else{
            checkNetworkConnection();
            Log.d("Network","Not Connected");
            return false;
        }
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {

            if (data != null) {
                contentUri = data.getData();

                try {
                    imageUri = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentUri);
                    String selectedPath = getPath(contentUri);
                    foto.setImageBitmap(imageUri);
                    f = new File(selectedPath);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }


    public void content(View view) {
        Intent intent = new Intent(Home.this, FragmentActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
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
                nama = jo.getString(konfigurasi.nama);
                username = jo.getString(konfigurasi.username);
                password = jo.getString(konfigurasi.password);
                userimage = jo.getString(konfigurasi.userimage);
                email = jo.getString(konfigurasi.email);
                alamat = jo.getString(konfigurasi.alamat);
                notelp = jo.getString(konfigurasi.notelp);
                register_date = jo.getString(konfigurasi.register_date);


                HashMap<String, String> data = new HashMap<>();
                data.put(konfigurasi.id_user, id_user);

                list.add(data);

                String id_user = getIdUser();

                if (id_user.equals(jo.getString("id_user"))) {
                    if (waktu >= 4 && waktu < 10) {
                        tiga.setText("Selamat Pagi " );
                        empat.setText(""+nama+" :)");
                    }
                    if (waktu >= 10 && waktu < 15) {
                        tiga.setText("Selamat Siang ");
                        empat.setText(""+nama+" :)");
                    }
                    if (waktu >= 15 && waktu < 19) {
                        tiga.setText("Selamat Sore ");
                        empat.setText(""+nama+" :)");
                    }
                    if (waktu >= 19) {
                        tiga.setText("Selamat Malam ");
                        empat.setText(""+nama+" :)");
                    }

                    if (userimage.equals("null")){
                        final Dialog dialog = new Dialog(Home.this);
                        dialog.setCancelable(true);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.setContentView(R.layout.custom_popup_dialog);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }
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
    private String getIdUser(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_user = preferences.getString("id_user", "null");
        return id_user;
    }

}
