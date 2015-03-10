package com.localisation.parcours.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zalila on 2015-02-27.
 */
public class PAWifi implements Parcelable{

    private int id;
    private String pa,bssid,ssid,rss;

    public PAWifi() {
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
        dest.writeString(pa);
        dest.writeString(bssid);
        dest.writeString(ssid);
        dest.writeString(rss);
    }

    // CREATOR permet de décrire au Parcel comment construire l'Objet
    public static final Parcelable.Creator<PAWifi> CREATOR = new Parcelable.Creator<PAWifi>()
    {
        @Override
        public PAWifi createFromParcel(Parcel source)
        {
            return new PAWifi(source);
        }

        @Override
        public PAWifi[] newArray(int size)
        {
            return new PAWifi[size];
        }
    };

    //Constructeur avec Parcel
    public PAWifi(Parcel in) {
        this.id = in.readInt();
        this.pa = in.readString();
        this.bssid = in.readString();
        this.ssid = in.readString();
        this.rss = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getRss() {
        return rss;
    }

    public void setRss(String rss) {
        this.rss = rss;
    }
}
