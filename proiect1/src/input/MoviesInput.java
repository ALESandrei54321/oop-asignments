package input;

import java.util.ArrayList;

public class MoviesInput {
    @Override
    public String toString() {
        return "MoviesInput{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", duration=" + duration +
                ", genres=" + genres +
                ", countriesBanned=" + countriesBanned +
                ", actors=" + actors +
                ", numLikes=" + numLikes +
                ", ratingSum=" + ratingSum +
                ", numRating=" + numRating +
                '}';
    }

    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> countriesBanned;
    private ArrayList<String> actors;
    private int numLikes = 0;
    private double ratingSum = 0.00;
    private int numRating = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public double getRatingSum() {
        return ratingSum;
    }

    public void setRatingSum(double ratingSum) {
        this.ratingSum = ratingSum;
    }

    public int getNumRating() {
        return numRating;
    }

    public void setNumRating(int numRating) {
        this.numRating = numRating;
    }


}
