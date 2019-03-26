package heuroix.myapps.com.heuroix.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import heuroix.myapps.com.heuroix.R;

public class Register2 extends AppCompatActivity {
    private EditText username, password;
    private ImageView buttonNext;
    private String mPostKeyNama ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        buttonNext = findViewById(R.id.buttonnext);

        mPostKeyNama = getIntent().getExtras().getString("nama");
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register2.this, Register3.class);
                intent.putExtra("nama", mPostKeyNama);
                intent.putExtra("username", username.getText().toString());
                intent.putExtra("password", password.getText().toString());
                startActivity(intent);
            }
        });
    }
}
