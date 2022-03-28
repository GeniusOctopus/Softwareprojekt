package com.softwareproject.backend.api;

public class Ranking {

    private String url;
    private long datetime;
    private int wins;
    private int loses;
    private long insertDatetime;
    private double winsPerVote;

    public Ranking() {

    }

    public Ranking(String url, long datetime, int wins, int loses, long insertDatetime, double winsPerVote) {
        this.url = url;
        this.datetime = datetime;
        this.wins = wins;
        this.loses = loses;
        this.insertDatetime = insertDatetime;
        this.winsPerVote = winsPerVote;
    }

    public Ranking(String url, int wins, int loses, long insertDatetime, double winsPerVote) {
        this.url = url;
        this.wins = wins;
        this.loses = loses;
        this.insertDatetime = insertDatetime;
        this.winsPerVote = winsPerVote;
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

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public long getInsertDatetime() {
        return insertDatetime;
    }

    public void setInsertDatetime(long insertDatetime) {
        this.insertDatetime = insertDatetime;
    }

    public double getWinsPerVote() {
        return winsPerVote;
    }

    public void setWinsPerVote(double winsPerVote) {
        this.winsPerVote = winsPerVote;
    }
}
