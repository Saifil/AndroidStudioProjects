package com.example.saifil.volleyproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    private Context mCtx;
    private List<ProjectDetails> projectList;

    public ProjectAdapter(Context mCtx, List<ProjectDetails> projectList) {
        this.mCtx = mCtx;
        this.projectList = projectList;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.project_layout,null);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        ProjectDetails projectDetails = projectList.get(position);
        holder.project_id.setText(projectDetails.getProject_id());
        holder.user_id.setText(projectDetails.getUser_id());
        holder.project_title.setText(projectDetails.getProject_title());
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder {
        TextView project_id, user_id, project_title;

        public ProjectViewHolder(View itemView) {
            super(itemView);

            project_id = itemView.findViewById(R.id.project_card_id);
            user_id = itemView.findViewById(R.id.user_card_id);
            project_title = itemView.findViewById(R.id.project_title_card_id);
        }
    }
}
