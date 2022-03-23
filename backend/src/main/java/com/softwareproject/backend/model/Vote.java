package com.softwareproject.backend.model;

import com.google.common.base.Objects;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@ApiObject(name = "Vote", description = "Repräsentiert ein Abstimmungsergebnis")
@Entity
public class Vote {

    @ApiObjectField(name = "id", description = "ID des Votes", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ApiObjectField(name = "datetime", description = "Aktuelles Datum und Zeit zum Zeitpunkt des hinzufügens des Datensatzes", required = true)
    @NotNull
    private long datetime;

    @ApiObjectField(name = "fk_ImageId_Winner", description = "FK des Gewinners", required = true)
    @ManyToOne
    @NotNull
    private Image fk_ImageId_Winner;

    @ApiObjectField(name = "fk_ImageId_Loser", description = "FK des Verlierers", required = true)
    @ManyToOne
    @NotNull
    private Image fk_ImageId_Loser;

    @ApiObjectField(name = "duration", description = "Zeit in Sekunden, welche für die Abstimmung benötigt wurde", required = true)
    @NotNull
    private int duration;

    @ApiObjectField(name = "winnerOnLeftSide", description = "true, wenn der Gewinner der Abstimmung sich auf der linken Seite befunden hat", required = true)
    @NotNull
    private boolean winnerOnLeftSide;

    public Vote() {

    }

    public Vote(int id, long datetime, Image fk_ImageId_Winner, Image fk_ImageId_Loser, int duration, boolean winnerOnLeftSide) {
        this.id = id;
        this.datetime = datetime;
        this.fk_ImageId_Winner = fk_ImageId_Winner;
        this.fk_ImageId_Loser = fk_ImageId_Loser;
        this.duration = duration;
        this.winnerOnLeftSide = winnerOnLeftSide;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public Image getFk_ImageId_Winner() {
        return fk_ImageId_Winner;
    }

    public void setFk_ImageId_Winner(Image fk_ImageId_Winner) {
        this.fk_ImageId_Winner = fk_ImageId_Winner;
    }

    public Image getFk_ImageId_Loser() {
        return fk_ImageId_Loser;
    }

    public void setFk_ImageId_Loser(Image fk_ImageId_Loser) {
        this.fk_ImageId_Loser = fk_ImageId_Loser;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isWinnerOnLeftSide() {
        return winnerOnLeftSide;
    }

    public void setWinnerOnLeftSide(boolean winnerOnLeftSide) {
        this.winnerOnLeftSide = winnerOnLeftSide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return id == vote.id && datetime == vote.datetime && duration == vote.duration && winnerOnLeftSide == vote.winnerOnLeftSide && Objects.equal(fk_ImageId_Winner, vote.fk_ImageId_Winner) && Objects.equal(fk_ImageId_Loser, vote.fk_ImageId_Loser);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, datetime, fk_ImageId_Winner, fk_ImageId_Loser, duration, winnerOnLeftSide);
    }
}
