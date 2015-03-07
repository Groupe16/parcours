package com.localisation.parcours.model;

/**
 * Created by Zalila on 2015-02-27.
 */
public class PtRC {

    private String type_R;//type du reseau cellulaire
    private String mmc;//Mobile Country Code
    private String MNC;//Mobile Network Code
    private String cell_id;//identifiant de la cellule d'attache
    private String lac;//Location Area Code
    private int niv_sig_sb;//niveau du signal de la station de base
    private Coord coord_sb;//coordonnees de la station de base

    public String getType_R() {
        return type_R;
    }

    public void setType_R(String type_R) {
        this.type_R = type_R;
    }

    public String getMmc() {
        return mmc;
    }

    public void setMmc(String mmc) {
        this.mmc = mmc;
    }

    public String getMNC() {
        return MNC;
    }

    public void setMNC(String MNC) {
        this.MNC = MNC;
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
