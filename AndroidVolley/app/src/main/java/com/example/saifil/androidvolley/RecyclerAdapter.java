package com.example.saifil.androidvolley;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    ArrayList<Project> arrayList = new ArrayList<>();

    public RecyclerAdapter(ArrayList<Project> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_row,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        Log.e("TAG","RecyclerAdapter onCreateViewHolder");


        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_project_id.setText(arrayList.get(position).getProject_id());
        holder.tv_user_id.setText(arrayList.get(position).getUser_id());
        holder.tv_project_title.setText(arrayList.get(position).getProject_title());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_project_id, tv_user_id, tv_project_title;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_project_id = itemView.findViewById(R.id.txt_project_id);
            tv_user_id = itemView.findViewById(R.id.txt_usr_id);
            tv_project_title =itemView.findViewById(R.id.txt_proj_detail_id);


        }
    }
}
