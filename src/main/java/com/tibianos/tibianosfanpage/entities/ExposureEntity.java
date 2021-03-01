package com.tibianos.tibianosfanpage.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "Exposures")
public class ExposureEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false,length = 50)
    private String type;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exposure" )
    private List<PostEntity> post = new ArrayList<>();


    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PostEntity> getPost() {
        return post;
    }

    public void setPost(List<PostEntity> post) {
        this.post = post;
    }
}
