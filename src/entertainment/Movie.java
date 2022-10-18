package entertainment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Movie extends Video {


    private final Map<String, Double> ratings;

    public Movie(final String title, final ArrayList<String> actors,
                 final ArrayList<String> genres, final int year,
                 final int duration, final boolean favorite, final int views) {
        super(title, year, actors, genres, favorite, views, duration);
        ratings = new HashMap<>();
    }

    public Movie(final Movie movie) {
        super(movie.getTitle(), movie.getYear(), movie.getCast(),
                movie.getGenres(), movie.getFavorite(),
                movie.getViews(), movie.getDuration());

        this.ratings = movie.getRatings();
    }

    /**
     *
     * @return - rating-ul final pentru un movie
     */
    public Double getRating() {
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

    /**
     *
     * @return - durarata unui film
     */
    public int getDuration() {
        return super.getDuration();
    }

    /**
     *
     * @return - returneaza nota unui film
     */
    public Map<String, Double> getRatings() {
        return ratings;
    }
}
