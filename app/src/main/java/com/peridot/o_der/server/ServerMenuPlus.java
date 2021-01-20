package com.peridot.o_der.server;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ServerMenuPlus extends AppCompatActivity  {

    //임시 추가
    EditText nameEditText;
    EditText priceEditText;
    ImageView coffee_image;
    RadioGroup menuChoice;

    RadioButton Coffe_btn;
    RadioButton Dessert_btn;
    RadioButton Tea_btn;

    String img_path;
    final static  private String URL = "http://teamperidot.dothome.co.kr/server_insert_img.php";
    static long imagename;

    static String tableName;
    static String name;
    static String price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_menu_plus);


        //임시추가
        nameEditText = findViewById(R.id.nameEditText);
        priceEditText = findViewById(R.id.priceEditText);
        menuChoice = findViewById(R.id.menuChoice);

        Coffe_btn = findViewById(R.id.Coffee_btn);
        Dessert_btn = findViewById(R.id.Disert_btn);
        Tea_btn = findViewById(R.id.Tea_btn);

        img_path = "";
        imagename = 0;
        tableName="";
        name="";
        price="";

        coffee_image = findViewById(R.id.coffee_image);
        coffee_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        //추가 버튼
        Button Add_btn = findViewById(R.id.Add_btn);
        Add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEditText.getText().toString();
                price = priceEditText.getText().toString();
                onRadioButtonClicked(menuChoice);

                SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                //요청 객체에 보낼 데이터를 추가
                Log.d("insertDB","insert DB content "+tableName+","+name+","+price);
                smpr.addStringParam("tableName", tableName);
                smpr.addStringParam("name", name);
                smpr.addStringParam("price", price);
                //이미지 파일 추가
                if(!img_path.equals("")) {
                    Log.d("insertDB","사진 있음");
                    smpr.addFile("img", img_path);

                }else if(img_path.equals("")){
                    Log.d("insertDB","사진 없음");
                    smpr.addFile("img", null);
                }
                //요청객체를 서버로 보낼 우체통 같은 객체 생성
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(smpr);

                Toast.makeText(getApplicationContext(),"메뉴 추가가 요청되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });

        Button close_btn = findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){
                    Uri uri = data.getData();
                    if(uri!=null){
                        coffee_image.setImageURI(uri);
                        img_path = getRealPathFromUri(uri);
                    }
                }else{
                    Toast.makeText(this,"이미지선택을 하지 않았습니다",Toast.LENGTH_SHORT).show();
                }
                break;
        }


    }

    protected String getRealPathFromUri(Uri uri){
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this,uri,proj,null,null,null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();

        return result;
    }

    public void onRadioButtonClicked(View view) {
        if(Coffe_btn.isChecked()){
            tableName = "COFFEE";
        }else if(Dessert_btn.isChecked()){
            tableName = "DESSERT";
        }else if(Tea_btn.isChecked()){
            tableName = "TEA";
        }

    }

}
