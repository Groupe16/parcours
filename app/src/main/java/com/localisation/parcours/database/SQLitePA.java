package com.localisation.parcours.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.localisation.parcours.model.Coord;
import com.localisation.parcours.model.PtMarquage;
import com.localisation.parcours.model.PAWifi;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zalila on 2015-03-07.
 */
public class SQLitePA extends SQLiteOpenHelper {

    public SQLitePA(Context context) {
        super(context, SQLiteTrajet.DATABASE_NAME, null, SQLiteTrajet.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_PA_TABLE = "CREATE TABLE point_access ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pa TEXT, "+
                "bssid TEXT, "+
                "ssid TEXT, "+
                "rss TEXT, "+
                "id_point INTEGER, "+
                "FOREIGN KEY(id_point) REFERENCES point_marquage(id) " +
                "ON UPDATE CASCADE ON DELETE CASCADE)";

        db.execSQL(CREATE_PA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS point_access");
        this.onCreate(db);
    }

    private static final String TABLE_PA = "point_access";

    private static final String KEY_ID = "id";
    private static final String KEY_PA = "pa";
    private static final String KEY_BSSID = "bssid";
    private static final String KEY_SSID = "ssid";
    private static final String KEY_RSS = "rss";
    private static final String KEY_ID_POINT = "id_point";

    private static final String[] COLUMNS = {KEY_ID,KEY_PA,KEY_BSSID,KEY_SSID,KEY_RSS,KEY_ID_POINT};

    public void addPA(PAWifi pa_wifi, int idPoint){
        Log.d("addPAWifi()", pa_wifi.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PA, pa_wifi.getPa());
        values.put(KEY_BSSID, pa_wifi.getBssid());
        values.put(KEY_SSID, pa_wifi.getSsid());
        values.put(KEY_RSS, pa_wifi.getRss());
        values.put(KEY_ID_POINT, idPoint);

        db.insert(TABLE_PA, null, values);

        db.close();
    }

    public PAWifi getPA(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_PA, COLUMNS, " id = ?", new String[] { String.valueOf(id) },
                        null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        PAWifi pa_wifi = new PAWifi();
        pa_wifi.setId(Integer.parseInt(cursor.getString(0)));
        pa_wifi.setPa(cursor.getString(1));
        pa_wifi.setBssid(cursor.getString(2));
        pa_wifi.setSsid(cursor.getString(3));
        pa_wifi.setRss(cursor.getString(4));

        Log.d("getPAWifi("+id+")", pa_wifi.toString());

        return pa_wifi;
    }

    public List<PAWifi> getAllPAs(PtMarquage point) {
        List<PAWifi> pas_wifi = new LinkedList<>();

        String query = "SELECT * FROM " + TABLE_PA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        PAWifi pa_wifi = null;
        if (cursor.moveToFirst()) {
            do {
                if (point.getId() == Integer.parseInt(cursor.getString(5))) {
                    pa_wifi = new PAWifi();
                    pa_wifi.setId(Integer.parseInt(cursor.getString(0)));
                    pa_wifi.setPa(cursor.getString(1));
                    pa_wifi.setBssid(cursor.getString(2));
                    pa_wifi.setSsid(cursor.getString(3));
                    pa_wifi.setRss(cursor.getString(4));

                    pas_wifi.add(pa_wifi);
                }
            } while (cursor.moveToNext());
        }

        Log.d("getAllPAWifis()", pas_wifi.toString());

        return pas_wifi;
    }

    public int updatePA(PAWifi pa_wifi, int idPoint) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PA, pa_wifi.getPa());
        values.put(KEY_BSSID, pa_wifi.getBssid());
        values.put(KEY_SSID, pa_wifi.getSsid());
        values.put(KEY_RSS, pa_wifi.getRss());
        values.put(KEY_ID_POINT, idPoint);

        int i = db.update(TABLE_PA, values, KEY_ID+" = ?",
                new String[] { String.valueOf(pa_wifi.getId()) });

        Log.d("updatePAWifi()", pa_wifi.toString());
        db.close();
        return i;
    }

    public void deletePA(PAWifi pa_wifi) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PA, KEY_ID+" = ?",
                new String[] { String.valueOf(pa_wifi.getId()) });

        db.close();
        Log.d("deletePAWifi", pa_wifi.toString());

    }
}