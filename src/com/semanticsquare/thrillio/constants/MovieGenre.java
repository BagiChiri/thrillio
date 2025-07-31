package com.semanticsquare.thrillio.constants;

public enum MovieGenre {
    CLASSICS ("Classics"),
    DRAMA ("Drama"),
    SCIFI_AND_FANTASY ("Scifi_and_Fantasy"),
    CHILDREN_AND_FAMILY ("Children_and_Family"),
    ACTION_AND_ADVENTURE ("Action_and_Adventure"),
    THRILLERS ("Thrillers"),
    MUSICS_AND_MUSICALS ("Music_and_Musicals"),
    TELEVISION ("Television"),
    HORROR ("Horrors"),
    SPECIAL_INTEREST ("Special_Interest"),
    INDEPENDENT ("Independent"),
    SPORTS_AND_FITNESS ("Sports_and_Fitness"),
    ANIME_AND_ANIMATION ("Anime_and_Animation"),
    GAY_AND_LESBIAN ("Gay_and_Lesbian"),
    CLASSIC_MOVIE_MUSICALS ("Classic_Movie_Musicals"),
    FAITH_AND_SPIRITUALITY ("Faith_and_Spirituality"),
    FOREIGN_DRAMAS ("Foreign_Dramas"),
    TV_SHOWS ("TV_Shows"),
    DRAMAS ("Dramas"),
    ROMANTIC_MOVIES ("Romantic_Movies"),
    COMEDIES ("Comedies"),
    DOCUMENTARIES ("Documentaries"),
    FOREIGN_MOVIES ("Foreign_Movies"),
    KOREAN_MOVIES ("Korean_Movies"),
    ASIAN_MOVIES ("Asian_Movies"),
    CHINESE_MOVIES ("Chinese_Movies");
    private MovieGenre(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
