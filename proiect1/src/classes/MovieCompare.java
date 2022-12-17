package classes;

import input.MoviesInput;

import java.util.Comparator;

public class MovieCompare implements Comparator<MoviesInput> {
    private int rate_order;
    private int duration_order;

    public int getRate_order() {
        return rate_order;
    }

    public void setRate_order(int rate_order) {
        this.rate_order = rate_order;
    }

    public int getDuration_order() {
        return duration_order;
    }

    public void setDuration_order(int duration_order) {
        this.duration_order = duration_order;
    }

    @Override
    public int compare(MoviesInput o1, MoviesInput o2) {

        double rate1 = o1.getRatingSum();
        if (o1.getNumRating() != 0)
            rate1 = rate1 / o1.getNumRating();

        double rate2 = o2.getRatingSum();
        if (o2.getNumRating() != 0) {
            rate2 = rate2 / o2.getNumRating();
        }

        if (getRate_order() == 0) {
            if (o1.getDuration() > o2.getDuration()) {
                return -getDuration_order();
            }
            else if (o1.getDuration() < o2.getDuration()) {
                return  getDuration_order();
            }
            return 0;
        }

        if (getDuration_order() == 0) {
            if (rate1 > rate2) {
                return -getRate_order();
            }
            else if (rate1 < rate2) {
                return getRate_order();
            }
            return 0;
        }

        if (o1.getDuration() > o2.getDuration()) {
            return -getDuration_order();
        }
        else if (o1.getDuration() < o2.getDuration()) {
            return  getDuration_order();
        } else {
            if (rate1 > rate2) {
                return -getRate_order();
            }
            else if (rate1 < rate2) {
                return getRate_order();
            }
        }

        return 0;
    }














}
