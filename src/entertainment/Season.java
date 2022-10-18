package entertainment;

import java.util.HashMap;
import java.util.Map;

/**
 * Information about a season of a tv show
 * <p>
 * DO NOT MODIFY
 */
public final class Season {
    /**
     * Number of current season
     */
    private final int currentSeason;
    /**
     * Duration in minutes of a season
     */
    private int duration;
    /**
     * List of ratings for each season
     */
    private Map<String, Double> ratings;

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new HashMap<>();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public Map<String, Double> getRatings() {
        return ratings;
    }

    public void setRatings(final Map<String, Double> ratings) {
        this.ratings = ratings;
    }


    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + '}';
    }

    /**
     *
     * @return - returnam rating-ul pentru un singur sezon
     */
    public Double getAverageRating() {
        Double totalRating = 0.0;

        for (Map.Entry<String, Double> entry : ratings.entrySet()) {
            totalRating += entry.getValue();
        }

        if (totalRating == 0.0) {
            return totalRating;
        } else {
            return totalRating / ratings.size();
        }
    }
}

