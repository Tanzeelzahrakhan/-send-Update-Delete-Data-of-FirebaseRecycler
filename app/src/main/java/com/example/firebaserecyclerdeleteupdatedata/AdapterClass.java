package com.example.firebaserecyclerdeleteupdatedata;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebaserecyclerdeleteupdatedata.databinding.ListItemBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class AdapterClass  extends FirebaseRecyclerAdapter<ModelClass,AdapterClass.ViewHolder> {

    public AdapterClass(@NonNull FirebaseRecyclerOptions<ModelClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ModelClass model) {
        holder.binding.textView.setText(model.getName());
        holder.binding.textView2.setText(model.getEmail());
        holder.binding.textView3.setText(model.getPassword());
        Glide.with(holder.itemView.getContext()).load(model.getImageUrl()).into(holder.binding.imageView);
        holder.binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.binding.imageView.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true,800)
                        .create();
                View myview=dialogPlus.getHolderView();


                final EditText ImgUrl=myview.findViewById(R.id.uimgurl);
                final EditText name=myview.findViewById(R.id.uname);
                final EditText Password=myview.findViewById(R.id.pass);
                final EditText email=myview.findViewById(R.id.uemail);
                Button submit=myview.findViewById(R.id.usubmit);

              ImgUrl.setText(model.getImageUrl());
                name.setText(model.getName());
                Password.setText(model.getPassword());
                email.setText(model.getEmail());

                dialogPlus.show();


                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();

                        map.put("Name",name.getText().toString());
                        map.put("Email",email.getText().toString());
                        map.put("Password",Password.getText().toString());
                        map.put("ImageUrl",ImgUrl.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("Student")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });

            }
        });
       holder.binding.delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder builder=new AlertDialog.Builder(holder.binding.imageView.getContext())
                       .setTitle("Delete Item")
                       .setMessage("Delete");
               builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       FirebaseDatabase.getInstance().getReference().child("Student")
                               .child(getRef(position).getKey()).removeValue();
                   }
               });
               builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                   }
               });
               builder.show();
           }
       });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         ListItemBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ListItemBinding.bind(itemView);
        }
    }
}
