package com.example.saifil.power;

public class ProjectDetails{

    private String img_url;
    private String title;
    private String desc;
    private String percent;

    public ProjectDetails(String img_url, String title, String desc, String percent) {
        this.img_url = img_url;
        this.title = title;
        this.desc = desc;
        this.percent = percent;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getPercent() {
        return percent;
    }
}
