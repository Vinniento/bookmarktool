package pt.ipp.isep.dei.examples.tdd.basic.domain;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookmarksTest {

    @Test
    public void addBookmarksTest() throws MalformedURLException {

        Bookmarks bookmarks = new Bookmarks();

        //arrange
        List<Bookmark> expectedResult = new ArrayList<>();
        List<Bookmark> result;
        URL url = new URL("http://www.google.com");

        //act
        expectedResult.add(new Bookmark(url));
        result = bookmarks.addBookmark(url);

        //assert
        assertEquals(expectedResult.get(0).getUrl(), result.get(0).getUrl());
    }

    @Test
    public void ensueGetTagsReturnsZeroWhenListIsEmpty() throws MalformedURLException {

        //arrange
        Bookmarks bookmarks = new Bookmarks();
        URL url = new URL("http://www.google.com");

        //act
        bookmarks.addBookmark(url);

        List<String> result = bookmarks.getBookmarkTags(url);

        //result
        assertEquals(0, result.size());

    }

    @Test
    public void ensureSingleTaggingOfBookmark() throws MalformedURLException {


        //arrange
        Bookmarks bookmarks = new Bookmarks();
        String tag = "katzenvideos";
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add(tag);
        URL url = new URL("https://www.google.com");

        //act
        bookmarks.addBookmark(url);
        bookmarks.addTagToBookmark(url, tag);

        List<String> result = bookmarks.getBookmarkTags(url);

        //result
        assertEquals(expectedResult, result);

    }

    @Test
    public void ensureMultipleTagsForBookmark() throws MalformedURLException {

        URL url = new URL("http://www.google.com");
        Bookmarks bookmarks = new Bookmarks();
        //arrange
        String firstTag = "firstTag";
        String secondTag = "secondTag";
        List<String> expectedResult = Arrays.asList(firstTag, secondTag);

        //act
        bookmarks.addBookmark(url);
        bookmarks.addTagToBookmark(url, firstTag);
        bookmarks.addTagToBookmark(url, secondTag);

        List<String> result = bookmarks.getBookmarkTags(url);

        //assert
        assertEquals(expectedResult, result);

    }
}
