package actions;

import entertainment.Movie;
import entertainment.Serial;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import user.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class Command {

    private Command() {

    }

    /**
     *
     * @param users - list of users
     * @param result - array-ul in care se pune rezultatul
     * @param action - actiunea
     * @param fileWriter - fisierul in care se pune rezultatul
     * @param movies - lista de filme
     * @param serials - lista de seriale
     * @throws IOException - exceptia
     */
    @SuppressWarnings("unchecked")
    public static void favourite(final List<User> users,
                                 final JSONArray result,
                                 final ActionInputData action,
                                 final Writer fileWriter,
                                 final List<Movie> movies,
                                 final List<Serial> serials) throws IOException {

        for (User user : users) {
            if (user.getUsername().equals(action.getUsername())) {
                for (Movie movie : user.getMovieList()) {
                    if (movie.getTitle().equals(action.getTitle())) {
                        if (!movie.getFavorite()) {
                            movie.setFavorite(true);
                            for (Movie movie1 : movies) {
                                if (movie1.getTitle().equals(movie.getTitle())) {
                                    movie1.increaseFavorite();
                                    movie1.setFavorite(true);
                                    String message = "success -> "
                                            + movie1.getTitle()
                                            + " was added as favourite";
                                    result.add(fileWriter.
                                            writeFile(action.getActionId(), "", message));
                                    return;
                                }
                            }
                        } else {
                            String message = "error -> "
                                    + action.getTitle()
                                    + " is already in favourite list";
                            result.add(fileWriter.
                                    writeFile(action.getActionId(), "", message));
                            return;
                        }
                    }
                }

                for (Serial serial : user.getSerialList()) {
                    if (serial.getTitle().equals(action.getTitle())) {
                        if (!serial.getFavorite()) {
                            serial.setFavorite(true);
                            for (Serial serial1 : serials) {
                                if (serial1.getTitle().equals(serial.getTitle())) {
                                    serial1.increaseFavorite();
                                    serial1.setFavorite(true);
                                    String message = "success -> "
                                            + serial1.getTitle()
                                            + " was added as favourite";
                                    result.add(fileWriter.
                                            writeFile(action.getActionId(), "", message));
                                    return;
                                }
                            }

                        } else {
                            String message = "error -> "
                                    + action.getTitle()
                                    + " is already in favourite list";
                            result.add(fileWriter.writeFile(action.getActionId(), "", message));
                            return;
                        }
                    }
                }

                String message = "error -> " + action.getTitle() + " is not seen";
                result.add(fileWriter.writeFile(action.getActionId(), "", message));
                return;
            }
        }
    }

    /**
     *
     * @param users - lista cu useri
     * @param result - array-ul in care se pune rezultatul
     * @param action - actiunea
     * @param fileWriter - fisierul in care se pune rezultatul
     * @param movies - lista de filme
     * @param serials - lista de seriale
     * @throws IOException - excepetia
     */
    @SuppressWarnings("unchecked")
    public static void view(final List<User> users,
                            final JSONArray result,
                            final ActionInputData action,
                            final Writer fileWriter,
                            final List<Movie> movies,
                            final List<Serial> serials) throws IOException {

        for (User user : users) {
            if (user.getUsername().equals(action.getUsername())) {
                for (Movie movie : movies) {
                    if (movie.getTitle().equals(action.getTitle())) {
                        for (Movie movie1 : user.getMovieList()) {
                            if (movie1.getTitle().equals(movie.getTitle())) {
                                movie1.setViews(movie1.getViews() + 1);
                                movie.setViews(movie.getViews() + 1);
                                String message = "success -> "
                                        + action.getTitle() + " was viewed with total views of "
                                        + movie1.getViews();
                                result.add(fileWriter.writeFile(action.getActionId(), "", message));
                                return;
                            }
                        }
                        movie.setViews(movie.getViews() + 1);
                        Movie movie1 = new Movie(movie);
                        movie1.setViews(1);
                        user.getMovieList().add(movie1);
                        String message = "success -> "
                                + action.getTitle() + " was viewed with total views of "
                                + movie1.getViews();
                        result.add(fileWriter.writeFile(action.getActionId(), "", message));
                        return;
                    }
                }
                for (Serial serial : serials) {
                    if (serial.getTitle().equals(action.getTitle())) {
                        for (Serial serial1 : user.getSerialList()) {
                            if (serial1.getTitle().equals(serial.getTitle())) {
                                serial1.setViews(serial1.getViews() + 1);
                                serial.setViews(serial.getViews() + 1);
                                String message = "success -> "
                                        + action.getTitle() + " was viewed with total views of "
                                        + serial1.getViews();
                                result.add(fileWriter.writeFile(action.getActionId(), "", message));
                                return;
                            }
                        }
                        serial.setViews(serial.getViews() + 1);
                        Serial serial1 = new Serial(serial);
                        serial1.setViews(1);
                        user.getSerialList().add(serial1);
                        String message = "success -> "
                                + action.getTitle() + " was viewed with total views of "
                                + serial1.getViews();
                        result.add(fileWriter.writeFile(action.getActionId(), "", message));
                        return;
                    }
                }
            }
        }
    }

    /**
     *
     * @param users - lista de useri
     * @param result - array-ul in care se pune rezultatul
     * @param action - actiunea
     * @param fileWriter - fisierul in care se scrie rezultatul
     * @throws IOException - exceptia
     */
    @SuppressWarnings("unchecked")
    public static void rating(final List<User> users,
                              final JSONArray result,
                              final ActionInputData action,
                              final Writer fileWriter) throws IOException {

        for (User user : users) {
            if (user.getUsername().equals(action.getUsername())) {
                for (Movie movie : user.getMovieList()) {
                    if (movie.getTitle().equals(action.getTitle())) {
                        for (Map.Entry<String, Double> entry : movie.getRatings().entrySet()) {
                            if (entry.getKey().equals(user.getUsername())) {
                                String message = "error -> "
                                        + action.getTitle() + " has been already rated";
                                result.add(fileWriter.writeFile(action.getActionId(), "", message));
                                return;
                            }
                        }

                        user.increaseNoOfRatings();
                        movie.getRatings().put(user.getUsername(), action.getGrade());
                        String message = "success -> " + action.getTitle() + " was rated with "
                                + action.getGrade() + " by " + user.getUsername();
                        result.add(fileWriter.writeFile(action.getActionId(), "", message));
                        return;
                    }
                }

                for (Serial serial : user.getSerialList()) {
                    if (serial.getTitle().equals(action.getTitle())) {
                        for (Map.Entry<String, Double> entry : serial.getSeasons().
                                get(action.getSeasonNumber() - 1).getRatings().entrySet()) {
                            if (entry.getKey().equals(user.getUsername())) {
                                String message = "error -> "
                                        + action.getTitle() + " has been already rated";
                                result.add(fileWriter.writeFile(action.getActionId(), "", message));
                                return;
                            }
                        }

                        user.increaseNoOfRatings();
                        serial.getSeasons().get(action.getSeasonNumber() - 1).
                                getRatings().put(user.getUsername(), action.getGrade());
                        String message = "success -> " + action.getTitle() + " was rated with "
                                + action.getGrade() + " by " + user.getUsername();
                        result.add(fileWriter.writeFile(action.getActionId(), "", message));
                        return;
                    }
                }

                String message = "error -> " + action.getTitle() + " is not seen";
                result.add(fileWriter.writeFile(action.getActionId(), "", message));
                return;
            }
        }
    }
}
