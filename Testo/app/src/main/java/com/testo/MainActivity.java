package com.testo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.testo.Home.HomeActivity;
import com.testo.Onboarding.Auth;
import com.testo.Schema.Products;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveProducts("00000001","Fossil Q Watch","Fossil","Wrist Watch","12999",15);
        saveProducts("00000002","Moto G 4","Moto","Smart phones","15000",10);
        saveProducts("00000003","Fossil marshell Watch","Fossil","Wrist Watch","21999",15);
        saveProducts("00000004","Vivo phone","Vivo","Smart phones","2999",15);
        saveProducts("00000005","Oppo","Oppo","Smart phones","12999",15);
        saveProducts("00000006","iphone 6s plus","apple","Smart phones","72999",15);
        saveProducts("00000007","iphone 5","apple","Smart phones","21999",15);
        /*
        saveProducts("00000008","Fossil Q Watch","Fossil","Wrist Watch","12999",15);
        saveProducts("00000009","Fossil Q Watch","Fossil","Wrist Watch","12999",15);
        saveProducts("00000010","Fossil Q Watch","Fossil","Wrist Watch","12999",15);
        saveProducts("00000011","Fossil Q Watch","Fossil","Wrist Watch","12999",15);
        saveProducts("00000012","Fossil Q Watch","Fossil","Wrist Watch","12999",15);
        saveProducts("00000013","Fossil Q Watch","Fossil","Wrist Watch","12999",15);
        saveProducts("00000014","Fossil Q Watch","Fossil","Wrist Watch","12999",15);
        saveProducts("00000015","Fossil Q Watch","Fossil","Wrist Watch","12999",15);
        saveProducts("00000016","Fossil Q Watch","Fossil","Wrist Watch","12999",15);
        saveProducts("00000017","Fossil Q Watch","Fossil","Wrist Watch","12999",15);
        saveProducts("00000018","Fossil Q Watch","Fossil","Wrist Watch","12999",15);
        saveProducts("00000019","Fossil Q Watch","Fossil","Wrist Watch","12999",15);
        saveProducts("00000020","Fossil Q Watch","Fossil","Wrist Watch","12999",15);
        saveProducts("00000021","Fossil Q Watch","Fossil","Wrist Watch","12999",15);
        saveProducts("00000022","Fossil Q Watch","Fossil","Wrist Watch","12999",15);*/
        String userPhoneNumber = getSharedPreferences(Strings.sharedPreferencesSetting, 0).getString(Strings.sharedPreferences_phone, "");
        if (userPhoneNumber.isEmpty())
            startActivity(new Intent(MainActivity.this,Auth.class));
        else
            startActivity(new Intent(MainActivity.this,HomeActivity.class));

    }

    private void saveProducts(String productCode,
                              String productName,
                              String productManufacturer,
                              String category,
                              String price,
                              int availableProducts) {
        Products products = new Products();
        products.setAvailableProducts(availableProducts);
        products.setCategory(category);
        products.setPrice(price);
        products.setProductCode(productCode);
        products.setProductName(productName);
        products.setProductManufacturer(productManufacturer);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(Strings.dbName).child(Strings.productsTableName).child(productCode).setValue(products);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
