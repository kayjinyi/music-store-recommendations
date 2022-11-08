package com.company.app.repository;

import com.company.app.model.TrackRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRecommendationRepository extends JpaRepository<TrackRecommendation, Integer> {
}
