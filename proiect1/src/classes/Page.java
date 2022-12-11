package classes;

import java.util.ArrayList;

public class Page {
    private ArrayList<String> page_references = null;
    private String page_name = "page";
    public boolean canMoveTo(String page_to_access) {
        for (String page: page_references)
            if (page.equals(page_to_access)) return true;
        return false;
    }
    public Page() {
        page_references = new ArrayList<>();
    }
    public ArrayList<String> getPage_references() {
        return page_references;
    }
    public void setPage_references(ArrayList<String> page_references) {
        this.page_references = page_references;
    }

    public String getPage_name() {
        return page_name;
    }

    public void setPage_name(String page_name) {
        this.page_name = page_name;
    }
}
