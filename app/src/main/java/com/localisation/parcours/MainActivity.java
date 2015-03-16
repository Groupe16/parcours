package com.localisation.parcours;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Spinner;
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
        modes.add("Réseau Cellulaire");
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
        Spinner modSpinner = (Spinner) findViewById(R.id.modeSpinner);
        NumberPicker minutesPicker = (NumberPicker) this.findViewById(R.id.minutesPicker);
        NumberPicker secondesPicker = (NumberPicker) this.findViewById(R.id.secondesPicker);
        SeekBar zoomBar = (SeekBar) findViewById(R.id.zoomSeekBar);

        SQLiteTrajet db = new SQLiteTrajet(this);
        Trajet trajet = new Trajet();
        trajet.setId(db.trajetCount() + 1);
        if (modSpinner.getSelectedItemId() == 0)
            trajet.setLoc_mode(true);
        else
            trajet.setLoc_mode(false);
        trajet.setFreq_pt_m(minutesPicker.getValue() * 60 + secondesPicker.getValue());

        trajet.setZoom(zoomBar.getProgress());

        if (db.trajetCount() == 0){
            SQLitePtMarquage dbpm = new SQLitePtMarquage(this);
            dbpm.onCreate(dbpm.getWritableDatabase());
            SQLitePA dbpa = new SQLitePA(this);
            dbpa.onCreate(dbpm.getWritableDatabase());
            SQLiteCellule dbrc = new SQLiteCellule(this);
            dbrc.onCreate(dbpm.getWritableDatabase());
        }

        Intent intent = new Intent(MainActivity.this, InitilalizeActivity.class);
        intent.putExtra("trajet", trajet);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //showDialog();
            Dialog dialog = onCreateDialog();
            dialog.show();
        }
        return true;
    }


    protected Dialog onCreateDialog() {
        // Création d'un boite de dialogue
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Voulez vous vraiment quitter ?");
        builder.setCancelable(false);
        builder.setTitle("Confirmation");

        builder.setPositiveButton("OUI",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                        System.exit(0);
                    }
                });

        builder.setNegativeButton("NON",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        dialog = builder.create();
        return dialog;
    }
}
