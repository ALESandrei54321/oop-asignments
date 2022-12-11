package classes;

public class HomepageAutentificat extends Page{
    public HomepageAutentificat() {
        super();
        this.setPage_name("homepage autentificat");
        this.getPage_references().add("movies");
        this.getPage_references().add("upgrades");
        this.getPage_references().add("logout");
    }
}
