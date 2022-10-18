package actions;

import entertainment.Movie;
import entertainment.PopularGenres;
import entertainment.Serial;
import entertainment.Video;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Recommandation {

    private Recommandation() {
    }

    /**
     *
     * @param users - lista cu useri
     * @param movies - lista cu movies
     * @param serials - lista cu seriale
     * @param result - array-ul in care punem rezultatul
     * @param action - actiunea
     * @param fileWriter - fisierul in care punem rezultatul
     * @throws IOException - exceptia
     */
    @SuppressWarnings("unchecked")
    public static void recommandationStandard(final List<User> users,
                                              final List<Movie> movies,
                                              final List<Serial> serials,
                                              final JSONArray result,
                                              final ActionInputData action,
                                              final Writer fileWriter) throws IOException {

        for (User user : users) {
            if (user.getUsername().equals(action.getUsername())) {
                for (Movie movie : movies) {
                    boolean found = false;
                    for (Movie movie1 : user.getMovieList()) {
                        if (movie.getTitle().equals(movie1.getTitle())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        String message = "StandardRecommendation result: " + movie.getTitle();
                        result.add(fileWriter.writeFile(action.getActionId(), "", message));
                        return;
                    }
                }

                for (Serial serial : serials) {
                    boolean found = false;
                    for (Serial serial1 : user.getSerialList()) {
                        if (serial.getTitle().equals(serial1.getTitle())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        String message = "StandardRecommendation result: " + serial.getTitle();
                        result.add(fileWriter.writeFile(action.getActionId(), "", message));
                        return;
                    }
                }

                String message = "StandardRecommendation cannot be applied!";
                result.add(fileWriter.writeFile(action.getActionId(), "", message));
                return;
            }
        }
    }

    /**
     *
     * @param users - lista cu useri
     * @param movies - lista cu movies
     * @param serials - lista cu seriale
     * @param result - array-ul in care punem rezultatul
     * @param action - actiunea
     * @param fileWriter - fisierul in care punem rezultatul
     * @throws IOException - exceptia
     */
    @SuppressWarnings("unchecked")
    public static void recommandationBestUnseen(final List<User> users,
                                                final List<Movie> movies,
                                                final List<Serial> serials,
                                                final JSONArray result,
                                                final ActionInputData action,
                                                final Writer fileWriter) throws IOException {

        List<Video> videos = new ArrayList<>();
        for (Movie movie : movies) {
            videos.add(movie);
        }

        for (Serial serial : serials) {
            videos.add(serial);
        }

        User newUser = null;
        for (User user : users) {
            if (user.getUsername().equals(action.getUsername())) {
                newUser = user;
            }
        }

        for (Movie movie : newUser.getMovieList()) {
            videos.removeIf(video -> video.getTitle().equals(movie.getTitle()));
        }

        for (Serial serial : newUser.getSerialList()) {
            videos.removeIf(video -> video.getTitle().equals(serial.getTitle()));
        }


        //videos.sort(Comparator.comparing(Video::getTitle));
        videos.sort((video1, video2) -> -Double.compare(video1.getRating(), video2.getRating()));

        //List<Video> videos1 = new ArrayList<>();
        //videos.removeIf(v -> v.getRatingVideo() == 0.0);

        if (!videos.isEmpty()) {
            String message = "BestRatedUnseenRecommendation result: "
                    + videos.get(0).getTitle();
            result.add(fileWriter.writeFile(action.getActionId(), "", message));
        } else {
            String message = "BestRatedUnseenRecommendation cannot be applied!";
            result.add(fileWriter.writeFile(action.getActionId(), "", message));
        }


//        List<Video> videos = new ArrayList<>();
//        for (User user : users) {
//            for (Movie movie : user.getMovieList()) {
//                boolean isInVideos = false;
//                for (Video video : videos) {
//                    if (video.getTitle().equals(movie.getTitle())) {
//                        video.setRatingVideo(movie.getRating());
//                        isInVideos = true;
//                    }
//                }
//                if (!isInVideos && movie.getRating() > 0.0) {
//                    Video videoAux = new Video();
//                    videoAux.setTitle(movie.getTitle());
//                    videoAux.setRatingVideo(movie.getRating());
//                    videos.add(videoAux);
//                }
//            }
//
//            for (Serial serial : user.getSerialList()) {
//                boolean isInVideos = false;
//                for (Video video : videos) {
//                    if (video.getTitle().equals(serial.getTitle())) {
//                        video.setRatingVideo(serial.getRating());
//                        isInVideos = true;
//                    }
//                }
//                if (!isInVideos && serial.getRating() > 0.0) {
//                    Video videoAux = new Video();
//                    videoAux.setTitle(serial.getTitle());
//                    videoAux.setRatingVideo(serial.getRating());
//                    videos.add(videoAux);
//                }
//            }
//        }
//
//        videos.sort((video1, video2) -> {
//            if (video1.getRatingVideo() == video2.getRatingVideo()) {
//                return -video1.getTitle().compareTo(video2.getTitle());
//            } else {
//                return -Double.compare(video1.getRatingVideo(), video2.getRatingVideo());
//            }
//        });
//
//        for (Movie movie : movies) {
//            boolean isInVideos = false;
//            for (Video video : videos) {
//                if (movie.getTitle().equals(video.getTitle())) {
//                    isInVideos = true;
//                }
//            }
//            if (!isInVideos) {
//                Video videoAux = new Video();
//                videoAux.setTitle(movie.getTitle());
//                videoAux.setRatingVideo(movie.getRating());
//                videos.add(videoAux);
//            }
//        }
//
//        for (Serial serial : serials) {
//            boolean isInVideos = false;
//            for (Video video : videos) {
//                if (video.getTitle().equals(serial.getTitle())) {
//                    isInVideos = true;
//                }
//            }
//            if (!isInVideos) {
//                Video videoAux = new Video();
//                videoAux.setTitle(serial.getTitle());
//                videos.add(videoAux);
//            }
//        }
//
//        for (User user : users) {
//            if (user.getUsername().equals(action.getUsername())) {
//                for (Video video : videos) {
//                    boolean found = false;
//                    for (Movie movie : user.getMovieList()) {
//                        if (video.getTitle().equals(movie.getTitle())) {
//                            found = true;
//                            break;
//                        }
//                    }
//                    if (!found) {
//                        String message = "BestRatedUnseenRecommendation result: "
//                                + video.getTitle();
//                        result.add(fileWriter.writeFile(action.getActionId(), "", message));
//                        return;
//                    }
//                }
//                for (Video video : videos) {
//                    boolean found = false;
//                    for (Serial serial : user.getSerialList()) {
//                        if (video.getTitle().equals(serial.getTitle())) {
//                            found = true;
//                            break;
//                        }
//                    }
//                    if (!found) {
//                        String message = "BestRatedUnseenRecommendation result: "
//                                + video.getTitle();
//                        result.add(fileWriter.writeFile(action.getActionId(), "", message));
//                        return;
//                    }
//                }
//                String message = "BestRatedUnseenRecommendation cannot be applied!";
//                result.add(fileWriter.writeFile(action.getActionId(), "", message));
//                return;
//            }
//        }
    }

    /**
     *
     * @param users - lista de ueri
     * @param movies - lista de filme
     * @param serials - lista de seriale
     * @param result - array-ul in care punem rezulatul
     * @param action -actiunea
     * @param fileWriter - fiserul in care punem rezultatul
     * @throws IOException - exceptia
     */
    @SuppressWarnings("unchecked")
    public static void recommandationPopular(final List<User> users,
                                             final List<Movie> movies,
                                             final List<Serial> serials,
                                             final JSONArray result,
                                             final ActionInputData action,
                                             final Writer fileWriter) throws IOException {
        for (User user : users) {
            if (user.getUsername().equals(action.getUsername())
                    && user.getSubscriptionType().equals("BASIC")) {
                String message = "PopularRecommendation cannot be applied!";
                result.add(fileWriter.writeFile(action.getActionId(), "", message));
                return;
            }
        }
        List<Video> unseenVideos = new ArrayList<>();
        List<PopularGenres> bestGenres = new ArrayList<>();

        for (User user : users) {
            for (Movie movie : user.getMovieList()) {
                for (String genreMovie : movie.getGenres()) {
                    boolean found = false;
                    for (PopularGenres genrePopular : bestGenres) {
                        if (genrePopular.getGenre().equals(genreMovie)) {
                            genrePopular.incrementCount();
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        bestGenres.add(new PopularGenres(genreMovie, 1));
                    }
                }
            }

            for (Serial serial : user.getSerialList()) {
                for (String genreSerial : serial.getGenres()) {
                    boolean found = false;
                    for (PopularGenres genrePopular : bestGenres) {
                        if (genrePopular.getGenre().equals(genreSerial)) {
                            genrePopular.incrementCount();
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        bestGenres.add(new PopularGenres(genreSerial, 1));
                    }
                }
            }
        }

        bestGenres.sort((genre1, genre2) -> {
            if (genre1.getCount() == genre2.getCount()) {
                return genre1.getGenre().compareTo(genre2.getGenre());
            } else {
                return -Double.compare(genre1.getCount(), genre2.getCount());
            }
        });

        for (User user : users) {
            if (user.getUsername().equals(action.getUsername())
                    && user.getSubscriptionType().equals("PREMIUM")) {
                for (PopularGenres genre : bestGenres) {
                    for (Movie movie : movies) {
                        boolean found = false;
                        for (Movie movie1 : user.getMovieList()) {
                            if (movie.getTitle().equals(movie1.getTitle())) {
                                found = true;
                                break;
                            }
                        }

                        if (!found && movie.getGenres().contains(genre.getGenre())) {
                            String message = "PopularRecommendation result: " + movie.getTitle();
                            result.add(fileWriter.writeFile(action.getActionId(), "", message));
                            return;
                        }
                    }

                    for (Serial serial : serials) {
                        boolean found = false;
                        for (Serial serial1 : user.getSerialList()) {
                            if (serial.getTitle().equals(serial1.getTitle())) {
                                found = true;
                                break;
                            }
                        }

                        if (!found && serial.getGenres().contains(genre.getGenre())) {
                            String message = "PopularRecommendation result: " + serial.getTitle();
                            result.add(fileWriter.writeFile(action.getActionId(), "", message));
                            return;
                        }
                    }
                }

                String message = "PopularRecommendation cannot be applied!";
                result.add(fileWriter.writeFile(action.getActionId(), "", message));
                return;
            }
        }
    }

    /**
     *
     * @param users - lista de useri
     * @param movies - lista de filme
     * @param serials - lista de seriale
     * @param result - array-ul in care punem rezultatul
     * @param action - actiunea
     * @param fileWriter - fiserul in care punem rezultatul
     * @throws IOException - exceptia
     */
    @SuppressWarnings("unchecked")
    public static void recommandationFavorite(final List<User> users,
                                              final List<Movie> movies,
                                              final List<Serial> serials,
                                              final JSONArray result,
                                              final ActionInputData action,
                                              final Writer fileWriter) throws IOException {

        for (User user : users) {
            if (user.getUsername().equals(action.getUsername())
                    && user.getSubscriptionType().equals("BASIC")) {
                String message = "FavoriteRecommendation cannot be applied!";
                result.add(fileWriter.writeFile(action.getActionId(), "", message));
                return;
            }
        }

        List<Video> unseenVideos = new ArrayList<>();
        for (Movie movie : movies) {
            Video video = new Video();
            video.setTitle(movie.getTitle());
            video.setNoOfAppearancesInFavorite(movie.getNoOfAppearancesInFavorite());
            unseenVideos.add(video);

        }
        for (Serial serial : serials) {
            Video video = new Video();
            video.setTitle(serial.getTitle());
            video.setNoOfAppearancesInFavorite(serial.getNoOfAppearancesInFavorite());
            unseenVideos.add(video);
        }

        unseenVideos.sort((video1, video2) -> {
            if (video1.getNoOfAppearancesInFavorite() == video2.getNoOfAppearancesInFavorite()) {
                return video1.getTitle().compareTo(video2.getTitle());
            } else {
                return -Double.compare(video1.getNoOfAppearancesInFavorite(),
                        video2.getNoOfAppearancesInFavorite());
            }
        });


        for (User user : users) {
            if (user.getUsername().equals(action.getUsername())
                    && user.getSubscriptionType().equals("PREMIUM")) {
                for (Video video : unseenVideos) {
                    boolean found = false;
                    for (Movie movie : user.getMovieList()) {
                        if (video.getTitle().equals(movie.getTitle())) {
                            found = true;
                            break;
                        }
                    }

                    for (Serial serial : user.getSerialList()) {
                        if (video.getTitle().equals(serial.getTitle())) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        String message = "FavoriteRecommendation result: " + video.getTitle();
                        result.add(fileWriter.writeFile(action.getActionId(), "", message));
                        return;
                    }
                }

                for (Video video : unseenVideos) {
                    boolean found = false;
                    for (Movie movie : user.getMovieList()) {
                        if (video.getTitle().equals(movie.getTitle())) {
                            found = true;
                            break;
                        }
                    }

                    for (Serial serial : user.getSerialList()) {
                        if (video.getTitle().equals(serial.getTitle())) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        String message = "FavoriteRecommendation result: " + video.getTitle();
                        result.add(fileWriter.writeFile(action.getActionId(), "", message));
                        return;
                    }
                }

                String message = "FavoriteRecommendation cannot be applied!";
                result.add(fileWriter.writeFile(action.getActionId(), "", message));
                return;
            }
        }
    }

    /**
     *
     * @param users - lista de useri
     * @param movies - lista de filme
     * @param serials - lista de seriale
     * @param result - array-ul in care se pune rezultatul
     * @param action - actiunea
     * @param fileWriter - fisierul in care se pune rezultatul
     * @throws IOException -exceptia
     */
    @SuppressWarnings("unchecked")
    public static void recommandationSearch(final List<User> users,
                                            final List<Movie> movies,
                                            final List<Serial> serials,
                                            final JSONArray result,
                                            final ActionInputData action,
                                            final Writer fileWriter) throws IOException {

        for (User user : users) {
            if (user.getUsername().equals(action.getUsername())
                    && user.getSubscriptionType().equals("BASIC")) {
                String message = "SearchRecommendation cannot be applied!";
                result.add(fileWriter.writeFile(action.getActionId(), "", message));
                return;
            }
        }
        List<Video> unseenVideos = new ArrayList<>();
        for (User user : users) {
            if (user.getUsername().equals(action.getUsername())
                    && user.getSubscriptionType().equals("PREMIUM")) {
                for (Movie movie : movies) {
                    boolean found = false;
                    for (Movie movie1 : user.getMovieList()) {
                        if (movie.getTitle().equals(movie1.getTitle())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found && movie.getGenres().contains(action.getGenre())) {
                        Video video = new Video();
                        video.setRatingVideo(movie.getRating());
                        video.setTitle(movie.getTitle());
                        unseenVideos.add(video);
                    }
                }

                for (Serial serial : serials) {
                    boolean found = false;
                    for (Serial serial1 : user.getSerialList()) {
                        if (serial.getTitle().equals(serial1.getTitle())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found && serial.getGenres().contains(action.getGenre())) {
                        Video video = new Video();
                        video.setRatingVideo(serial.getRating());
                        video.setTitle(serial.getTitle());
                        unseenVideos.add(video);
                    }
                }
            }
        }

        unseenVideos.sort((video1, video2) -> {
            if (video1.getRatingVideo() == video2.getRatingVideo()) {
                return video1.getTitle().compareTo(video2.getTitle());
            } else {
                return Double.compare(video1.getRatingVideo(), video2.getRatingVideo());
            }
        });

        ArrayList<String> sortedVideo = new ArrayList<>();

        for (Video video : unseenVideos) {
            sortedVideo.add(video.getTitle());
        }

        String message;
        if (sortedVideo.isEmpty()) {
            message = "SearchRecommendation cannot be applied!";
        } else {
            message = "SearchRecommendation result: " + sortedVideo;
        }

        result.add(fileWriter.writeFile(action.getActionId(), "", message));
    }
}
