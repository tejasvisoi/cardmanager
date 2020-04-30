package com.example.cardmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static int timeout = 2300;
    ImageView img,img2;
    Animation bottom;
    Animation top;
    Button login;
    private static String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img=(ImageView)findViewById(R.id.imageView2);
        img2=(ImageView)findViewById(R.id.imageView1);
        top= AnimationUtils.loadAnimation(this,R.anim.movemyface);
        img2.setAnimation(top);
        bottom= AnimationUtils.loadAnimation(this,R.anim.movemyass);
        img.setAnimation(bottom);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(MainActivity.this, CardDemoActivity.class);
                startActivity(i);
                finish();

            }
        }, timeout);

    }
}