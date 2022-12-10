package input;

public class ActionsInput {
    private String type;
    private String page;
    private UsersInput credentials;
    private String feature;
    private String movie;
    private ContainsInput filters;
    private int count;
    private String startsWith;
    private String objectType;
    private int rate;

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(String startsWith) {
        this.startsWith = startsWith;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public UsersInput getCredentials() {
        return credentials;
    }

    public void setCredentials(UsersInput credentials) {
        this.credentials = credentials;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public ContainsInput getFilters() {
        return filters;
    }

    public void setFilters(ContainsInput filters) {
        this.filters = filters;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
