package com.testo.Home.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.testo.CheckNetworkConnection;
import com.testo.MyCurrentDate;
import com.testo.R;
import com.testo.Schema.Products;
import com.testo.Schema.Users;
import com.testo.Strings;

import java.util.ArrayList;


public class Orders extends Fragment {
    private DatabaseReference mDatabase;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private SwipeRefreshLayout refresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_orders, container, false);
        refresh = (SwipeRefreshLayout)rootView.findViewById(R.id.refresh);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mRecycler = (RecyclerView) rootView.findViewById(R.id.orderList);
        mRecycler.setHasFixedSize(true);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mManager = new LinearLayoutManager(getActivity());
        //mManager.setReverseLayout(true);
        //mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
        mRecycler.setHasFixedSize(true);
        refresh.setRefreshing(true);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchOrders();
            }
        });
        fetchOrders();

    }

    private void fetchOrders() {
        if (!CheckNetworkConnection.isConnectionAvailable(getContext())){
            Snackbar.make(getView(),"No Internet connection",Snackbar.LENGTH_SHORT).show();
            refresh.setRefreshing(false);
            return;
        }
        final ArrayList<OrderListDataSet> orderListDataSetArrayList = new ArrayList<>();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child(Strings.dbName).child(Strings.orderstableNames).hasChild(getUserPhone())){
                    for (DataSnapshot childSnapshot : snapshot.child(Strings.dbName).child(Strings.orderstableNames).child(getUserPhone()).getChildren()){
                        com.testo.Schema.Orders orders = childSnapshot.getValue(com.testo.Schema.Orders.class);

                        OrderListDataSet orderListDataSet = new OrderListDataSet();
                        String parsedDate = MyCurrentDate.getParsedDate(orders.getTimeStamp(),"dd\nMMM","yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        orderListDataSet.setDate(parsedDate);
                        orderListDataSet.setPrice(orders.getPrice());

                        if (snapshot.child(Strings.dbName).child(Strings.productsTableName).hasChild(orders.getProductCode())){
                            Products products = snapshot.child(Strings.dbName).child(Strings.productsTableName).child(orders.getProductCode()).getValue(Products.class);
                            orderListDataSet.setName(products.getProductName());
                        }else {
                            orderListDataSet.setName("Name not Found");
                        }

                        orderListDataSetArrayList.add(orderListDataSet);
                    }
                }else{
                    Snackbar.make(getView(),"No orders.",Snackbar.LENGTH_SHORT).show();
                }

                OrderListAdapter orderListAdapter = new OrderListAdapter(Orders.this,orderListDataSetArrayList);
                mRecycler.setAdapter(orderListAdapter);
                refresh.setRefreshing(false);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                refresh.setRefreshing(false);

            }
        });
        Users users = new Users();
        users.setName("test");
        users.setPassword("test");
        users.setPhoneNumber("test");
        mDatabase.child(Strings.dbName).child(Strings.userTableName).child("test").setValue(users);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Orders");
    }

    public String getUserPhone() {
        return getActivity().getSharedPreferences(Strings.sharedPreferencesSetting,0).getString(Strings.sharedPreferences_phone,"");
        //return ;
    }

}