package com.peridot.o_der.server;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ServerMain extends AppCompatActivity {

    final DecimalFormat priceFormat = new DecimalFormat("###,###");
    RecyclerView recyclerView;
    OrderListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_main);

        recyclerView = findViewById(R.id.orderList_recycleView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OrderListAdapter();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this,
                new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String token = instanceIdResult.getToken();
                        println("등록 id : " + token);
                    }
                });


        //커피 리싸이클러뷰 db연결
        String url = "http://teamperidot.dothome.co.kr/orderList.php";

        Map<String, String> params = new HashMap();
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("response");
                    JSONObject jsonInnerObject;
                    Log.d("adapters","length "+jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {

                        jsonInnerObject = new JSONObject(jsonArray.get(i).toString());

                        int orderId = jsonInnerObject.getInt("ORDER_ID");

                        //주문자 식별 ID
                        String cusName = jsonInnerObject.getString("CUSTOMER_NAME");

                        //주문 메뉴
                        String OrderMenu = jsonInnerObject.getString("ORDER_MENU");
                        //주문 날짜
                        String OrderDate = jsonInnerObject.getString("ORDER_DATE");
                        //픽업 시간
                        String PickupTime = jsonInnerObject.getString("PICKUP_TIME");
                        //메모
                        String Order_Memo = jsonInnerObject.getString("ORDER_MEMO");

                        //가격, 가격 콤마표시
                        int inDB_orderPrice = jsonInnerObject.getInt("ORDER_PRICE");
                        String price = priceFormat.format(inDB_orderPrice);
                        String orderPrice = price + "원";

                        //어뎁터등록
                        adapter.addItem(new OrderList(orderId,cusName,OrderDate,OrderMenu,orderPrice,PickupTime,Order_Memo));

                    }
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);

//        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        Button main_logout_Btn = findViewById(R.id.main_logout_Button);   // 로그아웃 버튼 클릭 시
        main_logout_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //애니메이션 효과
                overridePendingTransition(R.anim.login_page_slide_in_left,R.anim.login_page_slide_out_right);
            }
        });

        Button menu_manage_Btn = findViewById(R.id.menu_manage_Btn);
        menu_manage_Btn.setOnClickListener(new View.OnClickListener() {    // 메뉴관리 버튼 클릭 시
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ServerMenuPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //애니메이션 효과
                overridePendingTransition(R.anim.login_page_slide_in_right, R.anim.login_page_slide_out_left);
            }
        });
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if(intent != null) {
            showDialog();
        }
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("주문");
        builder.setMessage("주문이 도착했습니다");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent3 = new Intent(getApplicationContext(),ServerMain.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intent3);
            }
        });
        builder.show();
    }

    public void println(String message) {
        Log.d("Main", message);
    }

}