package pt.ipp.isep.dei.examples.tdd.basic.domain;


import java.awt.print.Book;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        for (Bookmark bm : bookmarkList) {
            if (bm.getUrl() == url) return true;
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

    public long getSecureUrlCount() {
        long count = 0;
        for (Bookmark bm : bookmarkList) {
            if (bm.getUrl().toString().contains("https://"))
                count++;
        }
        return count;
    }

    public List<Bookmark> getAssociatedBookmarksForDomain(String domain) {

        return bookmarkList
                .stream()
                .filter(bookmark -> Objects.equals(bookmark.getDomain(), domain))
                .collect(Collectors.toList());
    }

    public List<Bookmark> getBookmarksByTag(String tag) {
        return bookmarkList
                .stream()
                .filter(bookmark -> bookmark.getTags().contains(tag))
                .collect(Collectors.toList());
    }

    public List<Bookmark> getBookmarksByTags(List<String> tags) {
        List<Bookmark> result = new ArrayList<>();
        for (String tag : tags) {
            result.addAll(getBookmarksByTag(tag));
        }
        return result;
    }

    public void deleteTagFromBookmark(URL url, String tag) {

        for (Bookmark bookmark : bookmarkList) {
            if (bookmark.getUrl() == url) {
                bookmark.deleteTag(tag);
            }
        }
    }

    public void removeBookmark(URL url) {
        bookmarkList.removeIf(bookmark -> bookmark.getUrl() == url);
    }


    public List<Bookmark> getBookmarksSortedByRating() {

        return bookmarkList
                .stream()
                .sorted(Comparator.comparingInt(Bookmark::getRating).reversed())
                .collect(Collectors.toList());

    }

    public List<Bookmark> getBookmarksSortedByDate() {

        if (!bookmarkList.isEmpty())
            return
                    bookmarkList
                            .stream()
                            .sorted(new Comparator<Bookmark>() {
                                public int compare(Bookmark o1, Bookmark o2) {
                                    return o1.getCreationTime().compareTo(o2.getCreationTime());
                                }
                            })
                            .collect(Collectors.toList());

        return  new ArrayList<>();
    }

    public void setDateTimeForBookmark(URL url, LocalDateTime time) {

        for (Bookmark bm : bookmarkList) {
            if (bm.getUrl().equals(url))
                bm.setDateTime(time);

        }
    }
}

