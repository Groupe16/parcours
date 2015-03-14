package com.localisation.parcours;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.localisation.parcours.model.PAWifi;


public class AccessPointActivity extends ActionBarActivity {

    private PAWifi pa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_point);

        pa = getIntent().getExtras().getParcelable("PAWifi");

        TextView paText = (TextView) findViewById(R.id.paText);
        TextView bssidText = (TextView) findViewById(R.id.bssidText);
        TextView ssidText = (TextView) findViewById(R.id.ssidText);
        TextView rssText = (TextView) findViewById(R.id.rssText);

        paText.setText(pa.getPa());
        bssidText.setText(pa.getBssid());
        ssidText.setText(pa.getSsid());
        rssText.setText(pa.getRss());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_access_point, menu);
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
