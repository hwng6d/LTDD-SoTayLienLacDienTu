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

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.MyViewHolder> {
    android.content.Context context;
    ArrayList<Courses> listcourses;

    public CoursesAdapter(android.content.Context context, ArrayList<Courses> listcourses) {
        this.context = context;
        this.listcourses = listcourses;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_course_name,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CoursesAdapter.MyViewHolder holder, int position) {
        Courses course = listcourses.get(position);
        holder.tvCourseName.setText(course.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("kiemtra adapter id khi click card xem dsmh: ",course.getCourse_id());
                String course_id = course.getCourse_id();
                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                ChiTietMHFragment chiTietMHFragment = new ChiTietMHFragment(course_id);
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frlListCourses,chiTietMHFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listcourses.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView tvCourseName;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
        }
    }
}
