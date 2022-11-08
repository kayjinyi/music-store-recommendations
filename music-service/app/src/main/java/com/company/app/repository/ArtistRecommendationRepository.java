package com.company.app.repository;

import com.company.app.model.ArtistRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRecommendationRepository extends JpaRepository<ArtistRecommendation, Integer> {
}
