package com.example.stltdd;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

public class ChiTietSVFragment extends Fragment {
    DatabaseReference databaseReference;
    String userId;

    public ChiTietSVFragment() {
        // Required empty public constructor
    }

    public ChiTietSVFragment(String userId) { this.userId = userId; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chitietsv,container,false);
        TextView tvStudentName = view.findViewById(R.id.tvStudentName);
        TextView tvStudentUid = view.findViewById(R.id.tvStudentUid);
        TextView tvStudentEmail = view.findViewById(R.id.tvStudentEmail);
        TextView tvStudentGender = view.findViewById(R.id.tvStudentGender);
        TextView tvStudentSchool = view.findViewById(R.id.tvStudentSchool);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");   //tạo đường dẫn tới node Users
        Query query = databaseReference.orderByChild("userId").equalTo(userId);             //lọc ra user nào có userId bằng với userId đc truyền vào fragment này
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {          //đổ data thông tin sinh viên lên fragment này
                    Students student = dataSnapshot.getValue(Students.class);
                    tvStudentName.setText(student.getName());
                    tvStudentEmail.setText(student.getEmail());
                    tvStudentGender.setText(student.getGender());
                    tvStudentSchool.setText(student.getSchool());
                    tvStudentUid.setText(student.getUserId());
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return view;
    }
}