package com.example.firebaserecyclerdeleteupdatedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.firebaserecyclerdeleteupdatedata.databinding.ActivityMainBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
    AdapterClass adapter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.Recyclerview.setLayoutManager(layoutManager);

        Query query= FirebaseDatabase.getInstance().getReference().child("Student");
        FirebaseRecyclerOptions<ModelClass>options=new FirebaseRecyclerOptions.Builder<ModelClass>()
                .setQuery(query,ModelClass.class)
                        .build();

             adapter=new AdapterClass(options);
       binding.Recyclerview.setAdapter(adapter);



        binding.floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddDataActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}