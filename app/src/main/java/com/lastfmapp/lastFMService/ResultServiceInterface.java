package com.lastfmapp.lastFMService;



import java.util.List;

public interface ResultServiceInterface<T> {

    void onResult(boolean state, List<T> list);

    void onWait();

}
