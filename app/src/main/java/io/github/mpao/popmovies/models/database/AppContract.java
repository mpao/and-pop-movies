package io.github.mpao.popmovies.models.database;

import android.net.Uri;

public final class AppContract {

    /*
     * Content provider constant and URIs definition
     */
    public static final String AUTHORITY     = "io.github.mpao.popmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIES   = "favs"; //todo keep track for all the movies, not just the favs

    private AppContract(){}

    /**
     * Contract for db, i don't need to implement BaseColumns because I dont't need the _ID column.
     * In fact, the movie's id attribute is the unique identifier for my data
     */
    public static class AppContractElement {

        // define the content uri for the table
        // for a single element, append che # to identify the integer movie's ID
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String TABLE_NAME        = "favs";
        public static final String VOTE_COUNT        = "vote_count";
        public static final String ID                = "id";
        public static final String VIDEO             = "video";
        public static final String VOTE_AVERAGE      = "vote_average";
        public static final String TITLE             = "title";
        public static final String POPULARITY        = "popularity";
        public static final String POSTER_PATH       = "poster_path";
        public static final String ORIGINAL_LANGUAGE = "original_language";
        public static final String ORIGINAL_TITLE    = "original_title";
        public static final String GENRE_IDS         = "genre_ids";
        public static final String BACKDROP_PATH     = "backdrop_pah";
        public static final String ADULT             = "adult";
        public static final String OVERVIEW          = "overview";
        public static final String RELEASE_DATE      = "release_date";

    }

    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AppContractElement.TABLE_NAME + " (" +
                    AppContractElement.VOTE_COUNT        + " INTEGER, " +
                    AppContractElement.ID                + " INTEGER PRIMARY KEY, " +
                    AppContractElement.VIDEO             + " INTEGER DEFAULT 0, " +
                    AppContractElement.VOTE_AVERAGE      + " REAL NOT NULL, " +
                    AppContractElement.TITLE             + " TEXT NOT NULL, " +
                    AppContractElement.POPULARITY        + " REAL, " +
                    AppContractElement.POSTER_PATH       + " TEXT NOT NULL, " +
                    AppContractElement.ORIGINAL_LANGUAGE + " TEXT, " +
                    AppContractElement.ORIGINAL_TITLE    + " TEXT, " +
                    AppContractElement.GENRE_IDS         + " TEXT, " +
                    AppContractElement.BACKDROP_PATH     + " TEXT, " +
                    AppContractElement.ADULT             + " INTEGER DEFAULT 0, " +
                    AppContractElement.OVERVIEW          + " TEXT NOT NULL, " +
                    AppContractElement.RELEASE_DATE      + " TEXT NOT NULL " +
                    ")";

    protected static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + AppContractElement.TABLE_NAME;

}