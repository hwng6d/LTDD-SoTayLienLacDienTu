package com.example.stltdd;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class CoursesFeeAdapter extends RecyclerView.Adapter<CoursesFeeAdapter.MyViewHolder> {
    android.content.Context context;
    ArrayList<Courses> listcourses;

    public CoursesFeeAdapter(android.content.Context context, ArrayList<Courses> listcourses) {
        this.context = context;
        this.listcourses = listcourses;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_course_fee,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CoursesFeeAdapter.MyViewHolder holder, int position) {
        Courses course = listcourses.get(position);
        holder.tvCourseName.setText(course.getName());
        holder.tvCourseFee.setText(String.valueOf(course.getFee()));
    }

    @Override
    public int getItemCount() {
        return listcourses.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView tvCourseName, tvCourseFee;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvCourseFee = itemView.findViewById(R.id.tvCourseFee);
        }
    }
}
