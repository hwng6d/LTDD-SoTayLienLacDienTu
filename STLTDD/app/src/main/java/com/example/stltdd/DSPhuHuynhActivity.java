package com.example.stltdd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DSPhuHuynhActivity extends AppCompatActivity {
    //initialize
    ImageView imgBack;
    RecyclerView rcvParents;
    DatabaseReference databaseReferencePH;
    ParentsAdapter parentsAdapter;
    ArrayList<Parents> listParents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsphuhuynh);

        //asign
        imgBack = findViewById(R.id.imgBack);
        rcvParents = findViewById(R.id.rcvParents);
        rcvParents.setHasFixedSize(true);
        rcvParents.setLayoutManager(new LinearLayoutManager(this));
        databaseReferencePH = FirebaseDatabase.getInstance().getReference("Users");
        listParents = new ArrayList<>();
        parentsAdapter = new ParentsAdapter(this,listParents);
        rcvParents.setAdapter(parentsAdapter);
        Query query = databaseReferencePH.orderByChild("role").equalTo("phuhuynh");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Parents parent = dataSnapshot.getValue(Parents.class);
                    listParents.add(parent);
                    parentsAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DSPhuHuynhActivity.this.onBackPressed();
            }
        });
    }
}