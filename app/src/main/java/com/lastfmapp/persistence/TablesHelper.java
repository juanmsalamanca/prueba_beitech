package com.lastfmapp.persistence;

public class TablesHelper {

    public static final String DATABASE_NAME = "lastFM.db";
    public static final int DATABASE_VERSION = 2;

    // ARTIST
    public static final String ARTIST_TABLE = "Artist";
    public static final String CREATE_ARTIST = "create table Artist(id integer primary key, mbid text, name text unique, listeners text, playCount text, image text," +
            "UNIQUE (mbid) ON CONFLICT IGNORE)";
    public static final String DROP_ARTIST = "drop table if exists Artist";

    //ALBUM
    public static final String ALBUM_TABLE = "Album";
    public static final String CREATE_ALBUM = "create table Album(id integer primary key, mbid text, name text, playCount text, image text, artistMbid text,  artistName text," +
            "UNIQUE (mbid) ON CONFLICT IGNORE)";
    public static final String DROP_ALBUM = "drop table if exists Album";

    //TRACK
    public static final String TRACK_TABLE = "Track";
    public static final String CREATE_TRACK = "create table Track(id integer primary key, name text, duration text, rank text, artistName text, albumName text, " +
            "UNIQUE (name, albumName) ON CONFLICT IGNORE );";
    public static final String DROP_TRACK = "drop table if exists Track";

}
