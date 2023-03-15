package com.example.firebaserecyclerdeleteupdatedata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebaserecyclerdeleteupdatedata.databinding.ActivityAddDataBinding;
import com.example.firebaserecyclerdeleteupdatedata.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AddDataActivity extends AppCompatActivity {
   ActivityAddDataBinding binding;
    int maxId=0;
    Uri uri;
    String ImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityAddDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseDatabase.getInstance().getReference().child("Student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                maxId=(int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseStorage.getInstance().getReference().child("StudentImages").child(String.valueOf(maxId+1)).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        ImageUrl=uriTask .getResult().toString();
                        HashMap<String, Object> map=new HashMap<>();
                        map.put("Name",binding.editText1.getText().toString());
                        map.put("Email",binding.editText2.getText().toString());
                        map.put("Password",binding.editText3.getText().toString());
                        map.put("ImageUrl",ImageUrl);
                        FirebaseDatabase.getInstance().getReference().child("Student").child(String.valueOf(maxId+1)).setValue(map);
                        Toast.makeText(AddDataActivity.this, "send Data", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddDataActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,45);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            uri=data.getData();
            binding.imageView.setImageURI(uri);
        }
    }
    }
