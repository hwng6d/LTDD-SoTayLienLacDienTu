package com.example.stltdd;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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
import org.w3c.dom.Text;

import java.util.Objects;

public class ChiTietMHFragment extends Fragment {
    DatabaseReference databaseReference;

    //String courseName, courseCredits, courseFaculty, courseSpecilization, courseTimeTable, courseStartDate, courseEndDate, courseFee;
    String courseId;
    public ChiTietMHFragment() {
        // Required empty public constructor
    }

    public ChiTietMHFragment(String courseId) {
        this.courseId = courseId;
    }

    /*// TODO: Rename and change types and number of parameters
    public static ChiTietMHFragment newInstance() {
        ChiTietMHFragment fragment = new ChiTietMHFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chitietmh,container,false);
        TextView tvCourseName = view.findViewById(R.id.tvCourseName);
        TextView tvCourseId = view.findViewById(R.id.tvCourseId);
        TextView tvCourseCredits = view.findViewById(R.id.tvCourseCredits);
        TextView tvCourseFaculty = view.findViewById(R.id.tvCourseFaculty);
        TextView tvCourseSpecilization = view.findViewById(R.id.tvCourseSpecilization);
        TextView tvCourseTimeTable = view.findViewById(R.id.tvCourseTimeTable);
        TextView tvCourseStartDate = view.findViewById(R.id.tvCourseStartDate);
        TextView tvCourseEndDate = view.findViewById(R.id.tvCourseEndDate);
        TextView tvCourseFee = view.findViewById(R.id.tvCourseFee);
        /*tvCourseName.setText(courseName);
        tvCourseId.setText(courseId);
        tvCourseCredits.setText(courseCredits);
        tvCourseFaculty.setText(courseFaculty);
        tvCourseSpecilization.setText(courseSpecilization);
        tvCourseTimeTable.setText(courseTimeTable);
        tvCourseStartDate.setText(courseStartDate);
        tvCourseEndDate.setText(courseEndDate);
        tvCourseFee.setText(courseEndDate);*/
        databaseReference = FirebaseDatabase.getInstance().getReference("Courses");
        Query query = databaseReference.orderByChild("course_id").equalTo(courseId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Courses course = dataSnapshot.getValue(Courses.class);
                    tvCourseName.setText(Objects.requireNonNull(course).getName());
                    tvCourseId.setText(course.getCourse_id());
                    tvCourseCredits.setText(String.valueOf(course.getCredits()));
                    tvCourseFaculty.setText(course.getFaculty());
                    tvCourseSpecilization.setText(course.getSpecilization());
                    tvCourseTimeTable.setText(course.getTimetable());
                    tvCourseStartDate.setText(course.getStartdate());
                    tvCourseEndDate.setText(course.getEnddate());
                    tvCourseFee.setText(String.valueOf(course.getFee()));
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return view;
    }
}