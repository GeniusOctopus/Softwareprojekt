package com.softwareproject.backend.service;

import com.softwareproject.backend.api.ImageDetails;
import com.softwareproject.backend.model.Image;
import com.softwareproject.backend.repository.ImageRepository;
import com.softwareproject.backend.repository.VoteRepository;
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
import java.util.Optional;

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

    public ImageDetails getImageDetailsById(int id) {

        Optional<Image> imageOptional = imageRepository.findById(id);
        if (imageOptional.isEmpty()) {
            return new ImageDetails();
        }

        Image image = imageOptional.get();
        ImageDetails imageDetails = new ImageDetails();

        String catApiUrl = "https://api.thecatapi.com/v1/images/" + image.getCatApiId();
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

            imageDetails = parseJsonForImageDetails(httpResponse.body());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        imageDetails.setImage(image);

        return imageDetails;
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

            List<Image> imageList = parseJsonForImage(httpResponse.body());

            getOnlyImagesWithCorrectProportion(imageList, minProportionFactor, maxProportionFactor);

            return imageRepository.saveAll(imageList);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Image> parseJsonForImage(String jsonString) {

        List<Image> imageList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonString);

        jsonArray.forEach(rawJsonObject -> {
            JSONObject jsonObject = (JSONObject) rawJsonObject;

            if (!jsonObject.getJSONArray("breeds").isEmpty()) {
                imageList.add(new Image(
                        System.currentTimeMillis(),
                        jsonObject.getString("id"),
                        jsonObject.getString("url"),
                        jsonObject.getInt("width"),
                        jsonObject.getInt("height"),
                        0,
                        jsonObject.getJSONArray("breeds").getJSONObject(0).getString("id")
                ));
            }
        });

        return imageList;
    }

    private ImageDetails parseJsonForImageDetails(String jsonString) {

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArrayBreeds = jsonObject.getJSONArray("breeds");
        JSONObject jsonObjectBreedsDetails = jsonArrayBreeds.getJSONObject(0);

        return new ImageDetails(
                jsonObjectBreedsDetails.getString("name"),
                jsonObjectBreedsDetails.getString("description"),
                jsonObjectBreedsDetails.getString("origin"),
                jsonObjectBreedsDetails.getString("wikipedia_url")
        );
    }

    private void getOnlyImagesWithCorrectProportion(List<Image> imageList, double minProportionFactor, double maxProportionFactor) {

        imageList.removeIf(image -> {
            double proportionalFactor = (double) image.getHeight() / image.getWidth();
            return proportionalFactor < minProportionFactor || proportionalFactor > maxProportionFactor;
        });
    }
}
