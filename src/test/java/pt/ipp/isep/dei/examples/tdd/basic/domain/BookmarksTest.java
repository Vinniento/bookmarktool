package pt.ipp.isep.dei.examples.tdd.basic.domain;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void ensureFilteringByMultipleKeywordsThatExistReturnTotal() throws MalformedURLException {
        URL url = new URL("http://www.google.com");
        URL url1 = new URL("http://www.face.com/bla");
        URL url2 = new URL("http://www.google.at/extra");

        Bookmarks bookmarks = new Bookmarks();
        //arrange
        String firstTag = "firstTag";
        String secondTag = "secondTag";
        String thirdTag = "thirdTag";
        bookmarks.addBookmark(url);
        bookmarks.addBookmark(url1);
        bookmarks.addBookmark(url2);

        bookmarks.addTagToBookmark(url, firstTag);
        bookmarks.addTagToBookmark(url, secondTag);
        bookmarks.addTagToBookmark(url2, thirdTag);

        //act
        int result = bookmarks.getBookmarksByTags(Arrays.asList(secondTag, firstTag)).size();

        //result

        int expectedResult = 2;

        assertEquals(expectedResult, result);
    }

    @Test
    public void ensureFilteringByMultipleKeywordsThatDoNotExistReturnTotal() throws MalformedURLException {
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

        //act
        int result = bookmarks.getBookmarksByTags(Arrays.asList(secondTag, firstTag)).size();

        //result
        int expectedResult = 0;

        assertEquals(expectedResult, result);
    }

    @Test
    public void ensureTagIsDeletedInBookmark() throws MalformedURLException {
        URL url = new URL("http://www.google.com");
        URL url1 = new URL("http://www.facebook.com");
        Bookmarks bookmarks = new Bookmarks();
        //arrange
        String firstTag = "firstTag";
        String secondTag = "secondTag";

        bookmarks.addBookmark(url);
        bookmarks.addBookmark(url1);

        bookmarks.addTagToBookmark(url, firstTag);
        bookmarks.addTagToBookmark(url1, firstTag);
        bookmarks.addTagToBookmark(url, secondTag);
        bookmarks.addTagToBookmark(url1, secondTag);


        //act
        bookmarks.deleteTagFromBookmark(url, firstTag);

        //result
        List<String> result = bookmarks.getBookmarkTags(url);
        List<String> expectedResult = Arrays.asList(secondTag);

        assertEquals(expectedResult, result);


    }

    @Test
    public void deleteTagOfNotExistentBookmark() throws MalformedURLException {
        URL url = new URL("http://www.google.com");
        URL url1 = new URL("http://www.facebook.com");
        Bookmarks bookmarks = new Bookmarks();
        //arrange
        String firstTag = "firstTag";
        String secondTag = "secondTag";

        bookmarks.addBookmark(url);


        bookmarks.addTagToBookmark(url, firstTag);
        bookmarks.addTagToBookmark(url, secondTag);

        //act
        bookmarks.deleteTagFromBookmark(url1, firstTag);

        //result
        List<String> result = bookmarks.getBookmarkTags(url1);
        List<String> expectedResult = Arrays.asList();

        assertEquals(expectedResult, result);

    }

    @Test
    public void deleteTagFromEmptyList() throws MalformedURLException {
        URL url = new URL("http://www.google.com");
        URL url1 = new URL("http://www.facebook.com");
        Bookmarks bookmarks = new Bookmarks();
        //arrange
        String firstTag = "firstTag";

        //act
        bookmarks.deleteTagFromBookmark(url1, firstTag);

        //result
        List<String> result = bookmarks.getBookmarkTags(url1);
        List<String> expectedResult = Arrays.asList();

        assertEquals(expectedResult, result);


    }

    @Test
    public void ensureRemovingExistingBookmarkResultsInSmallerList() throws MalformedURLException {
        URL url = new URL("http://www.google.com");
        URL url1 = new URL("http://www.facebook.com");
        Bookmarks bookmarks = new Bookmarks();
        //arramge
        bookmarks.addBookmark(url);
        bookmarks.addBookmark(url1);

        int expectedResult = 1;
        //act
        bookmarks.removeBookmark(url);

        //assert

        assertEquals(expectedResult, bookmarks.bookmarkList.size());

    }

    @Test
    public void ensureRemovingNotExistingBookmarkResultsInSame() throws MalformedURLException {
        URL url = new URL("http://www.google.com");
        URL url1 = new URL("http://www.facebook.com");
        URL url2 = new URL("http://www.asdf.com");
        Bookmarks bookmarks = new Bookmarks();
        //arramge
        bookmarks.addBookmark(url);
        bookmarks.addBookmark(url1);

        int expectedResult = 2;
        //act
        bookmarks.removeBookmark(url2);

        //assert

        assertEquals(expectedResult, bookmarks.bookmarkList.size());

    }

    @Test
    public void ensureBookmarksCanBeSortedByRatingFromHighToLow() throws MalformedURLException {
        Bookmarks bookmarks = new Bookmarks();

        URL url = new URL("https://www.google.com");
        URL url1 = new URL("https://www.get.com");
        URL url2 = new URL("https://www.google.at");
        // arrange
        bookmarks.addBookmark(url);
        bookmarks.addBookmark(url);
        bookmarks.addBookmark(url);
        bookmarks.addBookmark(url1);
        bookmarks.addBookmark(url2);

        int expectedResult = 2;

        // act

        List<Bookmark> result = bookmarks.getBookmarksSortedByRating();

        // assert
        assertEquals(expectedResult, result.get(0).getRating());
    }

    @Test
    public void ensureGetBookmarksSortedByDateReturnsEmptyListIsEmpty() throws MalformedURLException {
        Bookmarks bookmarks = new Bookmarks();


        List<Bookmark> result = bookmarks.getBookmarksSortedByDate();

        // assert
        assertTrue(result.isEmpty());
    }


    @Test
    public void ensureSetDateTimeForBookmark() throws MalformedURLException {
        Bookmarks bookmarks = new Bookmarks();

        URL url = new URL("http://www.google.com");
        String tag = "bla";
        bookmarks.addBookmark(url);
        bookmarks.addTagToBookmark(url, tag);
        //arrange
        LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        //act
        bookmarks.setDateTimeForBookmark(url, dateTime);
        Bookmark bookmark = bookmarks.getBookmarksByTag(tag).get(0);
        LocalDateTime result = bookmark.getCreationTime();
        //assert
        assertTrue(dateTime.isEqual(result));

    }

    @Test
    public void ensureSetDateTimeForBookmarkEmptyList() throws MalformedURLException, InterruptedException {
        Bookmarks bookmarks = new Bookmarks();
        LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        Thread.sleep(1000);

        URL url = new URL("http://www.google.com");
        URL url1 = new URL("http://www.gdfgoogle.com");
        String tag = "bla";
        bookmarks.addBookmark(url);
        bookmarks.addTagToBookmark(url, tag);
        //arrange

        //act
        bookmarks.setDateTimeForBookmark(url1, dateTime);

        Bookmark bookmark = bookmarks.getBookmarksByTag(tag).get(0);
        LocalDateTime result = bookmark.getCreationTime();
        //assert
        assertTrue(dateTime.isBefore(result));

    }

    @Test
    public void ensureSetDateTimeForBookmark2() throws MalformedURLException, InterruptedException {
        Bookmarks bookmarks = new Bookmarks();
        LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        Thread.sleep(1000);

        URL url = new URL("http://www.google.com");
        String tag = "bla";
        bookmarks.addBookmark(url);
        bookmarks.addTagToBookmark(url, tag);
        //arrange

        //act
        bookmarks.setDateTimeForBookmark(url, dateTime);

        Bookmark bookmark = bookmarks.getBookmarksByTag(tag).get(0);
        LocalDateTime result = bookmark.getCreationTime();
        //assert
        assertTrue(dateTime.isEqual(result));

    }

    @Test
    public void ensureBookmarksCanBeSortedByDateFromNewerToOlder() throws MalformedURLException, InterruptedException {
        Bookmarks bookmarks = new Bookmarks();
        URL url = new URL("https://www.google.com");
        URL url1 = new URL("https://www.get.com");

        // arrange
        bookmarks.addBookmark(url);

        Thread.sleep(1000);
        bookmarks.addBookmark(url1);
        // act


        List<Bookmark> result = bookmarks.getBookmarksSortedByDate();
        Bookmark older = result.get(0);
        Bookmark newer = result.get(1);


        // assert
        assertTrue(newer.getCreationTime().isAfter(older.getCreationTime()));
    }

}
