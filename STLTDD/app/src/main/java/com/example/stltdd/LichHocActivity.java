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

public class LichHocActivity extends AppCompatActivity {
    //initialize
    ImageView imgBack;
    RecyclerView rcvTimetable;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReferenceSVCourses;
    DatabaseReference databaseReferenceCoursesTimeTable;
    SV_CoursesAdapter svcoursesAdapter;
    CoursesTimeTableAdapter coursesTimeTableAdapter;
    ArrayList<SV_Courses> listsvcoursesForadpt;
    ArrayList<Courses> listcoursestimetalbeForadpt;
    String role = "";
    String sinhvien_userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lichhoc);

        //assign
        imgBack = findViewById(R.id.imgBack);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        rcvTimetable = findViewById(R.id.rcvTimetable);
        rcvTimetable.setHasFixedSize(true);
        rcvTimetable.setLayoutManager(new LinearLayoutManager(this));
        databaseReferenceSVCourses = FirebaseDatabase.getInstance().getReference("SV_Courses").child(firebaseUser.getUid());
        databaseReferenceCoursesTimeTable = FirebaseDatabase.getInstance().getReference("Courses");
        listsvcoursesForadpt = new ArrayList<>();
        listcoursestimetalbeForadpt = new ArrayList<>();
        svcoursesAdapter = new SV_CoursesAdapter(this,listsvcoursesForadpt);
        coursesTimeTableAdapter = new CoursesTimeTableAdapter(this,listcoursestimetalbeForadpt);
        rcvTimetable.setAdapter(coursesTimeTableAdapter);

        //kiểm tra role hiện tại (sinhvien/phuhuynh/admin) ?
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        Query query = databaseReference.orderByChild("userId").equalTo(firebaseUser.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    GeneralUser gu = dataSnapshot.getValue(GeneralUser.class);
                    role = gu.getRole();
                    Log.d("kiemtra role trong hocphiactivity ",role);

                    //nếu là phuhuynh, phải get được userId sinhvinh của phuhuynh. Không thì thôi
                    if (role.equals("phuhuynh")) {      //nếu là phuhuynh
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                    Parents pr = dataSnapshot1.getValue(Parents.class);
                                    sinhvien_userId = pr.getStudent_userId();
                                    Log.d("kiemtra sinhvienid cua ph trong hocphiactivity", sinhvien_userId);
                                    xemLichHoc(sinhvien_userId);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    } else {        //nếu là sinhvien
                        xemLichHoc(firebaseUser.getUid());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void xemLichHoc(String sinhvienUid) {
        databaseReferenceSVCourses = FirebaseDatabase.getInstance().getReference("SV_Courses").child(sinhvienUid);
        databaseReferenceCoursesTimeTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listcoursestimetalbeForadpt.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Courses course = dataSnapshot.getValue(Courses.class);
                    String each_course_id = course.getCourse_id();
                    coursesTimeTableAdapter.notifyDataSetChanged();
                    Query query = databaseReferenceSVCourses.orderByChild("course_id").equalTo(each_course_id);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            listsvcoursesForadpt.clear();
                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                SV_Courses svcourse = dataSnapshot1.getValue(SV_Courses.class);
                                course.setCourse_id(svcourse.getCourse_id());
                                listcoursestimetalbeForadpt.add(course);
                                coursesTimeTableAdapter.notifyDataSetChanged();
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
                LichHocActivity.this.onBackPressed();
            }
        });
    }
}