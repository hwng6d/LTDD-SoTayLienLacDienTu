package com.example.stltdd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

public class DSNotiADActivity extends AppCompatActivity {
    //initialize
    TextView tvReload;
    ImageView imgBack;
    RecyclerView rcvNoti;
    FloatingActionButton btAddNoti;
    DatabaseReference databaseReferenceTB;
    NotiADAdapter notiADAdapter;
    ArrayList<Notifications> listNoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsnotiad);

        //asign
        tvReload = findViewById(R.id.tvReload);
        imgBack = findViewById(R.id.imgBack);
        rcvNoti = findViewById(R.id.rcvNoti);
        btAddNoti = findViewById(R.id.btAddNoti);
        rcvNoti.setHasFixedSize(true);
        rcvNoti.setLayoutManager(new LinearLayoutManager(this));
        databaseReferenceTB = FirebaseDatabase.getInstance().getReference("Notifications");
        listNoti = new ArrayList<>();
        notiADAdapter = new NotiADAdapter(this,listNoti);
        rcvNoti.setAdapter(notiADAdapter);

        databaseReferenceTB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Notifications noti = dataSnapshot.getValue(Notifications.class);
                    listNoti.add(noti);
                    notiADAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        btAddNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //region code tao giao diện dialog
                Dialog dialog = new Dialog(DSNotiADActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_noti_add);
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
                EditText etAddNotiName = dialog.findViewById(R.id.etAddNotiName);
                EditText etAddNotiContent = dialog.findViewById(R.id.etAddNotiContent);
                AutoCompleteTextView actvNotiCourse = dialog.findViewById(R.id.actvNotiCourse);
                Button btCancel = dialog.findViewById(R.id.btCancel);
                Button btAdd = dialog.findViewById(R.id.btAdd);

                //hiển thị môn học lên spinner
                DatabaseReference databaseReferenceCourses = FirebaseDatabase.getInstance().getReference("Courses");
                ArrayList<String> listCourses = new ArrayList<>();
                databaseReferenceCourses.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Courses course = dataSnapshot.getValue(Courses.class);
                            listCourses.add(course.getName());
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(DSNotiADActivity.this,R.layout.support_simple_spinner_dropdown_item,listCourses);
                        actvNotiCourse.setAdapter(arrayAdapter);
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

                //sự kiện ấn add
                btAdd.setOnClickListener(new View.OnClickListener() {
                    String courseId;
                    @Override
                    public void onClick(View v) {
                        String notiName = etAddNotiName.getText().toString().trim();
                        String notiContent = etAddNotiContent.getText().toString().trim();

                        DatabaseReference databaseReferenceCourses = FirebaseDatabase.getInstance().getReference("Courses");
                        Query query = databaseReferenceCourses.orderByChild("name").equalTo(actvNotiCourse.getText().toString());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Courses course = dataSnapshot.getValue(Courses.class);
                                    courseId = course.getCourse_id();
                                    Log.d("kiemtra dữ liệu đã nhập ",courseId + " " + notiName + " " + notiContent + " " + String.valueOf(Calendar.getInstance().getTime()));
                                    Notifications noti = new Notifications(notiName, notiContent, courseId, String.valueOf(Calendar.getInstance().getTime()));
                                    DatabaseReference databaseReferenceNoti = FirebaseDatabase.getInstance().getReference("Notifications");
                                    databaseReferenceNoti.push().setValue(noti);

                                    //set lại noti_id
                                    Query query1 = databaseReferenceNoti.orderByChild("noti_date").equalTo(String.valueOf(Calendar.getInstance().getTime()));
                                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                                String key = dataSnapshot1.getKey();
                                                databaseReferenceNoti.child(key).child("noti_id").setValue(key);
                                                finish();
                                                startActivity(new Intent(DSNotiADActivity.this,DSNotiADActivity.class));
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
                    }
                });
                dialog.show();
            }
        });

        tvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReferenceTB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        listNoti.clear();
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            Notifications noti = dataSnapshot1.getValue(Notifications.class);
                            listNoti.add(noti);
                            notiADAdapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DSNotiADActivity.this.onBackPressed();
            }
        });

    }
}