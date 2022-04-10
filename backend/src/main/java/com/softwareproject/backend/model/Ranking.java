package com.softwareproject.backend.model;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name = "Ranking", description = "Stellt eine Platzierung eines Bildes dar")
public class Ranking {

    @ApiObjectField(name = "image", description = "Das Bild, zu dem das Ranking gehört")
    private Image image;

    @ApiObjectField(name = "datetime", description = "Aktuelles Datum, an dem das Ranking erstellt wurde")
    private long datetime;

    @ApiObjectField(name = "wins", description = "Gewonnene Votings")
    private int wins;

    @ApiObjectField(name = "loses", description = "Verlorene Votings")
    private int loses;

    @ApiObjectField(name = "winsPerVote", description = "Prozentualer Anteil der Gewinne des Bildes")
    private double winsPerVote;

    @ApiObjectField(name = "rank", description = "Rang des Bildes")
    private int rank;

    public Ranking() {

    }

    public Ranking(Image image, long datetime, int wins, int loses, double winsPerVote) {
        this.image = image;
        this.datetime = datetime;
        this.wins = wins;
        this.loses = loses;
        this.winsPerVote = winsPerVote;
    }

    public Ranking(Image image, int wins, int loses, double winsPerVote, int rank) {
        this.image = image;
        this.wins = wins;
        this.loses = loses;
        this.winsPerVote = winsPerVote;
        this.rank = rank;
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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
