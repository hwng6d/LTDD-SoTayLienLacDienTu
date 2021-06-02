package com.example.stltdd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.MyViewHolder>  {
    android.content.Context context;
    ArrayList<Students> liststudents;

    public StudentsAdapter(android.content.Context context, ArrayList<Students> liststudents) {
        this.context = context;
        this.liststudents = liststudents;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_student_parent_name_userid,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StudentsAdapter.MyViewHolder holder, int position) {
        Students student = liststudents.get(position);
        holder.tvStudentName.setText(student.getName());
        holder.tvStudentUid.setText((student.getUserId()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = student.getUserId();
                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                ChiTietSVFragment chiTietSVFragment = new ChiTietSVFragment(userId);
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frlListStudents,chiTietSVFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() { return liststudents.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName, tvStudentUid;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tvStudent_Parent_Name);
            tvStudentUid = itemView.findViewById(R.id.tvStudent_Parent_Uid);
        }
    }
}
