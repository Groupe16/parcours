package com.localisation.parcours;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.localisation.parcours.model.Trajet;


public class PathDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_detail);

        Trajet trajet = getIntent().getExtras().getParcelable("trajet");

        TextView adrDebutText = (TextView) findViewById(R.id.adrDebutText);
        TextView adrFinText = (TextView) findViewById(R.id.adrFinText);
        TextView dateText = (TextView) findViewById(R.id.dateText);
        TextView nivInitBattText = (TextView) findViewById(R.id.nivInitBattText);
        TextView nivFinBattText = (TextView) findViewById(R.id.nivFinBattText);
        TextView frequenceText = (TextView) findViewById(R.id.frequenceText);
        TextView modLocText = (TextView) findViewById(R.id.modLocText);
        TextView zoomText = (TextView) findViewById(R.id.zoomText);
        TextView nbrSBText = (TextView) findViewById(R.id.nbrSBText);

        adrDebutText.setText(trajet.getAdrDebut());
        adrFinText.setText(trajet.getAdrFin());
        dateText.setText(String.valueOf(trajet.getDate()));
        nivInitBattText.setText(trajet.getNiv_init_batt() + "%");
        nivFinBattText.setText(trajet.getNiv_fin_batt() + "%");
        frequenceText.setText(trajet.getFreq_pt_m() + "s");
        modLocText.setText(trajet.getModLoc());
        zoomText.setText(trajet.getZoom() + "%");
        nbrSBText.setText(trajet.getNbr_sb() + "station(s)");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_path_detail, menu);
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
}
