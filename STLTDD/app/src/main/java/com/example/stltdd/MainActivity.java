package com.example.stltdd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    //reference view
    AutoCompleteTextView actvRole;
    TextInputEditText metEmail, metPassword;
    CheckBox cbRememberMe;
    Button btLogin;
    FirebaseAuth firebaseAuth;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Code for Roles dropdown
        actvRole = findViewById(R.id.actvRole);
        ArrayAdapter<CharSequence> rolesadapter = ArrayAdapter.createFromResource(this,R.array.roles,R.layout.dropdown_item_role);
        actvRole.setText(rolesadapter.getItem(0).toString(),false); //this make default value
        actvRole.setAdapter(rolesadapter);

        //assign variables
        actvRole = findViewById(R.id.actvRole);     //dropdown chức vự
        metEmail = findViewById(R.id.metEmail);
        metPassword = findViewById(R.id.metPassword);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        btLogin = findViewById(R.id.btLogin);
        firebaseAuth = FirebaseAuth.getInstance();
        constraintLayout = findViewById(R.id.constraintLayout);

        //activity
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Objects.requireNonNull(metEmail.getText()).toString().trim();
                String password = Objects.requireNonNull(metPassword.getText()).toString().trim();
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    openSnackBar(2);
                }
                else if (TextUtils.isEmpty(email)) {
                    metEmail.setError("Vui lòng điền email");
                } else if (TextUtils.isEmpty(password)) {
                   openSnackBar(1);
                } else {
                    loginToApp(email,password);
                }
            }
            private void openSnackBar(int count) {
                Snackbar.make(constraintLayout,count == 1 ? "Vui lòng điền mật khẩu" : "Vui lòng điền email và mật khẩu",Snackbar.LENGTH_SHORT)
                        .setAction("Đóng", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {}
                        }).show();
            }
        });
    }
    private void loginToApp(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String role_Dbcheck = actvRole.getText().toString().trim();
                    if (role_Dbcheck.equals("Sinh viên")) {
                        role_Dbcheck = "Students";
                    } else if (role_Dbcheck.equals("Phụ huynh")) {
                        role_Dbcheck = "Parents";
                    } else {
                        role_Dbcheck = "Admins";
                    }

                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(role_Dbcheck).child(Objects.requireNonNull(firebaseUser).getUid());
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            GeneralUser gu = snapshot.getValue(GeneralUser.class);
                            String result = gu.getRole();
                            Log.d("kiemtraaaaaaa gu: ",result);

                            String final_role = actvRole.getText().toString().trim();
                            if (final_role.equals("Sinh viên")) {
                                final_role = "sinhvien";
                            } else if (final_role.equals("Phụ huynh")) {
                                final_role = "phuhuynh";
                            } else {
                                final_role = "admin";
                            }
                            if (final_role.equals(result)) {
                                //đăng nhập vào role đã chọn đúng
                                loginToRole(result);

                            } else {
                                Log.d("kiemtraaaaaaa Uid cureent: ",firebaseUser.getUid());
                                firebaseAuth.signOut();
                            }
                        }
                        private void loginToRole(String result) {
                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            Intent intent = new Intent();
                            switch (result) {
                                case "sinhvien":
                                    intent = new Intent(MainActivity.this, HomeSVActivity.class);
                                    break;
                                case "phuhuynh":
                                    intent = new Intent(MainActivity.this, HomePHActivity.class);
                                    break;
                                case "admin":
                                    intent = new Intent(MainActivity.this, HomeADActivity.class);
                                default:
                                    break;
                            }
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                } else {    //task is not successful
                    String errorText = Objects.requireNonNull(task.getException()).getMessage();
                    if (Objects.requireNonNull(errorText).contains("badly formatted")) {
                        openSnackBar("sai định dạng email");
                    } else if (Objects.requireNonNull(errorText).contains("no user")) {
                        openSnackBar("sai email (không có email này)");
                    } else if (Objects.requireNonNull(errorText).contains("The password is invalid")){
                        openSnackBar("sai mật khẩu");
                    } else {
                        openSnackBar(errorText);
                    }
                }
            }

            private void openSnackBar(String text) {
                Snackbar.make(constraintLayout,text.contains("sai") ? ("Bạn đã nhập " + text) : (text), Snackbar.LENGTH_SHORT)
                        .setAction("Đóng", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {}
                        }).show();
            }
        });
    }
}