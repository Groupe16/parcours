package com.localisation.parcours.model;

/**
 * Created by Zalila on 2015-02-27.
 */
public class PtRC {

    private int id;
    private String type_R;//type du reseau cellulaire
    private String mcc;//Mobile Country Code
    private String mnc;//Mobile Network Code
    private String cell_id;//identifiant de la cellule d'attache
    private String lac;//Location Area Code
    private int niv_sig_sb;//niveau du signal de la station de base
    private Coord coord_sb;//coordonnees de la station de base

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
