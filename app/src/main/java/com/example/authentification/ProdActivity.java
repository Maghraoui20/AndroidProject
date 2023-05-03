package com.example.authentification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ProdActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    productAdapter productAdpt;

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod);


        recyclerView = (RecyclerView) findViewById((R.id.recyView));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("produit"), MainModel.class)
                        .build();

        productAdpt = new productAdapter(options);
        recyclerView.setAdapter(productAdpt);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddActivity.class));
            }
        });

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

    //create menu search
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                              @Override
                                              public boolean onQueryTextSubmit(String query) {
                                                  txtSearch(query);
                                                  return false;
                                              }

                                              @Override
                                              public boolean onQueryTextChange(String query) {
                                                  txtSearch(query);
                                                  return true;
                                              }


                                          }
        );


        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearch(String str) {

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("produit").orderByChild("name").startAt(str).endAt(str +"-"), MainModel.class)
                        .build();

        productAdpt = new productAdapter(options);
        productAdpt.startListening();
        recyclerView.setAdapter(productAdpt);
    }

}