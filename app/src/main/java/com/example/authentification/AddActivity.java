package com.example.authentification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {


    EditText name, quantite, ref, description, url;

    Button btnAdd, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = (EditText)findViewById(R.id.txtName);
        url = (EditText)findViewById(R.id.txtImageUrl);
        quantite = (EditText)findViewById(R.id.txtQuantity);
        description = (EditText)findViewById(R.id.txtDescription);
        ref = (EditText)findViewById(R.id.txtRef);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnBack = (Button)findViewById(R.id.btnBack);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                clearAll();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(AddActivity.this, ProdActivity.class));
            }
        });
    }

    private void insertData()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("name",name.getText().toString());
        map.put("quantite",quantite.getText().toString());
        map.put("ref",ref.getText().toString());
        map.put("url",url.getText().toString());
        map.put("description",description.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("produit").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this,"Data added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this, "Error while adding product", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void clearAll()
    {
        name.setText("");
        quantite.setText("");
        url.setText("");
        ref.setText("");
        description.setText("");

    }
}