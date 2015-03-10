package com.localisation.parcours.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;

/**
 * Created by Zalila on 2015-02-27.
 */
public class PtRC implements Parcelable{

    private int id;
    private String type_R;//type du reseau cellulaire
    private String mcc;//Mobile Country Code
    private String mnc;//Mobile Network Code
    private String cell_id;//identifiant de la cellule d'attache
    private String lac;//Location Area Code
    private int niv_sig_sb;//niveau du signal de la station de base
    private Coord coord_sb;//coordonnees de la station de base

    public PtRC() {
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
        dest.writeString(type_R);
        dest.writeString(mcc);
        dest.writeString(mnc);
        dest.writeString(cell_id);
        dest.writeString(lac);
        dest.writeInt(niv_sig_sb);
        dest.writeParcelable(coord_sb, flags);
    }


    // CREATOR permet de décrire au Parcel comment construire l'Objet
    public static final Parcelable.Creator<PtRC> CREATOR = new Parcelable.Creator<PtRC>()
    {
        @Override
        public PtRC createFromParcel(Parcel source)
        {
            return new PtRC(source);
        }

        @Override
        public PtRC[] newArray(int size)
        {
            return new PtRC[size];
        }
    };

    //Constructeur avec Parcel
    public PtRC(Parcel in) {
        this.id = in.readInt();
        this.type_R = in.readString();
        this.mcc = in.readString();
        this.mnc = in.readString();
        this.cell_id = in.readString();
        this.lac = in.readString();
        this.niv_sig_sb = in.readInt();
        this.coord_sb = in.readParcelable(Coord.class.getClassLoader());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType_R() {
        return type_R;
    }

    public void setType_R(String type_R) {
        this.type_R = type_R;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getmnc() {
        return mnc;
    }

    public void setmnc(String mnc) {
        this.mnc = mnc;
    }

    public String getCell_id() {
        return cell_id;
    }

    public void setCell_id(String cell_id) {
        this.cell_id = cell_id;
    }

    public String getLac() {
        return lac;
    }

    public void setLac(String lac) {
        this.lac = lac;
    }

    public int getNiv_sig_sb() {
        return niv_sig_sb;
    }

    public void setNiv_sig_sb(int niv_sig_sb) {
        this.niv_sig_sb = niv_sig_sb;
    }

    public Coord getCoord_sb() {
        return coord_sb;
    }

    public void setCoord_sb(Coord coord_sb) {
        this.coord_sb = coord_sb;
    }
}
