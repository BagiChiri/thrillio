package com.semanticsquare.thrillio;

import com.semanticsquare.thrillio.constants.KidFriendlyStatus;
import com.semanticsquare.thrillio.controllers.BookmarkController;
import com.semanticsquare.thrillio.partner.Shareable;
import com.semanticsquare.thrillio.constants.UserType;
import com.semanticsquare.thrillio.entities.Bookmark;
import com.semanticsquare.thrillio.entities.User;

import java.util.List;

public class View {
    private static boolean getBookmarkDecision(Bookmark bookmark) {
        return Math.random() < 0.5 ? true : false;
    }

    public static void browse(User user, List<List<Bookmark>> bookmarks) {
        System.out.println("\n" + user.getEmail() + ": is browsing items ...");
        int bookmarkCount = 0;

        for (List<Bookmark> bookmarkList :
                bookmarks) {
            for (Bookmark bookmark :
                    bookmarkList) {
                //Bookmarking ...
//                if (bookmarkCount < DataStore.USER_BOOKMARK_LIMIT) {
                    boolean isBookmarked = getBookmarkDecision(bookmark);

                    if (isBookmarked) {
                        bookmarkCount++;

                        BookmarkController.getInstance().saveUserBookmark(user, bookmark);
                        System.out.println("New item bookmarked --" + bookmark);
                    }
//                }
                if (user.getUserType().equals(UserType.EDITOR) || user.getUserType().equals(UserType.CHIEF_EDITOR)) {

                    //                Mark as Kid-Friendly
                      if (bookmark.isKidFriendlyEligible() || bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN)) {
                        KidFriendlyStatus kidFriendlyStatus = getKidFriendlyStatusDecision(bookmark);
                        if (!kidFriendlyStatus.equals(KidFriendlyStatus.UNKNOWN)) {
                            BookmarkController.getInstance().setKidFriendlyStatus(user, kidFriendlyStatus, bookmark);
                        }
                    }

                    //              Sharing
                    if (bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.APPROVED)
                            && bookmark instanceof Shareable) {
                        boolean isShared = getShareDecision();
                        if (isShared) {
                            BookmarkController.getInstance().share(user, bookmark);
                        }
                    }
                }
            }
        }
    }
//  TODO:  Below methods simulate user input. After IO, we take input via console
    private static boolean getShareDecision() {
        return Math.random() <  0.5 ? true : false;
    }

    private static KidFriendlyStatus getKidFriendlyStatusDecision(Bookmark bookmark) {
        double decision = Math.random();
        return decision < 0.4 ? KidFriendlyStatus.APPROVED :
                (decision >= 0.4 && decision < 0.8) ? KidFriendlyStatus.REJECTED : KidFriendlyStatus.UNKNOWN;
    }
   /* public static void bookmarks(User user, Bookmark[][] bookmarks) {
        System.out.println("\n" + user.getEmail() + ": Bookmarking ...");
        for (int i = 0; i < DataStore.USER_LIMIT; i++) {
            int typeOffSet = (int) (Math.random() * DataStore.BOOKMARK_TYPES_COUNT);
            int bookmarkOffSet = (int) (Math.random() * DataStore.BOOKMARK_COUNT_PER_TYPE);

            Bookmark bookmark = bookmarks[typeOffSet][bookmarkOffSet];
            BookmarkController.getInstance().saveUserBookmark(user, bookmark);
            System.out.println(bookmark);
        }
    }*/
}
