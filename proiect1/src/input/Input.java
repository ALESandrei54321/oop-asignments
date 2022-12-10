package input;

import java.util.ArrayList;

public class Input {
    private ArrayList<CredentialsInput> users;
    private ArrayList<MoviesInput> movies;
    private ArrayList<ActionsInput> actions;

    public ArrayList<CredentialsInput> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<CredentialsInput> users) {
        this.users = users;
    }

    public ArrayList<MoviesInput> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<MoviesInput> movies) {
        this.movies = movies;
    }

    public ArrayList<ActionsInput> getActions() {
        return actions;
    }

    public void setActions(ArrayList<ActionsInput> actions) {
        this.actions = actions;
    }
}
