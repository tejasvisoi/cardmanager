package com.example.cardmanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;

public class CardDemoActivity extends Activity {

    public static final String FAVORITES = "Product_Favorite";
    private static final int PICK_IMAGE=1;
    private ArrayList<ExampleItem> exampleList;
    EditText customDialog_EditText1;
    EditText customDialog_EditText2;
    static final int CUSTOM_DIALOG_ID = 0;
    Button customDialog_Update;
    int position=0;
    boolean doubleBackToExitPressedOnce = false;
    TextView tv;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter madapter;
    private Button buttonInsert;
    Button delete,upload,save;
    ImageView imageView;
    TextView display,added;
    String selectedImage;
    Uri hell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadData();
        display=findViewById(R.id.textView);
        getTimeFromAndroid();
        tv=findViewById(R.id.hello);
        buildRecyclerView();
        save=findViewById(R.id.button3);
        onButtons();
        EditText editText = findViewById(R.id.search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(CUSTOM_DIALOG_ID);
        }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               saveData();

            }
        });
    }
    private void filter(String text) {
        ArrayList<ExampleItem> filteredList = new ArrayList<>();

        for (ExampleItem item : exampleList) {
            if (item.getText1().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        madapter.filterList(filteredList);
    }

  public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<ExampleItem>>() {
        }.getType();
        exampleList = gson.fromJson(json, type);
        if (exampleList == null) {
            exampleList = new ArrayList<>();
        }
    }

    private void getImage(){
        Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,PICK_IMAGE);
        added.setVisibility(View.VISIBLE);

    }
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(exampleList);
        editor.putString("task list", json);
        editor.commit();
        editor.apply();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            hell = data.getData();
            selectedImage=hell.toString();
        }

    }
    private Button.OnClickListener addimage=new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            getImage();

        }

    };
    private void getTimeFromAndroid() {
        Date dt = new Date();
        int hours = dt.getHours();
        int min = dt.getMinutes();
        if(hours>=1 && hours<=12){
            display.setText("Hi, Good Morning");
        }else if(hours>=12 && hours<=16){
            display.setText("Hi, Good Afternoon");
        }else if(hours>=16 && hours<=24){
            display.setText("Hi, Good Evening");
        }
    }

    private Button.OnClickListener customDialog_UpdateOnClickListener = new Button.OnClickListener(){
        @Override
        public void onClick(View arg0) {

            String name=customDialog_EditText1.getText().toString().trim();
            String number=customDialog_EditText2.getText().toString().trim();

            if (name.isEmpty()) {
                customDialog_EditText1.setError("Name is required");
                customDialog_EditText1.requestFocus();
                return;
            }
            else if (number.isEmpty()) {
                customDialog_EditText2.setError("Card Number is required");
                customDialog_EditText2.requestFocus();
                return;
            }
            else if(!name.isEmpty() && !number.isEmpty()) {
                position = exampleList.size();
                exampleList.add(position, new ExampleItem(selectedImage , name.toUpperCase(), number.toUpperCase()));

                customDialog_EditText1.setText("");
                customDialog_EditText2.setText("");
                madapter.notifyItemInserted(position);
                cardsnumber();
                customDialog_EditText1.setFocusableInTouchMode(true);
                customDialog_EditText1.requestFocus();
            }
            dismissDialog(CUSTOM_DIALOG_ID);
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {

        Dialog dialog = null;
        switch(id) {
            case CUSTOM_DIALOG_ID:
                dialog = new Dialog(CardDemoActivity.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.activity_main3);
                dialog.setTitle("Custom Dialog");
                customDialog_EditText1 = dialog.findViewById(R.id.editText);
                customDialog_EditText2 = dialog.findViewById(R.id.editText2);
                customDialog_Update = dialog.findViewById(R.id.but);
                added=dialog.findViewById(R.id.added);
                upload=dialog.findViewById(R.id.upload);
                upload.setOnClickListener(addimage);
                customDialog_Update.setOnClickListener(customDialog_UpdateOnClickListener);
                customDialog_EditText1.setFocusableInTouchMode(true);
                customDialog_EditText1.requestFocus();

                break;
        }
        return dialog;

    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Save and click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 1500);
    }

    public void cardsnumber(){
        int position = exampleList.size();
        tv.setText("Number Of Cards "+position);

    }

    public void removeItem(int position) {
        exampleList.remove(position);
        madapter.notifyItemRemoved(position);
    }
    public void removecount() {
        int position = exampleList.size();
        position--;
        tv.setText("Number Of Cards "+position);
    }

    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        madapter = new RecyclerAdapter(exampleList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(madapter);
        madapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(CardDemoActivity.this,nextview.class);
                intent.putExtra("Example Item",exampleList.get(position));
                startActivity(intent);
            }
            @Override
            public void onDeleteClick(int position) {
                removecount();
                removeItem(position);
            }
        });
    }
    public void onButtons(){
        buttonInsert = findViewById(R.id.button2);
        delete=findViewById(R.id.delete);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(CUSTOM_DIALOG_ID);
            }
        });
    }


}