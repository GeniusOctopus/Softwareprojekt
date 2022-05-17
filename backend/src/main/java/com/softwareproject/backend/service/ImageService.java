package com.softwareproject.backend.service;

import com.softwareproject.backend.model.ImageDetails;
import com.softwareproject.backend.model.Image;
import com.softwareproject.backend.repository.ImageRepository;
import org.apache.tomcat.util.codec.binary.Base64;
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
import java.sql.Blob;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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

        List<Image> imageList = imageRepository.getImagesForVoting();
        List<Image> imageListResponse = new ArrayList<>();

        int minimumTimesShown = imageList.get(0).getTimesShown();

        List<Image> imageListWithMinimumTimesShown = imageList.stream()
                .filter(image -> image.getTimesShown() == minimumTimesShown)
                .collect(Collectors.toList());

        if (imageListWithMinimumTimesShown.size() >= 2) {
            insertRandomImageToResponseList(imageListWithMinimumTimesShown, imageListResponse);
            insertRandomImageToResponseList(imageListWithMinimumTimesShown, imageListResponse);
        } else {
            //0 Einträge mit minimumTimesShown können nicht drin sein, es kann also nur noch 1 drin sein
            imageListResponse.add(imageListWithMinimumTimesShown.get(0));
            imageListResponse.add(imageList.stream()
                    .filter(image -> image.getTimesShown() == minimumTimesShown + 1)
                    .collect(Collectors.toList())
                    .get(0));
        }

        return imageListResponse;
    }

    public void increaseTimesShownForVoting(int id) {

        imageRepository.increaseTimesShownForVoting(id);
    }

    public Image addImage(@Valid Image image) {

        return imageRepository.save(image);
    }

    public String getBase64Image(String catApiUrl) {

        byte[] image = new byte[0];
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
            HttpResponse<byte[]> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());

            image = httpResponse.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64String(image);
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
                getValue("name", jsonObjectBreedsDetails),
                getValue("description", jsonObjectBreedsDetails),
                getValue("origin", jsonObjectBreedsDetails),
                getValue("wikipedia_url", jsonObjectBreedsDetails)
        );
    }

    private void getOnlyImagesWithCorrectProportion(List<Image> imageList, double minProportionFactor, double maxProportionFactor) {

        imageList.removeIf(image -> {
            double proportionalFactor = (double) image.getHeight() / image.getWidth();
            return proportionalFactor < minProportionFactor || proportionalFactor > maxProportionFactor;
        });
    }

    private String getValue(String key, JSONObject jsonObject) {
        return jsonObject.has(key)
                ? jsonObject.getString(key)
                : "not available";
    }

    private void insertRandomImageToResponseList(List<Image> imageListWithMinimumTimesShown, List<Image> imageListResponse) {
        int randomIndex = new Random().nextInt(imageListWithMinimumTimesShown.size());
        imageListResponse.add(imageListWithMinimumTimesShown.get(randomIndex));
        imageListWithMinimumTimesShown.remove(randomIndex);
    }
}
