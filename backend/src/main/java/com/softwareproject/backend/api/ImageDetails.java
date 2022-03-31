package com.softwareproject.backend.api;

import com.softwareproject.backend.model.Image;

public class ImageDetails {

    private Image image;
    private String breedName;
    private String description;
    private String origin;
    private String wikipediaUrl;

    public ImageDetails() {

    }

    public ImageDetails(Image image, String breedName, String description, String origin, String wikipediaUrl) {
        this.image = image;
        this.breedName = breedName;
        this.description = description;
        this.origin = origin;
        this.wikipediaUrl = wikipediaUrl;
    }

    public ImageDetails(String breedName, String description, String origin, String wikipediaUrl) {
        this.breedName = breedName;
        this.description = description;
        this.origin = origin;
        this.wikipediaUrl = wikipediaUrl;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }

    public void setWikipediaUrl(String wikipediaUrl) {
        this.wikipediaUrl = wikipediaUrl;
    }
}
