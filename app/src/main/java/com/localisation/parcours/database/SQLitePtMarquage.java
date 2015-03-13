package com.localisation.parcours.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.localisation.parcours.model.Coord;
import com.localisation.parcours.model.PtMarquage;
import com.localisation.parcours.model.Trajet;

import java.sql.Time;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Zalila on 2015-03-07.
 */
public class SQLitePtMarquage extends SQLiteOpenHelper {

    Context context;
    public SQLitePtMarquage(Context context) {
        super(context, SQLiteTrajet.DATABASE_NAME, null, SQLiteTrajet.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_POINT_TABLE = "CREATE TABLE point_marquage ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "im TEXT, "+
                "longitude REAL, "+
                "latitude REAL, "+
                "altitude REAL, "+
                "dir_dep TEXT, "+
                "drp INTEGER, "+
                "vm REAL, "+
                "dt REAL, "+
                "niv_batt INTEGER, "+
                //TODO image
                "id_trajet INTEGER, "+
                "FOREIGN KEY(id_trajet) REFERENCES trajet(id) ON UPDATE CASCADE)";

        db.execSQL(CREATE_POINT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS point_marquage");
        this.onCreate(db);
    }

    private static final String TABLE_POINT = "point_marquage";

    private static final String KEY_ID = "id";
    private static final String KEY_IM = "im";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_ALTITUDE = "altitude";
    private static final String KEY_DIRECTION_DEPLACEMENT = "dir_dep";
    private static final String KEY_DISTANCE = "drp";
    private static final String KEY_VITESSE = "vm";
    private static final String KEY_DISTANCE_TOTALE = "dt";
    private static final String KEY_BATTERIE = "niv_batt";
    private static final String KEY_ID_TRAJET = "id_trajet";

    private static final String[] COLUMNS = {KEY_ID,KEY_IM,KEY_LONGITUDE,KEY_LATITUDE,KEY_ALTITUDE,
        KEY_DIRECTION_DEPLACEMENT,KEY_DISTANCE,KEY_VITESSE,KEY_DISTANCE_TOTALE,KEY_BATTERIE,KEY_ID_TRAJET};

    public void addPoint(PtMarquage point, Trajet trajet){
        Log.d("addPtMarquage()", point.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        this.onUpgrade(db,SQLiteTrajet.DATABASE_VERSION,SQLiteTrajet.DATABASE_VERSION);

        ContentValues values = new ContentValues();
        values.put(KEY_IM, String.valueOf(point.getIm().getTime()));
        values.put(KEY_LONGITUDE, point.getCoord().getLongitude());
        values.put(KEY_LATITUDE, point.getCoord().getLatitude());
        values.put(KEY_ALTITUDE, point.getCoord().getAltitude());
        values.put(KEY_DIRECTION_DEPLACEMENT, point.getDir_dep());
        values.put(KEY_DISTANCE, point.getDrp());
        values.put(KEY_VITESSE, point.getVm());
        values.put(KEY_DISTANCE_TOTALE, point.getDt());
        values.put(KEY_BATTERIE, point.getNiv_batt());
        values.put(KEY_ID_TRAJET, trajet.getId());
        
        db.insert(TABLE_POINT, null, values);
        db.close();
    }

    public PtMarquage getPoint(int id, Trajet trajet){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_POINT, COLUMNS, " id = ?", new String[] { String.valueOf(id) },
                        null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        PtMarquage point = new PtMarquage();
        point.setId(Integer.parseInt(cursor.getString(0)));
        point.setIm(Time.valueOf(cursor.getString(1)));
        point.setCoord(new Coord(Double.parseDouble(cursor.getString(2)),
                Double.parseDouble(cursor.getString(3)), Double.parseDouble(cursor.getString(4))));
        point.setDir_dep(cursor.getString(5));
        point.setDrp(Integer.parseInt(cursor.getString(6)));
        point.setVm(Integer.parseInt(cursor.getString(7)));
        point.setDt(Integer.parseInt(cursor.getString(8)));
        point.setNiv_batt(Integer.parseInt(cursor.getString(9)));

        //si Mod_Loc = GPS
        if (trajet.isLoc_mode()) {
            SQLitePA dbPa = new SQLitePA(this.context);
            dbPa.getPA(point.getPa().getId());
        }else{//si Mod_Loc = reseau cellulaire
            SQLiteCellule dbCell = new SQLiteCellule(this.context);
            dbCell.getCellule(point.getPtRC().getId());
        }

        Log.d("getPtMarquage("+id+")", point.toString());

        return point;
    }

    public Vector<PtMarquage> getAllPoints(Trajet trajet) {
        Vector<PtMarquage> points = new Vector<>();

        String query = "SELECT * FROM " + TABLE_POINT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        PtMarquage point = null;
        if (cursor.moveToFirst()) {
            do {
                if (trajet.getId() == Integer.parseInt(cursor.getString(10))) {
                    point = new PtMarquage();
                    point.setId(Integer.parseInt(cursor.getString(0)));
                    //point.setIm(Time.valueOf(cursor.getString(1)));
                    point.setCoord(new Coord(Double.parseDouble(cursor.getString(2)),
                            Double.parseDouble(cursor.getString(3)),Double.parseDouble(cursor.getString(4))));
                    point.setDir_dep(cursor.getString(5));
                    point.setDrp(Integer.parseInt(cursor.getString(6)));
                    point.setVm(Integer.parseInt(cursor.getString(7)));
                    point.setDt(Integer.parseInt(cursor.getString(8)));
                    point.setNiv_batt(Integer.parseInt(cursor.getString(9)));
/*
                    //si Mod_Loc = GPS
                    if (trajet.isLoc_mode()) {
                        SQLitePA dbPa = new SQLitePA(this.context);
                        dbPa.getPA(point.getPa().getId());
                    }else{//si Mod_Loc = reseau cellulaire
                        SQLiteCellule dbCell = new SQLiteCellule(this.context);
                        dbCell.getCellule(point.getPtRC().getId());
                    }
*/
                    points.add(point);
                }
            } while (cursor.moveToNext());
        }

        Log.d("getAllPtMarquages()", points.toString());
db.close();
        return points;
    }

    public int updatePoint(PtMarquage point, Trajet trajet) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IM, point.getIm().getTime());
        values.put(KEY_LONGITUDE, point.getCoord().getLongitude());
        values.put(KEY_LATITUDE, point.getCoord().getLatitude());
        values.put(KEY_ALTITUDE, point.getCoord().getAltitude());
        values.put(KEY_DIRECTION_DEPLACEMENT, point.getDir_dep());
        values.put(KEY_DISTANCE, point.getDrp());
        values.put(KEY_VITESSE, point.getVm());
        values.put(KEY_DISTANCE_TOTALE, point.getDt());
        values.put(KEY_BATTERIE, point.getNiv_batt());
        values.put(KEY_ID_TRAJET, trajet.getId());

        //si Mod_Loc = GPS
        if (trajet.isLoc_mode()) {
            SQLitePA dbPa = new SQLitePA(this.context);
            dbPa.updatePA(point.getPa(), point.getId());
        }else{//si Mod_Loc = reseau cellulaire
            SQLiteCellule dbCell = new SQLiteCellule(this.context);
            dbCell.updateCellule(point.getPtRC(), point.getId());
        }

        int i = db.update(TABLE_POINT, values, KEY_ID+" = ?",
                new String[] { String.valueOf(point.getId()) });

        Log.d("updatePtMarquage()", point.toString());
        db.close();
        return i;
    }

    public void deletePoint(PtMarquage point) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_POINT, KEY_ID+" = ?",
                new String[] { String.valueOf(point.getId()) });

        db.close();
        Log.d("deletePtMarquage", point.toString());

    }
}