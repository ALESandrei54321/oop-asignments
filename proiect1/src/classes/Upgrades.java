package classes;

public class Upgrades extends Page{
    public Upgrades() {
        super();
        this.setPage_name("upgrades");
        this.getPage_references().add("homepage autentificat");
        this.getPage_references().add("movies");
        this.getPage_references().add("logout");
    }
}
