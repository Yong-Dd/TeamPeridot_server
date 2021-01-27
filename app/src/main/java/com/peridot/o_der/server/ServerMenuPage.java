package com.peridot.o_der.server;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerMenuPage extends AppCompatActivity {

    public static Context server_context_menu;
    static CoffeeAdapter coffeeAdapter;
    static DisertAdapter disertAdapter;
    static TeaAdapter teaAdapter;

    static int coffee_position;
    static int dessert_position;
    static int tea_position;

    static ArrayList<Integer> db_coffeePrice;
    static ArrayList<Integer> db_dessertPrice;
    static ArrayList<Integer> db_teaPrice;

    static int count2; //price 값
    //원단위 구별 위해
    final DecimalFormat priceFormat = new DecimalFormat("###,###");

    RecyclerView coffeerecyclerView;
    RecyclerView disertrecyclerView;
    RecyclerView tearecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_menu_page);

        coffee_position = -1;
        dessert_position = -1;
        tea_position = -1;

        db_coffeePrice = new ArrayList<>();
        db_dessertPrice = new ArrayList<>();
        db_teaPrice = new ArrayList<>();

        server_context_menu = this;

        coffeerecyclerView = findViewById(R.id.coffeerecyclerView);
        disertrecyclerView = findViewById(R.id.disertrecyclerView);
        tearecyclerView = findViewById(R.id.tearecyclerView);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        coffeerecyclerView.setLayoutManager(layoutManager);
        coffeeAdapter = new CoffeeAdapter();


        //커피 리싸이클러뷰 db연결
        String url = "http://teamperidot.dothome.co.kr/coffee1.php";

        Map<String, String> params = new HashMap();
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("response");
                    JSONObject jsonInnerObject;
                    Log.d("coffeeName", jsonArray.length()+"개 있음");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonInnerObject = new JSONObject(jsonArray.get(i).toString());
                        //커피 아이디
                        int coffeeId = jsonInnerObject.getInt("COFFEE_ID");

                        //커피 이름
                        String coffeeName = jsonInnerObject.getString("COFFEE_NAME");

                        //커피 가격
                        int inDB_coffeePrice = jsonInnerObject.getInt("COFFEE_PRICE");
                        String price = priceFormat.format(inDB_coffeePrice);
                        String coffeePrice = price + "원";

                        //커피 이미지
                        String coffeeImage = jsonInnerObject.getString("COFFEE_IMG");

                        Log.d("coffeeName", coffeeName + "," + coffeeId);
                        coffeeAdapter.addItem(new Coffee(Integer.toString(coffeeId),coffeeName, coffeePrice, coffeeImage));

                        //MenuFragment 전달 위함(리싸이클러뷰의 position과 i가 일치하게 됨)
                        db_coffeePrice.add(i,inDB_coffeePrice);

                    }
                    coffeerecyclerView.setAdapter(coffeeAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("menuAdapter","커피 어뎁터 response 오류 발생");
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);




        final GestureDetector gestureDetector = new GestureDetector(ServerMenuPage.this,new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }
        }); //onInterceptTouchEvent는 기본적으로 누르고 때고를 인식하여 2번 눌리는 효과가 생김, 그래서 onSingleTapUp을 넣어 1번 인식하는걸로 바꿈
        Intent intent = new Intent(getApplicationContext(),ServerMenuUpdate.class);
        coffee_position = 0;
        coffeerecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = coffeerecyclerView.findChildViewUnder(e.getX(), e.getY());//클릭시 X,Y좌표 구하기
                int position = coffeerecyclerView.getChildAdapterPosition(child);//해당 좌표가 몇번째 리사이클러뷰인지

                if(child!=null&&gestureDetector.onTouchEvent(e)) {
                    coffee_position = position;
                }
                /*Intent intent = new Intent(getApplicationContext(), ServerMenuUpdate.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //애니메이션 효과
                overridePendingTransition(R.anim.login_page_slide_in_right, R.anim.login_page_slide_out_left);*/

                Log.d("TAG", "클릭");

                return false;


            } //클릭시 발생하는 곳

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        }); //커피리사이클러뷰 클릭시 발생하는 이벤트 처리

        LinearLayoutManager layoutManager1 =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        disertrecyclerView.setLayoutManager(layoutManager1);
        disertAdapter = new DisertAdapter();


        //디저트 리싸이클러뷰 db 연결
        String url2 = "http://teamperidot.dothome.co.kr/dessert.php";

        Map<String, String> params2 = new HashMap();
        JSONObject parameters2 = new JSONObject(params2);

        JsonObjectRequest jsonRequest2 = new JsonObjectRequest(Request.Method.POST, url2, parameters2, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("response");
                    JSONObject jsonInnerObject;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonInnerObject = new JSONObject(jsonArray.get(i).toString());

                        //디저트 아이디
                        int dessertId = jsonInnerObject.getInt("DESSERT_ID");

                        //디저트 이름
                        String dessertName = jsonInnerObject.getString("DESSERT_NAME");

                        //디저트 가격
                        int inDB_dessertPrice = jsonInnerObject.getInt("DESSERT_PRICE");
                        String price = priceFormat.format(inDB_dessertPrice);
                        String dessertPrice = price + "원";

                        //디저트 이미지
                        String dessertImage = jsonInnerObject.getString("DESSERT_IMG");

                        disertAdapter.addItem(new Disert(Integer.toString(dessertId),dessertName, dessertPrice,dessertImage));

                        //MenuFragment 전달 위함(리싸이클러뷰의 position과 i가 일치하게 됨)
                        db_dessertPrice.add(i,inDB_dessertPrice);
                    }
                    disertrecyclerView.setAdapter(disertAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("menuAdapter","디저트 어뎁터 response 오류 발생");
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest2);


        //디저트 터치리스너
        disertrecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = disertrecyclerView.findChildViewUnder(e.getX(), e.getY());
                int position = disertrecyclerView.getChildAdapterPosition(child);

                if(child!=null&&gestureDetector.onTouchEvent(e)){
                    dessert_position = position;

                     /*Intent intent = new Intent(getApplicationContext(), ServerMenuUpdate.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    //애니메이션 효과
                    overridePendingTransition(R.anim.login_page_slide_in_right, R.anim.login_page_slide_out_left);*/
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        LinearLayoutManager layoutManager2 =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        tearecyclerView.setLayoutManager(layoutManager2);
        teaAdapter = new TeaAdapter();

        //티 리싸이클러뷰 db 연결
        String url3 = "http://teamperidot.dothome.co.kr/tea.php";

        Map<String, String> params3 = new HashMap();
        JSONObject parameters3 = new JSONObject(params3);

        JsonObjectRequest jsonRequest3 = new JsonObjectRequest(Request.Method.POST, url3, parameters3, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("response");
                    JSONObject jsonInnerObject;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonInnerObject = new JSONObject(jsonArray.get(i).toString());

                        //티 아이디
                        int teaId = jsonInnerObject.getInt("TEA_ID");

                        //티 이름
                        String teaName = jsonInnerObject.getString("TEA_NAME");

                        //티 가격
                        int inDB_teaPrice = jsonInnerObject.getInt("TEA_PRICE");
                        String price = priceFormat.format(inDB_teaPrice);
                        String teaPrice = price + "원";

                        //티 이미지
                        String teaImage = jsonInnerObject.getString("TEA_IMG");

                        Log.d("teatea","tea:"+teaName+","+teaPrice);
                        teaAdapter.addItem(new Tea(Integer.toString(teaId),teaName, teaPrice,teaImage));

                        //MenuFragment 전달 위함(리싸이클러뷰의 position과 i가 일치하게 됨)
                        db_teaPrice.add(i,inDB_teaPrice);

                    }
                    tearecyclerView.setAdapter(teaAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("menuAdapter","티 어뎁터 response 오류 발생");
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest3);


        //tea touch listener
        tearecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = tearecyclerView.findChildViewUnder(e.getX(), e.getY());
                int position = tearecyclerView.getChildAdapterPosition(child);


                if(child!=null&&gestureDetector.onTouchEvent(e)){
                    tea_position = position;
                }
                 /*Intent intent = new Intent(getApplicationContext(), ServerMenuUpdate.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    //애니메이션 효과
                    overridePendingTransition(R.anim.login_page_slide_in_right, R.anim.login_page_slide_out_left);*/
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        //메뉴 추가 버튼
        Button menu_plus_btn = findViewById(R.id.menu_plus_btn);
        menu_plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(),ServerMenuPlus.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent2);
            }
        });

        Button close_btn = findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.login_page_slide_in_left,R.anim.login_page_slide_out_right);
            }
        });
    }
    public void ItemSetting(){
        int coffee= coffee_position;
        int dessert = dessert_position;
        int tea = tea_position;

        String menuName = "";

        final String mainUrl = "http://teamperidot.dothome.co.kr/";

        if(coffee>-1){
            //메뉴 이름
            menuName = coffeeAdapter.getItem(coffee).getName();


            //가격 설정
            count2 = db_coffeePrice.get(coffee);

            //이미지 설정
            String image = coffeeAdapter.getItem(coffee).getImgPath();
            String url = mainUrl+image;

            if (!image.equals("null")) {
                ((ServerMenuUpdate)ServerMenuUpdate.server_context_menu_update).ImageSetting(url,image);
            }else{
                ((ServerMenuUpdate)ServerMenuUpdate.server_context_menu_update).ImageSetting(null,null);
            }
            //ServerMenuUpdate에 정보전달하기 손대지 않고 하는 방법으로 생각해보쟈
        }else if(dessert>-1){
            //메뉴 이름
            menuName = disertAdapter.getItem(dessert).getName();

            //가격 설정
            count2 = db_dessertPrice.get(dessert);

            //이미지 설정
            String image = disertAdapter.getItem(dessert).getImgPath();
            String url = mainUrl+image;
            if (!image.equals("null")) {
                ((ServerMenuUpdate)ServerMenuUpdate.server_context_menu_update).ImageSetting(url,image);
            }else{
                ((ServerMenuUpdate)ServerMenuUpdate.server_context_menu_update).ImageSetting(null,null);
            }
        }else if(tea>-1){
            //메뉴 이름
            menuName = teaAdapter.getItem(tea).getName();

            //가격 설정
            count2 = db_teaPrice.get(tea);

            //이미지 설정
            String image = teaAdapter.getItem(tea).getImgPath();
            String url = mainUrl+image;
            if (!image.equals("null")) {
                ((ServerMenuUpdate)ServerMenuUpdate.server_context_menu_update).ImageSetting(url,image);
            }else{
                ((ServerMenuUpdate)ServerMenuUpdate.server_context_menu_update).ImageSetting(null,null);
            }
        }
        //String price_format = priceFormat.format(count2);
        //String price_text = price_format+"원"+" 담기";
        ((ServerMenuUpdate)ServerMenuUpdate.server_context_menu_update).priceEditText.setText(Integer.toString(count2));
        ((ServerMenuUpdate)ServerMenuUpdate.server_context_menu_update).nameEditText.setText(menuName);
    } // 리사이클러뷰 내용을 update페이지에 동기화

    @Override
    protected void onResume() {
        super.onResume();
        coffeeAdapter.notifyDataSetChanged();
        coffeerecyclerView.setAdapter(coffeeAdapter);

        disertAdapter.notifyDataSetChanged();
        disertrecyclerView.setAdapter(disertAdapter);

        teaAdapter.notifyDataSetChanged();
        tearecyclerView.setAdapter(teaAdapter);
    }
}