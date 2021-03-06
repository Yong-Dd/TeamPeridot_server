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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DisertAdapter extends RecyclerView.Adapter<DisertAdapter.ViewHolder> {
    ArrayList<Disert> items = new ArrayList<Disert>();
    static View view;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.disert_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Disert item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView disertname;
        TextView disertprice;
        TextView disertId;
        ImageView dessertImage;

        public ViewHolder(View itemView) {
            super(itemView);
            disertId = itemView.findViewById(R.id.disertId);
            disertname = itemView.findViewById(R.id.disertname);
            disertprice = itemView.findViewById(R.id.disertprice);
            dessertImage = itemView.findViewById(R.id.menuImage);

            view = itemView;

            Button menuplusbtn = itemView.findViewById(R.id.menu_update_btn);
            menuplusbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //해당 position외 다른 메뉴 position 값이 있으면 ItemSetting이 제대로 동작안함
                    String id = disertId.getText().toString();
                    String tableName = "DESSERT";
                    ((ServerMenuPage)ServerMenuPage.server_context_menu).coffee_position=-1;
                    ((ServerMenuPage)ServerMenuPage.server_context_menu).tea_position=-1;
                    Intent intent = new Intent(itemView.getContext(), ServerMenuUpdate.class);
                    intent.putExtra("ID", id); //업데이트 및 삭제할 때 가져오기
                    intent.putExtra("tableName", tableName);
                    ContextCompat.startActivity(itemView.getContext(), intent, null);
                }
            });
        }

        public void setItem(Disert item) {
            disertId.setText(item.getId());
            disertname.setText(item.getName());
            disertprice.setText(item.getPrice());

            if(!item.getImgPath().equals("null")) {
                Glide.with(view).load("http://teamperidot.dothome.co.kr/" + item.getImgPath()).into(dessertImage);
            }else{
                dessertImage.setImageResource(R.drawable.standard_icon);
            }
        }
    }
    public void addItem(Disert item) {
        items.add(item);
    }

    public void setItems(ArrayList<Disert> items) {
        this.items = items;
    }

    public Disert getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Disert item) {
        items.set(position, item);
    }
}

