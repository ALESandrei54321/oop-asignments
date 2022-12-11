package classes;

public class Movies extends Page{
    public Movies() {
        super();
        this.setPage_name("movies");
        this.getPage_references().add("homepage autentificat");
        this.getPage_references().add("see details");
        this.getPage_references().add("logout");
    }
}
