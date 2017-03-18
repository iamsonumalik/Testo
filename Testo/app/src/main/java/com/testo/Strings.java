package com.testo;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by sonumalik on 17/03/17.
 */

public class Strings {
    public static String sharedPreferencesSetting = "user_settings";
    public static String sharedPreferences_name = "user_name";
    public static String sharedPreferences_phone = "user_phone";
    public static String sharedPreferences_password = "user_password";
    public static String userTableName = "users";
    public static String productsTableName = "products";
    public static String dbName = "Testo";
    public static String orderstableNames = "orders";

    public static void hideKeyboard(View view, FragmentActivity activity) {

        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
