package com.localisation.parcours.model;

import android.media.Image;

/**
 * Created by Zalila on 2015-02-27.
 */
public class PtMarquage {

    private InstantMarquage im;
    private Coord coord;
    private String dir_dep;
    private int drp;//distance depuis dernier pt
    private int vm;//vitesse moyenne
    private int dt;//Distance totale
    private int niv_batt;
    private Image image;

    public InstantMarquage getIm() {
        return im;
    }

    public void setIm(InstantMarquage im) {
        this.im = im;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getDir_dep() {
        return dir_dep;
    }

    public void setDir_dep(String dir_dep) {
        this.dir_dep = dir_dep;
    }

    public int getDrp() {
        return drp;
    }

    public void setDrp(int drp) {
        this.drp = drp;
    }

    public int getVm() {
        return vm;
    }

    public void setVm(int vm) {
        this.vm = vm;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getNiv_batt() {
        return niv_batt;
    }

    public void setNiv_batt(int niv_batt) {
        this.niv_batt = niv_batt;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
