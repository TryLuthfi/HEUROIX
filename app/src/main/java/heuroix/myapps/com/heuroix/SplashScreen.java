package heuroix.myapps.com.heuroix;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class SplashScreen extends AppCompatActivity {

    ImageView bg;
    int waktu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        bg = findViewById(R.id.background);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("HH");
        String formattedDate = date.format(c.getTime());
        int waktu = Integer.parseInt(formattedDate);
        backgroundChange(waktu);
        timer();
    }

    private void timer() {
        final Intent i = new Intent(this,Login.class);
        Thread timer = new Thread(){
            public void run (){
                try{
                    sleep(5000);
                } catch (InterruptedException e){
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
        int[] images = {R.drawable.c,R.drawable.b,R.drawable.m};
        Random rand = new Random();
        bg.setImageResource(images[rand.nextInt(images.length)]);
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
}
