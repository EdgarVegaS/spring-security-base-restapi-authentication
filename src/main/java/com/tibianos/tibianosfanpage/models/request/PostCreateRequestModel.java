package com.tibianos.tibianosfanpage.models.request;


public class PostCreateRequestModel {

    private String title;
    private String content;
    private long exposureId;
    private int expirationTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getexposureId() {
        return exposureId;
    }

    public void setexposureId(long exposureId) {
        this.exposureId = exposureId;
    }

    public int getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
    }
}
