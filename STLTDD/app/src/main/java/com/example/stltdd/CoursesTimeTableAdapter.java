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
import org.w3c.dom.Text;

import java.util.ArrayList;

public class CoursesTimeTableAdapter extends RecyclerView.Adapter<CoursesTimeTableAdapter.MyViewHolder> {
    android.content.Context context;
    ArrayList<Courses> listcourses;

    public CoursesTimeTableAdapter(android.content.Context context, ArrayList<Courses> listcourses) {
        this.context = context;
        this.listcourses = listcourses;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_course_timetable,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CoursesTimeTableAdapter.MyViewHolder holder, int position) {
        Courses course = listcourses.get(position);
        holder.tvCourseName.setText(course.getName());
        holder.tvCourseTimeTable.setText(course.getTimetable());
    }

    @Override
    public int getItemCount() {
        return listcourses.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView tvCourseName;
        TextView tvCourseTimeTable;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvCourseTimeTable = itemView.findViewById(R.id.tvCourseTimeTable);
        }
    }
}
