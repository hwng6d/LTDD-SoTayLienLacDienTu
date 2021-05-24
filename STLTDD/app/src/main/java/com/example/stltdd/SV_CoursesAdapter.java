package com.example.stltdd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SV_CoursesAdapter extends RecyclerView.Adapter<SV_CoursesAdapter.MyViewHolder> {
    android.content.Context context;
    ArrayList<SV_Courses> listsvcourses;

    public SV_CoursesAdapter(android.content.Context context, ArrayList<SV_Courses> listsvcourses) {
        this.context = context;
        this.listsvcourses = listsvcourses;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_course_name,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SV_CoursesAdapter.MyViewHolder holder, int position) {
        SV_Courses svcourses = listsvcourses.get(position);
        holder.tvCourseName.setText(svcourses.getCourse_id());
    }

    @Override
    public int getItemCount() {
        return listsvcourses.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView tvCourseName;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
        }
    }
}
