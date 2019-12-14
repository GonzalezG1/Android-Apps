package com.example.newsgateway;

import java.io.Serializable;

public class NewsSource implements Serializable {

    //based on assignment
    //DONE?
    private String id, name, url, category;

    public NewsSource(String id, String name, String url, String category) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.category = category;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return name;
    }
    public String getUrl() {
        return url;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
