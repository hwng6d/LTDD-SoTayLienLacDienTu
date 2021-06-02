package com.example.stltdd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class HomeADActivity extends AppCompatActivity {
    TextView tvGreet;
    CardView cvStudentsList, cvParentsList, cvManageNoti, cvManageCourse, cvLogout;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homead);

        //assign variables
        cvStudentsList = findViewById(R.id.cvStudentsList);
        cvParentsList = findViewById(R.id.cvParentsList);
        cvManageNoti = findViewById(R.id.cvManageNoti);
        cvManageCourse = findViewById(R.id.cvManageCourse);
        cvLogout = findViewById(R.id.cvLogout);
        tvGreet = findViewById(R.id.tvGreet);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Admins ad = snapshot.getValue(Admins.class);
                tvGreet.setText("Xin chào, " + ad.getName());
                Log.d("xemboicoi","asdfasdf");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        cvStudentsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeADActivity.this,DSSinhVienActivity.class));
            }
        });

        cvParentsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeADActivity.this,DSPhuHuynhActivity.class));
            }
        });

        cvManageNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeADActivity.this,DSNotiADActivity.class));
            }
        });

        cvManageCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeADActivity.this,DSMonHocADActivity.class));
            }
        });

        cvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeADActivity.this);
                builder.setMessage("Bạn có chắc muốn đăng xuất ?")
                        .setPositiveButton("Đúng", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firebaseAuth.signOut();
                                Intent intent = new Intent(HomeADActivity.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                //finish();
                                Log.d("kiemtra logout: ","da logout");
                            }
                        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        firebaseAuth = FirebaseAuth.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeADActivity.this);
        builder.setMessage("Bạn có chắc muốn đăng xuất ?")
                .setPositiveButton("Đúng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseAuth.signOut();
                        Intent intent = new Intent(HomeADActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();       //sao có cũng như ko vậy quí dzị
                        Log.d("kiemtra logout: ","da logout");
                    }
                }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}