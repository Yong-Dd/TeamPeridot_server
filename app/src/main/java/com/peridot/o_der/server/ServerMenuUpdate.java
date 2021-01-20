package com.peridot.o_der.server;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class ServerMenuUpdate extends AppCompatActivity {

    static ServerMenuPage serverMenuPage = new ServerMenuPage();
    public static Context server_context_menu_update;
    EditText nameEditText;
    EditText priceEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_menu_update);

        server_context_menu_update = this;

        nameEditText = findViewById(R.id.nameEditText);
        priceEditText = findViewById(R.id.priceEditText);

        serverMenuPage.ItemSetting(); //recyclerView의 데이터 불러오기

        Button close_btn = findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
