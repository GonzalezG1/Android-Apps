package com.example.newsgateway;

import java.io.Serializable;

public class Article implements Serializable {
    //Done
    private String author, title, description, url, urlToImage, publishedAt;

    public Article(String author, String title, String description,String url, String urlToImage, String publishedAt) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }
    //need the getters for sure
    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getUrl() {
        return url;
    }


    public String toString() {
        return "Article{" + "author='" + author + '\'' + ", title='" + title + '\'' + ", description='" + description + '\'' +
                ", url='" + url + '\'' + ", urlToImage='" + urlToImage + '\'' + ", publishedAt='" + publishedAt + '\'' +
                '}';
    }
    //not sure if i need the setters but ima put them anyways-- doesn't hurt.. yet
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }


}