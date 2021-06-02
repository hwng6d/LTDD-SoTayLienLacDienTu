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

public class ChiTietPHFragment extends Fragment {
    DatabaseReference databaseReference;
    String userId;

    public ChiTietPHFragment() {
        // Required empty public constructor
    }

    public ChiTietPHFragment(String userId) { this.userId = userId; }

    /*// TODO: Rename and change types and number of parameters
    public static ChiTietPHFragment newInstance(String param1, String param2) {
        ChiTietPHFragment fragment = new ChiTietPHFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chitietph,container,false);
        TextView tvParentName = view.findViewById(R.id.tvParentName);
        TextView tvParentUid = view.findViewById(R.id.tvParentUid);
        TextView tvParentEmail = view.findViewById(R.id.tvParentEmail);
        TextView tvParentGender = view.findViewById(R.id.tvParentGender);
        TextView tvParentStudentName = view.findViewById(R.id.tvParentStudentName);
        TextView tvParentStudentUid = view.findViewById(R.id.tvParentStudentUid);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        Query query = databaseReference.orderByChild("userId").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Parents parent = dataSnapshot.getValue(Parents.class);
                    tvParentName.setText(parent.getName());
                    tvParentUid.setText(parent.getUserId());
                    tvParentEmail.setText(parent.getEmail());
                    tvParentGender.setText(parent.getGender());
                    tvParentStudentName.setText(parent.getStudent_name());
                    tvParentStudentUid.setText(parent.getStudent_userId());
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return view;
    }
}