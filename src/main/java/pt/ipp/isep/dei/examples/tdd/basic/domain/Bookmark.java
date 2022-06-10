package pt.ipp.isep.dei.examples.tdd.basic.domain;


import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Bookmark {

    private final URL url;
    private final List<String> tags;
    private int rating = 0;
    private LocalDateTime creationTime;

    public Bookmark(URL url) {
        this.url = url;
        this.tags = new ArrayList<>();
        this.creationTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
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

    public String getDomain() {
        return url.getHost();
    }

    public void deleteTag(String tag) {
        this.tags.remove(tag);
    }

    public LocalDateTime getCreationTime() {
        return this.creationTime;
    }

    public void setDateTime(LocalDateTime time) {
        this.creationTime = time;
    }
}

