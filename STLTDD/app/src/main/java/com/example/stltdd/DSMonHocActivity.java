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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DSMonHocActivity extends AppCompatActivity {
    //initialize
    RecyclerView rcvCourses;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReferenceSVCourses;
    DatabaseReference databaseReferenceCourses;
    SV_CoursesAdapter svcoursesAdapter;
    CoursesAdapter coursesAdapter;
    ArrayList<SV_Courses> listsvcourses;
    ArrayList<Courses> listcourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsmonhoc);

        //assign
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        rcvCourses = findViewById(R.id.rcvCourses);
        rcvCourses.setHasFixedSize(true);
        rcvCourses.setLayoutManager(new LinearLayoutManager(this));
        databaseReferenceSVCourses = FirebaseDatabase.getInstance().getReference("SV_Courses").child(firebaseUser.getUid());

        databaseReferenceCourses = FirebaseDatabase.getInstance().getReference("Courses");

        listsvcourses = new ArrayList<>();
        listcourses = new ArrayList<>();
        svcoursesAdapter = new SV_CoursesAdapter(this,listsvcourses);
        coursesAdapter = new CoursesAdapter(this,listcourses);
        rcvCourses.setAdapter(svcoursesAdapter);

        /*databaseReferenceSVCourses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listsvcourses.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SV_Courses svcourses =  dataSnapshot.getValue(SV_Courses.class);
                    String each_course_id = svcourses.getCourse_id();
                    listsvcourses.add(svcourses);
                    databaseReferenceCourses = FirebaseDatabase.getInstance().getReference("Courses");
                    Query query = databaseReferenceCourses.orderByChild("course_id").equalTo(each_course_id);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            listcourses.clear();
                            svcoursesAdapter.notifyDataSetChanged();
                            //Log.d("kiemtranema id ",each_course_id);
                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                Courses course = dataSnapshot1.getValue(Courses.class);
                                String each_course_name = course.getName();
                                svcourses.setCourse_id(each_course_id);

                                //listcourses.add(course);

                                //svcoursesAdapter.notifyDataSetChanged();
                                //coursesAdapter.notifyDataSetChanged();
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
        });*/

        /*String courses_id[] = new String[0];
        databaseReferenceSVCourses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listsvcourses.clear();
                int dem = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SV_Courses svCourses = dataSnapshot.getValue(SV_Courses.class);
                    courses_id[dem] = svCourses.getUserId();
                    Log.d("kiemtra id: ",courses_id[dem]);
                    dem++;
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });*/

    }
}
