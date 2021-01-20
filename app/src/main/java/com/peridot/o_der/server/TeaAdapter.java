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

public class TeaAdapter extends RecyclerView.Adapter<TeaAdapter.ViewHolder> {
    ArrayList<Tea> items = new ArrayList<Tea>();
    static View view;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.tea_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Tea item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView teaname;
        TextView teaprice;
        ImageView teaImage;

        public ViewHolder(View itemView) {
            super(itemView);

            teaname = itemView.findViewById(R.id.teaname);
            teaprice = itemView.findViewById(R.id.teaprice);
            teaImage = itemView.findViewById(R.id.menuImage);

            view = itemView;

            Button menuplusbtn = itemView.findViewById(R.id.menu_update_btn);
            menuplusbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //해당 position외 다른 메뉴 position 값이 있으면 ItemSetting이 제대로 동작안함
                    ((ServerMenuPage)ServerMenuPage.server_context_menu).dessert_position=-1;
                    ((ServerMenuPage)ServerMenuPage.server_context_menu).coffee_position=-1;
                    Intent intent = new Intent(itemView.getContext(), ServerMenuUpdate.class);
                    ContextCompat.startActivity(itemView.getContext(), intent, null);
                }
            });
        }

        public void setItem(Tea item) {
            teaname.setText(item.getName());
            teaprice.setText(item.getPrice());
            Glide.with(view).load("http://teamperidot.dothome.co.kr/"+item.getImgPath()).into(teaImage);
        }
    }
    public void addItem(Tea item) {
            items.add(item);
        }

        public void setItems(ArrayList<Tea> items) {
            this.items = items;
        }

        public Tea getItem(int position) {
            return items.get(position);
        }

        public void setItem(int position, Tea item) {
            items.set(position, item);
        }
}

