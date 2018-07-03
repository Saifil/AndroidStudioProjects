package com.example.saifil.volleyproject;

//this class makes an object for project

public class ProjectDetails {

    private String project_id;
    private String user_id;
    private String project_title;

    public ProjectDetails(String project_id, String user_id, String project_title) {
        this.project_id = project_id;
        this.user_id = user_id;
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
