package com.semanticsquare.thrillio.managers;

import com.semanticsquare.thrillio.constants.KidFriendlyStatus;
import com.semanticsquare.thrillio.dao.BookmarkDao;
import com.semanticsquare.thrillio.constants.BookGenre;
import com.semanticsquare.thrillio.constants.MovieGenre;
import com.semanticsquare.thrillio.entities.*;

import java.util.List;

public class BookmarkManager {
    private static BookmarkManager instance = new BookmarkManager();
    private static BookmarkDao dao = new BookmarkDao();

    private BookmarkManager() {

    }

    public static BookmarkManager getInstance() {
        return instance;
    }

    public Movie createMovie(long id, String title, int releaseYear, String[] cast, String[] directors,
                             MovieGenre genre, double imdbRating) {
        Movie movie = new Movie();

        movie.setId(id);
        movie.setTitle(title);
        movie.setReleaseYear(releaseYear);
        movie.setCast(cast);
        movie.setDirectors(directors);
        movie.setGenre(genre);
        movie.setImdbRating(imdbRating);

        return movie;
    }

    public Book createBook(long id, String title, int publishYear, String publisher, String[] authors,
                           BookGenre genre, double amazonRating) {
        Book book = new Book();

        book.setId(id);
        book.setTitle(title);
        book.setPublisher(publisher);
        book.setAuthors(authors);
        book.setGenre(genre);
        book.setAmazonRating(amazonRating);

        return book;
    }

    public Weblink createWeblink(long id, String title, String profileURL, String url, String host) {
        Weblink weblink = new Weblink();

        weblink.setId(id);
        weblink.setTitle(title);
        weblink.setProfileURL(profileURL);
        weblink.setUrl(url);
        weblink.setHost(host);

        return weblink;
    }

    public List<List<Bookmark>> getBookmarks() {
        return dao.getBookmarks();
    }

    public void saveUserBookmark(User user, Bookmark bookmark) {
        UserBookmark userBookmark = new UserBookmark();
        userBookmark.setUser(user);
        userBookmark.setBookmark(bookmark);
/*
        if (bookmark instanceof Weblink) {
            try {
                String url = ((Weblink)bookmark).getUrl();
                if (!url.endsWith(".pdf")) {
                    String webpage = HttpConnect.download(((Weblink)bookmark).getUrl());
                    if (webpage != null) {
                        IOUtil.write(webpage, bookmark.getId());
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
*/
        dao.saveUserBookmark(userBookmark);
    }

    public void setKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus, Bookmark bookmark) {
        bookmark.setKidFriendlyStatus(kidFriendlyStatus);
        bookmark.setKidFriendlyMarkedBy(user);

        dao.updateKidFriendlyStatus(bookmark);
        System.out.println("Kid-Friendly status: " + kidFriendlyStatus + " , Marked by: " + user.getEmail() + ", " + bookmark);
    }

    public void share(User user, Bookmark bookmark) {
        bookmark.setSharedBy(user);

        System.out.println("Data to be shared : ");
        if (bookmark instanceof Book) {
            System.out.println(((Book) bookmark).getItemData());
        } else if (bookmark instanceof Weblink) {
            System.out.println(((Weblink) bookmark).getItemData());
        }
        dao.sharedByInfo(bookmark);
    }
}