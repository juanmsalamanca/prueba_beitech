package com.lastfmapp.lastFMService;

import android.os.AsyncTask;
import android.util.Log;


import com.lastfmapp.lastFMService.DTO.WebResponseDTO;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;

public class Service {

    private final String TAG = "SERVICE_lastFM";
    private static final String DEFAULT_API_ROOT = "https://ws.audioscrobbler.com/2.0/";

    private static Service service = null;

    public static Service getInstance () {
        if (service == null) {
            service = new Service();
        }
        return service;
    }


    public void execute (LinkedHashMap<String, String> params, ServiceInterface serviceInterface) {
        Caller caller  = new Caller();
        caller.response(serviceInterface);
        caller.execute(params);

    }

    private Service () {

    }


    private class Caller extends AsyncTask<LinkedHashMap<String, String>, Void, WebResponseDTO> {

        private ServiceInterface serviceInterface = null;

        public Caller() {
        }

        public void response (ServiceInterface serviceInterface) {
            this.serviceInterface = serviceInterface;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            serviceInterface.servicePreExecute();
        }

        @Override
        protected WebResponseDTO doInBackground(LinkedHashMap<String, String>... p) {
            LinkedHashMap<String, String> params = p[0];

            Log.v(TAG, params.size() + "");

            return send(DEFAULT_API_ROOT, params);
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected void onPostExecute(WebResponseDTO response) {
            super.onPostExecute(response);
            serviceInterface.servicePostExecute(response);
        }
    }




    private WebResponseDTO send (final String URL_, final LinkedHashMap<String, String> params) {
        String result = "";
        WebResponseDTO responseDTO = new WebResponseDTO();
        try {
            URL url = new URL(URL_);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            try {

                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(3000);


                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(params));
                writer.flush();
                writer.close();
                os.close();

                connection.connect();

                Log.e("response code", connection.getResponseCode()+"");
                Log.e("response message", connection.getResponseMessage());
                int code = connection.getResponseCode();
                InputStream in;

                responseDTO.setCode(code);
                if (code == 200) {
                    in = new BufferedInputStream(connection.getInputStream());
                } else {
                    in = new BufferedInputStream(connection.getErrorStream());
                }


                BufferedReader r = new BufferedReader(new InputStreamReader(in));

                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line).append('\n');
                }
                result = total.toString();

                responseDTO.setResponse(result);

                Log.v("conn", result);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseDTO;
    }



    private String getQuery(LinkedHashMap<String, String> params) {
        StringBuilder sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0){
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), "UTF-8"));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }

        System.err.println("query " + sbParams);

        return sbParams.toString();
    }


    private  final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
