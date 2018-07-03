package com.example.saifil.androidvolley;


import android.util.Log;

public class Project {
    private String project_id, user_id, project_title;

    public Project(String project_id, String user_id, String project_title) {
        this.project_id = project_id;
        this.user_id = user_id;
        this.project_title = project_title;
        Log.e("TAG","Project was here");

    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setProject_title(String project_title) {
        this.project_title = project_title;
    }

    public String getProject_id() {
        return project_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getProject_title() {
        return project_title;
    }
}
