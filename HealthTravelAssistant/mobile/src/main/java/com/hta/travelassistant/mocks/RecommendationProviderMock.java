package com.hta.travelassistant.mocks;

import com.hta.travelassistant.model.Action;
import com.hta.travelassistant.model.Recommendation;
import com.hta.travelassistant.providers.RecommendationProvider;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO: add description...
 */
public class RecommendationProviderMock implements RecommendationProvider {
    @Override
    public List<Recommendation> getRecommendations() {

        List<Recommendation> result = new ArrayList<>();

        Iterable<Action> action = Arrays.asList(Action.SLEEP, Action.NOSUNLIGHT);
        Recommendation recommendation = new Recommendation(DateTime.now().minusMinutes(1), Duration.millis(1000), action);
        result.add(recommendation);
        return result;
    }

    @Override
    public void updateRecommendation(List<Recommendation> recommendations) {

    }
}
