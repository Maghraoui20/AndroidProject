package com.example.authentification;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ProductActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    productAdapter productAdpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }



        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("produit"), MainModel.class)
                        .build();

        productAdpt = new productAdapter(options);
        recyclerView.setAdapter(productAdpt);

    }

    @Override
    protected void onStart() {
        super.onStart();
        productAdpt.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        productAdpt.stopListening();
    }
}
