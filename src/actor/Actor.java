package actor;

import entertainment.Movie;
import entertainment.Serial;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Actor {

    private final String name;

    private final String careerDescription;

    private final ArrayList<String> filmography;

    private final Map<ActorsAwards, Integer> awards;

    private double rating;

    private int noOfAppearances;

    private int noOfSpecifiedAwards = 0;

    private List<Movie> movieList = new ArrayList<>();
    private List<Serial> serialList = new ArrayList<>();

    public Actor(final String name, final String careerDescription,
                 final ArrayList<String> filmography, final Map<ActorsAwards,
            Integer> awards, final double rating, final int noOfAppearances) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
        this.rating = rating;
        this.noOfAppearances = noOfAppearances;
    }

    /**
     *
     * @return - numele
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return - detaliile despre cariera
     */
    public String getCareerDescription() {
        return careerDescription;
    }

    /**
     *
     * @return - filmele in care a jucat
     */
    public ArrayList<String> getFilmography() {
        return filmography;
    }

    /**
     *
     * @return - premiile
     */
    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    /**
     *
     * @return numarul total de premii pe care le are un actor
     */
    public int getAwardsNumber() {
        int count = 0;
        for (Map.Entry<ActorsAwards, Integer> award : awards.entrySet()) {
            count += award.getValue();
        }
        return count;
    }

    /**
     *
     * @return - rating
     */
    public double getRating() {
        return rating;
    }

    /**
     *
     * @return - numarul de apariti
     */
    public int getNoOfAppearances() {
        return noOfAppearances;
    }

    /**
     *
     * @param addRating - cu cat se aduna
     */
    public void setRating(final double addRating) {
        rating = addRating;
    }

    /**
     * calculeaza rating-ul final
     */
    public void finalRating() {
        rating = rating / noOfAppearances;
    }

    /**
     *
     * @return - lista cu filme
     */
    public List<Movie> getMovieList() {
        return movieList;
    }

    /**
     *
     * @param movieList - seteaza lista de filme
     */
    public void setMovieList(final List<Movie> movieList) {
        this.movieList = movieList;
    }

    /**
     *
     * @return - lista cu filme
     */
    public List<Serial> getSerialList() {
        return serialList;
    }

    /**
     *
     * @param serialList - seteaza lista de seriale
     */
    public void setSerialList(final List<Serial> serialList) {
        this.serialList = serialList;
    }

    /**
     *
     * @return - numarul de premii specifice
     */
    public int getNoOfSpecifiedAwards() {
        //return noOfSpecifiedAwards;
        return awards.values().stream().reduce(0, Integer::sum);
    }

    /**
     *
     * @param noOfSpecifiedAwards - seteaza numarul de premii specifice
     */
    public void setNoOfSpecifiedAwards(final int noOfSpecifiedAwards) {
        this.noOfSpecifiedAwards = noOfSpecifiedAwards;
    }
}
