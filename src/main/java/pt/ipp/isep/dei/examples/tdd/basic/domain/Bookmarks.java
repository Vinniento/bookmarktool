package pt.ipp.isep.dei.examples.tdd.basic.domain;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Bookmarks {
    List<Bookmark> bookmarkList = new ArrayList<>();

    public List<Bookmark> addBookmark(URL url) {

            bookmarkList.add(new Bookmark(url));

            return bookmarkList;
    }


    public List<String> getBookmarkTags(URL url) {


        return new ArrayList<>();
    }

}

