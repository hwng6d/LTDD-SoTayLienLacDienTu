package com.example.stltdd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DSMonHocADActivity extends AppCompatActivity {
    //initialize
    ImageView imgBack;
    RecyclerView rcvCourses;
    DatabaseReference databaseReferenceCourses;
    CoursesADAdapter coursesADAdapter;
    ArrayList<Courses> listCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsmonhocad);

        //asSign
        imgBack = findViewById(R.id.imgBack);
        rcvCourses = findViewById(R.id.rcvCourses);
        rcvCourses.setHasFixedSize(true);
        rcvCourses.setLayoutManager(new LinearLayoutManager(this));
        databaseReferenceCourses = FirebaseDatabase.getInstance().getReference("Courses");
        listCourses = new ArrayList<>();
        coursesADAdapter = new CoursesADAdapter(this,listCourses);
        rcvCourses.setAdapter(coursesADAdapter);

        //hiển thị lên recyclerview
        databaseReferenceCourses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Courses course = dataSnapshot.getValue(Courses.class);
                    listCourses.add(course);
                    coursesADAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DSMonHocADActivity.this.onBackPressed();
            }
        });

    }
}