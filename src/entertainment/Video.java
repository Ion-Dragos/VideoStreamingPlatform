package entertainment;

import java.util.ArrayList;

public class Video {

    private String title;

    private int year;

    private ArrayList<String> cast;

    private ArrayList<String> genres;

    private boolean favorite = false;

    private int views = 0;

    private double ratingVideo;

    private int noOfAppearancesInFavorite;

    private int duration;

    public Video() {

    }

    public Video(final String title, final int year,
                 final ArrayList<String> cast, final ArrayList<String> genres,
                 final boolean favorite, final int views, final int duration) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.favorite = favorite;
        this.views = views;
        this.duration = duration;
    }

    /**
     *
     * @param title - setter titlu
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     *
     * @return - getter titlu
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return - getter year
     */
    public int getYear() {
        return year;
    }

    /**
     *
     * @return - getter pentru actori
     */
    public ArrayList<String> getCast() {
        return cast;
    }

    /**
     *
     * @return - getter pentru categoriile de filme
     */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /**
     *
     * @return - getter daca e la favorite sau nu
     */
    public boolean getFavorite() {
        return favorite;
    }

    /**
     *
     * @param favorite - seteaza video la favorite
     */
    public void setFavorite(final boolean favorite) {
        this.favorite = favorite;
    }

    /**
     *
     * @return - getter pentru numarul de view-uri
     */
    public int getViews() {
        return views;
    }

    /**
     *
     * @param views - seteaza numarul de view-uri
     */
    public void setViews(final int views) {
        this.views = views;
    }

    /**
     * incrementeaza numarul de view-uri
     */
    public void increaseViews() {
        views++;
    }

    /**
     *
     * @return - returneaza rating-ul la un video
     */
    public double getRatingVideo() {
        return ratingVideo;
    }

    public Double getRating() {
        return 0.0;
    }

    /**
     *
     * @param ratingVideo - seteaza rating la un video
     */
    public void setRatingVideo(final double ratingVideo) {
        this.ratingVideo = ratingVideo;
    }

    /**
     *
     * @return - numarul de aparitii la favorite
     */
    public int getNoOfAppearancesInFavorite() {
        return noOfAppearancesInFavorite;
    }

    /**
     *
     * @param noOfAppearancesInFavorite - seteaza de cate ori apare la favorite
     */
    public void setNoOfAppearancesInFavorite(final int noOfAppearancesInFavorite) {
        this.noOfAppearancesInFavorite = noOfAppearancesInFavorite;
    }

    /**
     * incrementeaza de cate ori apare la favorite
     */
    public void increaseFavorite() {
        noOfAppearancesInFavorite++;
    }

    /**
     *
     * @return - durata unui video
     */
    public int getDuration() {
        return duration;
    }

    /**
     *
     * @param duration - seteaza durata unui video
     */
    public void setDuration(final int duration) {
        this.duration = duration;
    }
}
