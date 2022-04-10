package com.softwareproject.backend.model;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name = "BasicStatisticData", description = "Die grundlegenden Daten für die Statistik")
public class BasicStatisticData {

    @ApiObjectField(name = "votesLastTwentyFourHours", description = "Anzahl der Votes der letzten 24h")
    private int votesLastTwentyFourHours;

    @ApiObjectField(name = "votesLastSevenDays", description = "Anzahl der Votes der letzten 7 Tage")
    private int votesLastSevenDays;

    @ApiObjectField(name = "votesTotal", description = "Gesamtanzahl der Votes")
    private int votesTotal;

    public BasicStatisticData() {

    }

    public BasicStatisticData(int votesLastTwentyFourHours, int votesLastSevenDays, int votesTotal) {
        this.votesLastTwentyFourHours = votesLastTwentyFourHours;
        this.votesLastSevenDays = votesLastSevenDays;
        this.votesTotal = votesTotal;
    }

    public int getVotesLastTwentyFourHours() {
        return votesLastTwentyFourHours;
    }

    public void setVotesLastTwentyFourHours(int votesLastTwentyFourHours) {
        this.votesLastTwentyFourHours = votesLastTwentyFourHours;
    }

    public int getVotesLastSevenDays() {
        return votesLastSevenDays;
    }

    public void setVotesLastSevenDays(int votesLastSevenDays) {
        this.votesLastSevenDays = votesLastSevenDays;
    }

    public int getVotesTotal() {
        return votesTotal;
    }

    public void setVotesTotal(int votesTotal) {
        this.votesTotal = votesTotal;
    }
}
