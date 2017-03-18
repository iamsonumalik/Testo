package com.testo.Home.Fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

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


public class Home extends Fragment {
    private Button submit;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_home, container, false);
        submit = (Button) view.findViewById(R.id.submit);
        final EditText passcode = (EditText) view.findViewById(R.id.passcode);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarHome);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Strings.hideKeyboard(view,getActivity());
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Strings.hideKeyboard(view,getActivity());

                if (!CheckNetworkConnection.isConnectionAvailable(getContext())){
                    Snackbar.make(getView(),"No Internet connection",Snackbar.LENGTH_SHORT).show();
                    return;
                }


                if (passcode.getText().toString().length() == 8){
                    //showProductDetailView(view);
                    checkUserCredentials(passcode.getText().toString(),view);

                }else {
                    Snackbar.make(getView(),"Passcode should be of 8 digits",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private void showProductDetailView(final View view, final Products product) {
        getActivity().setTitle("Product Details");
        view.findViewById(R.id.productView).setVisibility(View.VISIBLE);
        Button notNow = (Button) view.findViewById(R.id.notNow);
        notNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.productView).setVisibility(View.GONE);
                getActivity().setTitle("Testo");

            }
        });

        Button buyNow = (Button) view.findViewById(R.id.buyNow);
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckNetworkConnection.isConnectionAvailable(getContext())){
                    Snackbar.make(getView(),"No Internet connection",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                // TODO Buy Product...
                buyNowClicked(product);
                view.findViewById(R.id.productView).setVisibility(View.GONE);
                getActivity().setTitle("Testo");
                Snackbar.make(getView(),"Order places you can check your order in Orders.",Snackbar.LENGTH_SHORT).show();
            }
        });

        ((TextView) view.findViewById(R.id.productName)).setText(product.getProductName());
        ((TextView) view.findViewById(R.id.manufacturer)).setText(product.getProductManufacturer());
        ((TextView) view.findViewById(R.id.category)).setText(product.getCategory());
        ((TextView) view.findViewById(R.id.price)).setText(getActivity().getResources().getString(R.string.rs)+product.getPrice());
        TextView availability = ((TextView) view.findViewById(R.id.availability));
        if (product.getAvailableProducts() == 0){
            availability.setTextColor(Color.RED);availability.setText("Not Available");
            buyNow.setVisibility(View.GONE);
            notNow.setText("Buy another product");

        }else {
            availability.setTextColor(Color.GREEN);availability.setText("Available");
            buyNow.setEnabled(true);
            buyNow.setVisibility(View.VISIBLE);
            notNow.setText("Not Now");
        }
    }

    private void buyNowClicked(Products product) {
        SharedPreferences prefs = getActivity().getSharedPreferences(Strings.sharedPreferencesSetting, 0);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String date = String.valueOf(MyCurrentDate.getCurrentDate());
        String key = MyCurrentDate.getTime(date);

        com.testo.Schema.Orders orders = new com.testo.Schema.Orders();
        orders.setTimeStamp(date);
        orders.setProductCode(product.getProductCode());
        orders.setPrice(product.getPrice());
        mDatabase.child(Strings.dbName)
                .child(Strings.orderstableNames)
                .child(prefs.getString(Strings.sharedPreferences_phone,""))
                .child(key).setValue(orders);

        int availableProducts = product.getAvailableProducts() - 1;
        product.setAvailableProducts(availableProducts);
        mDatabase.child(Strings.dbName)
                .child(Strings.productsTableName)
                .child(product.getProductCode())
                .setValue(product);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Testo");
    }

    private void checkUserCredentials(final String productCode, final View view) {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child(Strings.dbName).child(Strings.productsTableName).hasChild(productCode)){
                    Products product = snapshot.child(Strings.dbName).child(Strings.productsTableName).child(productCode).getValue(Products.class);

                    //Log.e("Data" , snapshot.child(Strings.dbName).child(Strings.productsTableName).toString());
                    showProductDetailView(view,product);
                }else{
                    Snackbar.make(getView(),"Product not found.",Snackbar.LENGTH_SHORT).show();
                }

                toggleProgressBar(false);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                toggleProgressBar(false);

            }
        });
        Users users = new Users();
        users.setName("test");
        users.setPassword("test");
        users.setPhoneNumber("test");
        mDatabase.child(Strings.dbName).child(Strings.userTableName).child("test").setValue(users);
        toggleProgressBar(true);
    }


    private void toggleProgressBar(boolean show) {
        if (show){
            submit.setClickable(false);
            progressBar.setVisibility(View.VISIBLE);
        }else {

            submit.setClickable(true);
            progressBar.setVisibility(View.GONE);
        }
    }
}
