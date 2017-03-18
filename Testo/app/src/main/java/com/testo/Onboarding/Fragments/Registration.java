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

/**
 * A simple {@link Fragment} subclass.
 */
public class Registration extends Fragment {

    private View registerButton;
    private View progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_registration, container, false);
        final EditText registrationphoneNumber = (EditText) view.findViewById(R.id.registrationphoneNumber);
        final EditText registrationpassword = (EditText) view.findViewById(R.id.registrationpassword);
        final EditText registrationName = (EditText) view.findViewById(R.id.registrationName);
        registerButton = view.findViewById(R.id.register);
        progressBar = view.findViewById(R.id.progressBarLogin);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registrationphoneNumber.getText().toString().contains(" ")){
                    Snackbar.make(getView(),"Phone number with space not allowed.", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (registrationphoneNumber.getText().length() !=10){
                    Snackbar.make(getView(),"Phone Number Should be of 10 digits.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (registrationpassword.getText().length() < 5){
                    Snackbar.make(getView(),"Password should be between 5-16 characters.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (registrationName.getText().toString().isEmpty()){
                    Snackbar.make(getView(),"Enter your Name Please.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (!CheckNetworkConnection.isConnectionAvailable(getContext())){
                    Snackbar.make(getView(),"No Internet Connection.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                checkUserCredentials(registrationphoneNumber.getText().toString(),registrationpassword.getText().toString(),registrationName.getText().toString());

            }
        });
        return view;
    }

    private void checkUserCredentials(final String phone, final String password, final String name) {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child(Strings.dbName).child(Strings.userTableName).hasChild(phone)) {
                    // run some code
                    Snackbar.make(getView(),"You are already Registered.", Snackbar.LENGTH_SHORT).show();
                }else {
                    Users users = new Users();
                    users.setName(name);
                    users.setPassword(password);
                    users.setPhoneNumber(phone);
                    mDatabase.child(Strings.dbName).child(Strings.userTableName).child(phone).setValue(users);
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(Strings.sharedPreferencesSetting, 0).edit();
                    editor.putString(Strings.sharedPreferences_name,name);
                    editor.putString(Strings.sharedPreferences_password,password);
                    editor.putString(Strings.sharedPreferences_phone,phone);
                    editor.apply();
                    editor.commit();
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                    getActivity().finish();

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

       // }
    }
    private void toggleProgressBar(boolean show) {
        if (show){
            registerButton.setClickable(false);
            progressBar.setVisibility(View.VISIBLE);
        }else {

            registerButton.setClickable(true);
            progressBar.setVisibility(View.GONE);
        }
    }

}
