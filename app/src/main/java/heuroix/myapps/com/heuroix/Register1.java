package heuroix.myapps.com.heuroix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Register1 extends AppCompatActivity {
    private EditText nama;
    private ImageView buttonNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        nama = findViewById(R.id.nama);
        buttonNext = findViewById(R.id.buttonnext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Register1.this, Register2.class);
                intent.putExtra("nama", nama.getText().toString());
                startActivity(intent);
            }
        });

    }
}
