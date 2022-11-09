package com.company.app.repository;

import com.company.app.model.ArtistRecommendation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ArtistRecommendationRepositoryTest {
    @Autowired
    ArtistRecommendationRepository artistRecommendationRepository;
    @Before
    public void setUp() throws Exception{
        artistRecommendationRepository.deleteAll();
    }
    @Test
    public void shouldCreateGetAndDeleteArtistRecommendation() {
        ArtistRecommendation newArtistRecommendation = new ArtistRecommendation(1, 1, true);
        newArtistRecommendation = artistRecommendationRepository.save(newArtistRecommendation);
        ArtistRecommendation foundArtistRecommendation = artistRecommendationRepository.findById(newArtistRecommendation.getId()).get();

        assertEquals(newArtistRecommendation, foundArtistRecommendation);

        artistRecommendationRepository.deleteById(newArtistRecommendation.getId());

        Optional<ArtistRecommendation> shouldBeEmptyOptional = artistRecommendationRepository.findById(newArtistRecommendation.getId());

        assertEquals(false, shouldBeEmptyOptional.isPresent());

    }

    @Test
    public void shouldFindAllArtistRecommendation(){
        ArtistRecommendation newArtistRecommendation = new ArtistRecommendation(1, 1, true);
        ArtistRecommendation newArtistRecommendation2 = new ArtistRecommendation(100, 10000, true);

        newArtistRecommendation = artistRecommendationRepository.save(newArtistRecommendation);
        newArtistRecommendation2 = artistRecommendationRepository.save(newArtistRecommendation2);
        List<ArtistRecommendation> allArtistRecommendationList = new ArrayList<>();
        allArtistRecommendationList.add(newArtistRecommendation);
        allArtistRecommendationList.add(newArtistRecommendation2);

        List<ArtistRecommendation> foundAll = artistRecommendationRepository.findAll();
        assertEquals(allArtistRecommendationList.size(), foundAll.size());

    }

}