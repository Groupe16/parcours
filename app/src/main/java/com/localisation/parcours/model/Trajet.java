package com.localisation.parcours.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by Zalila on 2015-02-27.
 */
public class Trajet implements Parcelable{

    private int id;
    private String adrDebut, adrFin;
    private Vector<PtMarquage> ptMs ;
    private int niv_init_batt, niv_fin_batt;
    private int freq_pt_m;
    private boolean loc_mode;
    private int zoom;
    private int nbr_sb;
    private Date date;

    public Trajet() {
        this.ptMs = new Vector<>();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeParcelableArray(ptMs.toArray(new PtMarquage[ptMs.size()]), flags);
        dest.writeInt(niv_init_batt);
        dest.writeInt(niv_fin_batt);
        dest.writeInt(freq_pt_m);
        dest.writeString(String.valueOf(loc_mode));
        dest.writeInt(zoom);
        dest.writeInt(nbr_sb);
        dest.writeString(String.valueOf(date));
    }

    // CREATOR permet de décrire au Parcel comment construire l'Objet
    public static final Parcelable.Creator<Trajet> CREATOR = new Parcelable.Creator<Trajet>()
    {
        @Override
        public Trajet createFromParcel(Parcel source)
        {
            return new Trajet(source);
        }

        @Override
        public Trajet[] newArray(int size)
        {
            return new Trajet[size];
        }
    };

    //Constructeur avec Parcel
    public Trajet(Parcel in) {
        this.id = in.readInt();
        PtMarquage[] ptMs = (PtMarquage[]) in.readParcelableArray(PtMarquage.class.getClassLoader());
        this.niv_init_batt = in.readInt();
        this.niv_fin_batt = in.readInt();
        this.freq_pt_m = in.readInt();
        this.loc_mode = Boolean.valueOf(in.readString());
        this.zoom = in.readInt();
        this.nbr_sb = in.readInt();
        this.date = Date.valueOf(in.readString());
    }

    public void addPoint(PtMarquage ptMarquage){
        this.ptMs.addElement(ptMarquage);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vector<PtMarquage> getPtMs() {
        return ptMs;
    }

    public void setPtMs(Vector<PtMarquage> ptMs) {
        this.ptMs = ptMs;
    }

    public int getNiv_init_batt() {
        return niv_init_batt;
    }

    public void setNiv_init_batt(int niv_init_batt) {
        this.niv_init_batt = niv_init_batt;
    }

    public int getNiv_fin_batt() {
        return niv_fin_batt;
    }

    public void setNiv_fin_batt(int niv_fin_batt) {
        this.niv_fin_batt = niv_fin_batt;
    }

    public int getFreq_pt_m() {
        return freq_pt_m;
    }

    public void setFreq_pt_m(int freq_pt_m) {
        this.freq_pt_m = freq_pt_m;
    }

    public boolean isLoc_mode() {
        return loc_mode;
    }

    public void setLoc_mode(boolean loc_mode) {
        this.loc_mode = loc_mode;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public int getNbr_sb() {
        return nbr_sb;
    }

    public void setNbr_sb(int nbr_sb) {
        this.nbr_sb = nbr_sb;
    }

    public String getDebut() {
        return this.getPtMs().get(0).getCoord().toString();
    }

    public String getFin() {
        return this.getPtMs().get(this.getPtMs().size() - 1).getCoord().toString();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
