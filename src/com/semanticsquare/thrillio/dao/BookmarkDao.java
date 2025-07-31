package com.semanticsquare.thrillio.dao;

import com.semanticsquare.thrillio.DataStore;
import com.semanticsquare.thrillio.entities.*;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookmarkDao {
    public List<List<Bookmark>> getBookmarks() {
        return DataStore.getBookmarks();
    }

    public void saveUserBookmark(UserBookmark userBookmark) {
//        DataStore.add(userBookmark);
        try (
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jid_thrillio?useSSL=false", "root", "root");
                Statement stmt = con.createStatement();
        ) {
            if (userBookmark.getBookmark() instanceof Book) {
                saveUserBook(userBookmark, stmt);
            } else if (userBookmark.getBookmark() instanceof Movie) {
                saveUserMovie(userBookmark, stmt);
            } else {
                saveUserWeblink(userBookmark, stmt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveUserWeblink(UserBookmark userBookmark, Statement stmt) throws SQLException {
        String query = "INSERT INTO User_Weblink (user_id, weblink_id) values (" + userBookmark.getUser().getId() + " , " +
                userBookmark.getBookmark().getId() + ")";
        stmt.executeUpdate(query);
    }

    private void saveUserMovie(UserBookmark userBookmark, Statement stmt) throws SQLException {
        String query = "INSERT INTO User_Movie (user_id, movie_id) values (" + userBookmark.getUser().getId() + " , " +
                userBookmark.getBookmark().getId() + ")";
        stmt.executeUpdate(query);
    }

    private void saveUserBook(UserBookmark userBookmark, Statement stmt) throws SQLException {
        String query = "INSERT INTO User_Book (user_id, book_id) values (" + userBookmark.getUser().getId() + " , " +
                userBookmark.getBookmark().getId() + ")";
        stmt.executeUpdate(query);
    }

    //In real applications, we could have SQL or Hibernate Queries
    public List<Weblink> getAllWeblinks() {
        //will be invoked by background job
        List<Weblink> result = new ArrayList<>();

        List<List<Bookmark>> bookmarks = DataStore.getBookmarks();
        List<Bookmark> allWeblinks = bookmarks.get(0);

        for (Bookmark bookmark :
                allWeblinks) {
            result.add((Weblink) bookmark);
        }

        return result;
    }

    public List<Weblink> getWeblinks(Weblink.DownloadStatus downloadStatus) {
        //will be invoked by background job
        List<Weblink> result = new ArrayList<>();
        List<Weblink> allWeblinks = getAllWeblinks();

        for (Weblink weblink :
                allWeblinks) {
            if (weblink.getDownloadStatus().equals(downloadStatus)) {
                result.add(weblink);
            }
        }
        return result;
    }

    public void updateKidFriendlyStatus(Bookmark bookmark) {
        int kidFriendlyStatus = bookmark.getKidFriendlyStatus().ordinal();
        long userId = bookmark.getKidFriendlyMarkedBy().getId();

        String tableToUpdate = "Book";
        if (bookmark instanceof Movie) {
            tableToUpdate = "Movie";
        } else if (bookmark instanceof Weblink) {
            tableToUpdate = "Weblink";
        }

        try (
                Connection con = DriverManager.getConnection("" +
                        "jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false", "root", "root");
                Statement stmt = con.createStatement();
                ) {
            String query = "UPDATE " + tableToUpdate + " set_kid_friendly_status = " + kidFriendlyStatus +
                    ", kid_friendly_marked_by = " + userId + " where id = " + bookmark.getId();
            System.out.println("query (updateKidFriendlyStatus): " + query);
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sharedByInfo(Bookmark bookmark) {
        long userId = bookmark.getSharedBy().getId();

        String tableToUpdate = "Book";
        if (bookmark instanceof Weblink) {
            tableToUpdate = "Weblink";
        }
        try (
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3308/jid_thrillio?useSSL=false" + "root" + "root");
                Statement stmt = con.createStatement();
                ) {
            String query = "UPDATE " + tableToUpdate + " SET shared_by = " + userId + " where id = " + bookmark.getId();
            System.out.println("update (updateKidFriendlyStatus): " + query);
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
