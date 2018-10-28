package com.lastfmapp.lastFMService;

import com.lastfmapp.persistence.DTO.AlbumDTO;
import com.lastfmapp.persistence.DTO.ArtistDTO;
import com.lastfmapp.persistence.DTO.TrackDTO;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JsonToJavaModel {


    public static List toJava (String method, String s) {
        List list = null;

        switch (method) {
            case LastFMService.ALBUM_GET_INFO:
                list = toTracks(s);
                break;
            case LastFMService.ARTIST_GET_TOP_ALBUMS:
                list = toAlbums(s);
                break;
            case LastFMService.ARTIST_SEARCH:
                list = toArtistSearch(s);
                break;
            case LastFMService.CHART_GET_TOP_ARTIST:
                list = toArtists(s);
                break;
            case LastFMService.ALBUM_SEARCH:
                list = toAlbumSearch(s);
                break;
            case LastFMService.TRACK_SEARCH:
                list = toTrackSearch(s);
                break;
            default:
                System.err.println("no case for  : " + method);
                break;
        }


        return list;
    }

    private static List<TrackDTO> toTrackSearch (String s) {
        List<TrackDTO> list = null;

        try {
            list = new LinkedList<>();
            JSONObject root = new JSONObject(s);
            JSONObject n1 = root.getJSONObject("results");
            JSONArray n2 = n1.getJSONObject("trackmatches").getJSONArray("track");
            for (int i = 0; i<n2.length(); i++) {
                JSONObject n3 = n2.getJSONObject(i);
                TrackDTO trackDTO = new TrackDTO();
                trackDTO.setName(n3.getString("name"));
                trackDTO.setArtistName(n3.getString("artist"));
                JSONArray n4 = n3.getJSONArray("image");
                trackDTO.setImage(n4.getJSONObject(2).getString("#text"));
                list.add(trackDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private static List<AlbumDTO> toAlbumSearch (String s) {
        List<AlbumDTO> list = null;

        try {
            list = new LinkedList<>();
            JSONObject root = new JSONObject(s);
            JSONObject n1 = root.getJSONObject("results");
            JSONArray n2 = n1.getJSONObject("albummatches").getJSONArray("album");
            for (int i = 0; i<n2.length(); i++) {
                AlbumDTO albumDTO = new AlbumDTO();
                JSONObject n3 = n2.getJSONObject(i);
                if (n3.has("mbid")) {
                    albumDTO.setMbid(n3.getString("mbid"));
                    albumDTO.setName(n3.getString("name"));
                    albumDTO.setArtistName(n3.getString("artist"));
                    JSONArray n4 = n3.getJSONArray("image");
                    albumDTO.setImage(n4.getJSONObject(2).getString("#text"));


                    list.add(albumDTO);
                }
            }


            System.out.println(s);

        } catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

    private static List<ArtistDTO> toArtistSearch (String s) {
        List<ArtistDTO> list = null;

        try {
            list = new LinkedList<>();
            JSONObject root = new JSONObject(s);
            JSONObject n1 = root.getJSONObject("results");
            JSONArray n2 = n1.getJSONObject("artistmatches").getJSONArray("artist");
            for (int i = 0; i<n2.length(); i++) {
                ArtistDTO artist = new ArtistDTO();
                JSONObject n3 = n2.getJSONObject(i);
                artist.setName(n3.getString("name"));
                artist.setListeners(n3.getString("listeners"));
                artist.setMbid(n3.getString("mbid"));

                JSONArray n4 = n3.getJSONArray("image");

                artist.setImage(n4.getJSONObject(2).getString("#text"));

                list.add(artist);
            }

            System.out.println(s);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static List<TrackDTO> toTracks (String s) {
        List<TrackDTO> list = null;

        try {
            list = new LinkedList<>();
            JSONObject root = new JSONObject(s);
            JSONObject n1 = root.getJSONObject("album");
            JSONObject n2 = n1.getJSONObject("tracks");
            JSONArray n3 = n2.getJSONArray("track");
            for (int i = 0; i<n3.length(); i++) {
                TrackDTO trackDTO = new TrackDTO();
                JSONObject n4 = n3.getJSONObject(i);

                trackDTO.setName(n4.getString("name"));
                trackDTO.setDuration(n4.getString("duration"));
                trackDTO.setAlbumName(n1.getString("name"));
                trackDTO.setArtistName(n1.getString("artist"));
                trackDTO.setRank(n4.getJSONObject("@attr").getString("rank"));
                list.add(trackDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }



    private static List<ArtistDTO> toArtists (String s) {
        List<ArtistDTO> list = null;

        try {
            list = new LinkedList<>();
            JSONObject root = new JSONObject(s);
            JSONObject n1 = root.getJSONObject("artists");
            JSONArray n2 = n1.getJSONArray("artist");
            for (int i = 0; i<n2.length(); i++) {
                ArtistDTO artist = new ArtistDTO();
                JSONObject n3 = n2.getJSONObject(i);
                artist.setName(n3.getString("name"));
                artist.setListeners(n3.getString("listeners"));
                artist.setPlayCount(n3.getString("playcount"));
                artist.setMbid(n3.getString("mbid"));

                JSONArray n4 = n3.getJSONArray("image");

                artist.setImage(n4.getJSONObject(2).getString("#text"));

                list.add(artist);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private static List<AlbumDTO> toAlbums (String s) {
        List<AlbumDTO> list = null;
        try {
            list = new LinkedList<>();
            JSONObject root = new JSONObject(s);
            JSONObject n1 = root.getJSONObject("topalbums");
            JSONArray n2 = n1.getJSONArray("album");
            for (int i = 0; i<n2.length(); i++) {
                AlbumDTO albumDTO = new AlbumDTO();
                JSONObject n3 = n2.getJSONObject(i);
                if (n3.has("mbid")) {
                    albumDTO.setMbid(n3.getString("mbid"));
                    albumDTO.setName(n3.getString("name"));
                    albumDTO.setPlayCount(n3.getString("playcount"));
                    JSONArray n4 = n3.getJSONArray("image");
                    albumDTO.setImage(n4.getJSONObject(2).getString("#text"));
                    JSONObject n4_2 = n3.getJSONObject("artist");
                    albumDTO.setArtistMbid(n4_2.getString("mbid"));
                    albumDTO.setArtistName(n4_2.getString("name"));
                    list.add(albumDTO);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


}
