package com.company.app.controller;

import com.company.app.model.TrackRecommendation;
import com.company.app.repository.TrackRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trackRecommendation")
public class TrackRecommendationController {
    @Autowired
    private TrackRecommendationRepository trackRecommendationRepository;

    @GetMapping()
    public List<TrackRecommendation> getTrackRecommendations(){
        return trackRecommendationRepository.findAll();
    }

    @GetMapping("/{id}")
    public TrackRecommendation getTrackRecommendationById(@PathVariable Integer id){
        Optional<TrackRecommendation> returnVal = trackRecommendationRepository.findById(id);
        if(returnVal.isPresent()){
            return returnVal.get();
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "trackRecommendationId '" + id + "' does not exist");
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TrackRecommendation addTrackRecommendation(@RequestBody TrackRecommendation trackRecommendation){
        return trackRecommendationRepository.save(trackRecommendation);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrackRecommendation(@RequestBody TrackRecommendation trackRecommendation){
        trackRecommendationRepository.save(trackRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrackRecommendation(@PathVariable Integer id){
        Optional<TrackRecommendation> trackRecommendation = trackRecommendationRepository.findById(id);
        if(trackRecommendation.isPresent()) {
            trackRecommendationRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "trackRecommendationId '" + id + "' does not exist");
        }
    }
}
