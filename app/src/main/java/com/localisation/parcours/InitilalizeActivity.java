package com.localisation.parcours;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.internal.pt;
import com.localisation.parcours.database.SQLiteCellule;
import com.localisation.parcours.database.SQLitePA;
import com.localisation.parcours.database.SQLitePtMarquage;
import com.localisation.parcours.database.SQLiteTrajet;
import com.localisation.parcours.model.Coord;
import com.localisation.parcours.model.PAWifi;
import com.localisation.parcours.model.PtMarquage;
import com.localisation.parcours.model.PtRC;
import com.localisation.parcours.model.Trajet;

import java.sql.Date;
import java.sql.Time;
import java.util.Vector;


public class InitilalizeActivity extends ActionBarActivity {

    private Trajet trajet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initilalize);

        trajet = getIntent().getExtras().getParcelable("trajet");

        new Thread(new Runnable() {
            public void run() {

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initilalize, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startClick(View view){

        EditText startEditText = (EditText) findViewById(R.id.startAddressEditText);
        trajet.setAdrDebut(startEditText.getText().toString());
        EditText endEditText = (EditText) findViewById(R.id.endAddressEditText);
        trajet.setAdrFin(endEditText.getText().toString());
        trajet.setNiv_fin_batt(trajet.getNiv_init_batt());
        SQLiteTrajet db = new SQLiteTrajet(this);
        db.addTrajet(trajet);

        //simulation(db);

        Intent intent = new Intent(InitilalizeActivity.this, MapsActivity.class);
        intent.putExtra("trajet", trajet);
        startActivity(intent);
    }

    private void simulation(SQLiteTrajet db) {
        SQLitePtMarquage dbPt = new SQLitePtMarquage(this);
        int nbrPtM = dbPt.pointCount();
        Vector<PtMarquage> ptMs;
        SQLitePA dbPa = new SQLitePA(this);
        int nbrPas = dbPa.paCount();
        PAWifi paWifi;
        SQLiteCellule dbCell = new SQLiteCellule(this);
        int nbrRC = dbCell.cellCount();
        PtRC ptRC;
        PtMarquage ptMarquage;

        int number = (int) (Math.random() * 100);

        for (int i = 0; i < number; i++){
            nbrPtM++;
            ptMarquage = new PtMarquage();
            ptMarquage.setId(nbrPtM);
            ptMarquage.setIm(new Time(System.currentTimeMillis()));
            ptMarquage.setCoord(new Coord(Math.random(), Math.random(), Math.random()));
            ptMarquage.setNiv_batt(trajet.getNiv_init_batt() - 3);
            trajet.setNiv_fin_batt(ptMarquage.getNiv_batt());
            ptMarquage.setDt((int) (Math.random() * 10000));
            ptMarquage.setDir_dep(nbrPtM + " Nord Est");
            ptMarquage.setDrp((int) (Math.random() * 10000));
            ptMarquage.setVm((int) (Math.random() * 1000));

            if (trajet.isLoc_mode()){
                nbrPas++;
                WifiManager wc = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifi = wc.getConnectionInfo();
                paWifi = new PAWifi();
                paWifi.setId(nbrPas);
                paWifi.setPa(wifi.getBSSID());
                paWifi.setBssid(wifi.getSSID());
                paWifi.setSsid(wifi.getRssi()+"");
                paWifi.setRss(wifi.getNetworkId()+"");
                ptMarquage.setPa(paWifi);
            }else{
                nbrRC++;
                ptRC = new PtRC();
                ptRC.setId(nbrRC);
                ptRC.setType_R(nbrRC + " type R");
                ptRC.setNiv_sig_sb(57);
                ptRC.setLac(nbrRC + " LAC");
                ptRC.setmnc(nbrRC + " MNC");
                ptRC.setMcc(nbrRC + " MCC");
                ptRC.setCell_id(nbrRC + " CELL ID");
                ptRC.setCoord_sb(new Coord(Math.random(),Math.random()));
                ptMarquage.setPtRC(ptRC);
            }
            trajet.addPoint(ptMarquage);
            dbPt.addPoint(ptMarquage, trajet);
            //si Mod_Loc = GPS
            if (trajet.isLoc_mode()) {
                dbPa.addPA(ptMarquage.getPa(), ptMarquage.getId());
            }else{//si Mod_Loc = reseau cellulaire
                dbCell.addCellule(ptMarquage.getPtRC(), ptMarquage.getId());
            }
        }
        db.updateTrajet(trajet);
    }
}
