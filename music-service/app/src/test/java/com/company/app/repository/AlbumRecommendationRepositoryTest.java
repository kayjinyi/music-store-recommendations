package com.company.app.repository;

import com.company.app.model.AlbumRecommendation;
import com.company.app.repository.AlbumRecommendationRepository;
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
public class AlbumRecommendationRepositoryTest {

    @Autowired
    AlbumRecommendationRepository albumRecommendationRepository;
    @Before
    public void setUp() throws Exception{
        albumRecommendationRepository.deleteAll();
    }
    @Test
    public void shouldCreateGetAndDeleteAlbumRecommendation() {
        AlbumRecommendation newAlbumRecommendation = new AlbumRecommendation(1, 1, true);
        newAlbumRecommendation = albumRecommendationRepository.save(newAlbumRecommendation);
        AlbumRecommendation foundAlbumRecommendation = albumRecommendationRepository.findById(newAlbumRecommendation.getId()).get();

        assertEquals(newAlbumRecommendation, foundAlbumRecommendation);

        albumRecommendationRepository.deleteById(newAlbumRecommendation.getId());

        Optional<AlbumRecommendation> shouldBeEmptyOptional = albumRecommendationRepository.findById(newAlbumRecommendation.getId());

        assertEquals(false, shouldBeEmptyOptional.isPresent());

    }

    @Test
    public void shouldFindAllAlbumRecommendation(){
        AlbumRecommendation newAlbumRecommendation = new AlbumRecommendation(1, 1, true);
        AlbumRecommendation newAlbumRecommendation2 = new AlbumRecommendation(100, 10000, true);

        newAlbumRecommendation = albumRecommendationRepository.save(newAlbumRecommendation);
        newAlbumRecommendation2 = albumRecommendationRepository.save(newAlbumRecommendation2);
        List<AlbumRecommendation> allAlbumRecommendationList = new ArrayList<>();
        allAlbumRecommendationList.add(newAlbumRecommendation);
        allAlbumRecommendationList.add(newAlbumRecommendation2);

        List<AlbumRecommendation> foundAll = albumRecommendationRepository.findAll();
        assertEquals(allAlbumRecommendationList.size(), foundAll.size());
        
    }
}