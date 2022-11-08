package com.company.app.controller;

import com.company.app.model.AlbumRecommendation;
import com.company.app.repository.AlbumRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("albumRecommendation")
public class AlbumRecommendationController {
    @Autowired
    private AlbumRecommendationRepository albumRecommendationRepository;

    @GetMapping()
    public List<AlbumRecommendation> getAlbumRecommendations(){
        return albumRecommendationRepository.findAll();
    }

    @GetMapping("/{id}")
    public AlbumRecommendation getAlbumRecommendationById(@PathVariable Integer id){
        Optional<AlbumRecommendation> returnVal = albumRecommendationRepository.findById(id);
        if(returnVal.isPresent()){
            return returnVal.get();
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "albumRecommendationId '" + id + "' does not exist");
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumRecommendation addAlbumRecommendation(@RequestBody AlbumRecommendation albumRecommendation){
        return albumRecommendationRepository.save(albumRecommendation);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbumRecommendation(@RequestBody AlbumRecommendation albumRecommendation){
        albumRecommendationRepository.save(albumRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbumRecommendation(@PathVariable Integer id){
        Optional<AlbumRecommendation> albumRecommendation = albumRecommendationRepository.findById(id);
        if(albumRecommendation.isPresent()) {
            albumRecommendationRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "albumRecommendationId '" + id + "' does not exist");
        }
    }
}
