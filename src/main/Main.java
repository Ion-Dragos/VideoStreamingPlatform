package main;

import actions.Command;
import actions.Query;
import actions.Recommandation;
import actor.Actor;
import checker.Checker;
import checker.Checkstyle;
import common.Constants;
import entertainment.Movie;
import entertainment.Season;
import entertainment.Serial;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import org.json.simple.JSONArray;
import user.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation

        List<User> users = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();
        List<Serial> serials = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();

        for (MovieInputData movieInputData : input.getMovies()) {
            Movie movie = new Movie(movieInputData.getTitle(),
                    movieInputData.getCast(),
                    movieInputData.getGenres(),
                    movieInputData.getYear(),
                    movieInputData.getDuration(), false, 0);

            movies.add(movie);
        }



        for (SerialInputData serialInputData : input.getSerials()) {
            int duration = 0;
            for (Season season : serialInputData.getSeasons()) {
                duration += season.getDuration();
            }
            Serial serial = new Serial(serialInputData.getTitle(),
                    serialInputData.getYear(),
                    serialInputData.getGenres(),
                    serialInputData.getCast(),
                    serialInputData.getNumberSeason(),
                    serialInputData.getSeasons(), false, 0, duration);

            serials.add(serial);
        }

        for (ActorInputData actorInputData : input.getActors()) {
            Actor actor = new Actor(actorInputData.getName(),
                    actorInputData.getCareerDescription(),
                    actorInputData.getFilmography(),
                    actorInputData.getAwards(), 0.0, 0);
            for (Movie movie : movies) {
                if (actor.getFilmography().contains(movie.getTitle())) {
                    actor.getMovieList().add(movie);
                }
            }

            for (Serial serial : serials) {
                if (actor.getFilmography().contains(serial.getTitle())) {
                    actor.getSerialList().add(serial);
                }
            }

            actors.add(actor);
        }

        for (UserInputData userInputData : input.getUsers()) {
            User user = new User();
            user.setUsername(userInputData.getUsername());
            user.setSubscriptionType(userInputData.getSubscriptionType());
            for (Map.Entry<String, Integer> entry : userInputData.getHistory().entrySet()) {
                for (Movie movie : movies) {
                    if (entry.getKey().equals(movie.getTitle())) {
                        movie.setViews(entry.getValue());
                        movie.setFavorite(false);
                        if (userInputData.getFavoriteMovies().contains(movie.getTitle())) {
                            movie.setFavorite(true);
                            movie.increaseFavorite();
                        }
                        user.getMovieList().add(new Movie(movie));
                    }
                }
                for (Serial serial : serials) {
                    if (entry.getKey().equals(serial.getTitle())) {
                        serial.setViews(entry.getValue());
                        serial.setFavorite(false);
                        if (userInputData.getFavoriteMovies().contains(serial.getTitle())) {
                            serial.setFavorite(true);
                            serial.increaseFavorite();
                        }
                        user.getSerialList().add(new Serial(serial));
                    }
                }
            }

            users.add(user);
        }

        for (ActionInputData action : input.getCommands()) {
            if (action.getActionType().equals("command")) {
                if (action.getType().equals("favorite")) {
                    Command.favourite(users, arrayResult, action, fileWriter, movies, serials);
                }
                if (action.getType().equals("view")) {
                    Command.view(users, arrayResult, action, fileWriter, movies, serials);
                }
                if (action.getType().equals("rating")) {
                    Command.rating(users, arrayResult, action, fileWriter);
                }
            }

            if (action.getActionType().equals("query")) {
                if (action.getObjectType().equals("actors")) {
                    if (action.getCriteria().equals("average")) {
                        Query.actorAverage(actors, arrayResult, action, fileWriter);
                    }
                    if (action.getCriteria().equals("awards")) {
                        Query.actorAward(actors, arrayResult, action, fileWriter);
                    }

                    if (action.getCriteria().equals("filter_description")) {
                        Query.actorFilterDescription(actors, arrayResult, action, fileWriter);
                    }
                }

                if (action.getObjectType().equals("movies")) {
                    if (action.getCriteria().equals("ratings")) {
                        Query.videoRatingMovie(movies, arrayResult, action, fileWriter);
                    }

                    if (action.getCriteria().equals("favorite")) {
                        Query.videoFavoriteMovie(movies, arrayResult, action, fileWriter);
                    }
                    if (action.getCriteria().equals("longest")) {
                        Query.videoLongestMovie(movies, arrayResult, action, fileWriter);
                    }
                    if (action.getCriteria().equals("most_viewed")) {
                        Query.videosMostViewedMovie(users, arrayResult, action, fileWriter);
                    }
                }

                if (action.getObjectType().equals("shows")) {
                    if (action.getCriteria().equals("ratings")) {
                        Query.videoRatingShow(serials, arrayResult, action, fileWriter);
                    }
                    if (action.getCriteria().equals("favorite")) {
                        Query.videoFavoriteShow(serials, arrayResult, action, fileWriter);
                    }

                    if (action.getCriteria().equals("longest")) {
                        Query.videoLongestShow(serials, arrayResult, action, fileWriter);
                    }

                    if (action.getCriteria().equals("most_viewed")) {
                        Query.videosMostViewedShow(users, arrayResult, action, fileWriter);
                    }
                }

                if (action.getObjectType().equals("users")) {
                    if (action.getCriteria().equals("num_ratings")) {
                        Query.usersRating(users, arrayResult, action, fileWriter);
                    }
                }
            }

            if (action.getActionType().equals("recommendation")) {
                if (action.getType().equals("standard")) {
                    Recommandation.recommandationStandard(users, movies, serials,
                            arrayResult, action, fileWriter);
                }
                if (action.getType().equals("best_unseen")) {
                    Recommandation.recommandationBestUnseen(users, movies, serials,
                            arrayResult, action, fileWriter);
                }

                if (action.getType().equals("popular")) {
                    Recommandation.recommandationPopular(users, movies, serials,
                            arrayResult, action, fileWriter);
                }

                if (action.getType().equals("favorite")) {
                    Recommandation.recommandationFavorite(users, movies, serials,
                            arrayResult, action, fileWriter);
                }

                if (action.getType().equals("search")) {
                    Recommandation.recommandationSearch(users, movies, serials,
                            arrayResult, action, fileWriter);
                }
            }
        }

        fileWriter.closeJSON(arrayResult);
    }
}
