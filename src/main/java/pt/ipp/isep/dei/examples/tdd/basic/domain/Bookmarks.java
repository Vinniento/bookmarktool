package pt.ipp.isep.dei.examples.tdd.basic.domain;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Bookmarks {
    List<Bookmark> bookmarks = new ArrayList<>();

    public List<Bookmark> addBookmark(URL url) {

            bookmarks.add(new Bookmark(url));

            return bookmarks;
    }

}

