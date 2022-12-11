package classes;

import input.MoviesInput;

public class MovieDetails extends Page{
    private MoviesInput movie;
    private MovieCharacteristics movie_char;
    public MovieDetails(MoviesInput movie_input) {
        this.movie = movie_input;
        this.movie_char = new MovieCharacteristics();
    }

    public MoviesInput getMovie() {
        return movie;
    }

    public void setMovie(MoviesInput movie) {
        this.movie = movie;
    }

    public MovieCharacteristics getMovie_char() {
        return movie_char;
    }

    public void setMovie_char(MovieCharacteristics movie_char) {
        this.movie_char = movie_char;
    }
}
