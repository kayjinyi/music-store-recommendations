package com.company.app.repository;

import com.company.app.model.AlbumRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRecommendationRepository extends JpaRepository<AlbumRecommendation, Integer> {
}
