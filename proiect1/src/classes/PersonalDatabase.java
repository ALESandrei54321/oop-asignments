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


    public PersonalDatabase(UsersInput usersInput) {
        user = usersInput;
        personal_movies = new ArrayList<>();
        for (MoviesInput movie : Database.getDatabase().getMovies()) {
            if (canWatch(usersInput, movie.getCountriesBanned()))
                personal_movies.add(new MovieDetails(movie));
        }
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


}
