package user;

import entertainment.Movie;
import entertainment.Serial;

import java.util.ArrayList;
import java.util.List;

public final class User {

    private String username;
    private String subscriptionType;
    private List<Movie> movieList = new ArrayList<>();
    private List<Serial> serialList = new ArrayList<>();
    private int noOfRatings;

    public User() {

    }

    public User(final String username, final int noOfRatings) {
        this.username = username;
        this.noOfRatings = noOfRatings;
    }

    /**
     *
     * @return - getter username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username - setter username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     *
     * @return - getter tippul de subscriptie
     */
    public String getSubscriptionType() {
        return subscriptionType;
    }

    /**
     *
     * @param subscriptionType - setter tipul de subscriptie
     */
    public void setSubscriptionType(final String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    /**
     *
     * @return - getter lista de filme a user-ului
     */
    public List<Movie> getMovieList() {
        return movieList;
    }

    /**
     *
     * @param movieList - setter lista de filme a user-ului
     */
    public void setMovieList(final List<Movie> movieList) {
        this.movieList = movieList;
    }

    /**
     *
     * @return getter - lista de seriale a user-ului
     */
    public List<Serial> getSerialList() {
        return serialList;
    }

    /**
     *
     * @param serialList - setter lista de seriale a user-ului
     */
    public void setSerialList(final List<Serial> serialList) {
        this.serialList = serialList;
    }

    /**
     *
     * @return - numarul de rating pe care le-a dat user-ul
     */
    public int getNoOfRatings() {
        return noOfRatings;
    }

    /**
     *
     * @param noOfRatings - setter pentru numarul de rating-uri pe care le-a dat
     */
    public void setNoOfRatings(final int noOfRatings) {
        this.noOfRatings = noOfRatings;
    }

    /**
     * incrementeaza numarul de ratings
     */
    public void increaseNoOfRatings() {
        noOfRatings++;
    }
}
