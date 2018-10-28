package com.lastfmapp.persistence.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lastfmapp.persistence.DTO.ArtistDTO;
import com.lastfmapp.persistence.AdminSQLiteOpenHelper;
import com.lastfmapp.persistence.TablesHelper;

import java.util.LinkedList;
import java.util.List;

public class ArtistDAO extends GenericDAO<ArtistDTO> {

    private ArtistDTO artistDTO;
    private AdminSQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;

    public ArtistDAO(Context context) {
        dbHelper = new AdminSQLiteOpenHelper(context);

    }

    @Override
    public boolean insert(ArtistDTO t) {
        db = dbHelper.getWritableDatabase();
        boolean result = false;
        try {
            ContentValues values = new ContentValues();
            values.put("mbid", t.getMbid());
            values.put("name", t.getName());
            values.put("listeners", t.getListeners());
            values.put("playCount", t.getPlayCount());
            values.put("image", t.getImage());
            long id = db.insert(TablesHelper.ARTIST_TABLE, null, values);

            if (id != -1 ) {
                System.out.println("id creado " + id );
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
    public List<ArtistDTO> findAll() {
        return findBy(null);
    }

    @Override
    public List<ArtistDTO> findByName(String s) {
        return findBy(s);
    }

    public List<ArtistDTO> findLikeName (String s) {
        db = dbHelper.getWritableDatabase();
        System.out.println("find all");
        List<ArtistDTO> list = null;
        Cursor row = null;
        try {
            list = new LinkedList<>();
            String q = "SELECT * FROM " + TablesHelper.ARTIST_TABLE +
                    ((s != null) ? " WHERE name like '%"+s+"%'" : "") +
                    ";";

            System.out.println("query " + q);
            row = db.rawQuery(q, null);
            while (row.moveToNext()) {
                ArtistDTO a = new ArtistDTO();
                a.setImage(row.getString(row.getColumnIndex("image")));
                a.setListeners(row.getString(row.getColumnIndex("listeners")));
                a.setMbid(row.getString(row.getColumnIndex("mbid")));
                a.setName(row.getString(row.getColumnIndex("name")));
                a.setPlayCount(row.getString(row.getColumnIndex("playCount")));
                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            row.close();
            db.close();
        }
        return list;
    }


    private List<ArtistDTO> findBy (String s) {
        db = dbHelper.getWritableDatabase();
        System.out.println("find all");
        List<ArtistDTO> list = null;
        Cursor row = null;
        try {
            list = new LinkedList<>();
            String q = "SELECT * FROM " + TablesHelper.ARTIST_TABLE +
                    ((s != null) ? " WHERE name = '"+s+"'" : "") +
                    ";";

            System.out.println("query " + q);
            row = db.rawQuery(q, null);
            while (row.moveToNext()) {
                ArtistDTO a = new ArtistDTO();
                a.setImage(row.getString(row.getColumnIndex("image")));
                a.setListeners(row.getString(row.getColumnIndex("listeners")));
                a.setMbid(row.getString(row.getColumnIndex("mbid")));
                a.setName(row.getString(row.getColumnIndex("name")));
                a.setPlayCount(row.getString(row.getColumnIndex("playCount")));
                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            row.close();
            db.close();
        }
        return list;
    }
}
