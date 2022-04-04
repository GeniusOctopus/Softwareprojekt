package com.softwareproject.backend.api;

import com.softwareproject.backend.model.Image;

public class RankingResponse {

    private Image image;
    private long datetime;
    private long value;

    public RankingResponse() {

    }

    public RankingResponse(Image image, long datetime, long value) {
        this.image = image;
        this.datetime = datetime;
        this.value = value;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
