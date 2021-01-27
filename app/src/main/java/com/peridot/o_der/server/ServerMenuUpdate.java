package com.peridot.o_der.server;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

public class ServerMenuUpdate extends AppCompatActivity {

    static ServerMenuPage serverMenuPage = new ServerMenuPage();
    public static Context server_context_menu_update;
    EditText nameEditText;
    EditText priceEditText;
    static ImageView menuImage;
    TextView itemid;



    final static private String URL = "http://teamperidot.dothome.co.kr/item_update.php";
    final static private String URL2 = "http://teamperidot.dothome.co.kr/item_delete.php";

    static long imagename;
    static String id;
    static String tableName;
    static String name;
    static String price;
    static String img_path;
    String img_exist;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_menu_update);


        server_context_menu_update = this;

        nameEditText = findViewById(R.id.nameEditText);
        priceEditText = findViewById(R.id.priceEditText);
        menuImage = findViewById(R.id.coffee_image);
        itemid = findViewById(R.id.itemid);

        img_exist = "";
        img_path = "";
        imagename = 0;
        tableName="";
        name="";
        price="";

        Intent intent = getIntent();
        if (intent != null){
            itemid.setText(intent.getStringExtra("ID"));
            tableName = intent.getStringExtra("tableName");
        }


        serverMenuPage.ItemSetting(); //recyclerView의 데이터 불러오기

        menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        //수정 버튼
        Button Add_btn = findViewById(R.id.Add_btn);
        Add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = itemid.getText().toString();
                name = nameEditText.getText().toString();
                price = priceEditText.getText().toString();
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
                smpr.addStringParam("id", id);
                smpr.addStringParam("name", name);
                smpr.addStringParam("price", price);
                smpr.addStringParam("tableName", tableName);
                //이미지 파일 추가
                    //사진을 선택했음
                if(!img_path.equals("")) {
                 Log.d("insertDB","사진 있음");
                 img_exist = null;
                 smpr.addFile("img", img_path);
                 smpr.addStringParam("existImage",img_exist);
                    //사진을 선택하지 않았음
                }else if(img_path.equals("") || img_path==null || img_path.equals("null")){
                    smpr.addStringParam("existImage",img_exist);
                    Log.d("insertDB","사진 없음");
                  smpr.addFile("img", null);
                }
                //요청객체를 서버로 보낼 우체통 같은 객체 생성
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(smpr);

                Intent intent3 = new Intent(getApplicationContext(),ServerMenuPage.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intent3);
            }
        }); //메뉴 update 하기

        //삭제 버튼
        Button delete_btn = findViewById(R.id.delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(ServerMenuUpdate.this);
                builder.setMessage("정말로 삭제하시겠습니까?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        id = itemid.getText().toString();
                        SimpleMultiPartRequest smpr = new SimpleMultiPartRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        smpr.addStringParam("id", id);
                        smpr.addStringParam("tableName", tableName);

                        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(smpr);


                        Intent intent2 = new Intent(getApplicationContext(),ServerMenuPage.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent2);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        }); //메뉴 삭제 하기

        Button close_btn = findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void ImageSetting(String ImagePath,String dbPath){
        if(ImagePath!=null) {
            Glide.with(this).load(ImagePath).into(menuImage);
            img_exist = dbPath;
        }else if(ImagePath == null){
            menuImage.setImageResource(R.drawable.coffee_icon);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){
                    Uri uri = data.getData();
                    if(uri!=null){
                        Log.d("imagenotnull","uri not null");
                        menuImage.setImageURI(uri);
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

}
