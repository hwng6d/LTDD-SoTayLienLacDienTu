package com.example.stltdd;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class NotiAdapter extends RecyclerView.Adapter<NotiAdapter.MyViewHolder> {android.content.Context context;
    ArrayList<Notifications> listNoti;

    public NotiAdapter(android.content.Context context, ArrayList<Notifications> listNoti) {
        this.context = context;
        this.listNoti = listNoti;
    }

    @NonNull
    @NotNull
    @Override
    public NotiAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_noti,parent,false);
        return new NotiAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotiAdapter.MyViewHolder holder, int position) {
        Notifications noti = listNoti.get(position);
        holder.tvNotiName.setText(noti.getNoti_name());

        //đổ tên môn học từ db lên app nò
        DatabaseReference databaseReferenceCourses = FirebaseDatabase.getInstance().getReference("Courses");
        Query query = databaseReferenceCourses.orderByChild("course_id").equalTo(noti.getCourse_id());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Courses course = dataSnapshot.getValue(Courses.class);
                    holder.tvNotiCourseName.setText(course.getName());
                    Log.d("kiemtra coursename trong dstbtheo courseid ",course.getName());
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

        holder.tvNotiDate.setText(noti.getNoti_date());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noti_id = noti.getNoti_id();
                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                ChiTietTBADFragment chiTietTBFragment = new ChiTietTBADFragment(noti_id);
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frlListNoti,chiTietTBFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() { return listNoti.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNotiName, tvNotiCourseName, tvNotiDate;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvNotiName = itemView.findViewById(R.id.tvNotiName);
            tvNotiCourseName = itemView.findViewById(R.id.tvNotiCourseName);
            tvNotiDate = itemView.findViewById(R.id.tvNotiDate);
        }
    }
}
