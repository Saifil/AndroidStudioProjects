package com.example.saifil.volleyrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    private Context mCtx;
    ArrayList<ProjectDetails> projectDetails = new ArrayList<>();

    public ProjectAdapter(Context mCtx, ArrayList<ProjectDetails> projectDetails) {
        this.mCtx = mCtx;
        this.projectDetails = projectDetails;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_layout,parent,false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        final String title = projectDetails.get(position).getTitle();
        holder.titleView.setText(title);

        String desc = projectDetails.get(position).getDesc();
        holder.textView.setText(desc);

        String percent = projectDetails.get(position).getPercent();
        holder.percentView.setText(percent);

        //Add image using Glide
        String url = projectDetails.get(position).getImg_url();
        Glide.with(holder.imageView.getContext()).load(url).into(holder.imageView);

        //Set on click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG","clicked card");
                Toast.makeText(mCtx, title, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectDetails.size();
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView, titleView, percentView;

        public ProjectViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_view_id);
            titleView = itemView.findViewById(R.id.txt_title_id);
            textView = itemView.findViewById(R.id.txt_view_id);
            percentView = itemView.findViewById(R.id.txt_percent_id);
        }
    }
}
