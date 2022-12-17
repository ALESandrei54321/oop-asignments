package classes;

import input.ContainsInput;
import input.MoviesInput;


import java.util.ArrayList;
import java.util.Objects;

public class Movies extends Page{

    public Movies() {
        super();
        this.setPage_name("movies");
        this.getPage_references().add("movies");
        this.getPage_references().add("homepage autentificat");
        this.getPage_references().add("see details");
        this.getPage_references().add("logout");
    }

    public void searchStartsWith(String startsWith) {
        ArrayList<MoviesInput> list = new ArrayList<>();
        for (MoviesInput in: Database.getDatabase().getCurrent_movie_list()) {
            if (in.getName().startsWith(startsWith)) {
                list.add(in);
            }
        }
        Database.getDatabase().setCurrent_movie_list(list);
    }

    public void filter(ContainsInput filters) {
        ArrayList<MoviesInput> list = new ArrayList<>();
        for (MovieDetails movieDetails: Database.getDatabase().getCurrent_user().getPersonal_movies()) {
            list.add(movieDetails.getMovie());
        }


        if (filters.getContains() != null && filters.getContains().getActors() != null) {
            ArrayList<MoviesInput> contains_list = new ArrayList<>();
            for (MoviesInput movie : list) {
                boolean found = false;
                for (int i = 0; i < movie.getActors().size(); i++) {
                   for (int j = 0; j < filters.getContains().getActors().size(); j++) {
                       if (Objects.equals(movie.getActors().get(i), filters.getContains().getActors().get(j))) {
                           found = true;
                           break;
                       }
                   }
                }
                if (found) contains_list.add(movie);
            }
            list = contains_list;
        }


        if (filters.getContains() != null && filters.getContains().getGenre() != null) {
            ArrayList<MoviesInput> contains_list = new ArrayList<>();
            for (MoviesInput movie : list) {
                boolean found = false;
                for (int i = 0; i < movie.getGenres().size(); i++) {
                    for (int j = 0; j < filters.getContains().getGenre().size(); j++) {
                        if (Objects.equals(movie.getGenres().get(i), filters.getContains().getGenre().get(j))) {
                            found = true;
                            break;
                        }
                    }
                }
                if (found) contains_list.add(movie);
            }
            list = contains_list;
        }

        if (filters.getSort() != null) {
            MovieCompare comp = new MovieCompare();
            comp.setRate_order(0);
            comp.setDuration_order(0);
            if (Objects.equals(filters.getSort().getRating(), "decreasing"))
                comp.setRate_order(1);
            else if (Objects.equals(filters.getSort().getRating(), "increasing"))
                comp.setRate_order(-1);

            if (Objects.equals(filters.getSort().getDuration(), "decreasing"))
                comp.setDuration_order(1);
            else if (Objects.equals(filters.getSort().getDuration(), "increasing"))
                comp.setDuration_order(-1);

            list.sort(comp);
        }
        Database.getDatabase().setCurrent_movie_list(list);
    }
}
