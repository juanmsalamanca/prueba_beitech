package com.lastfmapp.persistence.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lastfmapp.persistence.AdminSQLiteOpenHelper;
import com.lastfmapp.persistence.DTO.AlbumDTO;
import com.lastfmapp.persistence.DTO.ArtistDTO;
import com.lastfmapp.persistence.TablesHelper;

import java.util.LinkedList;
import java.util.List;


public class AlbumDAO extends GenericDAO<AlbumDTO>  {


    private AlbumDTO albumDTO;
    private AdminSQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;

    private final String TAG = "ALBUM_DAO";

    public AlbumDAO(Context context) {
        dbHelper = new AdminSQLiteOpenHelper(context);

    }


    @Override
    public boolean insert(AlbumDTO t) {
        db = dbHelper.getWritableDatabase();
        Log.v(TAG, "insert album" + t.getName());
        boolean result = false;
        try {
            ContentValues values = new ContentValues();
            values.put("mbid", t.getMbid());
            values.put("name", t.getName());
            values.put("playCount", t.getPlayCount());
            values.put("image", t.getImage());
            values.put("artistMbid", t.getArtistMbid());
            values.put("artistName", t.getArtistName());
            long id = db.insert(TablesHelper.ALBUM_TABLE, null, values);

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

    @Override
    public List<AlbumDTO> findAll() {
        return null;
    }

    @Override
    public List<AlbumDTO> findByName(String s) {
        Log.v(TAG,"find by name " + s);
        db = dbHelper.getWritableDatabase();
        List<AlbumDTO> list = null;
        Cursor row = null;
        try {
            list = new LinkedList<>();
            String q =
                    "SELECT * FROM " + TablesHelper.ALBUM_TABLE +
                            ((s != null) ? " WHERE name = '"+s+"'" : "") +
                            ";";

            Log.v(TAG,"Query : " + q);
            row = db.rawQuery(q, null);

            while (row.moveToNext()) {
                AlbumDTO a = new AlbumDTO();
                a.setImage(row.getString(row.getColumnIndex("image")));
                a.setMbid(row.getString(row.getColumnIndex("mbid")));
                a.setName(row.getString(row.getColumnIndex("name")));
                a.setArtistName(row.getString(row.getColumnIndex("artistName")));
                a.setPlayCount(row.getString(row.getColumnIndex("playCount")));
                list.add(a);
            }
            System.out.println(list);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            row.close();
            db.close();
        }

        return list;
    }

    public List<AlbumDTO> findByArtistName(String s) {
        Log.v(TAG,"find by name " + s);
        db = dbHelper.getWritableDatabase();
        List<AlbumDTO> list = null;
        Cursor row = null;
        try {
            list = new LinkedList<>();
            String q =
                    "SELECT * FROM " + TablesHelper.ALBUM_TABLE +
                            ((s != null) ? " WHERE artistName = '"+s+"'" : "") +
                            ";";

            Log.v(TAG,"Query : " + q);
            row = db.rawQuery(q, null);

            while (row.moveToNext()) {
                AlbumDTO a = new AlbumDTO();
                a.setImage(row.getString(row.getColumnIndex("image")));
                a.setMbid(row.getString(row.getColumnIndex("mbid")));
                a.setName(row.getString(row.getColumnIndex("name")));
                a.setArtistName(row.getString(row.getColumnIndex("artistName")));
                a.setPlayCount(row.getString(row.getColumnIndex("playCount")));
                list.add(a);
            }
            System.out.println(list);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            row.close();
            db.close();
        }

        return list;
    }
}
