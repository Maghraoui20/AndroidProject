package com.example.authentification;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class productAdapter extends FirebaseRecyclerAdapter<MainModel, productAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public productAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {

        super(options);
    }

    @SuppressLint("RecyclerView")
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull MainModel model) {

        holder.name.setText(model.getName());
        holder.quantite.setText(model.getQuantite());
        holder.ref.setText(model.getRef());
        holder.description.setText(model.getDescription());

        Glide.with(holder.img.getContext())
                .load(model.getUrl())
                .placeholder(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1400 )
                        .create();

                dialogPlus.show();
//Show data inside the form
                View view = dialogPlus.getHolderView();

                EditText name = view.findViewById(R.id.txtName);
                EditText quantite = view.findViewById(R.id.txtQuantity);
                EditText ref = view.findViewById(R.id.txtRef);
                EditText description = view.findViewById(R.id.txtDescription);
                EditText url = view.findViewById(R.id.txtImageUrl);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                name.setText(model.getName());
                quantite.setText(model.getQuantite());
                ref.setText(model.getRef());
                description.setText(model.getDescription());
                url.setText(model.getUrl());


                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("quantite", quantite.getText().toString());
                        map.put("ref", ref.getText().toString());
                        map.put("description", description.getText().toString());
                        map.put("url", url.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("produit")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(), "Data Updated successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.name.getContext(), "Error while updating", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });


                    }
                });




            }
        });


                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                        builder.setTitle("Are you sure ?");
                        builder.setMessage("This product is going to be deleted.");

                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase.getInstance().getReference().child("produit")
                                        .child(getRef(position).getKey()).removeValue();

                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(holder.name.getContext(), "cancelled", Toast.LENGTH_SHORT).show();

                            }
                        });
                        builder.show();
                    }
                });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView name, quantite, ref, description;

        Button btnEdit, btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.img1);
            name = (TextView)itemView.findViewById(R.id.nameText);
            quantite = (TextView)itemView.findViewById(R.id.quantiteText);
            description = (TextView)itemView.findViewById(R.id.descriptionText);
            ref = (TextView)itemView.findViewById(R.id.refText);

            btnEdit= (Button) itemView.findViewById(R.id.btnEdit);
            btnDelete= itemView.findViewById(R.id.btnDelete);

        }
    }

}
