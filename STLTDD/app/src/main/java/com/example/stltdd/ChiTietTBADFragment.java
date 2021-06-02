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

public class ChiTietTBADFragment extends Fragment {
    DatabaseReference databaseReference;
    String notiId;

    public ChiTietTBADFragment() {
        // Required empty public constructor
    }

    public ChiTietTBADFragment(String notiId) { this.notiId = notiId; }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chitiettbad,container,false);
        TextView tvNotiName = view.findViewById(R.id.tvNotiName);
        TextView tvNotiId = view.findViewById(R.id.tvNotiId);
        TextView tvNotiContent = view.findViewById(R.id.tvNotiContent);
        TextView tvCourseName = view.findViewById(R.id.tvCourseName);
        TextView tvNotiDate = view.findViewById(R.id.tvNotiDate);
        databaseReference = FirebaseDatabase.getInstance().getReference("Notifications");
        Query query = databaseReference.orderByChild("noti_id").equalTo(notiId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Notifications noti = dataSnapshot.getValue(Notifications.class);
                    tvNotiName.setText(noti.getNoti_name());
                    tvNotiId.setText(noti.getNoti_id());
                    tvNotiContent.setText(noti.getNoti_content());

                    Query query1 = FirebaseDatabase.getInstance().getReference("Courses").orderByChild("course_id").equalTo(noti.getCourse_id());
                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                Courses course = dataSnapshot1.getValue(Courses.class);
                                tvCourseName.setText(course.getName());
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        }
                    });

                    tvNotiDate.setText(noti.getNoti_date());
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

        return view;
    }
}