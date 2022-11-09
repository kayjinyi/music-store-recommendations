package com.company.app.repository;

import com.company.app.model.LabelRecommendation;
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
public class LabelRecommendationRepositoryTest {
    @Autowired
    LabelRecommendationRepository labelRecommendationRepository;
    @Before
    public void setUp() throws Exception{
        labelRecommendationRepository.deleteAll();
    }
    @Test
    public void shouldCreateGetAndDeleteLabelRecommendation() {
        LabelRecommendation newLabelRecommendation = new LabelRecommendation(1, 1, true);
        newLabelRecommendation = labelRecommendationRepository.save(newLabelRecommendation);
        LabelRecommendation foundLabelRecommendation = labelRecommendationRepository.findById(newLabelRecommendation.getId()).get();

        assertEquals(newLabelRecommendation, foundLabelRecommendation);

        labelRecommendationRepository.deleteById(newLabelRecommendation.getId());

        Optional<LabelRecommendation> shouldBeEmptyOptional = labelRecommendationRepository.findById(newLabelRecommendation.getId());

        assertEquals(false, shouldBeEmptyOptional.isPresent());

    }

    @Test
    public void shouldFindAllLabelRecommendation(){
        LabelRecommendation newLabelRecommendation = new LabelRecommendation(1, 1, true);
        LabelRecommendation newLabelRecommendation2 = new LabelRecommendation(100, 10000, true);

        newLabelRecommendation = labelRecommendationRepository.save(newLabelRecommendation);
        newLabelRecommendation2 = labelRecommendationRepository.save(newLabelRecommendation2);
        List<LabelRecommendation> allLabelRecommendationList = new ArrayList<>();
        allLabelRecommendationList.add(newLabelRecommendation);
        allLabelRecommendationList.add(newLabelRecommendation2);

        List<LabelRecommendation> foundAll = labelRecommendationRepository.findAll();
        assertEquals(allLabelRecommendationList.size(), foundAll.size());

    }

}