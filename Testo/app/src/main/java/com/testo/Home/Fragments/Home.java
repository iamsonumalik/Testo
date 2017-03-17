package com.testo.Home.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.testo.R;


public class Home extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_home, container, false);
        Button submit = (Button) view.findViewById(R.id.submit);
        final EditText passcode = (EditText) view.findViewById(R.id.passcode);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passcode.getText().toString().length() == 8){
                    showProductDetailView(view);
                }else {
                    Toast.makeText(getContext(),"Passcode should be of 8 digits",Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    private void showProductDetailView(final View view) {
        view.findViewById(R.id.productView).setVisibility(View.VISIBLE);
        view.findViewById(R.id.notNow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.productView).setVisibility(View.GONE);
            }
        });
        view.findViewById(R.id.buyNow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Buy Product...
            }
        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Testo");
    }
}
