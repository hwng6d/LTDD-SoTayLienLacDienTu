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

public class DSSinhVienActivity extends AppCompatActivity {
    //initialize
    ImageView imgBack;
    RecyclerView rcvStudents;
    DatabaseReference databaseReferenceSV;
    StudentsAdapter studentsAdapter;
    ArrayList<Students> listStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dssinhvien);

        //asign
        imgBack = findViewById(R.id.imgBack);
        rcvStudents = findViewById(R.id.rcvStudents);
        rcvStudents.setHasFixedSize(true);
        rcvStudents.setLayoutManager(new LinearLayoutManager(this));
        databaseReferenceSV = FirebaseDatabase.getInstance().getReference("Users");
        listStudents = new ArrayList<>();
        studentsAdapter = new StudentsAdapter(this,listStudents);
        rcvStudents.setAdapter(studentsAdapter);
        Query query = databaseReferenceSV.orderByChild("role").equalTo("sinhvien");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Students student = dataSnapshot.getValue(Students.class);
                    listStudents.add(student);
                    studentsAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DSSinhVienActivity.this.onBackPressed();
            }
        });
    }
}