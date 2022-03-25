package com.softwareproject.backend.service;

import com.softwareproject.backend.model.Image;
import com.softwareproject.backend.repository.ImageRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Validated
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> getAllImages() {

        return imageRepository.findAll();
    }

    public List<Image> getImagesForVoting() {

        return imageRepository.getImagesForVoting();
    }

    public void increaseTimesShownForVoting(int id) {

        imageRepository.increaseTimesShownForVoting(id);
    }

    public Image addImage(@Valid Image image) {

        return imageRepository.save(image);
    }

    public List<Image> addImages(int limit, double minProportionFactor, double maxProportionFactor) {

        String catApiUrl = "https://api.thecatapi.com/v1/images/search?limit=" + limit;
        HttpClient httpClient = HttpClient
                .newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        try {
            URI catApiUri = new URI(catApiUrl);
            HttpRequest httpRequest = HttpRequest
                    .newBuilder(catApiUri)
                    .GET()
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            List<Image> imageList = parseJson(httpResponse.body());
            getOnlyImagesWithCorrectProportion(imageList, minProportionFactor, maxProportionFactor);

            return imageRepository.saveAll(imageList);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Image> parseJson(String jsonString) {

        List<Image> imageList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonString);

        jsonArray.forEach(rawJsonObject -> {
            JSONObject jsonObject = (JSONObject) rawJsonObject;
            imageList.add(new Image(
                    System.currentTimeMillis(),
                    jsonObject.getString("id"),
                    jsonObject.getString("url"),
                    jsonObject.getInt("width"),
                    jsonObject.getInt("height"),
                    0
            ));
        });

        return imageList;
    }

    private void getOnlyImagesWithCorrectProportion(List<Image> imageList, double minProportionFactor, double maxProportionFactor) {

        imageList.removeIf(image -> {
            double proportionalFactor = (double) image.getHeight() / image.getWidth();
            return proportionalFactor < minProportionFactor || proportionalFactor > maxProportionFactor;
        });
    }
}
