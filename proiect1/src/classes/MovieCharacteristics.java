package classes;

public class MovieCharacteristics {
    private boolean owned;
    private boolean hasWatched;
    private boolean liked;
    private int rating;

    public MovieCharacteristics() {
        this.owned = false;
        this.hasWatched = false;
        this.liked = false;
        this.rating = 0;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public boolean isHasWatched() {
        return hasWatched;
    }

    public void setHasWatched(boolean hasWatched) {
        this.hasWatched = hasWatched;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
