package com.softwareproject.backend.api;

public class RankingResponse {

    private String url;
    private long datetime;
    private long value;

    public RankingResponse() {

    }

    public RankingResponse(String url, long datetime, long value) {
        this.url = url;
        this.datetime = datetime;
        this.value = value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
