package com.lastfmapp.persistence.DTO;

public class AlbumDTO {
    private String name;
    private String mbid;
    private String image;
    private String playCount;
    private String artistMbid;
    private String artistName;


    public AlbumDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getArtistMbid() {
        return artistMbid;
    }

    public void setArtistMbid(String artistMbid) {
        this.artistMbid = artistMbid;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public String toString() {
        return "AlbumDTO{" +
                "name='" + name + '\'' +
                ", mbid='" + mbid + '\'' +
                ", image='" + image + '\'' +
                ", playCount='" + playCount + '\'' +
                ", artistMbid='" + artistMbid + '\'' +
                ", artistName='" + artistName + '\'' +
                '}';
    }
}
