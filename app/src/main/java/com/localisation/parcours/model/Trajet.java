package com.localisation.parcours.model;

import java.sql.Date;
import java.sql.Time;
import java.util.Vector;

/**
 * Created by Zalila on 2015-02-27.
 */
public class Trajet {

    private int id;
    private Vector<PtMarquage> ptMs ;
    private int niv_init_batt, niv_fin_batt;
    private int freq_pt_m;
    private boolean loc_mode;
    private int zoom;
    private int nbr_sb;
    private Time debut, fin;
    private Date date;

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

    public Time getDebut() {
        return debut;
    }

    public void setDebut(Time debut) {
        this.debut = debut;
    }

    public Time getFin() {
        return fin;
    }

    public void setFin(Time fin) {
        this.fin = fin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
