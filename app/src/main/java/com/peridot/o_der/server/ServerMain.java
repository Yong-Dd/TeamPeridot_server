package com.peridot.o_der.server;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ServerMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_main);

        RecyclerView recyclerView = findViewById(R.id.orderList_recycleView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        OrderListAdapter adapter = new OrderListAdapter();

        adapter.addItem(new OrderList("홍길동", "010-0000-0000", "아메리카노",
                    "1", "3000", "16시 30분", "잘 부탁드립니다"));
        adapter.addItem(new OrderList("홍길동", "010-0000-0000", "아메리카노",
                "1", "3000", "16시 30분", "잘 부탁드립니다"));
        adapter.addItem(new OrderList("홍길동", "010-0000-0000", "아메리카노",
                "1", "3000", "16시 30분", "잘 부탁드립니다"));
        adapter.addItem(new OrderList("홍길동", "010-0000-0000", "아메리카노",
                "1", "3000", "16시 30분", "잘 부탁드립니다"));
        
        recyclerView.setAdapter(adapter);


        Button main_logout_Btn = findViewById(R.id.main_logout_Button);   // 로그아웃 버튼 클릭 시
        main_logout_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //애니메이션 효과
                overridePendingTransition(R.anim.login_page_slide_in_right, R.anim.login_page_slide_out_left);
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

}