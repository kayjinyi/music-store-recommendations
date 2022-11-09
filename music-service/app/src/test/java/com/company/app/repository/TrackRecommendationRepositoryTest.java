package com.company.app.repository;

import com.company.app.model.TrackRecommendation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TrackRecommendationRepositoryTest {
    @Autowired
    TrackRecommendationRepository trackRecommendationRepository;
    @Before
    public void setUp() throws Exception{
        trackRecommendationRepository.deleteAll();
    }
    @Test
    public void shouldCreateGetAndDeleteTrackRecommendation() {
        TrackRecommendation newTrackRecommendation = new TrackRecommendation(1, 1, true);
        newTrackRecommendation = trackRecommendationRepository.save(newTrackRecommendation);
        TrackRecommendation foundTrackRecommendation = trackRecommendationRepository.findById(newTrackRecommendation.getId()).get();

        assertEquals(newTrackRecommendation, foundTrackRecommendation);

        trackRecommendationRepository.deleteById(newTrackRecommendation.getId());

        Optional<TrackRecommendation> shouldBeEmptyOptional = trackRecommendationRepository.findById(newTrackRecommendation.getId());

        assertEquals(false, shouldBeEmptyOptional.isPresent());

    }

    @Test
    public void shouldFindAllTrackRecommendation(){
        TrackRecommendation newTrackRecommendation = new TrackRecommendation(1, 1, true);
        TrackRecommendation newTrackRecommendation2 = new TrackRecommendation(100, 10000, true);

        newTrackRecommendation = trackRecommendationRepository.save(newTrackRecommendation);
        newTrackRecommendation2 = trackRecommendationRepository.save(newTrackRecommendation2);
        List<TrackRecommendation> allTrackRecommendationList = new ArrayList<>();
        allTrackRecommendationList.add(newTrackRecommendation);
        allTrackRecommendationList.add(newTrackRecommendation2);

        List<TrackRecommendation> foundAll = trackRecommendationRepository.findAll();
        assertEquals(allTrackRecommendationList.size(), foundAll.size());

    }
}