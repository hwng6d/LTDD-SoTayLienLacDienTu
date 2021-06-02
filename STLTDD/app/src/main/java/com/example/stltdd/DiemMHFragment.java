package com.example.stltdd;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class DiemMHFragment extends Fragment {
    //initialize
    DatabaseReference databaseReferenceCourses;
    DatabaseReference databaseReferenceCoursesPoints;
    DatabaseReference databaseReferenceSVCourses;
    String courseId;
    String role = "";

    public DiemMHFragment() {
        // Required empty public constructor
    }

    public DiemMHFragment(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diemmh,container,false);
        TextView tvCourseName = view.findViewById(R.id.tvCourseName);
        TextView tvMidSemPoint = view.findViewById(R.id.tvMidSemPoint);
        TextView tvEndSemPoint = view.findViewById(R.id.tvEndSemPoint);
        TextView tvOverallPoint = view.findViewById(R.id.tvOverallPoint);

        //hiển thị tên mọc học cho fragment
        //region hiển thị tên môn học cho fragment
        databaseReferenceCourses = FirebaseDatabase.getInstance().getReference("Courses");
        Query query = databaseReferenceCourses.orderByChild("course_id").equalTo(courseId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Courses course = dataSnapshot.getValue(Courses.class);
                    tvCourseName.setText(course.getName());
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        //endregion

        //kiểm tra role của user hiện tại (sinhvien/phuhuynh)?
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        Query query1 = databaseReference.orderByChild("userId").equalTo(firebaseUser.getUid());
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            String sinhvien_userId = "";
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    GeneralUser gu = dataSnapshot.getValue(GeneralUser.class);
                    role = gu.getRole();
                    Log.d("kiemtra role trong diemfragment: ", role);

                    //nếu là phuhuynh, phải get đc userId sinhvien của phụ huynh. Không thì thôi
                    if (role.equals("phuhuynh")) {                                                                                  //nếu là phuhuynh
                        //Query query1 = databaseReference.orderByChild("userId").equalTo(firebaseUser.getUid());
                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Parents pr = dataSnapshot.getValue(Parents.class);
                                    sinhvien_userId = pr.getStudent_userId();
                                    Log.d("kiemtra id sinhvien cua phuhuynhtrong fragment o diem: ", sinhvien_userId);
                                    hienthiDiem(sinhvien_userId);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }
                    else {                                                                                                          //nếu là sinhvien
                        hienthiDiem(firebaseUser.getUid());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
            private void hienthiDiem(String sinhvienUid) {
                databaseReferenceSVCourses = FirebaseDatabase.getInstance().getReference("SV_Courses").child(sinhvienUid);
                Query query2 = databaseReferenceSVCourses.orderByChild("course_id").equalTo(courseId);
                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            SV_Courses svcourse = dataSnapshot.getValue(SV_Courses.class);
                            Log.d("kiemtra id trong fragment o diem: ", svcourse.getCourse_id());
                            databaseReferenceCoursesPoints = databaseReferenceSVCourses.child(svcourse.getCourse_id());
                            //databaseReferenceSVCourses.child(svcourse.getCourse_id());
                            databaseReferenceCoursesPoints.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot1) {
                                    Points point = snapshot1.child("Points").getValue(Points.class);
                                    double midSemPoint = point.getMidsem_point();
                                    Log.d("kiemtra diem giua ky trong fragment o diem: ", String.valueOf(midSemPoint));
                                    tvMidSemPoint.setText(String.valueOf(midSemPoint));
                                    double endSemPoint = point.getEndsem_point();
                                    Log.d("kiemtra diem cuoi ky trong fragment o diem: ", String.valueOf(endSemPoint));
                                    tvEndSemPoint.setText(String.valueOf(endSemPoint));
                                    double overallPoint = (midSemPoint + endSemPoint)/2;
                                    Log.d("kiemtra diem trung binh trong fragment o diem: ", String.valueOf(overallPoint));
                                    tvOverallPoint.setText(String.valueOf(overallPoint));
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
            }
        });
        return view;
    }

}