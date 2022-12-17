package classes;

import input.Input;
import input.MoviesInput;
import input.UsersInput;


import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;

public class PersonalDatabase {
    private UsersInput user;
    private ArrayList<MovieDetails> personal_movies = null;
    private ArrayList<MovieDetails> purchased_movies = null;
    private ArrayList<MovieDetails> watched_movies = null;
    private ArrayList<MovieDetails> liked_movies = null;
    private ArrayList<MovieDetails> rated_movies = null;

    public PersonalDatabase(UsersInput usersInput) {
        user = usersInput;
        personal_movies = new ArrayList<>();
        for (MoviesInput movie : Database.getDatabase().getMovies()) {
            if (canWatch(usersInput, movie.getCountriesBanned()))
                personal_movies.add(new MovieDetails(movie));
        }
        purchased_movies = new ArrayList<>();
        watched_movies = new ArrayList<>();
        liked_movies = new ArrayList<>();
        rated_movies = new ArrayList<>();
    }

    public MovieDetails get_movie_details(MoviesInput input) {
        for (MovieDetails details: this.getPersonal_movies()) {
            if (Objects.equals(details.getMovie().getName(), input.getName()))
                return details;
        }
        return null;
    }

    public boolean canWatch(UsersInput usersInput, ArrayList<String> countries) {
        for (String country: countries) {
            if (Objects.equals(country, usersInput.getCountry())) {
                return false;
            }
        }
        return true;
    }

    public UsersInput getUser() {
        return user;
    }

    public void setUser(UsersInput user) {
        this.user = user;
    }

    public ArrayList<MovieDetails> getPersonal_movies() {
        return personal_movies;
    }

    public void setPersonal_movies(ArrayList<MovieDetails> personal_movies) {
        this.personal_movies = personal_movies;
    }

    public ArrayList<MovieDetails> getPurchased_movies() {
        return purchased_movies;
    }

    public void setPurchased_movies(ArrayList<MovieDetails> purchased_movies) {
        this.purchased_movies = purchased_movies;
    }

    public ArrayList<MovieDetails> getWatched_movies() {
        return watched_movies;
    }

    public void setWatched_movies(ArrayList<MovieDetails> watched_movies) {
        this.watched_movies = watched_movies;
    }

    public ArrayList<MovieDetails> getLiked_movies() {
        return liked_movies;
    }

    public void setLiked_movies(ArrayList<MovieDetails> liked_movies) {
        this.liked_movies = liked_movies;
    }

    public ArrayList<MovieDetails> getRated_movies() {
        return rated_movies;
    }

    public void setRated_movies(ArrayList<MovieDetails> rated_movies) {
        this.rated_movies = rated_movies;
    }
}
