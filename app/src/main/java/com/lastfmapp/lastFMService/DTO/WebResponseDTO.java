package com.lastfmapp.lastFMService.DTO;

public class WebResponseDTO {

    private int code;
    private String response;

    public WebResponseDTO() {
        code = 0;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "WebResponseDTO{" +
                "code=" + code +
                ", response='" + response + '\'' +
                '}';
    }
}
