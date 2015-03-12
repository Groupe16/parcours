package com.localisation.parcours.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.localisation.parcours.model.PtMarquage;
import com.localisation.parcours.model.Trajet;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Zalila on 2015-03-07.
 */
public class SQLiteTrajet extends SQLiteOpenHelper {

    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "trackingdb";

    Context context;
    public SQLiteTrajet(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TRAJET_TABLE = "CREATE TABLE trajet ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date TEXT, "+
                "frequence TEXT, "+
                "mod_loc INTEGER, "+
                "niv_init_batt INTEGER, "+
                "niv_fin_batt INTEGER, "+
                "zoom INTEGER, "+
                "nbr_sb INTEGER )";

        db.execSQL(CREATE_TRAJET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS trajet");
        this.onCreate(db);
    }

    private static final String TABLE_TRAJET = "trajet";

    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_FREQ = "frequence";
    private static final String KEY_MOD_LOC = "mod_loc";
    private static final String KEY_NIV_INIT_BATT = "niv_init_batt";
    private static final String KEY_NIV_FIN_BATT = "niv_fin_batt";
    private static final String KEY_ZOOM = "zoom";
    private static final String KEY_NBR_SB = "nbr_sb";

    private static final String[] COLUMNS = {KEY_ID,KEY_DATE,KEY_FREQ,KEY_MOD_LOC,
            KEY_NIV_INIT_BATT,KEY_NIV_FIN_BATT,KEY_ZOOM,KEY_NBR_SB};

    public void addTrajet(Trajet trajet){
        Log.d("addTrajet()", trajet.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, String.valueOf(trajet.getDate()));
        values.put(KEY_FREQ, trajet.getFreq_pt_m());
        values.put(KEY_MOD_LOC, trajet.isLoc_mode());
        values.put(KEY_NIV_INIT_BATT, trajet.getNiv_init_batt());
        values.put(KEY_NIV_FIN_BATT, trajet.getNiv_fin_batt());
        values.put(KEY_ZOOM, trajet.getZoom());
        values.put(KEY_NBR_SB, trajet.getNbr_sb());

        db.insert(TABLE_TRAJET, null, values);
        db.close();
/*
        SQLitePtMarquage dbPt = new SQLitePtMarquage(this.context);
        for (int i = 0; i < trajet.getPtMs().size(); i++){
            dbPt.addPoint(trajet.getPtMs().get(i), trajet);
        }*/

    }

    public Trajet getTrajet(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_TRAJET, COLUMNS, " id = ?", new String[] { String.valueOf(id) },
                        null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Trajet trajet = new Trajet();
        trajet.setId(Integer.parseInt(cursor.getString(0)));
        trajet.setDate(Date.valueOf(cursor.getString(1)));
        trajet.setFreq_pt_m(Integer.parseInt(cursor.getString(2)));
        trajet.setLoc_mode(Boolean.parseBoolean(cursor.getString(3)));
        trajet.setNiv_init_batt(Integer.parseInt(cursor.getString(4)));
        trajet.setNiv_fin_batt(Integer.parseInt(cursor.getString(5)));
        trajet.setZoom(Integer.parseInt(cursor.getString(6)));
        trajet.setNbr_sb(Integer.parseInt(cursor.getString(7)));

        SQLitePtMarquage dbPt = new SQLitePtMarquage(this.context);
        trajet.setPtMs((Vector<PtMarquage>) dbPt.getAllPoints(trajet));

        Log.d("getTrajet("+id+")", trajet.toString());

        return trajet;
    }

    public List<Trajet> getAllTrajets() {
        List<Trajet> trajets = new LinkedList<>();

        String query = "SELECT * FROM " + TABLE_TRAJET;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Trajet trajet = null;
        if (cursor.moveToFirst()) {
            do {
                trajet = new Trajet();
                trajet.setId(Integer.parseInt(cursor.getString(0)));
                trajet.setDate(Date.valueOf(cursor.getString(1)));
                trajet.setFreq_pt_m(Integer.parseInt(cursor.getString(2)));
                trajet.setLoc_mode(Boolean.parseBoolean(cursor.getString(3)));
                trajet.setNiv_init_batt(Integer.parseInt(cursor.getString(4)));
                trajet.setNiv_fin_batt(Integer.parseInt(cursor.getString(5)));
                trajet.setZoom(Integer.parseInt(cursor.getString(6)));
                trajet.setNbr_sb(Integer.parseInt(cursor.getString(7)));



                trajets.add(trajet);
            } while (cursor.moveToNext());
        }

        Log.d("getAllTrajets()", trajets.toString());

        return trajets;
    }

    public int updateTrajet(Trajet trajet) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, trajet.getDate().getTime());
        values.put(KEY_FREQ, trajet.getFreq_pt_m());
        values.put(KEY_MOD_LOC, trajet.isLoc_mode());
        values.put(KEY_NIV_INIT_BATT, trajet.getNiv_init_batt());
        values.put(KEY_NIV_FIN_BATT, trajet.getNiv_fin_batt());
        values.put(KEY_ZOOM, trajet.getZoom());
        values.put(KEY_NBR_SB, trajet.getNbr_sb());

        SQLitePtMarquage dbPt = new SQLitePtMarquage(this.context);
        for (int i = 0; i < trajet.getPtMs().size(); i++){
            dbPt.updatePoint(trajet.getPtMs().get(i), trajet);
        }

        int i = db.update(TABLE_TRAJET, values, KEY_ID+" = ?",
                new String[] { String.valueOf(trajet.getId()) });

        Log.d("updateTrajet()", trajet.toString());
        db.close();
        return i;
    }

    public void deleteTrajet(Trajet trajet) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TRAJET, KEY_ID+" = ?",
                new String[] { String.valueOf(trajet.getId()) });

        db.close();
        Log.d("deleteTrajet", trajet.toString());

    }
}