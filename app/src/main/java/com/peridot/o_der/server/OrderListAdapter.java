package com.peridot.o_der.server;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    ArrayList<OrderList> items = new ArrayList<OrderList>();

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView order_date;
        TextView cus_Name;
        //TextView cus_Phone;
        TextView product_name;
        TextView order_price;
        TextView pickup_time;
        TextView pickup_time_text;
        TextView Memo;
        TextView Memo_text;
        Button pickup_finish_Btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cus_Name = itemView.findViewById(R.id.custome_name);
           //cus_Phone = itemView.findViewById(R.id.customer_phone);
            product_name = itemView.findViewById(R.id.product_name);
            pickup_time_text = itemView.findViewById(R.id.pickup_text);
            pickup_time = itemView.findViewById(R.id.pickup_time);
            Memo_text = itemView.findViewById(R.id.memo_text);
            Memo = itemView.findViewById(R.id.memo);
            pickup_finish_Btn = itemView.findViewById(R.id.pickup_finish_Btn);
            order_date = itemView.findViewById(R.id.orderdate);
            order_price = itemView.findViewById(R.id.orderprice);

        }

        public void setItem(OrderList item) {
            cus_Name.setText(item.getName());
            order_date.setText(item.getOrder_date());
            product_name.setText(item.getProduct_name());
            order_price.setText(item.getPrice());
            pickup_time.setText(item.getPickUp_time());
            Memo.setText(item.getMemo());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.order_list, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        OrderList item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(OrderList item) {
        items.add(item);
    }

    public void setItems(ArrayList<OrderList> items) {
        this.items = items;
    }

    public OrderList getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, OrderList item) {
        items.set(position, item);
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        // 갱신처리 반드시 해야함
        notifyDataSetChanged();
    }
}
