package com.example.authentification.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.authentification.DBHelper;
import com.example.authentification.LoginActivity;
import com.example.authentification.PasswordActivity;
import com.example.authentification.ProdActivity;
import com.example.authentification.ProductActivity;
import com.example.authentification.R;
import com.example.authentification.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    TextView addbtn;
    private FragmentHomeBinding binding;



    public void onViewCreated(@NonNull View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initEvent();
    }



    private void initView(){

        addbtn = getView().findViewById(R.id.addtxt);


    }

    public void initEvent() {
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });
    }

    public void addProduct() {
        Intent intent = new Intent( getActivity(), ProdActivity.class);
        startActivity(intent);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}