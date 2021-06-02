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
import com.google.android.gms.tasks.OnCompleteListener;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    //reference view
    AutoCompleteTextView actvRole;
    TextInputEditText metEmail, metPassword;
    Button btLogin;
    FirebaseAuth firebaseAuth;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Code for Roles dropdown auto complete view
        actvRole = findViewById(R.id.actvRole);
        ArrayAdapter<CharSequence> rolesadapter = ArrayAdapter.createFromResource(this,R.array.roles,R.layout.dropdown_item_role);
        actvRole.setText(rolesadapter.getItem(0).toString(),false); //this make default value
        actvRole.setAdapter(rolesadapter);

        //assign variables
        constraintLayout = findViewById(R.id.constraintLayout);
        actvRole = findViewById(R.id.actvRole);     //dropdown chức vự
        metEmail = findViewById(R.id.metEmail);
        metPassword = findViewById(R.id.metPassword);
        btLogin = findViewById(R.id.btLogin);
        firebaseAuth = FirebaseAuth.getInstance();

        //activity
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Objects.requireNonNull(metEmail.getText()).toString().trim();
                String password = Objects.requireNonNull(metPassword.getText()).toString().trim();
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {      //ô email và password trống
                    openSnackBar(2);
                }
                else if (TextUtils.isEmpty(email)) {        //ô email trống
                    metEmail.setError("Vui lòng điền email");
                } else if (TextUtils.isEmpty(password)) {   //ô password trống
                   openSnackBar(1);
                } else {                                    //cả 2 ô đều đã nhập, chuyển sang bước xác thực và login bên dưới
                    loginToApp(email,password);
                }
            }
            private void openSnackBar(int count) {      //hàm gọi snackbar nếu password trống | (email, password) trống
                Snackbar.make(constraintLayout,count == 1 ? "Vui lòng điền mật khẩu" : "Vui lòng điền email và mật khẩu",Snackbar.LENGTH_SHORT)
                        .setAction("Đóng", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {}
                        }).show();
            }
        });
    }

    String real_role = "";
    private void loginToApp(String email, String password) {
        //login vào và xác thực actv_role có khớp với real_role ?
        //nếu không thì out ra và hiện ra snackbar thông báo

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                    Query query = databaseReference.orderByChild("userId").equalTo(firebaseUser.getUid());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            String actv_role = actvRole.getText().toString().trim();
                            if (actv_role.equals("Sinh viên")) {
                                actv_role = "sinhvien";
                            } else if (actv_role.equals("Phụ huynh")) {
                                actv_role = "phuhuynh";
                            } else {
                                actv_role = "admin";
                            }
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                GeneralUser gu = dataSnapshot.getValue(GeneralUser.class);
                                real_role = gu.getRole();
                                Log.d("kiemtra role ",real_role);
                                if (real_role.equals(actv_role)) {      //nếu đúng real_role và actv_role khớp nhau thì sẽ đăng nhập và chuyển vào màn hình đăng nhập
                                    loginToRole(real_role);
                                } else {                                //nếu sai thì hiện snackbar thông báo lỗi và signout ra
                                    openSnackBar("sai thông tin đăng nhập");
                                    firebaseAuth.signOut();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    }
                    );
                } else {
                    String errorText = Objects.requireNonNull(task.getException()).getMessage();
                    if (Objects.requireNonNull(errorText).contains("badly formatted")) {
                        openSnackBar("nhập sai định dạng email");
                    } else if (Objects.requireNonNull(errorText).contains("no user")) {
                        openSnackBar("nhập sai email (không có email này)");
                    } else if (Objects.requireNonNull(errorText).contains("The password is invalid")){
                        openSnackBar("nhập sai mật khẩu");
                    } else if (Objects.requireNonNull(errorText).contains("blocked all requested")){
                        openSnackBar("nhập quá nhiều lần. Vui lòng chờ trong giây lát");
                    } else if (Objects.requireNonNull(errorText).contains("network error")) {
                        openSnackBar("không có kết nối internet");
                    } else {
                        openSnackBar(errorText);
                    }
                }
            }
            private void openSnackBar(String text) {
                Snackbar.make(constraintLayout,(text.contains("nhập") || text.contains("không")) ? ("Bạn " + text) : (text), Snackbar.LENGTH_SHORT)
                        .setAction("Đóng", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {}
                        }).show();
            }
            private void loginToRole(String result) {           //login và chuyển vào màn hình đăng nhập
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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
        });

    }
}