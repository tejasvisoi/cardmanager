package com.example.cardmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class nextview extends AppCompatActivity {
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nextview);
        Intent intent=getIntent();
        ExampleItem exampleItem=intent.getParcelableExtra("Example Item");
        String image=exampleItem.getImageResource();
        ImageView img=findViewById(R.id.item_image1);
        img.setImageURI(Uri.parse(image));
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
