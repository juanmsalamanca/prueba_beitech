package com.lastfmapp.lastFMService;


import com.lastfmapp.lastFMService.DTO.WebResponseDTO;

public interface ServiceInterface {

    void servicePostExecute(WebResponseDTO responseDTO);
    void servicePreExecute();
}
