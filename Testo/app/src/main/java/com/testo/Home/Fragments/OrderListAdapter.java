package com.testo.Home.Fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.testo.R;

import java.util.ArrayList;

/**
 * Created by sonumalik on 18/03/17.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder>{
    private final ArrayList<OrderListDataSet> orderListDataSetArrayList;
    private final Orders orders;

    public OrderListAdapter(Orders orders, ArrayList<OrderListDataSet> orderListDataSetArrayList) {
        this.orders = orders;
        this.orderListDataSetArrayList = orderListDataSetArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderListDataSet item = orderListDataSetArrayList.get(position);
        holder.orderDate.setText(item.getDate());
        holder.orderName.setText(item.getName());
        holder.orderPrice.setText(orders.getActivity().getResources().getString(R.string.rs)+item.getPrice());
    }

    @Override
    public int getItemCount() {
        return orderListDataSetArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView orderName;
        private final TextView orderPrice;
        private final TextView orderDate;

        public ViewHolder(View itemView) {
            super(itemView);
            orderName = (TextView) itemView.findViewById(R.id.orderName);
            orderPrice = (TextView) itemView.findViewById(R.id.orderPrice);
            orderDate = (TextView) itemView.findViewById(R.id.orderDate);
        }
    }
}
