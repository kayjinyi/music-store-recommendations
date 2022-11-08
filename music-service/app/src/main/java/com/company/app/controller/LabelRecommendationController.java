package com.company.app.controller;

import com.company.app.model.LabelRecommendation;
import com.company.app.repository.LabelRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/labelRecommendation")
public class LabelRecommendationController {
    @Autowired
    private LabelRecommendationRepository labelRecommendationRepository;

    @GetMapping()
    public List<LabelRecommendation> getLabelRecommendations(){
        return labelRecommendationRepository.findAll();
    }

    @GetMapping("/{id}")
    public LabelRecommendation getLabelRecommendationById(@PathVariable Integer id){
        Optional<LabelRecommendation> returnVal = labelRecommendationRepository.findById(id);
        if(returnVal.isPresent()){
            return returnVal.get();
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "labelRecommendationId '" + id + "' does not exist");
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public LabelRecommendation addLabelRecommendation(@RequestBody LabelRecommendation labelRecommendation){
        return labelRecommendationRepository.save(labelRecommendation);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabelRecommendation(@RequestBody LabelRecommendation labelRecommendation){
        labelRecommendationRepository.save(labelRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabelRecommendation(@PathVariable Integer id){
        Optional<LabelRecommendation> labelRecommendation = labelRecommendationRepository.findById(id);
        if(labelRecommendation.isPresent()) {
            labelRecommendationRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "labelRecommendationId '" + id + "' does not exist");
        }
    }

}
