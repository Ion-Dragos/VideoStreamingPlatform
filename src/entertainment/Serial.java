package entertainment;

import java.util.ArrayList;

public class Serial extends Video {

    private final int numberOfSeasons;
    private final ArrayList<Season> seasons;

    public Serial(final String title, final int year,
                  final ArrayList<String> genres, final ArrayList<String> cast,
                  final int numberOfSeasons, final ArrayList<Season> seasons,
                  final boolean favorite, final int views, final int duration) {
        super(title, year, cast, genres, favorite, views, duration);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public Serial(final Serial serial) {
        super(serial.getTitle(), serial.getYear(), serial.getCast(),
                serial.getGenres(), serial.getFavorite(),
                serial.getViews(), serial.getDuration());
        this.numberOfSeasons = serial.getNumberOfSeasons();
        this.seasons = serial.getSeasons();
    }

    /**
     *
     * @return - rating la tot serialul
     */
    public Double getRating() {
        Double totalRating = 0.0;

        for (Season season : seasons) {
            if (season.getAverageRating() != 0.0) {
                totalRating += season.getAverageRating();
            }
        }

        return totalRating / numberOfSeasons;
    }

    /**
     *
     * @return - durata unui intreg serial
     */
    public int getDuration() {
        int duration = 0;
        for (Season season : seasons) {
            duration += season.getDuration();
        }
        return duration;
    }

    /**
     *
     * @return - numarul de sezoane
     */
    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    /**
     *
     * @return - getter pentru un anumit sezon
     */
    public ArrayList<Season> getSeasons() {
        return seasons;
    }
}
