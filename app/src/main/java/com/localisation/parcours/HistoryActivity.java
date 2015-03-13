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

import com.localisation.parcours.database.SQLitePtMarquage;
import com.localisation.parcours.database.SQLiteTrajet;
import com.localisation.parcours.model.Trajet;

import java.util.ArrayList;
import java.util.List;


public class HistoryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        SQLiteTrajet db = new SQLiteTrajet(this);
        List<Trajet> trajets = db.getAllTrajets();
        final ListView listTrajets = (ListView) findViewById(R.id.historyListView);
        SQLitePtMarquage dbPM;
        for (int i = 0; i < trajets.size(); i++){
            dbPM = new SQLitePtMarquage(this);
            trajets.get(i).setPtMs(dbPM.getAllPoints(trajets.get(i)));
        }
        trajets = sort(trajets);


        List<String> lasts = new ArrayList<>();
        for (int i = 0; i < trajets.size() ; i++){
            lasts.add((i+1) + "-\t Debut: \n" + trajets.get(i).getDebut() + " \n Fin: \n" + trajets.get(i).getFin());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lasts);
        listTrajets.setAdapter(adapter);

        listTrajets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(HistoryActivity.this, PathDetailActivity.class);
// TODO                intent.putExtra("trajet", (Trajet) listTrajets.getAdapter().getView(position, view, parent));
                startActivity(intent);
            }
        });

    }

    private List sort(List trajets) {
        int j;
        List<Trajet> trajetList = new ArrayList<Trajet>();
        if (trajets.size() > 3)
            j=3;
        else
            j= trajets.size();

        for (int i = 0 ; i < j; i++){
            Trajet trajet = (Trajet) trajets.get(i);
            trajetList.add(trajet);
        }
        return trajetList;
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
