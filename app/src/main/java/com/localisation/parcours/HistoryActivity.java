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
import com.localisation.parcours.model.Trajet;

import java.util.ArrayList;
import java.util.List;


public class HistoryActivity extends ActionBarActivity {

    private List<Trajet> trajets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        SQLiteTrajet db = new SQLiteTrajet(this);

        if (db.trajetCount() > 0) {
            trajets = db.getLastTrajets(3);
            final ListView listTrajets = (ListView) findViewById(R.id.historyListView);
            /*SQLitePtMarquage dbPM;
            for (int i = 0; i < trajets.size(); i++) {
                dbPM = new SQLitePtMarquage(this);
                trajets.get(i).setPtMs(dbPM.getAllPoints(trajets.get(i)));
            }*/

            List<String> lasts = new ArrayList<>();
            for (int i = 0; i < trajets.size(); i++) {
                lasts.add((i + 1) + "-\t Debut: \n" + trajets.get(i).getAdrDebut() + " \n Fin: \n" + trajets.get(i).getAdrFin());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lasts);
            listTrajets.setAdapter(adapter);

            listTrajets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(HistoryActivity.this, PathDetailActivity.class);
                    intent.putExtra("trajet", trajets.get(position));
                    startActivity(intent);
                }
            });

        }else{
            TextView msgView = (TextView) findViewById(R.id.msgTextView);
            msgView.setText("aucun trajet sauvegardé");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
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
