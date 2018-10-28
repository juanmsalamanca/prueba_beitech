package com.lastfmapp.persistence.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lastfmapp.persistence.AdminSQLiteOpenHelper;
import com.lastfmapp.persistence.DTO.AlbumDTO;
import com.lastfmapp.persistence.DTO.TrackDTO;
import com.lastfmapp.persistence.TablesHelper;

import java.util.LinkedList;
import java.util.List;

public class TrackDAO extends GenericDAO<TrackDTO> {


    private AdminSQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;

    private final String TAG = "TRACK_DAO";

    public TrackDAO(Context context) {
        dbHelper = new AdminSQLiteOpenHelper(context);
    }

    @Override
    public boolean insert(TrackDTO t) {
        db = dbHelper.getWritableDatabase();
        Log.v(TAG, "insert track" + t.getName());
        boolean result = false;
        try {
            ContentValues values = new ContentValues();
            values.put("name", t.getName());
            values.put("rank", t.getRank());
            values.put("duration", t.getDuration());
            values.put("albumName", t.getAlbumName());
            values.put("artistName", t.getArtistName());
            long id = db.insert(TablesHelper.TRACK_TABLE, null, values);

            if (id != -1 ) {
                Log.v(TAG, "insert success id:" + id);
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return result;
    }

    public List<TrackDTO> findByArtistAlbum (String artist, String album) {
        List<TrackDTO> list = null;
        Log.v(TAG,"find by artist and name " + artist + " " + album);
        db = dbHelper.getWritableDatabase();
        Cursor row = null;
        try {
            list = new LinkedList<>();
            String q =
                    "SELECT * FROM " + TablesHelper.TRACK_TABLE + " WHERE artistName = '"+artist+"' and albumName = '"+album+"'";

            Log.v(TAG,"Query : " + q);
            row = db.rawQuery(q, null);

            while (row.moveToNext()) {
                TrackDTO a = new TrackDTO();
                a.setName(row.getString(row.getColumnIndex("name")));
                a.setRank(row.getString(row.getColumnIndex("rank")));
                a.setDuration(row.getString(row.getColumnIndex("duration")));
                a.setArtistName(row.getString(row.getColumnIndex("artistName")));
                a.setAlbumName(row.getString(row.getColumnIndex("albumName")));
                list.add(a);
            }
            System.out.println(list);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;
    }

    @Override
    public List<TrackDTO> findAll() {
        return null;
    }

    @Override
    public List<TrackDTO> findByName(String s) {
        return null;
    }
}
