package com.localisation.parcours.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.localisation.parcours.model.Coord;
import com.localisation.parcours.model.PtMarquage;
import com.localisation.parcours.model.PtRC;
import com.localisation.parcours.model.Trajet;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zalila on 2015-03-07.
 */
public class SQLiteCellule extends SQLiteOpenHelper {

    public SQLiteCellule(Context context) {
        super(context, SQLiteTrajet.DATABASE_NAME, null, SQLiteTrajet.DATABASE_VERSION);
        /*SQLiteDatabase db = this.getWritableDatabase();
        this.onUpgrade(db,SQLiteTrajet.DATABASE_VERSION,SQLiteTrajet.DATABASE_VERSION);*/
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CELLULE_TABLE = "CREATE TABLE cellule ( " +
                "id INTEGER PRIMARY KEY, " +
                "type_r TEXT, "+
                "mcc TEXT, "+
                "mnc TEXT, "+
                "cell_id TEXT, "+
                "lac TEXT, "+
                "niv_sig_sb INTEGER, "+
                "long_sb INTEGER, "+
                "lat_sb INTEGER, "+
                "id_point INTEGER, "+
                "FOREIGN KEY(id_point) REFERENCES point_marquage(id) " +
                "ON UPDATE CASCADE)";

        db.execSQL(CREATE_CELLULE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS cellule");
        this.onCreate(db);
    }

    private static final String TABLE_CELLULE = "cellule";

    private static final String KEY_ID = "id";
    private static final String KEY_TYPE_R = "type_r";
    private static final String KEY_MCC = "mcc";
    private static final String KEY_MNC = "mnc";
    private static final String KEY_CELL_ID = "cell_id";
    private static final String KEY_LAC = "lac";
    private static final String KEY_NIV_SIG_SB = "niv_sig_sb";
    private static final String KEY_LONG_SB = "long_sb";
    private static final String KEY_LAT_SB = "lat_sb";
    private static final String KEY_ID_POINT = "id_point";

    private static final String[] COLUMNS = {KEY_ID,KEY_TYPE_R,KEY_MCC,KEY_MNC,KEY_CELL_ID,
        KEY_LAC,KEY_NIV_SIG_SB,KEY_LONG_SB,KEY_LAT_SB,KEY_ID_POINT};

    public void addCellule(PtRC cellule, int idPoint){
        Log.d("addPtRC()", cellule.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, String.valueOf(cellule.getId()));
        values.put(KEY_TYPE_R, cellule.getType_R());
        values.put(KEY_MCC, cellule.getMcc());
        values.put(KEY_MNC, cellule.getmnc());
        values.put(KEY_CELL_ID, cellule.getCell_id());
        values.put(KEY_LAC, cellule.getLac());
        values.put(KEY_NIV_SIG_SB, cellule.getNiv_sig_sb());
        values.put(KEY_LONG_SB, cellule.getCoord_sb().getLongitude());
        values.put(KEY_LAT_SB, cellule.getCoord_sb().getLatitude());
        values.put(KEY_ID_POINT, idPoint);

        db.insert(TABLE_CELLULE, null, values);

        db.close();
    }

    public PtRC getCellule(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_CELLULE, COLUMNS, " id_point = ?", new String[] { String.valueOf(id) },
                        null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        PtRC cellule = new PtRC();
        cellule.setId(Integer.parseInt(cursor.getString(0)));
        cellule.setType_R(cursor.getString(1));
        cellule.setMcc(cursor.getString(2));
        cellule.setmnc(cursor.getString(3));
        cellule.setCell_id(cursor.getString(4));
        cellule.setLac(cursor.getString(5));
        cellule.setNiv_sig_sb(Integer.parseInt(cursor.getString(6)));
        cellule.setCoord_sb(new Coord(Double.parseDouble(cursor.getString(7)),
                Double.parseDouble(cursor.getString(8))));

        Log.d("getPtRC("+id+")", cellule.toString());

        return cellule;
    }

    public List<PtRC> getAllCellules(PtMarquage point) {
        List<PtRC> cellules = new LinkedList<>();

        String query = "SELECT * FROM " + TABLE_CELLULE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        PtRC cellule = null;
        if (cursor.moveToFirst()) {
            do {
                if (point.getId() == Integer.parseInt(cursor.getString(9))) {
                    cellule = new PtRC();
                    cellule.setId(Integer.parseInt(cursor.getString(0)));
                    cellule.setType_R(cursor.getString(1));
                    cellule.setMcc(cursor.getString(2));
                    cellule.setmnc(cursor.getString(3));
                    cellule.setCell_id(cursor.getString(4));
                    cellule.setLac(cursor.getString(5));
                    cellule.setNiv_sig_sb(Integer.parseInt(cursor.getString(6)));
                    cellule.setCoord_sb(new Coord(Double.parseDouble(cursor.getString(7)),
                            Double.parseDouble(cursor.getString(8))));

                    cellules.add(cellule);
                }
            } while (cursor.moveToNext());
        }

        Log.d("getAllPtRCs()", cellules.toString());

        return cellules;
    }

    public int updateCellule(PtRC cellule, int idPoint) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE_R, cellule.getType_R());
        values.put(KEY_MCC, cellule.getMcc());
        values.put(KEY_MNC, cellule.getmnc());
        values.put(KEY_CELL_ID, cellule.getCell_id());
        values.put(KEY_LAC, cellule.getLac());
        values.put(KEY_NIV_SIG_SB, cellule.getNiv_sig_sb());
        values.put(KEY_LONG_SB, cellule.getCoord_sb().getLongitude());
        values.put(KEY_LAT_SB, cellule.getCoord_sb().getLatitude());
        values.put(KEY_ID_POINT, idPoint);

        int i = db.update(TABLE_CELLULE, values, KEY_ID+" = ?",
                new String[] { String.valueOf(cellule.getId()) });

        Log.d("updatePtRC()", cellule.toString());
        db.close();
        return i;
    }

    public void deleteCellule(PtRC cellule) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CELLULE, KEY_ID+" = ?",
                new String[] { String.valueOf(cellule.getId()) });

        db.close();
        Log.d("deletePtRC", cellule.toString());

    }

    public int cellCount(){
        int number = 0;
        String query = "SELECT count(*) FROM " + TABLE_CELLULE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                number = Integer.parseInt(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        Log.d("cellCount()", number + "cellules");
        db.close();
        return number;
    }
}