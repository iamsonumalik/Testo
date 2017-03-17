package com.testo.Onboarding.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.testo.CheckNetworkConnection;
import com.testo.Home.HomeActivity;
import com.testo.R;
import com.testo.Schema.Users;
import com.testo.Strings;


public class Login extends Fragment {

    private View loginButton;
    private View progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_login, container, false);
        final EditText loginphoneNumber = (EditText) view.findViewById(R.id.loginphoneNumber);
        final EditText loginpassword = (EditText) view.findViewById(R.id.loginpassword);
        loginButton = view.findViewById(R.id.login);
        progressBar = view.findViewById(R.id.progressBarLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginphoneNumber.getText().length() != 10){
                    Snackbar.make(getView(),"Phone Number Should be of 10 digits.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (loginpassword.getText().length() < 5){
                    Snackbar.make(getView(),"Password should be between 5-16 characters.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (!CheckNetworkConnection.isConnectionAvailable(getContext())){
                    Snackbar.make(getView(),"No Internet Connection.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                checkUserCredentials(loginphoneNumber.getText().toString(),loginpassword.getText().toString());
            }
        });
        return view;
    }

    private void checkUserCredentials(final String phone, final String password) {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.child(Strings.dbName).child(Strings.userTableName).hasChild(phone)) {
                    // run some code
                    Snackbar.make(getView(),"This phone number is not Registered.", Snackbar.LENGTH_SHORT).show();
                }else {
                    Users users = snapshot.child(Strings.dbName).child(Strings.userTableName).child(phone).getValue(Users.class);
                    if (users.getPhoneNumber().equals(phone) && users.getPassword().equals(password)) {
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences(Strings.sharedPreferencesSetting, 0).edit();
                        editor.putString(Strings.sharedPreferences_name, users.getName());
                        editor.putString(Strings.sharedPreferences_password,users.getPassword());
                        editor.putString(Strings.sharedPreferences_phone, users.getPhoneNumber());
                        editor.apply();
                        editor.commit();
                        startActivity(new Intent(getActivity(), HomeActivity.class));
                        getActivity().finish();
                    }else {
                        Snackbar.make(getView(),"Wrong phone number and/or password.",Snackbar.LENGTH_SHORT).show();
                    }

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
            loginButton.setClickable(false);
            progressBar.setVisibility(View.VISIBLE);
        }else {

            loginButton.setClickable(true);
            progressBar.setVisibility(View.GONE);
        }
    }
}
