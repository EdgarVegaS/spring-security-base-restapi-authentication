package com.tibianos.tibianosfanpage.shared.dto;

import java.io.Serializable;

public class PostUpdateDto implements Serializable {
    

    private static final long serialVersionUID = 1L;
    
    private String title;
    private String content;
    private String exposureId;
    private String expirationTime;

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

    public String getExposureId() {
        return exposureId;
    }

    public void setExposureId(String exposureId) {
        this.exposureId = exposureId;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }
}
