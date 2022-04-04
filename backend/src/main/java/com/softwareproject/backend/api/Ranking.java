package com.softwareproject.backend.api;

import com.softwareproject.backend.model.Image;

public class Ranking {

    private Image image;
    private long datetime;
    private int wins;
    private int loses;
    private double winsPerVote;

    public Ranking() {

    }

    public Ranking(Image image, long datetime, int wins, int loses, double winsPerVote) {
        this.image = image;
        this.datetime = datetime;
        this.wins = wins;
        this.loses = loses;
        this.winsPerVote = winsPerVote;
    }

    public Ranking(Image image, int wins, int loses, double winsPerVote) {
        this.image = image;
        this.wins = wins;
        this.loses = loses;
        this.winsPerVote = winsPerVote;
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

    public double getWinsPerVote() {
        return winsPerVote;
    }

    public void setWinsPerVote(double winsPerVote) {
        this.winsPerVote = winsPerVote;
    }
}
