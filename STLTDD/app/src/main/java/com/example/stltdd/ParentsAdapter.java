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

public class ParentsAdapter extends RecyclerView.Adapter<ParentsAdapter.MyViewHolder> {
    android.content.Context context;
    ArrayList<Parents> listParents;

    public ParentsAdapter(android.content.Context context, ArrayList<Parents> listParents) {
        this.context = context;
        this.listParents = listParents;
    }

    @NonNull
    @NotNull
    @Override
    public ParentsAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_student_parent_name_userid,parent,false);
        return new ParentsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ParentsAdapter.MyViewHolder holder, int position) {
        Parents parent = listParents.get(position);
        holder.tvParentName.setText(parent.getName());
        holder.tvParentUid.setText((parent.getUserId()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = parent.getUserId();
                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                ChiTietPHFragment chiTietPHFragment = new ChiTietPHFragment(userId);
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frlListParents,chiTietPHFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() { return listParents.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvParentName, tvParentUid;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvParentName = itemView.findViewById(R.id.tvStudent_Parent_Name);
            tvParentUid = itemView.findViewById(R.id.tvStudent_Parent_Uid);
        }
    }
}
