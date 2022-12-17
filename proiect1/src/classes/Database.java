package classes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class Database {
    private static Database instance = null;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    //private ArrayList<UsersInput> users;
    private ArrayList<PersonalDatabase> user_accounts = new ArrayList<>();
    private ArrayList<MoviesInput> movies = new ArrayList<>();
    private Page current_page;
    private PersonalDatabase current_user;
    private ArrayList<MoviesInput> current_movie_list;
    private Database() {
        current_page = new HomepageNeautentificat();
        current_user = null;
        current_movie_list = new ArrayList<>();
    }

    public static Database getDatabase() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
    public void initialiseDatabase(Input input) {
        Database.getDatabase().setMovies(input.getMovies());
        ArrayList<PersonalDatabase> list = new ArrayList<>();
        for (CredentialsInput in : input.getUsers()) {
            UsersInput usersInput = in.getCredentials();
            PersonalDatabase user_database = new PersonalDatabase(usersInput);
            //System.out.println(user_database.getPersonal_movies());
            list.add(user_database);
        }
        Database.getDatabase().setUser_accounts(list);

        current_user = null;
        current_page = new HomepageNeautentificat();
        current_movie_list = new ArrayList<>();
    }
    public void printCurrentUser(ObjectNode objectNode, ObjectMapper mapper) {

        if (current_user == null) {
            objectNode.put("currentUser", (JsonNode) null);
        } else {
            ObjectNode currentUserNode = mapper.createObjectNode();
            ObjectNode credentials = mapper.createObjectNode();
            credentials.put("name", current_user.getUser().getName());
            credentials.put("password", current_user.getUser().getPassword());
            credentials.put("accountType", current_user.getUser().getAccountType());
            credentials.put("country", current_user.getUser().getCountry());
            credentials.put("balance", current_user.getUser().getBalance());
            currentUserNode.set("credentials", credentials);
            currentUserNode.put("tokensCount", current_user.getUser().getTokens());
            currentUserNode.put("numFreePremiumMovies", current_user.getUser().getNumFreeMovies());
            ArrayNode arrayNode1 = mapper.createArrayNode();
            ArrayNode arrayNode2 = mapper.createArrayNode();
            ArrayNode arrayNode3 = mapper.createArrayNode();
            ArrayNode arrayNode4 = mapper.createArrayNode();
            for (MovieDetails movieDetails: current_user.getPurchased_movies()) {
                ObjectNode film = mapper.createObjectNode();
                printMovieOutput(movieDetails.getMovie(), film, mapper);
                arrayNode1.add(film);
            }
            for (MovieDetails movieDetails: current_user.getWatched_movies()) {
                ObjectNode film = mapper.createObjectNode();
                printMovieOutput(movieDetails.getMovie(), film, mapper);
                arrayNode2.add(film);
            }
            for (MovieDetails movieDetails: current_user.getLiked_movies()) {
                ObjectNode film = mapper.createObjectNode();
                printMovieOutput(movieDetails.getMovie(), film, mapper);
                arrayNode3.add(film);
            }
            for (MovieDetails movieDetails: current_user.getRated_movies()) {
                ObjectNode film = mapper.createObjectNode();
                printMovieOutput(movieDetails.getMovie(), film, mapper);
                arrayNode4.add(film);
            }


//            for (MovieDetails movieDetails: current_user.getPersonal_movies()) {
//                if (movieDetails.getMovie_char().isOwned()) {
//                    ObjectNode film = mapper.createObjectNode();
//                    printMovieOutput(movieDetails.getMovie(), film, mapper);
//                    arrayNode1.add(film);
//                }
//                if (movieDetails.getMovie_char().isHasWatched()) {
//                    ObjectNode film = mapper.createObjectNode();
//                    printMovieOutput(movieDetails.getMovie(), film, mapper);
//                    arrayNode2.add(film);
//                }
//                if (movieDetails.getMovie_char().isLiked()) {
//                    ObjectNode film = mapper.createObjectNode();
//                    printMovieOutput(movieDetails.getMovie(), film, mapper);
//                    arrayNode3.add(film);
//                }
//                if (movieDetails.getMovie_char().getRating() != 0) {
//                    ObjectNode film = mapper.createObjectNode();
//                    printMovieOutput(movieDetails.getMovie(), film, mapper);
//                    arrayNode4.add(film);
//                }
//            }
            currentUserNode.set("purchasedMovies", arrayNode1);
            currentUserNode.set("watchedMovies", arrayNode2);
            currentUserNode.set("likedMovies", arrayNode3);
            currentUserNode.set("ratedMovies", arrayNode4);
            objectNode.set("currentUser", currentUserNode);
        }
    }

    public void printMovieOutput(MoviesInput film, ObjectNode film_out, ObjectMapper mapper) {
        if (film == null) return;
        film_out.put("name", film.getName());
        film_out.put("year", film.getYear());
        film_out.put("duration", film.getDuration());
        ArrayNode node1 = mapper.createArrayNode();
        for (String elem: film.getGenres()) {
            node1.add(elem);
        }
        film_out.set("genres", node1);

        ArrayNode node2 = mapper.createArrayNode();
        for (String elem: film.getActors()) {
            node2.add(elem);
        }
        film_out.set("actors", node2);

        ArrayNode node3 = mapper.createArrayNode();
        for (String elem: film.getCountriesBanned()) {
            node3.add(elem);
        }
        film_out.set("countriesBanned", node3);
        film_out.put("numLikes", film.getNumLikes());
        if (film.getNumRating() == 0)
            film_out.put("rating", Double.parseDouble( df.format(film.getRatingSum())));
        else
            film_out.put("rating", Double.parseDouble(df.format(((film.getRatingSum() / film.getNumRating())))));
        film_out.put("numRatings", film.getNumRating());
    }

    public void printCurrentMovieList(ObjectNode objectNode, ObjectMapper mapper) {
        ArrayNode node = mapper.createArrayNode();
        for (MoviesInput elem: current_movie_list) {
            ObjectNode film_out = mapper.createObjectNode();
            printMovieOutput(elem, film_out, mapper);
            node.add(film_out);
        }
        objectNode.set("currentMoviesList", node);
    }
    public void doAction(ActionsInput action, ObjectMapper mapper, ArrayNode output) {
        if (Objects.equals(action.getType(), "change page")) {
            if (!current_page.canMoveTo(action.getPage())) {

                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("error", "Error");
                ArrayNode arrayNode = mapper.createArrayNode();
                objectNode.set("currentMoviesList", arrayNode);
                //printCurrentMovieList(objectNode, mapper);
                objectNode.put("currentUser", (JsonNode) null);
                output.add(objectNode);

                return;
            }

            switch (action.getPage()) {
                case "login" -> {
                    current_page = new Login();
                }
                case "register" -> {
                    current_page = new Register();
                }
                case "logout" -> {
                    current_user = null;
                    current_movie_list = new ArrayList<>();
                    current_page = new HomepageNeautentificat();
                }
                case "movies" -> {
                    current_page = new Movies();
                    ArrayList<MoviesInput> visible_movies = new ArrayList<>();
                    for (MovieDetails in : current_user.getPersonal_movies())
                        visible_movies.add(in.getMovie());
                    current_movie_list = visible_movies;

                    ObjectNode objectNode = mapper.createObjectNode();
                    objectNode.put("error", (JsonNode) null);
                    printCurrentMovieList(objectNode, mapper);
                    printCurrentUser(objectNode, mapper);
                    output.add(objectNode);
                }
                case "see details"-> {

                    boolean found = false;
                    ArrayList<MoviesInput> list = new ArrayList<>();

                    for (MoviesInput moviesInput: current_movie_list) {

                        if (Objects.equals(moviesInput.getName(), action.getMovie())) {
                            list.add(moviesInput);

                            found = true;
                        }
                    }

                    if (!found) {
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);

                        return;
                    }
                    current_page = new SeeDetails();
                    current_movie_list = list;
                    ObjectNode objectNode = mapper.createObjectNode();
                    objectNode.put("error", (JsonNode) null);
                    printCurrentMovieList(objectNode, mapper);
                    printCurrentUser(objectNode, mapper);
                    output.add(objectNode);
                }
                case "upgrades" -> {
                    current_page = new Upgrades();
                }

            }

        }
        else { // action type == "on page"
            switch (action.getFeature()) {
                case "login" -> {
                    if (!Objects.equals(current_page.getPage_name(), "login")) {

                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        //printCurrentMovieList(objectNode, mapper);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);
                        return;

                    }
                    if (!Database.getDatabase().checkLogin(action.getCredentials())) {

//                        ObjectNode objectNode = mapper.createObjectNode();
//                        objectNode.put("error", "Error");
//                        printCurrentMovieList(objectNode, mapper);
//                        objectNode.put("currentUser", (JsonNode) null);
//                        output.add(objectNode);

                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        //printCurrentMovieList(objectNode, mapper);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);

                        current_page = new HomepageNeautentificat();
                        return;


                    }
                    current_user = Database.getDatabase().getUser(action.getCredentials());
                    current_page = new HomepageAutentificat();
                    ObjectNode objectNode = mapper.createObjectNode();
                    objectNode.put("error", (JsonNode) null);
                    printCurrentMovieList(objectNode, mapper);
                    printCurrentUser(objectNode, mapper);
                    output.add(objectNode);
                }
                case "register" -> {
                    if (!Objects.equals(current_page.getPage_name(), "register")) {

//                        ObjectNode objectNode = mapper.createObjectNode();
//                        objectNode.put("error", "Error");
//                        printCurrentMovieList(objectNode, mapper);
//                        objectNode.put("currentUser", (JsonNode) null);
//                        output.add(objectNode);
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        //printCurrentMovieList(objectNode, mapper);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);

                        return;

                    }
                    if (Database.getDatabase().alreadyTakenCheck(action.getCredentials().getName())) {

//                        ObjectNode objectNode = mapper.createObjectNode();
//                        objectNode.put("error", "Error");
//                        printCurrentMovieList(objectNode, mapper);
//                        objectNode.put("currentUser", (JsonNode) null);
//                        output.add(objectNode);

                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        //printCurrentMovieList(objectNode, mapper);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);


                        current_page = new HomepageNeautentificat();

                        return;
                    }



                    UsersInput new_user = new UsersInput(action.getCredentials());
                    current_user = new PersonalDatabase(new_user);
                    Database.getDatabase().getUser_accounts().add(current_user);
                    current_page = new HomepageAutentificat();

                    ObjectNode objectNode = mapper.createObjectNode();
                    objectNode.put("error", (JsonNode) null);
                    printCurrentMovieList(objectNode, mapper);
                    printCurrentUser(objectNode, mapper);
                    output.add(objectNode);
                }
                case "search" -> {
                    if (!Objects.equals(current_page.getPage_name(), "movies")) {
//                        ObjectNode objectNode = mapper.createObjectNode();
//                        objectNode.put("error", "Error");
//                        printCurrentMovieList(objectNode, mapper);
//                        objectNode.put("currentUser", (JsonNode) null);
//                        output.add(objectNode);
//                        return;
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        //printCurrentMovieList(objectNode, mapper);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);

                        return;
                    }
                    ((Movies)current_page).searchStartsWith(action.getStartsWith());
                    ObjectNode objectNode = mapper.createObjectNode();
                    objectNode.put("error", (JsonNode) null);
                    printCurrentMovieList(objectNode, mapper);
                    printCurrentUser(objectNode, mapper);
                    output.add(objectNode);
                }
                case "filter" -> {
                    if (!Objects.equals(current_page.getPage_name(), "movies")) {
//                        ObjectNode objectNode = mapper.createObjectNode();
//                        objectNode.put("error", "Error");
//                        printCurrentMovieList(objectNode, mapper);
//                        objectNode.put("currentUser", (JsonNode) null);
//                        output.add(objectNode);
//                        return;
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        //printCurrentMovieList(objectNode, mapper);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);

                        return;
                    }
                    ((Movies)current_page).filter(action.getFilters());
                    ObjectNode objectNode = mapper.createObjectNode();
                    objectNode.put("error", (JsonNode) null);
                    printCurrentMovieList(objectNode, mapper);
                    printCurrentUser(objectNode, mapper);
                    output.add(objectNode);
                }
                case "buy tokens" -> {
                    if (!Objects.equals(current_page.getPage_name(), "upgrades")) {
                        System.out.println("wrong page");
                        System.out.println(current_page.getPage_name());
//                        ObjectNode objectNode = mapper.createObjectNode();
//                        objectNode.put("error", "Error");
//                        printCurrentMovieList(objectNode, mapper);
//                        objectNode.put("currentUser", (JsonNode) null);
//                        output.add(objectNode);
//                        return;
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        //printCurrentMovieList(objectNode, mapper);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);

                        return;
                    }
                    if (Integer.parseInt(current_user.getUser().getBalance()) < action.getCount()) {
                        System.out.println("no money");
//                        ObjectNode objectNode = mapper.createObjectNode();
//                        objectNode.put("error", "Error");
//                        printCurrentMovieList(objectNode, mapper);
//                        objectNode.put("currentUser", (JsonNode) null);
//                        output.add(objectNode);
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        //printCurrentMovieList(objectNode, mapper);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);

                        return;
                    }
                    current_user.getUser().setTokens(current_user.getUser().getTokens() + action.getCount());
                    current_user.getUser().setBalance(Integer.toString( Integer.parseInt(current_user.getUser().getBalance()) - action.getCount()));
                }
                case "buy premium account" -> {
                    if (!Objects.equals(current_page.getPage_name(), "upgrades")) {
//                        ObjectNode objectNode = mapper.createObjectNode();
//                        objectNode.put("error", "Error");
//                        printCurrentMovieList(objectNode, mapper);
//                        objectNode.put("currentUser", (JsonNode) null);
//                        output.add(objectNode);
//                        return;
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        //printCurrentMovieList(objectNode, mapper);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);

                        return;
                    }
                    if (!Objects.equals(current_user.getUser().getAccountType(), "standard")) {
//                        ObjectNode objectNode = mapper.createObjectNode();
//                        objectNode.put("error", "Error");
//                        printCurrentMovieList(objectNode, mapper);
//                        objectNode.put("currentUser", (JsonNode) null);
//                        output.add(objectNode);
//                        return;
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        //printCurrentMovieList(objectNode, mapper);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);

                        return;
                    }
                    if (current_user.getUser().getTokens() < 10) {
//                        ObjectNode objectNode = mapper.createObjectNode();
//                        objectNode.put("error", "Error");
//                        printCurrentMovieList(objectNode, mapper);
//                        objectNode.put("currentUser", (JsonNode) null);
//                        output.add(objectNode);
//                        return;
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        //printCurrentMovieList(objectNode, mapper);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);

                        return;
                    }
                    current_user.getUser().setAccountType("premium");
                    current_user.getUser().setTokens(current_user.getUser().getTokens() - 10);

                }
                case "purchase" -> {
                    if (!Objects.equals(current_page.getPage_name(), "see details")) {
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        //printCurrentMovieList(objectNode, mapper);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);
                        return;
                    }
                    MoviesInput movie = null;
                    for (MoviesInput moviesInput: current_movie_list) {
                        if (moviesInput.getName().equals(action.getMovie())) {
                            movie = moviesInput;
                        }
                    }
                    if (action.getMovie() == null && current_movie_list.size() == 1) {
                        movie = current_movie_list.get(0);
                    }
                    if (movie == null) {
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        //printCurrentMovieList(objectNode, mapper);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);
                        return;
                    }
                    if (Objects.equals(current_user.getUser().getAccountType(), "premium")) {
                        if (current_user.getUser().getNumFreeMovies() == 0) {
                            if (current_user.getUser().getTokens() < 2) {
                                ObjectNode objectNode = mapper.createObjectNode();
                                objectNode.put("error", "Error");
                                ArrayNode arrayNode = mapper.createArrayNode();
                                objectNode.set("currentMoviesList", arrayNode);
                                objectNode.put("currentUser", (JsonNode) null);
                                output.add(objectNode);
                                return;
                            } else {
                                current_user.get_movie_details(movie).getMovie_char().setOwned(true);
                                current_user.getPurchased_movies().add(current_user.get_movie_details(movie));
                                current_user.getUser().setTokens(current_user.getUser().getTokens() - 2);
                            }
                        } else {
                            current_user.get_movie_details(movie).getMovie_char().setOwned(true);
                            current_user.getPurchased_movies().add(current_user.get_movie_details(movie));
                            current_user.getUser().setNumFreeMovies(current_user.getUser().getNumFreeMovies() - 1);
                        }
                    } else {
                        if (current_user.getUser().getTokens() < 2) {
                            ObjectNode objectNode = mapper.createObjectNode();
                            objectNode.put("error", "Error");
                            ArrayNode arrayNode = mapper.createArrayNode();
                            objectNode.set("currentMoviesList", arrayNode);
                            objectNode.put("currentUser", (JsonNode) null);
                            output.add(objectNode);
                            return;
                        } else {
                            current_user.get_movie_details(movie).getMovie_char().setOwned(true);
                            current_user.getPurchased_movies().add(current_user.get_movie_details(movie));
                            current_user.getUser().setTokens(current_user.getUser().getTokens() - 2);
                        }

                    }
                    ObjectNode objectNode = mapper.createObjectNode();
                    objectNode.put("error", (JsonNode) null);
                    printCurrentMovieList(objectNode, mapper);
                    printCurrentUser(objectNode, mapper);
                    output.add(objectNode);
                }
                case "watch" -> {
                    if (!Objects.equals(current_page.getPage_name(), "see details")) {
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);
                        return;
                    }
                    MoviesInput movie = null;
                    for (MovieDetails details: current_user.getPersonal_movies()) {
                        if (details.getMovie().getName().equals(action.getMovie())) {
                            movie = details.getMovie();
                        }
                    }
                    if (action.getMovie() == null && current_movie_list.size() == 1) {
                        movie = current_movie_list.get(0);
                    }
                    if (movie == null) {
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        //printCurrentMovieList(objectNode, mapper);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);
                        return;
                    }
                    if (!current_user.get_movie_details(movie).getMovie_char().isOwned()) {
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);
                        return;
                    }
                    current_user.get_movie_details(movie).getMovie_char().setHasWatched(true);
                    current_user.getWatched_movies().add(current_user.get_movie_details(movie));

                    ObjectNode objectNode = mapper.createObjectNode();
                    objectNode.put("error", (JsonNode) null);
                    printCurrentMovieList(objectNode, mapper);
                    printCurrentUser(objectNode, mapper);
                    output.add(objectNode);
                }
                case "like" -> {
                    if (!Objects.equals(current_page.getPage_name(), "see details")) {
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);
                        return;
                    }
                    MoviesInput movie = null;
                    for (MovieDetails details: current_user.getPersonal_movies()) {
                        if (details.getMovie().getName().equals(action.getMovie())) {
                            movie = details.getMovie();
                        }
                    }
                    if (action.getMovie() == null && current_movie_list.size() == 1) {
                        movie = current_movie_list.get(0);
                    }
                    if (movie == null) {
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        //printCurrentMovieList(objectNode, mapper);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);
                        return;
                    }
                    if (!current_user.get_movie_details(movie).getMovie_char().isOwned()) {
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);
                        return;
                    }
                    if (!current_user.get_movie_details(movie).getMovie_char().isHasWatched()) {
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);
                        return;
                    }
                    current_user.get_movie_details(movie).getMovie_char().setLiked(true);
                    current_user.getLiked_movies().add(current_user.get_movie_details(movie));
                    movie.setNumLikes(movie.getNumLikes() + 1);
                    ObjectNode objectNode = mapper.createObjectNode();
                    objectNode.put("error", (JsonNode) null);
                    printCurrentMovieList(objectNode, mapper);
                    printCurrentUser(objectNode, mapper);
                    output.add(objectNode);
                }
                case "rate" -> {
                    if (!Objects.equals(current_page.getPage_name(), "see details")) {
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);
                        return;
                    }
                    MoviesInput movie = null;
                    for (MovieDetails details: current_user.getPersonal_movies()) {
                        if (details.getMovie().getName().equals(action.getMovie())) {
                            movie = details.getMovie();
                        }
                    }
                    if (action.getMovie() == null && current_movie_list.size() == 1) {
                        movie = current_movie_list.get(0);
                    }
                    if (movie == null) {
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        //printCurrentMovieList(objectNode, mapper);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);
                        return;
                    }
                    if (!current_user.get_movie_details(movie).getMovie_char().isOwned()) {
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);
                        return;
                    }
                    if (!current_user.get_movie_details(movie).getMovie_char().isHasWatched()) {
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);
                        return;
                    }
                    if (action.getRate() < 1 || action.getRate() > 5) {
                        ObjectNode objectNode = mapper.createObjectNode();
                        objectNode.put("error", "Error");
                        ArrayNode arrayNode = mapper.createArrayNode();
                        objectNode.set("currentMoviesList", arrayNode);
                        objectNode.put("currentUser", (JsonNode) null);
                        output.add(objectNode);
                        return;
                    }
                    current_user.get_movie_details(movie).getMovie_char().setRating(action.getRate());
                    current_user.getRated_movies().add(current_user.get_movie_details(movie));
                    movie.setRatingSum(movie.getRatingSum() + action.getRate());
                    movie.setNumRating(movie.getNumRating() + 1);
                    ObjectNode objectNode = mapper.createObjectNode();
                    objectNode.put("error", (JsonNode) null);
                    printCurrentMovieList(objectNode, mapper);
                    printCurrentUser(objectNode, mapper);
                    output.add(objectNode);
                }
            }
        }
    }

    public boolean alreadyTakenCheck(String name) {
        for (PersonalDatabase user: getUser_accounts()) {
            if (Objects.equals(user.getUser().getName(), name)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkLogin(UsersInput user) {
        for (PersonalDatabase account: Database.getDatabase().getUser_accounts()) {
            if (user.equals(account.getUser()))
                return true;
        }
        return false;
    }

    public PersonalDatabase getUser(UsersInput credentials) {
        for (PersonalDatabase user: Database.getDatabase().getUser_accounts()) {
            if (Objects.equals(user.getUser().getName(), credentials.getName())) return user;
        }
        return null;
    }

    public ArrayList<MoviesInput> getCurrent_movie_list() {
        return current_movie_list;
    }

    public void setCurrent_movie_list(ArrayList<MoviesInput> current_movie_list) {
        this.current_movie_list = current_movie_list;
    }

    public Page getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(Page current_page) {
        this.current_page = current_page;
    }

    public PersonalDatabase getCurrent_user() {
        return current_user;
    }

    public void setCurrent_user(PersonalDatabase current_user) {
        this.current_user = current_user;
    }

    public ArrayList<MoviesInput> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<MoviesInput> movies) {
        this.movies = movies;
    }

    public ArrayList<PersonalDatabase> getUser_accounts() {
        return user_accounts;
    }

    public void setUser_accounts(ArrayList<PersonalDatabase> user_accounts) {
        this.user_accounts = user_accounts;
    }

}
