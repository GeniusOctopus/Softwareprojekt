package com.softwareproject.backend.model;

import com.google.common.base.Objects;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@ApiObject(name = "Image", description = "Repräsentiert ein Bild für das Voting")
@Entity
public class Image {

    @ApiObjectField(name = "id", description = "ID des Bildes", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ApiObjectField(name = "datetime", description = "Aktuelles Datum und Zeit zum Zeitpunkt des hinzufügens des Datensatzes", required = true)
    @NotNull
    private long datetime;

    @ApiObjectField(name = "catApiId", description = "ID, welche die CatApi vergeben hat", required = true)
    @NotNull
    private String catApiId;

    @ApiObjectField(name = "url", description = "URL, unter welcher das Bild abgerufen werden kann", required = true)
    @NotNull
    private String url;

    @ApiObjectField(name = "width", description = "Breite des Bildes", required = true)
    @NotNull
    private int width;

    @ApiObjectField(name = "height", description = "Höhe des Bildes", required = true)
    @NotNull
    private int height;

    @ApiObjectField(name = "timesShown", description = "Anzahl, wie oft das Bld schon im Voting angezeigt wurde", required = true)
    @NotNull
    private int timesShown;

    public Image() {

    }

    public Image(int id, long datetime, String catApiId, String url, int width, int height, int timesShown) {
        this.id = id;
        this.datetime = datetime;
        this.catApiId = catApiId;
        this.url = url;
        this.width = width;
        this.height = height;
        this.timesShown = timesShown;
    }

    public Image(long datetime, String catApiId, String url, int width, int height, int timesShown) {
        this.datetime = datetime;
        this.catApiId = catApiId;
        this.url = url;
        this.width = width;
        this.height = height;
        this.timesShown = timesShown;
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

    public String getCatApiId() {
        return catApiId;
    }

    public void setCatApiId(String catApiId) {
        this.catApiId = catApiId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTimesShown() {
        return timesShown;
    }

    public void setTimesShown(int timesShown) {
        this.timesShown = timesShown;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id == image.id && datetime == image.datetime && width == image.width && height == image.height && timesShown == image.timesShown && Objects.equal(catApiId, image.catApiId) && Objects.equal(url, image.url);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, datetime, catApiId, url, width, height, timesShown);
    }
}
