package com.softwareproject.backend.repository;

import com.softwareproject.backend.BackendApplication;
import com.softwareproject.backend.model.Image;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
class ImageRepositoryTest {

    @Autowired
    ImageRepository imageRepository;

    @Test
    void getImagesForVoting() {

        Image imageOne = new Image(1, 1648112468722L, "bkm", "https://cdn2.thecatapi.com/images/bkm.jpg", 669, 650, 0);
        Image imageTwo = new Image(2, 1648112468722L, "cke", "https://cdn2.thecatapi.com/images/cke.jpg", 620, 465, 0);
        Image imageThree = new Image(3, 1648112468722L, "abc", "https://cdn2.thecatapi.com/images/cke.jpg", 620, 465, 1);
        Image imageFour = new Image(4, 1648112468722L, "def", "https://cdn2.thecatapi.com/images/cke.jpg", 620, 465, 1);
        imageRepository.saveAll(Arrays.asList(imageOne, imageTwo, imageThree, imageFour));

        List<Image> imageListResponse = imageRepository.getImagesForVoting();

        assertEquals(2, imageListResponse.size());
        checkEntry(imageOne, imageListResponse.get(0), null);
        checkEntry(imageTwo, imageListResponse.get(1), null);
    }

    @Test
    void increaseTimesShownForVoting() {

        Image imageOne = new Image(1, 1648112468722L, "bkm", "https://cdn2.thecatapi.com/images/bkm.jpg", 669, 650, 0);
        imageRepository.save(imageOne);

        imageRepository.increaseTimesShownForVoting(1);

        Image imageResponse = imageRepository.findById(1).get();
        checkEntry(imageOne, imageResponse, 1);
    }

    private void checkEntry(Image imageExpected, Image imageActual, Integer timesShown) {

        assertEquals(imageExpected.getId(), imageActual.getId());
        assertEquals(imageExpected.getCatApiId(), imageActual.getCatApiId());
        assertEquals(imageExpected.getDatetime(), imageActual.getDatetime());
        assertEquals(imageExpected.getHeight(), imageActual.getHeight());
        assertEquals(imageExpected.getWidth(), imageActual.getWidth());
        assertEquals(imageExpected.getUrl(), imageActual.getUrl());
        assertEquals(timesShown == null ? imageExpected.getTimesShown() : timesShown, imageActual.getTimesShown());
    }
}