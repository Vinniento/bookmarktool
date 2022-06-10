package pt.ipp.isep.dei.examples.tdd.basic.domain;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Bookmark {

    URL url;
    List<String> tags;
    int rating = 0;

    public Bookmark(URL url) {
        this.url = url;
        this.tags = new ArrayList<>();

    }

    public URL getUrl() {
        return url;
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public List<String> getTags() {
        return this.tags;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

