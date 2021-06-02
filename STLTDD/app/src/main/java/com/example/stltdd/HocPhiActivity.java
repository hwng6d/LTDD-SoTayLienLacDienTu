package com.example.stltdd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
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

public class HocPhiActivity extends AppCompatActivity {
    //initialize
    ImageView imgBack;
    TextView tvSumOfFee;
    RecyclerView rcvFee;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReferenceSVCourses;
    DatabaseReference databaseReferenceCoursesFee;
    SV_CoursesAdapter svcoursesAdapter;
    CoursesFeeAdapter coursesFeeAdapter;
    ArrayList<SV_Courses> listsvcoursesForadpt;
    ArrayList<Courses> listcoursesFeeForadpt;
    String role = "";
    String sinhvien_userId = "";
    int tonghocphi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hocphi);

        //assign
        imgBack = findViewById(R.id.imgBack);
        tvSumOfFee = findViewById(R.id.tvSumOfFee);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        rcvFee = findViewById(R.id.rcvFee);
        rcvFee.setHasFixedSize(true);
        rcvFee.setLayoutManager(new LinearLayoutManager(this));
        databaseReferenceSVCourses = FirebaseDatabase.getInstance().getReference("SV_Courses").child(firebaseUser.getUid());
        databaseReferenceCoursesFee = FirebaseDatabase.getInstance().getReference("Courses");
        listsvcoursesForadpt = new ArrayList<>();
        listcoursesFeeForadpt = new ArrayList<>();
        svcoursesAdapter = new SV_CoursesAdapter(this,listsvcoursesForadpt);
        coursesFeeAdapter = new CoursesFeeAdapter(this,listcoursesFeeForadpt);
        rcvFee.setAdapter(coursesFeeAdapter);

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
                                    xemHocPhi(sinhvien_userId);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    } else {        //nếu là sinhvien
                        xemHocPhi(firebaseUser.getUid());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void xemHocPhi(String sinhvienUid) {
        databaseReferenceSVCourses = FirebaseDatabase.getInstance().getReference("SV_Courses").child(sinhvienUid);
        databaseReferenceCoursesFee.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listcoursesFeeForadpt.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Courses course = dataSnapshot.getValue(Courses.class);
                    String each_course_id = course.getCourse_id();
                    coursesFeeAdapter.notifyDataSetChanged();
                    Query query = databaseReferenceSVCourses.orderByChild("course_id").equalTo(each_course_id);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            listsvcoursesForadpt.clear();
                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                SV_Courses svcourse = dataSnapshot1.getValue(SV_Courses.class);
                                course.setCourse_id(svcourse.getCourse_id());

                                tonghocphi = tonghocphi + course.getFee();

                                listcoursesFeeForadpt.add(course);
                                coursesFeeAdapter.notifyDataSetChanged();
                            }

                            tvSumOfFee.setText(String.valueOf(tonghocphi));
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
                HocPhiActivity.this.onBackPressed();
            }
        });
    }
}