package heuroix.myapps.com.heuroix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Register3 extends AppCompatActivity {
    private EditText email, alamat,notelp;
    private ImageView buttonNext;
    private String mPostKeyNama = null;
    private String mPostKeyUsername = null;
    private String mPostKeyPassword = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        email = findViewById(R.id.email);
        alamat = findViewById(R.id.alamat);
        notelp = findViewById(R.id.notelp);
        buttonNext = findViewById(R.id.buttonnext);

        mPostKeyNama = getIntent().getExtras().getString("nama");
        mPostKeyUsername = getIntent().getExtras().getString("username");
        mPostKeyPassword = getIntent().getExtras().getString("password");
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register3.this, Register4.class);
                intent.putExtra("nama", mPostKeyNama);
                intent.putExtra("username",mPostKeyUsername);
                intent.putExtra("password", mPostKeyPassword);
                intent.putExtra("email", email.getText().toString());
                intent.putExtra("alamat", alamat.getText().toString());
                intent.putExtra("notelp", notelp.getText().toString());
                startActivity(intent);
            }
        });

    }
}
