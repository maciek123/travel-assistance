package com.hta.travelassistant.providers;

import com.hta.travelassistant.model.Recommendation;

import java.util.List;

/**
 * TODO: add description...
 */
public interface RecommendationProvider {
    List<Recommendation> getRecommendations();

    //  right now it should remove the existing recommendations and add the new one
    void updateRecommendation(List<Recommendation> recommendations);

}
