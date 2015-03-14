package com.localisation.parcours.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zalila on 2015-02-27.
 */
public class Coord implements Parcelable{

    private double longitude, latitude, altitude;

    public Coord(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Coord(double longitude, double latitude, double altitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeDouble(altitude);
    }


    // CREATOR permet de décrire au Parcel comment construire l'Objet
    public static final Parcelable.Creator<Coord> CREATOR = new Parcelable.Creator<Coord>()
    {
        @Override
        public Coord createFromParcel(Parcel source)
        {
            return new Coord(source);
        }

        @Override
        public Coord[] newArray(int size)
        {
            return new Coord[size];
        }
    };

    //Constructeur avec Parcel
    public Coord(Parcel in) {
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.altitude = in.readDouble();
    }


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    @Override
    public String toString() {
        return "longitude =" + longitude +
                ", latitude =" + latitude +
                ", altitude =" + altitude;
    }
}
