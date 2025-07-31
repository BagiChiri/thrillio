package com.semanticsquare.thrillio.entities;

import com.semanticsquare.thrillio.partner.Shareable;
import com.semanticsquare.thrillio.constants.BookGenre;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class Book extends Bookmark implements Shareable {
    private int publishYear;
    private String publisher;
    private String[] authors;
    private BookGenre genre;
    private double amazonRating;

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public BookGenre getGenre() {
        return genre;
    }

    public void setGenre(BookGenre genre) {
        this.genre = genre;
    }

    public double getAmazonRating() {
        return amazonRating;
    }

    public void setAmazonRating(double amazonRating) {
        this.amazonRating = amazonRating;
    }

    @Override
    public String toString() {
        return "Book{" +
                "publishYear=" + publishYear +
                ", publisher='" + publisher + '\'' +
                ", authors=" + Arrays.toString(authors) +
                ", genre='" + genre + '\'' +
                ", amazonRating=" + amazonRating +
                '}';
    }

    @Override
    public boolean isKidFriendlyEligible() {
        if ((genre.equals(BookGenre.PHILOSOPHY)) || (genre.equals(BookGenre.SELF_HELP))) {
            return false;
        }
        return true;
    }
    @Override
    public String getItemData() {
        StringBuilder builder = new StringBuilder();

        builder.append("<item>");
            builder.append("<type>Book</type>");
            builder.append("<title>").append(getTitle()).append("</title>");
            builder.append("<authors>").append(StringUtils.join(authors, ",")).append("</authors>");
            builder.append("<publisher>").append(publisher).append("</publisher>");
            builder.append("<publishYear>").append(publishYear).append("</publishYear>");
            builder.append("<genre>").append(genre).append("</genre>");
            builder.append("<amazonRating>").append(amazonRating).append("</amazonRating>");
        builder.append("</item>");
        return builder.toString();
    }
}
