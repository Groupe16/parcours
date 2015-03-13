package com.localisation.parcours.model;

/**
 * Created by Zalila on 2015-03-13.
 */
public class Address {

    private String address;
    private Coord coord;

    public static Coord getCoordFromAdr(String adr){
        return new Coord(0,0,0);
    }

    public Address() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }
}
