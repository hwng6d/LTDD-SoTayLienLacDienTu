package com.example.stltdd;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

public class ChiTietMHADFragment extends Fragment {
    DatabaseReference databaseReferenceCourses;
    String courseId;

    public ChiTietMHADFragment() {
        // Required empty public constructor
    }

    public ChiTietMHADFragment(String courseId) {
        this.courseId = courseId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chitietmhad,container,false);
        ImageButton imgbtEditCourse = view.findViewById(R.id.imgbtEditCourse);
        TextView tvCourseName = view.findViewById(R.id.tvCourseName);
        TextView tvCourseId = view.findViewById(R.id.tvCourseId);
        TextView tvCourseCredits = view.findViewById(R.id.tvCourseCredits);
        TextView tvCourseFaculty = view.findViewById(R.id.tvCourseFaculty);
        TextView tvCourseSpecilization = view.findViewById(R.id.tvCourseSpecilization);
        TextView tvCourseTimeTable = view.findViewById(R.id.tvCourseTimeTable);
        TextView tvCourseStartDate = view.findViewById(R.id.tvCourseStartDate);
        TextView tvCourseEndDate = view.findViewById(R.id.tvCourseEndDate);
        TextView tvCourseFee = view.findViewById(R.id.tvCourseFee);
        databaseReferenceCourses = FirebaseDatabase.getInstance().getReference("Courses");
        Query query = databaseReferenceCourses.orderByChild("course_id").equalTo(courseId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {      //hiển thị thông tin môn học lên fragment này
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

                imgbtEditCourse.setOnClickListener(new View.OnClickListener() {     //xử lý sự kiện nhấn nút sửa
                    @Override
                    public void onClick(View v) {
                        //region code tao giao diện dialog
                        Dialog dialog = new Dialog(ChiTietMHADFragment.super.getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_course_edit);
                        Window window = dialog.getWindow();
                        if (window == null) {
                            return;
                        }
                        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        WindowManager.LayoutParams windowAttribute = window.getAttributes();
                        windowAttribute.gravity = Gravity.CENTER;
                        window.setAttributes(windowAttribute);
                        dialog.setCancelable(false);
                        //endregion

                        //khai báo biến
                        TextView tvCourseName = dialog.findViewById(R.id.tvCourseName);
                        TextView tvCourseId = dialog.findViewById(R.id.tvCourseId);
                        TextView tvCourseCredits = dialog.findViewById(R.id.tvCourseCredits);
                        TextView tvCourseFaculty = dialog.findViewById(R.id.tvCourseFaculty);
                        TextView tvCourseSpecilization = dialog.findViewById(R.id.tvCourseSpecilization);
                        TextView tvCourseFee = dialog.findViewById(R.id.tvCourseFee);
                        EditText etCourseTimeTable = dialog.findViewById(R.id.etCourseTimeTable);
                        Button btCancel = dialog.findViewById(R.id.btCancel);
                        Button btEdit = dialog.findViewById(R.id.btEdit);

                        //hiển thị thông tin môn học lên fragment
                        Query query1 = databaseReferenceCourses.orderByChild("course_id").equalTo(courseId);
                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Courses course = dataSnapshot.getValue(Courses.class);
                                    tvCourseName.setText(course.getName());
                                    tvCourseId.setText(course.getCourse_id());
                                    tvCourseCredits.setText(String.valueOf(course.getCredits()));
                                    tvCourseFaculty.setText(course.getFaculty());
                                    tvCourseSpecilization.setText(course.getSpecilization());
                                    tvCourseFee.setText(String.valueOf(course.getFee()));
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                            }
                        });

                        //set sự kiện cho dialog
                        //sự kiện ấn cancel
                        btCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        //sự kiện ấn edit
                        btEdit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String courseTimeTable = etCourseTimeTable.getText().toString().trim();
                                if (courseTimeTable.isEmpty()) {    //nếu rỗng
                                    Toast.makeText(ChiTietMHADFragment.super.getContext(), "Khung nhập lịch học trống, lịch học vẫn như cũ !", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {                            //nếu ko rỗng
                                    if (courseTimeTable.length() == 21) {           //nếu đủ 21 ký tự
                                        String infoTiet = courseTimeTable.substring(0, 5);       //ki tu thu 5 phai la so
                                        String infoComma = courseTimeTable.substring(6, 8);      //ki tu thu 8 phai la so
                                        String infoSoTiet = courseTimeTable.substring(9, 14);
                                        String infoComma2 = courseTimeTable.substring(14, 16);
                                        String infoThu = courseTimeTable.substring(16, 20);      //ki tu thu 20 phai la so
                                        boolean check2Commma = infoComma.equals(", ") && infoComma2.equals(", ");
                                        boolean checkTietThu = infoTiet.equals("Tiết ") && infoSoTiet.equals(" tiết") && infoThu.equals("thứ ");
                                        boolean checkIsNumber = TextUtils.isDigitsOnly(courseTimeTable.substring(5, 6))
                                                && TextUtils.isDigitsOnly(courseTimeTable.substring(8, 9))
                                                && TextUtils.isDigitsOnly(courseTimeTable.substring(20, 21));
                                        Log.d("kiemtra ky tu thu 5, 8, 20 la so ",courseTimeTable.substring(5, 5));

                                        if (check2Commma && checkTietThu && checkIsNumber) {        //nếu tất cả các đk trên đúng
                                            DatabaseReference databaseReferenceEditCourses = FirebaseDatabase.getInstance().getReference("Courses").child(courseId);
                                            databaseReferenceEditCourses.child("timetable").setValue(courseTimeTable);
                                            Toast.makeText(ChiTietMHADFragment.super.getContext(), "Đã sửa lịch học !", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();

                                            AppCompatActivity appCompatActivity = (AppCompatActivity) ChiTietMHADFragment.super.getContext();
                                            ChiTietMHADFragment chiTietMHADFragment = new ChiTietMHADFragment(courseId);
                                            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frlListCourses, chiTietMHADFragment).addToBackStack(null).commit();
                                        } else {                                                    //nếu sai
                                            Toast.makeText(ChiTietMHADFragment.super.getContext(), "Sửa lịch học phải đúng định dạng như bên dưới (x,y,z là số) !", Toast.LENGTH_SHORT).show();
                                            Log.d("kiemtra abc ", courseTimeTable.substring(0,0));
                                        }
                                    } else {                    //nếu ko phải 21 ký tự
                                        Toast.makeText(ChiTietMHADFragment.super.getContext(), "aaSửa lịch học phải đúng định dạng như bên dưới (x,y,z là số) !", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        dialog.show();
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return view;
    }
}