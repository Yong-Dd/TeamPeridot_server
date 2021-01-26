package com.peridot.o_der.server;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.ViewHolder> {
    ArrayList<Coffee> items = new ArrayList<Coffee>();
    static View view;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.coffee_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Coffee item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView coffeename;
        TextView coffeeprice;
        TextView coffeeId;
        ImageView coffeeImage;

        public ViewHolder(View itemView) {
            super(itemView);

            coffeename = itemView.findViewById(R.id.coffeename);
            coffeeprice = itemView.findViewById(R.id.coffeeprice);
            coffeeId = itemView.findViewById(R.id.coffeeId);
            coffeeImage = itemView.findViewById(R.id.menuImage);

            view = itemView;

            Button menuplusbtn = itemView.findViewById(R.id.menu_update_btn);
            menuplusbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //해당 position외 다른 메뉴 position 값이 있으면 ItemSetting이 제대로 동작안함
                    String id = coffeeId.getText().toString();
                    String tableName = "COFFEE";
                    ((ServerMenuPage)ServerMenuPage.server_context_menu).dessert_position=-1;
                    ((ServerMenuPage)ServerMenuPage.server_context_menu).tea_position=-1;
                    Intent intent = new Intent(itemView.getContext(), ServerMenuUpdate.class);
                    intent.putExtra("ID", id); //업데이트 및 삭제할 때 가져오기
                    intent.putExtra("tableName",tableName);
                    ContextCompat.startActivity(itemView.getContext(), intent, null);
                }
            });
        }

        public void setItem(Coffee item) {
            coffeeId.setText(item.getId());
            coffeename.setText(item.getName());
            coffeeprice.setText(item.getPrice());
            if(!item.getImgPath().equals("null")) {
                Glide.with(view).load("http://teamperidot.dothome.co.kr/" + item.getImgPath()).into(coffeeImage);
            }else{
                coffeeImage.setImageResource(R.drawable.standard_icon);
            }
        }
    }
    public void addItem(Coffee item) {
        items.add(item);
    }

    public void setItems(ArrayList<Coffee> items) {
        this.items = items;
    }

    public Coffee getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Coffee item) {
        items.set(position, item);
    }
}
