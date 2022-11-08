package com.company.app.repository;

import com.company.app.model.LabelRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRecommendationRepository extends JpaRepository<LabelRecommendation, Integer> {
}
