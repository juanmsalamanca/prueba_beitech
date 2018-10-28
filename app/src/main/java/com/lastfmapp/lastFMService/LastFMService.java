package com.lastfmapp.lastFMService;

import com.lastfmapp.persistence.DTO.AlbumDTO;
import com.lastfmapp.persistence.DTO.ArtistDTO;
import com.lastfmapp.lastFMService.DTO.WebResponseDTO;
import com.lastfmapp.persistence.DTO.TrackDTO;

import java.util.LinkedHashMap;

public class LastFMService {

    private final String API_KEY = "75dc490f6f7042bf983da1a8f6a1a333";

    private final String TAG = "LastFMService";

    private Service service;

    private static LastFMService lastFMService;

    private static final String LIMIT = "20";


    public static final String ARTIST_SEARCH = "artist.search";
    public static final String ALBUM_SEARCH = "album.search";
    public static final String TRACK_SEARCH = "track.search";
    public static final String CHART_GET_TOP_ARTIST = "chart.getTopArtists";
    public static final String ARTIST_GET_TOP_ALBUMS = "artist.getTopAlbums";
    public static final String ALBUM_GET_INFO = "album.getInfo";

    public static LastFMService getInstance () {
        if (lastFMService == null) {
            lastFMService = new LastFMService();
        }
        return  lastFMService;
    }

    private LastFMService() {
        service = Service.getInstance();
    }

    public void getTopTags(ServiceInterface serviceInterface){
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("method", "chart.getTopTags");
        params.put("api_key", API_KEY);
        params.put("format", "json");
        service.execute(params, serviceInterface);
    }

    public void getTrackSearch(String text, final ResultServiceInterface resultServiceInterface) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("method", TRACK_SEARCH);
        params.put("track", text);
        params.put("limit", LIMIT);
        params.put("api_key", API_KEY);
        params.put("format", "json");
        exec(TRACK_SEARCH, params, resultServiceInterface);
    }

    public void getAlbumSearch (String text, final ResultServiceInterface resultServiceInterface) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("method", ALBUM_SEARCH);
        params.put("album", text);
        params.put("limit", LIMIT);
        params.put("api_key", API_KEY);
        params.put("format", "json");
        exec(ALBUM_SEARCH, params, resultServiceInterface);
    }

    public void getArtistSearch (String text, final ResultServiceInterface resultServiceInterface) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("method", ARTIST_SEARCH);
        params.put("artist", text);
        params.put("limit", LIMIT);
        params.put("api_key", API_KEY);
        params.put("format", "json");
        exec(ARTIST_SEARCH, params, resultServiceInterface);
    }


    public void getChartGetTopArtists(final ResultServiceInterface resultServiceInterface) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("method", CHART_GET_TOP_ARTIST);
        params.put("limit", LIMIT);
        params.put("api_key", API_KEY);
        params.put("format", "json");
        exec(CHART_GET_TOP_ARTIST, params, resultServiceInterface);
    }

    public void getArtistGetTopAlbums (String name,String mbid, final ResultServiceInterface resultServiceInterface) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("method", ARTIST_GET_TOP_ALBUMS);
        params.put("limit", LIMIT);
        if (name != null) {
            params.put("artist", name);
        }
        if (mbid != null) {
            params.put("mbid", mbid);
        }
        params.put("autocorrect", "1");
        params.put("api_key", API_KEY);
        params.put("format", "json");

        exec(ARTIST_GET_TOP_ALBUMS, params, resultServiceInterface);
    }

    public void getAlbumGetInfo(String artistName, String albumName, final ResultServiceInterface resultServiceInterface){
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("method", ALBUM_GET_INFO);
        params.put("artist", artistName);
        params.put("album", albumName);
        params.put("autocorrect", "1");
        params.put("api_key", API_KEY);
        params.put("format", "json");
        exec(ALBUM_GET_INFO, params, resultServiceInterface);
    }


    private void exec (final String method, LinkedHashMap<String, String> p, final ResultServiceInterface callback) {
        service.execute(p, new ServiceInterface() {
            @Override
            public void servicePostExecute(WebResponseDTO responseDTO) {
                if (responseDTO.getCode() == 200) {
                    callback.onResult(true, JsonToJavaModel.toJava(method, responseDTO.getResponse()));
                } else {
                    callback.onResult(false, null);
                }
            }
            @Override
            public void servicePreExecute() {
                callback.onWait();
            }
        });


    }




}
