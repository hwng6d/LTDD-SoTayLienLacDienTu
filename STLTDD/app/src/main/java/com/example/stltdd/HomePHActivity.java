package com.example.stltdd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class HomePHActivity extends AppCompatActivity {
    //initilize
    TextView tvGreet,tvGreet1;
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
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child("Parents").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Parents ph = snapshot.getValue(Parents.class);
                tvGreet.setText("Xin chào, " + ph.getName());
                tvGreet1.setText(("Phụ huynh của: " + ph.getStudent_name()));
                Log.d("xemboicoi",ph.getName() + ph.getEmail() + ph.getStudent_id() + ph.getGender());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}