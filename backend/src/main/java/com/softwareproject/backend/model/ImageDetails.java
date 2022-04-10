package com.softwareproject.backend.model;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name = "ImageDetails", description = "Die Details zu einem Bild")
public class ImageDetails {

    @ApiObjectField(name = "image", description = "Das Bild, zu dem die Details angegeben werden")
    private Image image;

    @ApiObjectField(name = "breedName", description = "Name der Rasse")
    private String breedName;

    @ApiObjectField(name = "description", description = "Eine nähere Beschreibung der Rasse")
    private String description;

    @ApiObjectField(name = "origin", description = "Ursprungsland der Rasse")
    private String origin;

    @ApiObjectField(name = "wikipediaUrl", description = "URL zum Wikipedia-Artikel der Rasse")
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
