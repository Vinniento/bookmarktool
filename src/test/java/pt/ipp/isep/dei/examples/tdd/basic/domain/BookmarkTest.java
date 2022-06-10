package pt.ipp.isep.dei.examples.tdd.basic.domain;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookmarkTest {

    @Test
    public void ensureGetUrlReturnsUrl() throws MalformedURLException {

        //arrange
        URL url = new URL("http://www.google.com");
        Bookmark bookmark = new Bookmark(url);

        //act
        URL result = bookmark.getUrl();
        //assert
        assertEquals(url, result);
    }

    @Test
    public void ensureSingleTaggingOfBookmark() throws MalformedURLException {

        URL url = new URL("http://www.google.com");
        Bookmark bookmark = new Bookmark(url);

        //arrange
        String firstTag = "firstTag";
        List<String> expectedResult = Arrays.asList(firstTag);

        //act
        bookmark.addTag(firstTag);

        List<String> result = bookmark.getTags();

        //assert
        assertEquals(expectedResult, result);

    }
}
