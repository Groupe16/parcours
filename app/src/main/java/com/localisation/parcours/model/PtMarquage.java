package com.localisation.parcours.model;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by Zalila on 2015-02-27.
 */
public class PtMarquage implements Parcelable{

    private int id;
    private Time im;
    private Coord coord;
    private String dir_dep;//direction de déplacement {Nord, Sud, Est, Ouest ou combinaison}
    private int drp;//distance depuis dernier pt
    private int vm;//vitesse moyenne
    private int dt;//Distance totale
    private int niv_batt;
    private Image image;
    private PtRC ptRCs;
    private PAWifi pas;

    public PtMarquage() {
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
        dest.writeString(String.valueOf(im));
        dest.writeParcelable(coord, flags);
        dest.writeString(dir_dep);
        dest.writeInt(drp);
        dest.writeInt(vm);
        dest.writeInt(dt);
        dest.writeInt(niv_batt);
        dest.writeParcelable(ptRCs, flags);
        dest.writeParcelable(pas, flags);
    }


    // CREATOR permet de décrire au Parcel comment construire l'Objet
    public static final Parcelable.Creator<PtMarquage> CREATOR = new Parcelable.Creator<PtMarquage>()
    {
        @Override
        public PtMarquage createFromParcel(Parcel source)
        {
            return new PtMarquage(source);
        }

        @Override
        public PtMarquage[] newArray(int size)
        {
            return new PtMarquage[size];
        }
    };

    //Constructeur avec Parcel
    public PtMarquage(Parcel in) {
        this.id = in.readInt();
        this.im = Time.valueOf(in.readString());
        this.coord = in.readParcelable(Coord.class.getClassLoader());
        this.dir_dep = in.readString();
        this.drp = in.readInt();
        this.vm = in.readInt();
        this.dt = in.readInt();
        this.niv_batt = in.readInt();
        this.ptRCs = in.readParcelable(PtRC.class.getClassLoader());
        this.pas = in.readParcelable(PAWifi.class.getClassLoader());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getIm() {
        return im;
    }

    public void setIm(Time im) {
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

    public PtRC getPtRC() {
        return ptRCs;
    }

    public void setPtRC(PtRC ptRCs) {
        this.ptRCs = ptRCs;
    }

    public PAWifi getPa() {
        return pas;
    }

    public void setPa(PAWifi pas) {
        this.pas = pas;
    }
}
