package heuroix.myapps.com.heuroix;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register4 extends AppCompatActivity {
    public static final String URL = "https://heuroix.000webhostapp.com/";
    private ProgressDialog progress;
    private ImageView registerBtn;
    private String mPostKeyNama ;
    private String mPostKeyUsername ;
    private String mPostKeyPassword ;
    private String mPostKeyEmail ;
    private String mPostKeyAlamat ;
    private String mPostKeyNoTelp ;

    @BindView(R.id.nama) EditText nama;
    @BindView(R.id.username) EditText username;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.email) EditText email;
    @BindView(R.id.alamat) EditText alamat;
    @BindView(R.id.notelp) EditText notelp;



    @OnClick(R.id.register) void daftar() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(Register4.this, android.R.style.Theme_DeviceDefault_Light_Dialog));
        LayoutInflater inflater = LayoutInflater.from(Register4.this);
        final View vv = inflater.inflate(R.layout.activity_register4, null);

        String namaS  = nama.getText().toString();
        String usernameS  = username.getText().toString();
        String passwordS  = password.getText().toString();
        String emailS  = email.getText().toString();
        String alamatS  = alamat.getText().toString();
        String notelpS  = notelp.getText().toString();

        progress = new ProgressDialog(Register4.this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<Value> call = api.daftar(namaS, usernameS, passwordS, emailS, alamatS, notelpS);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                progress.dismiss();
                if (value.equals("1")) {
                    Toast.makeText(Register4.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(""+message,response.toString());
                    Toast.makeText(Register4.this, message, Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(Register4.this, Login.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP ));
                finish();
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progress.dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register4);
        ButterKnife.bind(this);

        mPostKeyNama = getIntent().getExtras().getString("nama");
        mPostKeyUsername = getIntent().getExtras().getString("username");
        mPostKeyPassword = getIntent().getExtras().getString("password");
        mPostKeyEmail = getIntent().getExtras().getString("email");
        mPostKeyAlamat = getIntent().getExtras().getString("alamat");
        mPostKeyNoTelp = getIntent().getExtras().getString("notelp");

        registerBtn = findViewById(R.id.register);

        nama.setText(""+mPostKeyNama);
        username.setText(""+mPostKeyUsername);
        password.setText(""+mPostKeyPassword);
        email.setText(""+mPostKeyEmail);
        alamat.setText(""+mPostKeyAlamat);
        notelp.setText(""+mPostKeyNoTelp);
    }
}
