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
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class ServerMenuPage extends AppCompatActivity {

    public static Context server_context_menu;
    static CoffeeAdapter coffeeAdapter;
    static DisertAdapter disertAdapter;
    static TeaAdapter teaAdapter;

    static int coffee_position;
    static int dessert_position;
    static int tea_position;

    //원단위 구별 위해
    final DecimalFormat priceFormat = new DecimalFormat("###,###");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_menu_page);

        server_context_menu = this;

        RecyclerView coffeerecyclerView = findViewById(R.id.coffeerecyclerView);
        RecyclerView disertrecyclerView = findViewById(R.id.disertrecyclerView);
        RecyclerView tearecyclerView = findViewById(R.id.tearecyclerView);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        coffeerecyclerView.setLayoutManager(layoutManager);
        coffeeAdapter = new CoffeeAdapter();

       Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("response");
                    JSONObject jsonInnerObject;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonInnerObject = new JSONObject(jsonArray.get(i).toString());
                        int coffeeId = jsonInnerObject.getInt("COFFEE_ID");
                        String coffeeName = jsonInnerObject.getString("COFFEE_NAME");

                        //가격
                        int inDB_coffeePrice = jsonInnerObject.getInt("COFFEE_PRICE");
                        String price = priceFormat.format(inDB_coffeePrice);
                        String coffeePrice = price + "원";
                        Log.d("coffeeName", coffeeName + "," + coffeeId);
                        coffeeAdapter.addItem(new Coffee(coffeeName, coffeePrice));

                        //MenuFragment 전달 위함(리싸이클러뷰의 position과 i가 일치하게 됨)
                        //db_coffeePrice.add(i,inDB_coffeePrice);

                    }
                    coffeerecyclerView.setAdapter(coffeeAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        //coffee table 요청
        CoffeeRequest coffeeRequest = new CoffeeRequest(null,responseListener);
        RequestQueue coffee_queue = Volley.newRequestQueue(ServerMenuPage.this);
        coffee_queue.add(coffeeRequest);

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
                Intent intent = new Intent(getApplicationContext(), ServerMenuUpdate.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //애니메이션 효과
                overridePendingTransition(R.anim.login_page_slide_in_right, R.anim.login_page_slide_out_left);

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

        //디저트 db연결, 리사이클러뷰 add
        Response.Listener<JSONObject> responseListener2 = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("response");
                    JSONObject jsonInnerObject;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonInnerObject = new JSONObject(jsonArray.get(i).toString());
                        int dessertId = jsonInnerObject.getInt("DESSERT_ID");
                        String dessertName = jsonInnerObject.getString("DESSERT_NAME");

                        //가격
                        int inDB_dessertPrice = jsonInnerObject.getInt("DESSERT_PRICE");
                        String price = priceFormat.format(inDB_dessertPrice);
                        String dessertPrice = price + "원";

                        disertAdapter.addItem(new Disert(dessertName, dessertPrice));

                        //MenuFragment 전달 위함(리싸이클러뷰의 position과 i가 일치하게 됨)
                        //db_dessertPrice.add(i,inDB_dessertPrice);
                    }
                    disertrecyclerView.setAdapter(disertAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DessertRequest dessertRequest = new DessertRequest(null,responseListener2);
        RequestQueue dessert_queue = Volley.newRequestQueue(ServerMenuPage.this);
        dessert_queue.add(dessertRequest);

        disertrecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = disertrecyclerView.findChildViewUnder(e.getX(), e.getY());
                int position = disertrecyclerView.getChildAdapterPosition(child);

                if(child!=null&&gestureDetector.onTouchEvent(e)){
                    dessert_position = position;

                    Intent intent = new Intent(getApplicationContext(), ServerMenuUpdate.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    //애니메이션 효과
                    overridePendingTransition(R.anim.login_page_slide_in_right, R.anim.login_page_slide_out_left);
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

        //티 db연결, 리사이클러뷰 add
        Response.Listener<JSONObject> responseListener3 = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("response");
                    JSONObject jsonInnerObject;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonInnerObject = new JSONObject(jsonArray.get(i).toString());
                        int teaId = jsonInnerObject.getInt("TEA_ID");
                        String teaName = jsonInnerObject.getString("TEA_NAME");

                        //가격
                        int inDB_teaPrice = jsonInnerObject.getInt("TEA_PRICE");
                        String price = priceFormat.format(inDB_teaPrice);
                        String teaPrice = price + "원";

                        Log.d("teatea","tea:"+teaName+","+teaPrice);
                        teaAdapter.addItem(new Tea(teaName, teaPrice));

                        //MenuFragment 전달 위함(리싸이클러뷰의 position과 i가 일치하게 됨)
                        //db_teaPrice.add(i,inDB_teaPrice);

                    }
                    tearecyclerView.setAdapter(teaAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        TeaRequest teaRequest = new TeaRequest(null,responseListener3);
        RequestQueue tea_queue = Volley.newRequestQueue(ServerMenuPage.this);
        tea_queue.add(teaRequest);

        //tea touch listener
        tearecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = tearecyclerView.findChildViewUnder(e.getX(), e.getY());
                int position = tearecyclerView.getChildAdapterPosition(child);


                if(child!=null&&gestureDetector.onTouchEvent(e)){
                    tea_position = position;
                }
                Intent intent = new Intent(getApplicationContext(), ServerMenuUpdate.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //애니메이션 효과
                overridePendingTransition(R.anim.login_page_slide_in_right, R.anim.login_page_slide_out_left);
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        Button menu_plus_btn = findViewById(R.id.menu_plus_btn);
        menu_plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(),ServerMenuPlus.class);
                startActivity(intent2);
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
}