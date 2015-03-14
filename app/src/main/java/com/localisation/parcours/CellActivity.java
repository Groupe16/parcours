package com.localisation.parcours;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.localisation.parcours.model.PtRC;


public class CellActivity extends ActionBarActivity {

    private PtRC cell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cell);

        cell = getIntent().getExtras().getParcelable("Cellule");

        TextView typeRText = (TextView) findViewById(R.id.typeRText);
        TextView mccText = (TextView) findViewById(R.id.mccText);
        TextView mncText = (TextView) findViewById(R.id.mncText);
        TextView cellIdText = (TextView) findViewById(R.id.cellIdText);
        TextView lacText = (TextView) findViewById(R.id.lacText);
        TextView nivBattText = (TextView) findViewById(R.id.nivBattText);
        TextView coordText = (TextView) findViewById(R.id.coordText);

        typeRText.setText(cell.getType_R());
        mccText.setText(cell.getMcc());
        mncText.setText(cell.getmnc());
        cellIdText.setText(cell.getCell_id());
        lacText.setText(cell.getLac());
        nivBattText.setText(cell.getNiv_sig_sb() + "%");
        coordText.setText("(" + cell.getCoord_sb().getLongitude() + "," +
                cell.getCoord_sb().getLatitude()+ ")");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cell, menu);
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
