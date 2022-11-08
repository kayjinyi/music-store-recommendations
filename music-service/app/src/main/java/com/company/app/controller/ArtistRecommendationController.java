package com.company.app.controller;


import com.company.app.model.ArtistRecommendation;
import com.company.app.repository.ArtistRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artistRecommendation")
public class ArtistRecommendationController {
    @Autowired
    private ArtistRecommendationRepository artistRecommendationRepository;

    @GetMapping()
    public List<ArtistRecommendation> getArtistRecommendations(){
        return artistRecommendationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ArtistRecommendation getArtistRecommendationById(@PathVariable Integer id){
        Optional<ArtistRecommendation> returnVal = artistRecommendationRepository.findById(id);
        if(returnVal.isPresent()){
            return returnVal.get();
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "artistRecommendationId '" + id + "' does not exist");
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistRecommendation addArtistRecommendation(@RequestBody ArtistRecommendation artistRecommendation){
        return artistRecommendationRepository.save(artistRecommendation);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtistRecommendation(@RequestBody ArtistRecommendation artistRecommendation){
        artistRecommendationRepository.save(artistRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtistRecommendation(@PathVariable Integer id){
        Optional<ArtistRecommendation> artistRecommendation = artistRecommendationRepository.findById(id);
        if(artistRecommendation.isPresent()) {
            artistRecommendationRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "artistRecommendationId '" + id + "' does not exist");
        }
    }
}
