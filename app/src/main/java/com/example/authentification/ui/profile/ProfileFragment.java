package com.example.authentification.ui.profile;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.authentification.DBHelper;
import com.example.authentification.R;
import com.example.authentification.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private DBHelper db;
    private EditText nameET, passwordET;
    private Button addBTN ;
    private String email;
    ProgressDialog pd;


    @Override
    public void onViewCreated(@NonNull View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new DBHelper(getContext());

        initView();
        initEvent();
    }
    @SuppressLint("Range")
    private void initView() {

        nameET = getView().findViewById(R.id.name);
        passwordET = getView().findViewById(R.id.password);

        addBTN = getView().findViewById(R.id.updatebtn);
        SharedPreferences sh = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        email = sh.getString("email", "");

        Log.d(email, "initView: ");
        Cursor profile = db.getProfile(email);
        if(profile.moveToFirst()){
            do{
                String varaible1 = profile.getString(profile.getColumnIndex("name"));
                String varaible2 = profile.getString(profile.getColumnIndex("password"));

                nameET.setText(varaible1);
                passwordET.setText(varaible2);
            }while (profile.moveToNext());
        }
        profile.close();





    }

    private void initEvent() {

        addBTN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (nameET.getText().toString().equals("")) {
                    Toast.makeText(getView().getContext(), "Name Required", Toast.LENGTH_LONG).show();
                } else if (passwordET.getText().toString().equals("")) {
                    Toast.makeText(getView().getContext(), "Password Required", Toast.LENGTH_LONG).show();
                } else {


                    String  name = nameET.getText().toString().trim();
                    String  password = passwordET.getText().toString().trim();

                    Boolean updatedProfile = db.updateNamePassword(email,name,password);
                    if(updatedProfile == true) {
                        Toast.makeText(getView().getContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show();
    //intent to home

                    }else{
                        Toast.makeText(getView().getContext(), "Err Update", Toast.LENGTH_SHORT).show();

                    }


                }
            }
        });

    }
@Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {


    return inflater.inflate(R.layout.fragment_profile, container, false);
    }









    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}