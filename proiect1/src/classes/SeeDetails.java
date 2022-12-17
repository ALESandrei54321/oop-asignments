package classes;

public class SeeDetails extends Page{
    public SeeDetails() {
        super();
        this.setPage_name("see details");
        this.getPage_references().add("movies");
        this.getPage_references().add("homepage autentificat");
        this.getPage_references().add("upgrades");
        this.getPage_references().add("logout");
    }
}
