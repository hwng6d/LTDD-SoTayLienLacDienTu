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

public class HomePHActivity extends AppCompatActivity {
    //initilize
    TextView tvGreet,tvGreet1;
    CardView cvPoint, cvFee, cvTimeTable, cvLogout;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeph);

        //assign variables
        tvGreet = findViewById(R.id.tvGreet);
        tvGreet1 = findViewById(R.id.tvGreet1);
        cvPoint = findViewById(R.id.cvPoint);
        cvFee = findViewById(R.id.cvFee);
        cvTimeTable = findViewById(R.id.cvTimeTable);
        cvLogout = findViewById(R.id.cvLogout);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()); //đường dẫn tới userId trong node
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Parents ph = snapshot.getValue(Parents.class);
                tvGreet.setText("Xin chào, " + ph.getName());
                tvGreet1.setText(("Phụ huynh của: " + ph.getStudent_name()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        cvPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePHActivity.this,DiemMonHocActivity.class));
            }
        });

        cvFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePHActivity.this,HocPhiActivity.class));
            }
        });

        cvTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePHActivity.this,LichHocActivity.class));
            }
        });

        cvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        firebaseAuth = FirebaseAuth.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(HomePHActivity.this);
        builder.setMessage("Bạn có chắc muốn đăng xuất ?")
                .setPositiveButton("Đúng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseAuth.signOut();
                        Intent intent = new Intent(HomePHActivity.this,MainActivity.class);
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