package classes;

import input.ActionsInput;
import input.ContainsInput;
import input.MoviesInput;
import input.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class Movies extends Page{



    public Movies() {
        super();
        this.setPage_name("movies");
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

        MovieCompare comp = new MovieCompare();
        if (Objects.equals(filters.getSort().getRating(), "decreasing"))
            comp.setRate_order(1);
        else
            comp.setRate_order(-1);

        if (Objects.equals(filters.getSort().getDuration(), "decreasing"))
            comp.setDuration_order(1);
        else
            comp.setDuration_order(-1);
        list.sort(comp);

        Database.getDatabase().setCurrent_movie_list(list);



    }
}
