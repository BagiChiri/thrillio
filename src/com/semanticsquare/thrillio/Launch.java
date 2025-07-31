package com.semanticsquare.thrillio;

import com.semanticsquare.thrillio.bgjobs.WebpageDownloaderTask;
import com.semanticsquare.thrillio.entities.Bookmark;
import com.semanticsquare.thrillio.entities.User;
import com.semanticsquare.thrillio.managers.BookmarkManager;
import com.semanticsquare.thrillio.managers.UserManager;

import java.util.List;

public class Launch {

    private static List<User> users;
    private static List<List<Bookmark>> bookmarks;

    private static void loadData() {
        System.out.println("1: Loading Data ...");
        DataStore.loadData();

        users = UserManager.getInstance().getUsers();
        bookmarks = BookmarkManager.getInstance().getBookmarks();

        System.out.println("Printing Data ...");
        printUserData();
        printBookmarkData();

    }

    private static void printBookmarkData() {
        for (List<Bookmark> bookmarkList : bookmarks
        ) {
            for (Bookmark bookmark : bookmarkList
            ) {
                System.out.println(bookmark);
            }
        }
    }

    private static void printUserData() {
        for (User user : users
        ) {
            System.out.println(user);
        }
    }
/*

    private static void printData() {
        int usersCount = users.length;
        for (int i = 0; i < usersCount; i++) {
            System.out.println(users[i]);
        }

        int bookmarksTypeCount = bookmarks.length;
        for (int i = 0; i < bookmarksTypeCount; i++) {
            int bookmarksTypeMemberCount = bookmarks[i].length;
            for (int j = 0; j < bookmarksTypeMemberCount; j++) {
                System.out.println(bookmarks[i][j]);
            }
        }
    }
*/

    private static void start() {
//        System.out.println("\n2: Bookmarking ...");

        for (User user :
                users) {
            View.browse(user, bookmarks);
        }
    }

    public static void runDownloaderJob() {
        WebpageDownloaderTask webpageDownloaderTask = new WebpageDownloaderTask(true);
        (new Thread(webpageDownloaderTask)).start();
    }
    public static void main(String[] args) {
        loadData();
        start();

        //start background job
//        runDownloaderJob();
    }
}
