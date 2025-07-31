package com.semanticsquare.thrillio.entities;

import com.semanticsquare.thrillio.managers.BookmarkManager;

import static org.junit.Assert.*;

public class WeblinkTest {

    @org.junit.Test
    public void isKidFriendlyEligible() {

        //Test 1: porn in url -- false
        Weblink weblink = BookmarkManager.getInstance().createWeblink(2000, "Taming Tiger, Part 2", "", ":http://www.javaworld.com/article/2072759/core-java/taming-porn--part-2.html", "http://www.javaworld.com");
        boolean isKidFriendlyEligible = weblink.isKidFriendlyEligible();
        assertFalse("for porn in url -- isKidFrindlyEligible must return false", isKidFriendlyEligible);

        //Test 2: porn in title -- false
        weblink = BookmarkManager.getInstance().createWeblink(2000, "Taming porn, Part 2", "", ":http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html", "http://www.javaworld.com");
        isKidFriendlyEligible = weblink.isKidFriendlyEligible();
        assertFalse("for porn in title -- isKidFrindlyEligible must return false", isKidFriendlyEligible);

        //Test 3: adult in host -- false
        weblink = BookmarkManager.getInstance().createWeblink(2000, "Taming tiger, Part 2", "", ":http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html", "http://www.adult.com");
        isKidFriendlyEligible = weblink.isKidFriendlyEligible();
        assertFalse("for adult in host -- isKidFrindlyEligible must return false", isKidFriendlyEligible);

        //Test 4: adult in url, but not in host part -- true
        weblink = BookmarkManager.getInstance().createWeblink(2000, "Taming tiger, Part 2", "", ":http://www.javaworld.com/article/2072759/core-java/taming-adult--part-2.html", "http://www.javaworld.com");
        isKidFriendlyEligible = weblink.isKidFriendlyEligible();
        assertTrue("for adult in url, but not in host part -- isKidFriendlyEligible must return true", isKidFriendlyEligible);

        //Test 5: adult in title only -- true
        weblink = BookmarkManager.getInstance().createWeblink(2000, "Taming adult, Part 2", "", ":http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html", "http://www.javaworld.com");
        isKidFriendlyEligible = weblink.isKidFriendlyEligible();
        assertTrue("for adult in title only -- isKidFriendlyEligible must return true", isKidFriendlyEligible);
    }
}