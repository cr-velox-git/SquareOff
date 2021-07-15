package com.example.squareoff;

public class Model {

    private String name,slug,img;
    private long status;

    public Model(String name, String slug, String img, long status) {
        this.name = name;
        this.slug = slug;
        this.img = img;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }
}
