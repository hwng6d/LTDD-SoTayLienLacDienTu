package com.example.stltdd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DSNotiActivity extends AppCompatActivity {
    //initialize
    TextView tvReload;
    ImageView imgBack;
    RecyclerView rcvNoti;
    DatabaseReference databaseReferenceTB;
    DatabaseReference databaseReferenceSVCourses;
    NotiAdapter notiAdapter;
    SV_CoursesAdapter svcoursesAdapter;
    ArrayList<Notifications> listNoti;
    ArrayList<SV_Courses> listSVCourses;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsnoti);

        //asign
        tvReload = findViewById(R.id.tvReload);
        imgBack = findViewById(R.id.imgBack);
        rcvNoti = findViewById(R.id.rcvNoti);
        rcvNoti.setHasFixedSize(true);
        rcvNoti.setLayoutManager(new LinearLayoutManager(this));
        databaseReferenceTB = FirebaseDatabase.getInstance().getReference("Notifications");
        databaseReferenceSVCourses = FirebaseDatabase.getInstance().getReference("SV_Courses");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        listNoti = new ArrayList<>();
        listSVCourses = new ArrayList<>();
        notiAdapter = new NotiAdapter(this,listNoti);
        svcoursesAdapter = new SV_CoursesAdapter(this, listSVCourses);
        rcvNoti.setAdapter(notiAdapter);

        databaseReferenceTB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listNoti.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Notifications noti = dataSnapshot.getValue(Notifications.class);
                    String each_course_id = noti.getCourse_id();
                    notiAdapter.notifyDataSetChanged();
                    Query query = databaseReferenceSVCourses.child(firebaseUser.getUid()).orderByChild("course_id").equalTo(each_course_id);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            listSVCourses.clear();
                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                Courses course = dataSnapshot1.getValue(Courses.class);
                                noti.setCourse_id(course.getCourse_id());
                                listNoti.add(noti);
                                notiAdapter.notifyDataSetChanged();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DSNotiActivity.this.onBackPressed();
            }
        });
    }
}