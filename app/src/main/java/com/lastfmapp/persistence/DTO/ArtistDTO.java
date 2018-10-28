package com.lastfmapp.persistence.DTO;

public class ArtistDTO {

    private String name;
    private String listeners;
    private String playCount;
    private String mbid;
    private String image;

    public ArtistDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getListeners() {
        return listeners;
    }

    public void setListeners(String listeners) {
        this.listeners = listeners;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
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


    @Override
    public String toString() {
        return "ArtistDTO{" +
                "name='" + name + '\'' +
                ", playCount='" + listeners + '\'' +
                ", playcount='" + playCount + '\'' +
                ", mbid='" + mbid + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
