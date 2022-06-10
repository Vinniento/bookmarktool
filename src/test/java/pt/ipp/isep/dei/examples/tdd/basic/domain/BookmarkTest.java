package pt.ipp.isep.dei.examples.tdd.basic.domain;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

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
}
