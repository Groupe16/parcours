package com.localisation.parcours;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.localisation.parcours.database.SQLiteCellule;
import com.localisation.parcours.database.SQLitePA;
import com.localisation.parcours.database.SQLitePtMarquage;
import com.localisation.parcours.database.SQLiteTrajet;
import com.localisation.parcours.model.Coord;
import com.localisation.parcours.model.GPSLocation;
import com.localisation.parcours.model.PAWifi;
import com.localisation.parcours.model.PtMarquage;
import com.localisation.parcours.model.PtRC;
import com.localisation.parcours.model.Trajet;
import com.localisation.parcours.model.Accelerometer;


import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;




public class MapsActivity extends FragmentActivity {




    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private Marker markerInit;
    private Marker markerFin;
    private List<Trajet> trajets;
    private Trajet trajet;
    private LatLng previousPosition;
    int DistanceTotal = 0;
    Accelerometer accelerometer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        GPSLocation GPSLocator = new GPSLocation(this, MapsActivity.this);
        accelerometer = new Accelerometer();
        trajet = getIntent().getExtras().getParcelable("trajet");
        LoadTrajetOnMap(trajet);

    }

    private void LoadTrajetOnMap(Trajet trajet) {


        List<Address> geocodesMatches = null;
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider= locationManager.getBestProvider(criteria, false);
        if(provider!= null && !provider.equals(""))
        {
            List<Address> addresses = null;
            Geocoder geoCoder = new Geocoder(this);
            try {
                addresses = geoCoder.getFromLocationName(trajet.getAdrDebut().toString(), 1);
                Marker MarkerHandle = mMap.addMarker(new MarkerOptions().position(new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude())).title(trajet.getAdrDebut().toString()).snippet("" + trajet.getNiv_init_batt()));
                markerInit = MarkerHandle;
                //MarkerTable.put(MarkerHandle, trajet.getPtMs().get(0));
                previousPosition = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                addresses = geoCoder.getFromLocationName(trajet.getAdrFin().toString(), 1);
                MarkerHandle = mMap.addMarker(new MarkerOptions().position(new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude())).title(trajet.getAdrFin().toString()).snippet("Niveau final inconnu"));
                markerFin = MarkerHandle;
                //MarkerTable.put(MarkerHandle, trajet.getPtMs().get(1));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude())));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(trajet.getZoom()));

                mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                        if(MarkerTable.get(marker) != null) {
                            builder.setMessage("Coordonnées: Latitude: " + MarkerTable.get(marker).getCoord().getLatitude() + " Longitude: " + MarkerTable.get(marker).getCoord().getLongitude() + " Altitude: " + MarkerTable.get(marker).getCoord().getAltitude() + "\n"
                                    + "Direction: " + MarkerTable.get(marker).getDir_dep() + "\n"
                                    + "Distance parcourus: " + MarkerTable.get(marker).getDrp() + "\n"
                                    + "Vitesse moyenne: " + MarkerTable.get(marker).getVm() + "\n"
                                    + "Distance total: " + MarkerTable.get(marker).getDt() + "\n"
                                    + "Mode localisation: " + "GPS" + "\n"
                                    + "Niveau Batterie: " + MarkerTable.get(marker).getNiv_batt())
                                    .setTitle("Données pour le point");

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        return false;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.v("Old Position", previousPosition.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    Intent batteryStatus;
    public void onLocationChanged(Location location)
    {
        SQLitePtMarquage dbPt = new SQLitePtMarquage(this);
        int nbrPtM = dbPt.pointCount();
        SQLitePA dbPa = new SQLitePA(this);
        SQLiteCellule dbCell = new SQLiteCellule(this);
        PtMarquage ptMarquage = new PtMarquage();

        ptMarquage.setId(nbrPtM + 1);
        ptMarquage.setIm(new Time(System.currentTimeMillis()));
        ptMarquage.setCoord(new Coord(location.getLongitude(), location.getLatitude(), location.getAltitude()));
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryStatus = this.registerReceiver(null, ifilter);
        ptMarquage.setNiv_batt(batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1));
        trajet.setNiv_fin_batt(ptMarquage.getNiv_batt());
        int distance = accelerometer.GetNumberOfStepSinceLast();
        //Quick workaround. This is obviously going to break at 360/0
        double diffLatitude = location.getLatitude() - previousPosition.latitude;
        double diffLongtitude = location.getLongitude() - previousPosition.longitude;
        String LongitudeString = "";
        String LatitudeString = "";
        if(diffLongtitude > 0)
        {
            LongitudeString = "Ouest";
        }
        else if(diffLongtitude < 0)
        {
            LongitudeString = "Est";
        }
        if(diffLatitude > 0)
        {
            LongitudeString = "Nord";
        }
        else if(diffLatitude < 0)
        {
            LongitudeString = "Sud";
        }
        DistanceTotal += distance;
        ptMarquage.setDt(DistanceTotal);
        ptMarquage.setDir_dep(LatitudeString + " " + LongitudeString);
        ptMarquage.setDrp(distance);
        ptMarquage.setVm(distance/trajet.getFreq_pt_m());
        /////////////////////////////////////////////////

        if (trajet.isLoc_mode()){
            int nbrPas = dbPa.paCount();
            PAWifi paWifi;
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
            int nbrRC = dbCell.cellCount();
            PtRC ptRC;
            nbrRC++;
            ptRC = new PtRC();
            ptRC.setId(nbrRC);
            ptRC.setType_R(nbrRC + " type R");
            ptRC.setNiv_sig_sb(57);
            ptRC.setLac(nbrRC + " LAC");
            ptRC.setmnc(nbrRC + " MNC");
            ptRC.setMcc(nbrRC + " MCC");
            ptRC.setCell_id(nbrRC + " CELL ID");
            ptRC.setCoord_sb(new Coord(location.getLongitude(), location.getLatitude()));
            ptMarquage.setPtRC(ptRC);
        }

        AddPtMarquageToMap(ptMarquage);
        trajet.addPoint(ptMarquage);
        dbPt.addPoint(ptMarquage, trajet);
        //si Mod_Loc = GPS
        if (trajet.isLoc_mode()) {
            dbPa.addPA(ptMarquage.getPa(), ptMarquage.getId());
        }else{//si Mod_Loc = reseau cellulaire
            dbCell.addCellule(ptMarquage.getPtRC(), ptMarquage.getId());
        }

        SQLiteTrajet db = new SQLiteTrajet(this);
        db.updateTrajet(trajet);

        playSound(R.raw.up);

        Log.v("createPtMarquage", ptMarquage.toString());
    }

    private MediaPlayer mPlayer = null;
    private void playSound(int resId) {
        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
        mPlayer = MediaPlayer.create(this, resId);
        mPlayer.start();
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


    Dictionary<Marker, PtMarquage> MarkerTable = new Hashtable<Marker, PtMarquage>();


    public void AddPtMarquageToMap(PtMarquage pt)
    {
        final PtMarquage _pt = pt;
        LatLng latLng = new LatLng(pt.getCoord().getLatitude(), pt.getCoord().getLongitude());
        Geocoder geoCoder = new Geocoder(this);
        try
        {

            List<Address> matches = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            Address bestMatch = (matches.isEmpty()? null : matches.get(0));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(trajet.getZoom()));
            Marker markerHandle = mMap.addMarker(new MarkerOptions().position(new LatLng(bestMatch.getLatitude(), bestMatch.getLongitude())).title(bestMatch.getAddressLine(0).toString()));
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(previousPosition, new LatLng(bestMatch.getLatitude(), bestMatch.getLongitude()))
                    .width(5)
                    .color(Color.RED));

            MarkerTable.put(markerHandle, _pt);

            previousPosition = new LatLng(bestMatch.getLatitude(), bestMatch.getLongitude());
        }catch(IOException e)
        {
        }
        /*.snippet("Coordonées: Latitude: " + pt.getCoord().getLatitude() + " Longitude: " + pt.getCoord().getLongitude() + " Altitude: " + pt.getCoord().getAltitude() + "\n"
        + "Direction: " + pt.getDir_dep() + "\n"
                + "Distance parcourus: " + pt.getDrp() + "\n"
                + "Vitesse moyenne: " + pt.getVm() + "\n"
                + "Distance total: " + pt.getDt() + "\n"
                + "Mode localisation: " + "GPS" + "\n"
                + "Niveau Batterie: " + pt.getNiv_batt()));*/
    }


    protected Dialog onCreateDialog() {
        // Création d'un boite de dialogue
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Voulez vous vraiment arrêter le parcours ?");
        builder.setCancelable(false);
        builder.setTitle("Confirmation");

        builder.setPositiveButton("OUI",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                        MapsActivity.this.finish();
                        startActivity(intent);
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
