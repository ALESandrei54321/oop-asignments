package classes;

import java.util.ArrayList;

public class HomepageNeautentificat extends Page{
    public HomepageNeautentificat() {
        super();
        this.setPage_name("homepage neautentificat");
        this.getPage_references().add("login");
        this.getPage_references().add("register");
    }
}
