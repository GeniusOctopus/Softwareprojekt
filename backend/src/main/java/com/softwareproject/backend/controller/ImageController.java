package com.softwareproject.backend.controller;

import com.softwareproject.backend.model.Image;
import com.softwareproject.backend.service.ImageService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(name = "Image", description = "Endpunkt für Images", group = "Image")
@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageService imageService;

    @ApiMethod(description = "Gibt alle Bilder in der Datenbank zurück")
    @RequestMapping(value = "/images", method = RequestMethod.GET)
    public @ResponseBody List<Image> getAllImages() {

        return imageService.getAllImages();
    }

    @ApiMethod(description = "Gibt 2 Bilder für das Voting zurück")
    @RequestMapping(value = "/voting", method = RequestMethod.GET)
    public @ResponseBody List<Image> getImagesForVoting() {

        return imageService.getImagesForVoting();
    }

    @ApiMethod(description = "Erhöht den Zähler, der repräsentiert, wie oft ein Bild bisher angezeigt wurde")
    @RequestMapping(value = "/increaseTimesShownForVoting/{id}", method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity<Void> increaseTimesShownForVoting(@ApiPathParam(name = "id", description = "Die ID des Bilds, welches angezeigt wurde") @PathVariable("id") int id) {

        imageService.increaseTimesShownForVoting(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiMethod(description = "Speichert ein Bild in der Datenbank")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody Image addImage(@ApiPathParam(name = "image", description = "Bild, welches in der Datenbank gespeichert werden soll") @Valid @RequestBody Image image) {

        return imageService.addImage(image);
    }

    @ApiMethod(description = "Speichert mehrere Bilder in der Datenbank, die Daten werden von der CatApi abgerufen")
    @RequestMapping(value = "/addMultiple/{limit}", method = RequestMethod.GET)
    public @ResponseBody List<Image> addImages(
            @ApiPathParam(name = "limit", description = "Anzahl der Bilder, welche in der Datenbank gespeichert werden sollen") @PathVariable("limit") int limit) {

        return imageService.addImages(limit);
    }
}
