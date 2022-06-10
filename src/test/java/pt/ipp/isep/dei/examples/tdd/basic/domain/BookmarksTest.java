package pt.ipp.isep.dei.examples.tdd.basic.domain;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
    public void ensureGetTagsReturnsZeroWhenListIsEmpty() throws MalformedURLException {

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
    public void ensureNoTagIsAddedIfUrlNotExist() throws MalformedURLException {


        //arrange
        Bookmarks bookmarks = new Bookmarks();
        String tag = "katzenvideos";
        List<String> expectedResult = new ArrayList<>();
        URL url = new URL("https://www.google.com");
        URL url1 = new URL("https://www.google.at");

        //act
        bookmarks.addBookmark(url);
        bookmarks.addTagToBookmark(url1, tag);

        List<String> result = bookmarks.getBookmarkTags(url);

        //result
        assertEquals(expectedResult, result);

    }

    @Test
    public void ensureNoTagIsReturnedIfBookmarkListEmpty() throws MalformedURLException {

        //arrange
        Bookmarks bookmarks = new Bookmarks();
        List<String> expectedResult = new ArrayList<>();
        URL url = new URL("https://www.google.com");

        //act

        List<String> result = bookmarks.getBookmarkTags(url);

        //result
        assertEquals(expectedResult, result);

    }

    @Test
    public void ensureNoTagIsReturnedIfUrlNotExist() throws MalformedURLException {

        //arrange
        Bookmarks bookmarks = new Bookmarks();
        List<String> expectedResult = new ArrayList<>();
        URL url = new URL("https://www.google.com");
        URL url1 = new URL("https://www.google.at");
        bookmarks.addBookmark(url);
        //act

        List<String> result = bookmarks.getBookmarkTags(url1);

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

    @Test
    public void ensureRatingGetsIncreasedWhenBookmarkIsAgainAdded() throws MalformedURLException {
        Bookmarks bookmarks = new Bookmarks();

        URL url = new URL("https://www.google.com");
        // arrange
        bookmarks.addBookmark(url);
        bookmarks.addBookmark(url);

        int expectedResult = 1;
        int result;

        // act
        result = Objects.requireNonNull(bookmarks.bookmarkList
                .stream().filter(bookmark -> bookmark.getUrl() == url)
                .findFirst().orElse(null)).getRating();

        // assert
        assertEquals(expectedResult, result);
    }

    @Test
    public void ensureCheckIfBookmarkExists() throws MalformedURLException {
        Bookmarks bookmarks = new Bookmarks();

        URL url = new URL("https://www.google.com");
        URL url1 = new URL("https://www.google.at");
        // arrange
bookmarks.addBookmark(url);
        // act
        boolean result = bookmarks.checkIfBookmarkExists(url1);

        // assert
        assertFalse(result);
    }

    //CheckSecureUrlCount
    @Test
    public void ensureNoSecureUrlsInBookmarksReturnsZero() throws MalformedURLException {

        //arrange
        Bookmarks bookmarks = new Bookmarks();

        bookmarks.addBookmark(new URL("http://www.google.at"));
        bookmarks.addBookmark(new URL("http://www.orf.at"));

        //act
        long result = bookmarks.getSecureUrlCount();

        //assert
        assertEquals(0, result);

    }

    @Test
    public void ensureOneSecureUrlInBookmarksReturnsOne() throws MalformedURLException {

        //arrange
        Bookmarks bookmarks = new Bookmarks();

        bookmarks.addBookmark(new URL("https://www.google.at"));
        bookmarks.addBookmark(new URL("http://www.orf.at"));

        //act
        long result = bookmarks.getSecureUrlCount();

        //assert
        assertEquals(1, result);
    }

    @Test
    public void ensureBookmarkOfTheSameDomainAreAssociated() throws MalformedURLException {

        //arrange
        Bookmarks bookmarks = new Bookmarks();
        URL url = new URL("https://www.google.at/dadu");
        URL url1 = new URL("https://www.google.at/bla");
        URL url2 = new URL("https://www.google.com/blasdf");

        bookmarks.addBookmark(url);
        bookmarks.addBookmark(url1);
        bookmarks.addBookmark(url2);

        //act
        int result = bookmarks.getAssociatedBookmarksForDomain("www.google.at").size();

        int expectedResult = 2;

        //assert
        assertEquals(expectedResult, result);
    }

    @Test
    public void ensureFilteringByKeywordThatExistsReturnsResult() throws MalformedURLException {

        URL url = new URL("http://www.google.com");
        URL url1 = new URL("http://www.face.com/bla");
        URL url2 = new URL("http://www.google.at/extra");

        Bookmarks bookmarks = new Bookmarks();
        //arrange
        String firstTag = "firstTag";
        String secondTag = "secondTag";
        bookmarks.addBookmark(url);
        bookmarks.addBookmark(url1);
        bookmarks.addBookmark(url2);

        bookmarks.addTagToBookmark(url, firstTag);
        bookmarks.addTagToBookmark(url, secondTag);
        bookmarks.addTagToBookmark(url2, firstTag);

        //act

        int result = bookmarks.getBookmarksByTag(firstTag).size();

        //result

        int expectedResult = 2;

        assertEquals(expectedResult, result);


    }

    @Test
    public void ensureFilteringByKeywordThatDoesNotExistsReturnsZero() throws MalformedURLException {

        URL url = new URL("http://www.google.com");
        URL url1 = new URL("http://www.face.com/bla");
        URL url2 = new URL("http://www.google.at/extra");

        Bookmarks bookmarks = new Bookmarks();
        //arrange
        String firstTag = "firstTag";
        bookmarks.addBookmark(url);
        bookmarks.addBookmark(url1);
        bookmarks.addBookmark(url2);


        //act

        int result = bookmarks.getBookmarksByTag(firstTag).size();


        //result

        int expectedResult = 0;

        assertEquals(expectedResult, result);
    }

}
