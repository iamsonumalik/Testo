package com.testo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.testo.Home.HomeActivity;
import com.testo.Onboarding.Auth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String userPhoneNumber = getSharedPreferences(Strings.sharedPreferencesSetting, 0).getString(Strings.sharedPreferences_phone, "");
        if (userPhoneNumber.isEmpty())
            startActivity(new Intent(MainActivity.this,Auth.class));
        else
            startActivity(new Intent(MainActivity.this,HomeActivity.class));

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
