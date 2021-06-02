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
import java.util.Objects;

public class DiemMonHocActivity extends AppCompatActivity {
    //initialize
    ImageView imgBack;
    RecyclerView rcvCourses;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReferenceSVCourses;
    DatabaseReference databaseReferenceCourses;
    SV_CoursesAdapter svcoursesAdapter;
    CoursesPointsAdapter coursesPointsAdapter;
    ArrayList<SV_Courses> listsvcoursesForadpt;
    ArrayList<Courses> listcoursesForadpt;
    String role = "";
    String sinhvien_userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diemmonhoc);

        //assign
        imgBack = findViewById(R.id.imgBack);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();     //phải là get current sinhvien/sinhvien của phuhuynh hiện tại
        rcvCourses = findViewById(R.id.rcvCourses);
        rcvCourses.setHasFixedSize(true);
        rcvCourses.setLayoutManager(new LinearLayoutManager(this));
        listsvcoursesForadpt = new ArrayList<>();
        listcoursesForadpt = new ArrayList<>();
        svcoursesAdapter = new SV_CoursesAdapter(this,listsvcoursesForadpt);
        coursesPointsAdapter = new CoursesPointsAdapter(this,listcoursesForadpt);
        rcvCourses.setAdapter(coursesPointsAdapter);

        //b1
        //kiểm tra role hiện tại (sinhvien/phuhuynh)?
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        Query query = databaseReference.orderByChild("userId").equalTo(firebaseUser.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    GeneralUser gu = dataSnapshot.getValue(GeneralUser.class);
                    role = gu.getRole();
                    Log.d("kiemtra role trong  diemactivity ", role);

                    //nếu là phuhuynh, phải get đc userId sinhvien của phụ huynh. Không thì thôi
                    if (role.equals("phuhuynh")) { //nếu là phuhuynh
                        //Query query1 = databaseReference.orderByChild("userId").equalTo(firebaseUser.getUid());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                    Parents pr = dataSnapshot1.getValue(Parents.class);
                                    sinhvien_userId = pr.getStudent_userId();
                                    Log.d("kiemtra sinhvienid cua ph trong diemactivity: ", sinhvien_userId);
                                    xemDiemMonHoc(sinhvien_userId);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    } else {  //nếu là sinhvien
                        xemDiemMonHoc(firebaseUser.getUid());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void xemDiemMonHoc(String sinhvienUid) {
        databaseReferenceSVCourses = FirebaseDatabase.getInstance().getReference("SV_Courses").child(sinhvienUid);
        databaseReferenceCourses = FirebaseDatabase.getInstance().getReference("Courses");

        databaseReferenceCourses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listcoursesForadpt.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Courses course = dataSnapshot.getValue(Courses.class);
                    //listcoursesForadpt.add(course);
                    String each_course_id = Objects.requireNonNull(course).getCourse_id();
                    //Log.d("kiemtra id tat ca: ",each_course_id);
                    coursesPointsAdapter.notifyDataSetChanged();
                    //databaseReferenceSVCourses = FirebaseDatabase.getInstance().getReference("SV_Courses").child(firebaseUser.getUid());;
                    Query query = databaseReferenceSVCourses.orderByChild("course_id").equalTo(each_course_id);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            listsvcoursesForadpt.clear();
                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                SV_Courses svcourse = dataSnapshot1.getValue(SV_Courses.class);
                                course.setCourse_id(Objects.requireNonNull(svcourse).getCourse_id());
                                listcoursesForadpt.add(course);
                                coursesPointsAdapter.notifyDataSetChanged();
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
                DiemMonHocActivity.this.onBackPressed();
            }
        });
    }
}