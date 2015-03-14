package com.localisation.parcours;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.localisation.parcours.model.PtMarquage;


public class PointActivity extends ActionBarActivity {

    private PtMarquage ptMarquage;
    private boolean mod_loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);

        ptMarquage = getIntent().getExtras().getParcelable("point");
        mod_loc = getIntent().getExtras().getBoolean("mod_loc");

        TextView timeText = (TextView) findViewById(R.id.timeText);
        TextView coordText = (TextView) findViewById(R.id.coordText);
        TextView dirDepText = (TextView) findViewById(R.id.dirDepText);
        TextView drpText = (TextView) findViewById(R.id.drpText);
        TextView vmText = (TextView) findViewById(R.id.vmText);
        TextView dtText = (TextView) findViewById(R.id.dtText);
        TextView nivBattText = (TextView) findViewById(R.id.nivBattText);

        timeText.setText(ptMarquage.getIm().toString());
        coordText.setText("(" + ptMarquage.getCoord() + ")");
        dirDepText.setText(ptMarquage.getDir_dep());
        drpText.setText(ptMarquage.getDrp() + "Métres");
        vmText.setText(ptMarquage.getVm() + "M/m");
        dtText.setText(ptMarquage.getDt() + "Métres");
        nivBattText.setText(ptMarquage.getNiv_batt() + "%");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_point, menu);
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

    public void paClick(View view){
        Intent intent;
        if (this.mod_loc) {
            intent = new Intent(PointActivity.this, AccessPointActivity.class);
            intent.putExtra("PAWifi", ptMarquage.getPa());
        }else {
            intent = new Intent(PointActivity.this, CellActivity.class);
            intent.putExtra("Cellule", ptMarquage.getPtRC());
        }
        startActivity(intent);
    }
}
