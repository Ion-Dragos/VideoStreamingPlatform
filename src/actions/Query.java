package actions;

import actor.Actor;
import actor.ActorsAwards;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Video;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import user.User;

import java.io.IOException;
import java.util.*;
//import java.util.*;

public final class Query {

    private Query() {

    }

    /**
     *
     * @param actorsList - lista de actori
     * @param result - array-ul in care punem rezultatul
     * @param action - actiunea
     * @param fileWriter - fisierul in care punem rezultatul
     * @throws IOException - exceptia
     */
    @SuppressWarnings("unchecked")
    public static void actorAverage(final List<Actor> actorsList,
                                    final JSONArray result,
                                    final ActionInputData action,
                                    final Writer fileWriter
                                    ) throws IOException {

        List<Actor> actors = new ArrayList<>(actorsList);
        for (Actor actor : actors) {
            Double totalRating = 0.0;
            int noMovies = actor.getMovieList().size();
            int noSerials = actor.getSerialList().size();
            for (Movie movie : actor.getMovieList()) {
                if (movie.getRating() == 0.0) {
                    noMovies--;
                } else {
                    totalRating += movie.getRating();
                }
            }

            for (Serial serial : actor.getSerialList()) {
                if (serial.getRating() == 0.0) {
                    noSerials--;
                } else {
                    totalRating += serial.getRating();
                }
            }
            if ((noMovies + noSerials) == 0) {
                actor.setRating(0);
            } else {
                actor.setRating(totalRating / (noMovies + noSerials));
            }
        }

        actors.removeIf(v -> v.getRating() == 0);

        if (action.getSortType().equals("asc")) {
            actors.sort((actor1, actor2) -> {
                if (actor1.getRating() == actor2.getRating()) {
                    return actor1.getName().compareTo(actor2.getName());
                } else {
                    return Double.compare(actor1.getRating(), actor2.getRating());
                }
            });
        } else {
            actors.sort((actor1, actor2) -> {
                if (actor1.getRating() == actor2.getRating()) {
                    return -actor1.getName().compareTo(actor2.getName());
                } else {
                    return -Double.compare(actor1.getRating(), actor2.getRating());
                }
            });
        }

        ArrayList<String> actorSorted = new ArrayList<>();

        int count = 0;
        for (Actor actor : actors) {
            actorSorted.add(actor.getName());
            count++;
            if (!(count < action.getNumber())) {
                break;
            }
        }

        String message = "Query result: " + actorSorted;
        result.add(fileWriter.writeFile(action.getActionId(), "", message));
    }

    /**
     *
     * @param actors - lista de actori
     * @param result - array-ul in care punem rezultatul
     * @param action - actiunea
     * @param fileWriter - fisierul in care punem rezultatul
     * @throws IOException - exceptia
     */
    @SuppressWarnings("unchecked")
    public static void actorAward(final List<Actor> actors,
                                  final JSONArray result,
                                  final ActionInputData action,
                                  final Writer fileWriter) throws IOException {

        final int magicNumber = 3;
        List<Actor> actorListAwarded = new ArrayList<>();
        //int count = 0;
        for (Actor actor : actors) {
            int count = 0;
            for (Map.Entry<ActorsAwards, Integer> entry : actor.getAwards().entrySet()) {
                if (action.getFilters().get(magicNumber).contains(entry.getKey().toString())) {
                    count++;
                }
            }
            if (count == action.getFilters().get(magicNumber).size()) {
                actorListAwarded.add(actor);
                count = 0;
            }
        }

//        for (Actor actor : actorListAwarded) {
//            for (Map.Entry<ActorsAwards, Integer> entry : actor.getAwards().entrySet()) {
//                if (action.getFilters().get(magicNumber).contains(entry.getKey().toString())) {
//                    actor.setNoOfSpecifiedAwards(entry.getValue() + actor.getNoOfSpecifiedAwards());
//                }
//            }
//        }




        if (action.getSortType().equals("asc")) {
            // Sort ascending
            actorListAwarded.sort((actor1, actor2) -> {
                if (actor1.getNoOfSpecifiedAwards() == actor2.getNoOfSpecifiedAwards()) {
                    return actor1.getName().compareTo(actor2.getName());
                } else {
                    return Integer.compare(actor1.getNoOfSpecifiedAwards(),
                            actor2.getNoOfSpecifiedAwards());
                }
            });
        } else {
            // Sort descending
            actorListAwarded.sort((actor1, actor2) -> {
                if (actor1.getNoOfSpecifiedAwards() == actor2.getNoOfSpecifiedAwards()) {
                    return -actor1.getName().compareTo(actor2.getName());
                } else {
                    return -Integer.compare(actor1.getNoOfSpecifiedAwards(),
                            actor2.getNoOfSpecifiedAwards());
                }
            });
        }
        List<String> actorSorted = new ArrayList<>();

        for (Actor actor : actorListAwarded) {
            actorSorted.add(actor.getName());
        }

        String message = "Query result: " + actorSorted;
        result.add(fileWriter.writeFile(action.getActionId(), "", message));
    }

    /**
     *
     * @param actors - lista cu actori
     * @param result - array-ul in care punem rezultatul
     * @param action - actiunea
     * @param fileWriter - fisierul in care punem rezultatul
     * @throws IOException - exceptia
     */
    @SuppressWarnings("unchecked")
    public static void actorFilterDescription(final List<Actor> actors,
                                              final JSONArray result,
                                              final ActionInputData action,
                                              final  Writer fileWriter) throws IOException {

        ArrayList<String> actorsThatHasWords = new ArrayList<>();
        for (Actor actor : actors) {
            String[] words = actor.getCareerDescription().split("\\W+");
            int count = 0;
            for (String word : action.getFilters().get(2)) {
                boolean hasWord = false;
                for (String keyWords : words) {
                    if (keyWords.equalsIgnoreCase(word)) {
                        hasWord = true;
                        break;
                    }
                }
                if (hasWord) {
                    count++;
                }
            }
            if (count == action.getFilters().get(2).size()) {
                actorsThatHasWords.add(actor.getName());
            }
        }

        if (action.getSortType().equals("asc")) {
            actorsThatHasWords.sort(Comparator.comparing(String::toString));
        } else {
            actorsThatHasWords.sort(Comparator.comparing(String::toString).reversed());
        }

        String message = "Query result: " + actorsThatHasWords;
        result.add(fileWriter.writeFile(action.getActionId(), "", message));
    }

    /**
     *
     * @param movies - list de filme
     * @param result - array-ul in care punem rezultatul
     * @param action - actiunea
     * @param fileWriter - fisierul in care punem rezultatul
     * @throws IOException -exceptia
     */
    @SuppressWarnings("unchecked")
    public static void videoRatingMovie(final List<Movie> movies,
                                    final JSONArray result,
                                    final ActionInputData action,
                                    final Writer fileWriter) throws IOException {

        List<Video> videos = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getRating() != 0.0) {
                if (action.getFilters().get(0).get(0) == null && action.getFilters().get(1).get(0) == null) {
                    movie.setRatingVideo(movie.getRating());
                    Video video = new Video();
                    video.setTitle(movie.getTitle());
                    video.setRatingVideo(movie.getRatingVideo());
                    videos.add(video);
                } else if (action.getFilters().get(0).get(0) != null
                        && action.getFilters().get(1).get(0) == null) {
                    if (action.getFilters().get(0).contains(String.valueOf(movie.getYear()))) {
                        movie.setRatingVideo(movie.getRating());
                        Video video = new Video();
                        video.setTitle(movie.getTitle());
                        video.setRatingVideo(movie.getRatingVideo());
                        videos.add(video);
                    }
                } else if (action.getFilters().get(0).get(0) == null
                        && action.getFilters().get(1).get(0) != null) {
                    if (movie.getGenres().contains(action.getFilters().get(1))) {
                        movie.setRatingVideo(movie.getRating());
                        Video video = new Video();
                        video.setTitle(movie.getTitle());
                        video.setRatingVideo(movie.getRatingVideo());
                        videos.add(video);
                    }
                } else {
                    if (action.getFilters().get(0).contains(String.valueOf(movie.getYear()))
                            && movie.getGenres().contains(action.getFilters().get(1))) {
                        movie.setRatingVideo(movie.getRating());
                        Video video = new Video();
                        video.setTitle(movie.getTitle());
                        video.setRatingVideo(movie.getRatingVideo());
                        videos.add(video);
                    }
                }
            }
        }


        if (action.getSortType().equals("asc")) {
            videos.sort((video1, video2) -> {
                if (video1.getRatingVideo() == video2.getRatingVideo()) {
                    return video1.getTitle().compareTo(video2.getTitle());
                } else {
                    return Double.compare(video1.getRatingVideo(), video2.getRatingVideo());
                }
            });
        } else {
            videos.sort((video1, video2) -> {
                if (video1.getRatingVideo() == video2.getRatingVideo()) {
                    return -video1.getTitle().compareTo(video2.getTitle());
                } else {
                    return -Double.compare(video1.getRatingVideo(), video2.getRatingVideo());
                }
            });
        }
        List<String> sortedVideosbyRating = new ArrayList<>();

        for (Video video : videos) {
            sortedVideosbyRating.add(video.getTitle());
        }
        String message = "Query result: " + sortedVideosbyRating;
        result.add(fileWriter.writeFile(action.getActionId(), "", message));
    }

    /**
     *
     * @param serials - lista de seriale
     * @param result - array-ul in care punem rezultatul
     * @param action - actiunea
     * @param fileWriter - fisierul in care punem rezultatul
     * @throws IOException - exceptia
     */
    @SuppressWarnings("unchecked")
    public static void videoRatingShow(
                                    final List<Serial> serials,
                                    final JSONArray result,
                                    final ActionInputData action,
                                    final Writer fileWriter) throws IOException {

        List<Video> videos = new ArrayList<>();
        for (Serial serial : serials) {
            if (serial.getRating() != 0.0) {
                if (action.getFilters().get(0).get(0) == null && action.getFilters().get(1).get(0) == null) {
                    serial.setRatingVideo(serial.getRating());
                    Video video = new Video();
                    video.setTitle(serial.getTitle());
                    video.setRatingVideo(serial.getRatingVideo());
                    videos.add(video);
                } else if (action.getFilters().get(0).get(0) != null
                        && action.getFilters().get(1).get(0) == null) {
                    if (action.getFilters().get(0).contains(String.valueOf(serial.getYear()))) {
                        serial.setRatingVideo(serial.getRating());
                        Video video = new Video();
                        video.setTitle(serial.getTitle());
                        video.setRatingVideo(serial.getRatingVideo());
                        videos.add(video);
                    }
                } else if (action.getFilters().get(0).get(0) == null
                        && action.getFilters().get(1).get(0) != null) {
                    if (serial.getGenres().contains(action.getFilters().get(1).get(0))) {
                        serial.setRatingVideo(serial.getRating());
                        Video video = new Video();
                        video.setTitle(serial.getTitle());
                        video.setRatingVideo(serial.getRatingVideo());
                        videos.add(video);
                    }
                } else {
                    if (action.getFilters().get(0).contains(String.valueOf(serial.getYear()))
                            && serial.getGenres().contains(action.getFilters().get(1).get(0))) {
                        serial.setRatingVideo(serial.getRating());
                        Video video = new Video();
                        video.setTitle(serial.getTitle());
                        video.setRatingVideo(serial.getRatingVideo());
                        videos.add(video);
                    }
                }
            }
        }


        if (action.getSortType().equals("asc")) {
            videos.sort((video1, video2) -> {
                if (video1.getRatingVideo() == video2.getRatingVideo()) {
                    return video1.getTitle().compareTo(video2.getTitle());
                } else {
                    return Double.compare(video1.getRatingVideo(), video2.getRatingVideo());
                }
            });
        } else {
            videos.sort((video1, video2) -> {
                if (video1.getRatingVideo() == video2.getRatingVideo()) {
                    return -video1.getTitle().compareTo(video2.getTitle());
                } else {
                    return -Double.compare(video1.getRatingVideo(), video2.getRatingVideo());
                }
            });
        }
        List<String> sortedVideosbyRating = new ArrayList<>();

        for (Video video : videos) {
            sortedVideosbyRating.add(video.getTitle());
        }
        String message = "Query result: " + sortedVideosbyRating;
        result.add(fileWriter.writeFile(action.getActionId(), "", message));
    }

    /**
     *
     * @param movies - lista cu filme
     * @param result - array-ul in care punem rezultatul
     * @param action - actiunea
     * @param fileWriter - fisierul in care punem rezultatul
     * @throws IOException - exceptia
     */
    @SuppressWarnings("unchecked")
    public static void videoFavoriteMovie(final List<Movie> movies,
                                     final JSONArray result,
                                     final ActionInputData action,
                                     final Writer fileWriter) throws IOException {

        List<Video> videos = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getNoOfAppearancesInFavorite() != 0) {
                if (action.getFilters().get(0).get(0) == null && action.getFilters().get(1).get(0) == null) {
                    Video video = new Video();
                    video.setTitle(movie.getTitle());
                    video.setNoOfAppearancesInFavorite(movie.getNoOfAppearancesInFavorite());
                    videos.add(video);
                } else if (action.getFilters().get(0).get(0) != null
                        && action.getFilters().get(1).get(0) == null) {
                    if (action.getFilters().get(0).contains(String.valueOf(movie.getYear()))) {
                        Video video = new Video();
                        video.setTitle(movie.getTitle());
                        video.setNoOfAppearancesInFavorite(movie.getNoOfAppearancesInFavorite());
                        videos.add(video);
                    }
                } else if (action.getFilters().get(0).get(0) == null
                        && action.getFilters().get(1).get(0) != null) {
                    if (movie.getGenres().contains(action.getFilters().get(1).get(0))) {
                        Video video = new Video();
                        video.setTitle(movie.getTitle());
                        video.setNoOfAppearancesInFavorite(movie.getNoOfAppearancesInFavorite());
                        videos.add(video);
                    }
                } else {
                    if (action.getFilters().get(0).contains(String.valueOf(movie.getYear()))
                            && movie.getGenres().contains(action.getFilters().get(1).get(0))) {
                        Video video = new Video();
                        video.setTitle(movie.getTitle());
                        video.setNoOfAppearancesInFavorite(movie.getNoOfAppearancesInFavorite());
                        videos.add(video);
                    }
                }
            }
        }
        if (action.getSortType().equals("asc")) {
            videos.sort((video1, video2) -> {
                if (video1.getNoOfAppearancesInFavorite()
                        == video2.getNoOfAppearancesInFavorite()) {
                    return video1.getTitle().compareTo(video2.getTitle());
                } else {
                    return Double.compare(video1.getNoOfAppearancesInFavorite(),
                            video2.getNoOfAppearancesInFavorite());
                }
            });
        } else {
            videos.sort((video1, video2) -> {
                if (video1.getNoOfAppearancesInFavorite()
                        == video2.getNoOfAppearancesInFavorite()) {
                    return -video1.getTitle().compareTo(video2.getTitle());
                } else {
                    return -Double.compare(video1.getNoOfAppearancesInFavorite(),
                            video2.getNoOfAppearancesInFavorite());
                }
            });
        }

        ArrayList<String> sortedVideoByFavorite = new ArrayList<>();

        int count = 0;
        for (Video video : videos) {
            sortedVideoByFavorite.add(video.getTitle());
            count++;
            if (!(count < action.getNumber())) {
                break;
            }
        }

        String message = "Query result: " + sortedVideoByFavorite;
        result.add(fileWriter.writeFile(action.getActionId(), "", message));
    }

    /**
     *
     * @param serials - lista cu seriale
     * @param result - array-ul in care punem rezultatele
     * @param action - actiunea
     * @param fileWriter - fisierul in care punem rezultatul
     * @throws IOException -exceptia
     */
    @SuppressWarnings("unchecked")
    public static void videoFavoriteShow(final List<Serial> serials,
                                         final JSONArray result,
                                         final ActionInputData action,
                                         final Writer fileWriter) throws IOException {

        List<Video> videos = new ArrayList<>();

        for (Serial serial : serials) {
            if (serial.getNoOfAppearancesInFavorite() != 0) {
                if (action.getFilters().get(0).get(0) == null && action.getFilters().get(1).get(0) == null) {
                    Video video = new Video();
                    video.setTitle(serial.getTitle());
                    video.setNoOfAppearancesInFavorite(serial.getNoOfAppearancesInFavorite());
                    videos.add(video);
                } else if (action.getFilters().get(0).get(0) != null
                        && action.getFilters().get(1).get(0) == null) {
                    if (action.getFilters().get(0).contains(String.valueOf(serial.getYear()))) {
                        Video video = new Video();
                        video.setTitle(serial.getTitle());
                        video.setNoOfAppearancesInFavorite(serial.getNoOfAppearancesInFavorite());
                        videos.add(video);
                    }
                } else if (action.getFilters().get(0).get(0) == null
                        && action.getFilters().get(1).get(0) != null) {
                    if (serial.getGenres().contains(action.getFilters().get(1).get(0))) {
                        Video video = new Video();
                        video.setTitle(serial.getTitle());
                        video.setNoOfAppearancesInFavorite(serial.getNoOfAppearancesInFavorite());
                        videos.add(video);
                    }
                } else {
                    if (action.getFilters().get(0).contains(String.valueOf(serial.getYear()))
                            && serial.getGenres().contains(action.getFilters().get(1).get(0))) {
                        Video video = new Video();
                        video.setTitle(serial.getTitle());
                        video.setNoOfAppearancesInFavorite(serial.getNoOfAppearancesInFavorite());
                        videos.add(video);
                    }
                }
            }
        }

        if (action.getSortType().equals("asc")) {
            videos.sort((video1, video2) -> {
                if (video1.getNoOfAppearancesInFavorite()
                        == video2.getNoOfAppearancesInFavorite()) {
                    return video1.getTitle().compareTo(video2.getTitle());
                } else {
                    return Double.compare(video1.getNoOfAppearancesInFavorite(),
                            video2.getNoOfAppearancesInFavorite());
                }
            });
        } else {
            videos.sort((video1, video2) -> {
                if (video1.getNoOfAppearancesInFavorite()
                        == video2.getNoOfAppearancesInFavorite()) {
                    return -video1.getTitle().compareTo(video2.getTitle());
                } else {
                    return -Double.compare(video1.getNoOfAppearancesInFavorite(),
                            video2.getNoOfAppearancesInFavorite());
                }
            });
        }

        ArrayList<String> sortedVideoByFavorite = new ArrayList<>();

        int count = 0;
        for (Video video : videos) {
            sortedVideoByFavorite.add(video.getTitle());
            count++;
            if (!(count < action.getNumber())) {
                break;
            }
        }

        String message = "Query result: " + sortedVideoByFavorite;
        result.add(fileWriter.writeFile(action.getActionId(), "", message));
    }

    /**
     *
     * @param movies - lista de filme
     * @param result - array-ul in care punem rezultatul
     * @param action - actiunea
     * @param fileWriter - fisierul in care punem rezultatul
     * @throws IOException - exceptia
     */
    @SuppressWarnings("unchecked")
    public static void videoLongestMovie(final List<Movie> movies,
                                     final JSONArray result,
                                     final ActionInputData action,
                                     final Writer fileWriter) throws IOException {

        List<Video> videos = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getDuration() != 0) {
                if (action.getFilters().get(0).get(0) == null && action.getFilters().get(1).get(0) == null) {
                    Video video = new Video();
                    video.setTitle(movie.getTitle());
                    video.setDuration(movie.getDuration());
                    videos.add(video);
                } else if (action.getFilters().get(0).get(0) != null
                        && action.getFilters().get(1).get(0) == null) {
                    if (action.getFilters().get(0).contains(String.valueOf(movie.getYear()))) {
                        Video video = new Video();
                        video.setTitle(movie.getTitle());
                        video.setDuration(movie.getDuration());
                        videos.add(video);
                    }
                } else if (action.getFilters().get(0).get(0) == null
                        && action.getFilters().get(1).get(0) != null) {
                    if (movie.getGenres().contains(action.getFilters().get(1).get(0))) {
                        Video video = new Video();
                        video.setTitle(movie.getTitle());
                        video.setDuration(movie.getDuration());
                        videos.add(video);
                    }
                } else {
                    if (action.getFilters().get(0).contains(String.valueOf(movie.getYear()))
                            && movie.getGenres().contains(action.getFilters().get(1).get(0))) {
                        Video video = new Video();
                        video.setTitle(movie.getTitle());
                        video.setDuration(movie.getDuration());
                        videos.add(video);
                    }
                }
            }
        }
        if (action.getSortType().equals("asc")) {
            videos.sort((video1, video2) -> {
                if (video1.getDuration() == video2.getDuration()) {
                    return video1.getTitle().compareTo(video2.getTitle());
                } else {
                    return Double.compare(video1.getDuration(), video2.getDuration());
                }
            });
        } else {
            videos.sort((video1, video2) -> {
                if (video1.getNoOfAppearancesInFavorite()
                        == video2.getNoOfAppearancesInFavorite()) {
                    return -video1.getTitle().compareTo(video2.getTitle());
                } else {
                    return -Double.compare(video1.getDuration(), video2.getDuration());
                }
            });
        }

        ArrayList<String> sortedVideoByDuration = new ArrayList<>();

        int count = 0;
        for (Video video : videos) {
            sortedVideoByDuration.add(video.getTitle());
            count++;
            if (!(count < action.getNumber())) {
                break;
            }
        }

        String message = "Query result: " + sortedVideoByDuration;
        result.add(fileWriter.writeFile(action.getActionId(), "", message));
    }

    /**
     *
     * @param serials - lista de seriale
     * @param result - array-ul unde punem rezultatul
     * @param action - actiunea
     * @param fileWriter - fisierul in care punem rezultatul
     * @throws IOException - excpetia
     */
    @SuppressWarnings("unchecked")
    public static void videoLongestShow(final List<Serial> serials,
                                        final JSONArray result,
                                        final ActionInputData action,
                                        final Writer fileWriter) throws IOException {

        List<Video> videos = new ArrayList<>();

        for (Serial serial : serials) {
            if (serial.getDuration() != 0) {
                if (action.getFilters().get(0).get(0) == null && action.getFilters().get(1).get(0) == null) {
                    Video video = new Video();
                    video.setTitle(serial.getTitle());
                    video.setDuration(serial.getDuration());
                    videos.add(video);
                } else if (action.getFilters().get(0).get(0) != null
                        && action.getFilters().get(1).get(0) == null) {
                    if (action.getFilters().get(0).contains(String.valueOf(serial.getYear()))) {
                        Video video = new Video();
                        video.setTitle(serial.getTitle());
                        video.setDuration(serial.getDuration());
                        videos.add(video);
                    }
                } else if (action.getFilters().get(0).get(0) == null
                        && action.getFilters().get(1).get(0) != null) {
                    if (serial.getGenres().contains(action.getFilters().get(1).get(0))) {
                        Video video = new Video();
                        video.setTitle(serial.getTitle());
                        video.setDuration(serial.getDuration());
                        videos.add(video);
                    }
                } else {
                    if (action.getFilters().get(0).contains(String.valueOf(serial.getYear()))
                            && serial.getGenres().contains(action.getFilters().get(1).get(0))) {
                        Video video = new Video();
                        video.setTitle(serial.getTitle());
                        video.setDuration(serial.getDuration());
                        videos.add(video);
                    }
                }
            }
        }

        if (action.getSortType().equals("asc")) {
            videos.sort((video1, video2) -> {
                if (video1.getDuration() == video2.getDuration()) {
                    return video1.getTitle().compareTo(video2.getTitle());
                } else {
                    return Double.compare(video1.getDuration(), video2.getDuration());
                }
            });
        } else {
            videos.sort((video1, video2) -> {
                if (video1.getNoOfAppearancesInFavorite()
                        == video2.getNoOfAppearancesInFavorite()) {
                    return -video1.getTitle().compareTo(video2.getTitle());
                } else {
                    return -Double.compare(video1.getDuration(), video2.getDuration());
                }
            });
        }

        ArrayList<String> sortedVideoByDuration = new ArrayList<>();

        int count = 0;
        for (Video video : videos) {
            sortedVideoByDuration.add(video.getTitle());
            count++;
            if (!(count < action.getNumber())) {
                break;
            }
        }

        String message = "Query result: " + sortedVideoByDuration;
        result.add(fileWriter.writeFile(action.getActionId(), "", message));
    }

    /**
     *
     * @param users - lista de useri
     * @param result - array-ul unde punem rezultatul
     * @param action - actiunea
     * @param fileWriter - fisierul unde punem rezultatul
     * @throws IOException -exceptia
     */
    @SuppressWarnings("unchecked")
    public static void videosMostViewedMovie(final List<User> users,
                                        final JSONArray result,
                                        final ActionInputData action,
                                        final Writer fileWriter) throws IOException {

        List<Video> videos = new ArrayList<>();
        for (User user : users) {
            for (Movie movie : user.getMovieList()) {
                if (movie.getViews() != 0) {
                    if (action.getFilters().get(0).get(0) == null && action.getFilters().get(1).get(0) == null) {
                        boolean isInVideos = false;
                        for (Video video : videos) {
                            if (video.getTitle().equals(movie.getTitle())) {
                                video.setViews(movie.getViews() + video.getViews());
                                isInVideos = true;
                            }
                        }
                        if (!isInVideos) {
                            Video video = new Video();
                            video.setTitle(movie.getTitle());
                            video.setViews(movie.getViews());
                            videos.add(video);
                        }
                    } else if (action.getFilters().get(0).get(0) != null
                            && action.getFilters().get(1).get(0) == null) {
                        if (action.getFilters().get(0).contains(String.valueOf(movie.getYear()))) {
                            boolean isInVideos = false;
                            for (Video video : videos) {
                                if (video.getTitle().equals(movie.getTitle())) {
                                    video.setViews(movie.getViews() + video.getViews());
                                    isInVideos = true;
                                }
                            }
                            if (!isInVideos) {
                                Video video = new Video();
                                video.setTitle(movie.getTitle());
                                video.setViews(movie.getViews());
                                videos.add(video);
                            }
                        }
                    } else if (action.getFilters().get(0).get(0) == null
                            && action.getFilters().get(1).get(0) != null) {
                        if (movie.getGenres().contains(action.getFilters().get(1).get(0))) {
                            boolean isInVideos = false;
                            for (Video video : videos) {
                                if (video.getTitle().equals(movie.getTitle())) {
                                    video.setViews(movie.getViews() + video.getViews());
                                    isInVideos = true;
                                }
                            }
                            if (!isInVideos) {
                                Video video = new Video();
                                video.setTitle(movie.getTitle());
                                video.setViews(movie.getViews());
                                videos.add(video);
                            }
                        }
                    } else {
                        if (action.getFilters().get(0).contains(String.valueOf(movie.getYear()))
                                && movie.getGenres().contains(action.getFilters().get(1).get(0))) {
                            boolean isInVideos = false;
                            for (Video video : videos) {
                                if (video.getTitle().equals(movie.getTitle())) {
                                    video.setViews(movie.getViews() + video.getViews());
                                    isInVideos = true;
                                }
                            }
                            if (!isInVideos) {
                                Video video = new Video();
                                video.setTitle(movie.getTitle());
                                video.setViews(movie.getViews());
                                videos.add(video);
                            }
                        }
                    }
                }
            }
        }

        if (action.getSortType().equals("asc")) {
            videos.sort((video1, video2) -> {
                if (video1.getViews() == video2.getViews()) {
                    return video1.getTitle().compareTo(video2.getTitle());
                } else {
                    return Double.compare(video1.getViews(), video2.getViews());
                }
            });
        } else {
            videos.sort((video1, video2) -> {
                if (video1.getViews() == video2.getViews()) {
                    return -video1.getTitle().compareTo(video2.getTitle());
                } else {
                    return -Double.compare(video1.getViews(), video2.getViews());
                }
            });
        }

        ArrayList<String> sortedVideoByViews = new ArrayList<>();

        int count = 0;
        for (Video video : videos) {
            sortedVideoByViews.add(video.getTitle());
            count++;
            if (!(count < action.getNumber())) {
                break;
            }
        }

        String message = "Query result: " + sortedVideoByViews;
        result.add(fileWriter.writeFile(action.getActionId(), "", message));
    }

    /**
     *
     * @param users - lista de useri
     * @param result - array-ul in care punem rezultatul
     * @param action -actiunea
     * @param fileWriter - fisierul in care punem rezultatul
     * @throws IOException - exceptia
     */
    @SuppressWarnings("unchecked")
    public static void videosMostViewedShow(final List<User> users,
                                              final JSONArray result,
                                              final ActionInputData action,
                                              final Writer fileWriter) throws IOException {

        List<Video> videos = new ArrayList<>();
        for (User user : users) {
            for (Serial serial : user.getSerialList()) {
                if (serial.getViews() != 0) {
                    if (action.getFilters().get(0).get(0) == null && action.getFilters().get(1).get(0) == null) {
                        boolean isInVideos = false;
                        for (Video video : videos) {
                            if (video.getTitle().equals(serial.getTitle())) {
                                video.setViews(serial.getViews() + video.getViews());
                                isInVideos = true;
                            }
                        }
                        if (!isInVideos) {
                            Video video = new Video();
                            video.setTitle(serial.getTitle());
                            video.setViews(serial.getViews());
                            videos.add(video);
                        }
                    } else if (action.getFilters().get(0).get(0) != null
                            && action.getFilters().get(1).get(0) == null) {
                        if (action.getFilters().get(0).contains(String.valueOf(serial.getYear()))) {
                            boolean isInVideos = false;
                            for (Video video : videos) {
                                if (video.getTitle().equals(serial.getTitle())) {
                                    video.setViews(serial.getViews() + video.getViews());
                                    isInVideos = true;
                                }
                            }
                            if (!isInVideos) {
                                Video video = new Video();
                                video.setTitle(serial.getTitle());
                                video.setViews(serial.getViews());
                                videos.add(video);
                            }
                        }
                    } else if (action.getFilters().get(0).get(0) == null
                            && action.getFilters().get(1).get(0) != null) {
                        if (serial.getGenres().contains(action.getFilters().get(1).get(0))) {
                            boolean isInVideos = false;
                            for (Video video : videos) {
                                if (video.getTitle().equals(serial.getTitle())) {
                                    video.setViews(serial.getViews() + video.getViews());
                                    isInVideos = true;
                                }
                            }
                            if (!isInVideos) {
                                Video video = new Video();
                                video.setTitle(serial.getTitle());
                                video.setViews(serial.getViews());
                                videos.add(video);
                            }
                        }
                    } else {
                        if (action.getFilters().get(0).contains(String.valueOf(serial.getYear()))
                                && serial.getGenres().contains(action.getFilters().get(1).get(0))) {
                            boolean isInVideos = false;
                            for (Video video : videos) {
                                if (video.getTitle().equals(serial.getTitle())) {
                                    video.setViews(serial.getViews() + video.getViews());
                                    isInVideos = true;
                                }
                            }
                            if (!isInVideos) {
                                Video video = new Video();
                                video.setTitle(serial.getTitle());
                                video.setViews(serial.getViews());
                                videos.add(video);
                            }
                        }
                    }
                }
            }
        }

        if (action.getSortType().equals("asc")) {
            videos.sort((video1, video2) -> {
                if (video1.getViews() == video2.getViews()) {
                    return video1.getTitle().compareTo(video2.getTitle());
                } else {
                    return Double.compare(video1.getViews(), video2.getViews());
                }
            });
        } else {
            videos.sort((video1, video2) -> {
                if (video1.getViews() == video2.getViews()) {
                    return -video1.getTitle().compareTo(video2.getTitle());
                } else {
                    return -Double.compare(video1.getViews(), video2.getViews());
                }
            });
        }

        ArrayList<String> sortedVideoByViews = new ArrayList<>();

        int count = 0;
        for (Video video : videos) {
            sortedVideoByViews.add(video.getTitle());
            count++;
            if (!(count < action.getNumber())) {
                break;
            }
        }

        String message = "Query result: " + sortedVideoByViews;
        result.add(fileWriter.writeFile(action.getActionId(), "", message));
    }

    /**
     *
     * @param users - lista de useri
     * @param result - array-ul unde punem rezultatul
     * @param action - actiunea
     * @param fileWriter - fisierul unde punem rezultatul
     * @throws IOException - exceptia
     */
    @SuppressWarnings("unchecked")
    public static void usersRating(final List<User> users,
                                   final JSONArray result,
                                   final ActionInputData action,
                                   final Writer fileWriter) throws IOException {
        List<User> newUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getNoOfRatings() != 0) {
                newUsers.add(new User(user.getUsername(), user.getNoOfRatings()));
            }
        }

        if (action.getSortType().equals("asc")) {
            newUsers.sort((user1, user2) -> {
                if (user1.getNoOfRatings() == user2.getNoOfRatings()) {
                    return user1.getUsername().compareTo(user2.getUsername());
                } else {
                    return Double.compare(user1.getNoOfRatings(), user2.getNoOfRatings());
                }
            });
        } else {
            newUsers.sort((user1, user2) -> {
                if (user1.getNoOfRatings() == user2.getNoOfRatings()) {
                    return -user1.getUsername().compareTo(user2.getUsername());
                } else {
                    return -Double.compare(user1.getNoOfRatings(), user2.getNoOfRatings());
                }
            });
        }

        ArrayList<String> sortedUsersByNoRating = new ArrayList<>();

        int count = 0;
        for (User user : newUsers) {
            sortedUsersByNoRating.add(user.getUsername());
            count++;
            if (!(count < action.getNumber())) {
                break;
            }
        }

        String message = "Query result: " + sortedUsersByNoRating;
        result.add(fileWriter.writeFile(action.getActionId(), "", message));
    }
}
