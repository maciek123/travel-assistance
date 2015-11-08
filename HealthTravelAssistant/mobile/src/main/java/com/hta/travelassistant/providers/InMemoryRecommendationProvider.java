package com.hta.travelassistant.providers;

import com.hta.travelassistant.model.Recommendation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * TODO: add description...
 */
public class InMemoryRecommendationProvider implements RecommendationProvider {
    private final static RecommendationProvider INSTANCE = new InMemoryRecommendationProvider();

    public static RecommendationProvider getInstance(){
        return INSTANCE;
    }

    private List<Recommendation> recommendations = new CopyOnWriteArrayList<>();


    @Override
    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    @Override
    public void updateRecommendation(List<Recommendation> recommendations) {

        this.recommendations = recommendations;

    }
}
