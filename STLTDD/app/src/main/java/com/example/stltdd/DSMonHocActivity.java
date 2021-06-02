/*
package com.example.stltdd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.stltdd.R;
import com.example.stltdd.SV_Courses;
import com.example.stltdd.SV_CoursesAdapter;
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

public class DSMonHocActivity extends AppCompatActivity {
    //initialize
    RecyclerView rcvCourses;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    SV_CoursesAdapter svcoursesAdapter;
    ArrayList<SV_Courses> listcourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsmonhoc);

        //assign
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        rcvCourses = findViewById(R.id.rcvCourses);
        databaseReference = FirebaseDatabase.getInstance().getReference("SV_Courses").child(firebaseUser.getUid());
        rcvCourses.setHasFixedSize(true);
        rcvCourses.setLayoutManager(new LinearLayoutManager(this));

        listcourses = new ArrayList<>();
        svcoursesAdapter = new SV_CoursesAdapter(this,listcourses);
        rcvCourses.setAdapter(svcoursesAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SV_Courses svcourses = dataSnapshot.getValue(SV_Courses.class);
                    listcourses.add(svcourses);

                }
                svcoursesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}

*/
package com.example.stltdd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class DSMonHocActivity extends AppCompatActivity {
    //initialize
    ImageView imgBack;
    RecyclerView rcvCourses;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReferenceSVCourses;
    DatabaseReference databaseReferenceCourses;
    SV_CoursesAdapter svcoursesAdapter;
    CoursesAdapter coursesAdapter;
    ArrayList<SV_Courses> listsvcoursesForadpt;
    ArrayList<Courses> listcoursesForadpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsmonhoc);

        //assign
        imgBack = findViewById(R.id.imgBack);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        rcvCourses = findViewById(R.id.rcvCourses);
        rcvCourses.setHasFixedSize(true);
        rcvCourses.setLayoutManager(new LinearLayoutManager(this));
        databaseReferenceSVCourses = FirebaseDatabase.getInstance().getReference("SV_Courses").child(firebaseUser.getUid());
        databaseReferenceCourses = FirebaseDatabase.getInstance().getReference("Courses");

        listsvcoursesForadpt = new ArrayList<>();
        listcoursesForadpt = new ArrayList<>();
        svcoursesAdapter = new SV_CoursesAdapter(this,listsvcoursesForadpt);
        coursesAdapter = new CoursesAdapter(this,listcoursesForadpt);
        rcvCourses.setAdapter(coursesAdapter);

        databaseReferenceCourses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listcoursesForadpt.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Courses course =  dataSnapshot.getValue(Courses.class);
                    //listcoursesForadpt.add(course);
                    String each_course_id = course.getCourse_id();
                    //Log.d("kiemtra id tat ca: ",each_course_id);
                    coursesAdapter.notifyDataSetChanged();
                    //databaseReferenceSVCourses = FirebaseDatabase.getInstance().getReference("SV_Courses").child(firebaseUser.getUid());;
                    Query query = databaseReferenceSVCourses.orderByChild("course_id").equalTo(each_course_id);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            //listcoursesForadpt.add(course);
                            listsvcoursesForadpt.clear();
                            //Log.d("kiemtranema id ",each_course_id);
                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                //svcoursesAdapter.notifyDataSetChanged();
                                SV_Courses svcourse = dataSnapshot1.getValue(SV_Courses.class);
                                course.setCourse_id(svcourse.getCourse_id());
                                listcoursesForadpt.add(course);
                                coursesAdapter.notifyDataSetChanged();

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
                DSMonHocActivity.this.onBackPressed();
            }
        });
    }
}
