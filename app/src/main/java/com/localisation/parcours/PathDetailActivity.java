package com.localisation.parcours;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.localisation.parcours.database.SQLitePtMarquage;
import com.localisation.parcours.database.SQLiteTrajet;
import com.localisation.parcours.model.PtMarquage;
import com.localisation.parcours.model.Trajet;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class PathDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_detail);

        final Trajet trajet = getIntent().getExtras().getParcelable("trajet");

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

        SQLitePtMarquage db = new SQLitePtMarquage(this);

        if (db.pointCount(trajet) > 0) {
            final Vector<PtMarquage> points = db.getAllPoints(trajet);
            final ListView listPoints = (ListView) findViewById(R.id.pointListView);

            List<String> lasts = new ArrayList<>();
            for (int i = 0; i < points.size(); i++) {
                lasts.add((i + 1) + "-\t Debut: \n" + points.get(i).getIm() + " \n Fin: \n" + points.get(i).getCoord());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lasts);
            listPoints.setAdapter(adapter);

            listPoints.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(PathDetailActivity.this, PointActivity.class);
                    intent.putExtra("point", points.get(position));
                    intent.putExtra("mod_loc", trajet.isLoc_mode());
                    startActivity(intent);
                }
            });

        }else{
            TextView msgView = (TextView) findViewById(R.id.msgTextView);
            msgView.setText("aucun point de marquage sauvegardé");
        }
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
