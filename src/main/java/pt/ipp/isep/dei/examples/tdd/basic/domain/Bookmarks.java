package pt.ipp.isep.dei.examples.tdd.basic.domain;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Bookmarks {
    List<Bookmark> bookmarkList = new ArrayList<>();

    public List<Bookmark> addBookmark(URL url) {

        if (checkIfBookmarkExists(url)) {
            for (Bookmark bookmark : bookmarkList) {
                bookmark.setRating(bookmark.getRating() + 1);
            }
        } else {
            bookmarkList.add(new Bookmark(url));
        }
        return bookmarkList;
    }

    public boolean checkIfBookmarkExists(URL url) {
        for (Bookmark bm: bookmarkList) {
            if(bm.getUrl() == url) return true;
        }
        return false;
    }
    public void addTagToBookmark(URL url, String tag) {

        bookmarkList.forEach(bookmark -> {
            if (bookmark.getUrl() == url) {
                bookmark.addTag(tag);
            }
        });
    }

    public List<String> getBookmarkTags(URL url) {
        List<String> tag = new ArrayList<>();

        for (Bookmark bookmark : bookmarkList) {
            if (bookmark.getUrl() == url) {
                tag = bookmark.getTags();
                break;
            }
        }
        return tag;
    }

}

