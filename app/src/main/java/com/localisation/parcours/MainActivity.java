package com.localisation.parcours;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Spinner;

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
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner modList = (Spinner) findViewById(R.id.modeSpinner);
        List<String> modes = new ArrayList<>();
        modes.add("GPS");
        modes.add("RC");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, modes);
        modList.setAdapter(adapter);

        NumberPicker minutesPicker = (NumberPicker) this.findViewById(R.id.minutesPicker);
        minutesPicker.setMinValue(0);
        minutesPicker.setMaxValue(59);
        minutesPicker.setValue(1);

        NumberPicker secondesPicker = (NumberPicker) this.findViewById(R.id.secondesPicker);
        secondesPicker.setMinValue(0);
        secondesPicker.setMaxValue(59);

        SeekBar zoomBar = (SeekBar) findViewById(R.id.zoomSeekBar);

        PAWifi paWifi = new PAWifi();
        paWifi.setPa("pa name");
        paWifi.setBssid("BSSID");
        paWifi.setSsid("SSID");
        paWifi.setRss("RSS");
        PtRC ptRC = new PtRC();
        ptRC.setType_R("type R");
        ptRC.setNiv_sig_sb(57);
        ptRC.setLac("LAC");
        ptRC.setmnc("MNC");
        ptRC.setMcc("MCC");
        ptRC.setCell_id("CELL ID");
        ptRC.setCoord_sb(new Coord(5678,5678.34));
        PtMarquage ptMarquage = new PtMarquage();
        ptMarquage.setIm(new Time(System.currentTimeMillis()));
        ptMarquage.setCoord(new Coord(10, 1098, 40836));
        ptMarquage.setNiv_batt(100);
        ptMarquage.setDt(203);
        ptMarquage.setDir_dep("Nord Est");
        ptMarquage.setDrp(33);
        ptMarquage.setVm(3);
        //ptMarquage.setPa(paWifi);
        ptMarquage.setPtRC(ptRC);
        Trajet trajet = new Trajet();
        trajet.setId(1);
        trajet.setDate(new Date(System.currentTimeMillis()));
        trajet.setLoc_mode(false);
        trajet.setFreq_pt_m(3200);
        trajet.setZoom(zoomBar.getProgress());
        trajet.setNbr_sb(0);
        trajet.setNiv_fin_batt(45);
        trajet.setNiv_init_batt(99);
        trajet.addPoint(ptMarquage);
        SQLiteTrajet db = new SQLiteTrajet(this);
        db.addTrajet(trajet);

        for (int i = 0; i < trajet.getPtMs().size(); i++) {
            SQLitePtMarquage dbPt = new SQLitePtMarquage(this);
            dbPt.addPoint(trajet.getPtMs().get(i), trajet);
            //si Mod_Loc = GPS
            if (trajet.isLoc_mode()) {
                SQLitePA dbPa = new SQLitePA(this);
                dbPa.addPA(trajet.getPtMs().get(i).getPa(), trajet.getPtMs().get(i).getId());
            }else{//si Mod_Loc = reseau cellulaire
                SQLiteCellule dbCell = new SQLiteCellule(this);
                dbCell.addCellule(trajet.getPtMs().get(i).getPtRC(), trajet.getPtMs().get(i).getId());
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        Intent intent = new Intent(MainActivity.this, InitilalizeActivity.class);
        startActivity(intent);
    }

    public void historyClick(View view){
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(intent);
    }

    public void exitClick(View view){
        this.finish();
        System.exit(0);
    }
}
