package com.example.miguel.placestore;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;


public class listPlaces implements Parcelable {
    private String place = null;
    private String coord = null;
    private Drawable markLogo = null;

    public listPlaces() {

    }

    public listPlaces(Parcel source) {
        place = source.readString();
        coord = source.readString();

        Bitmap bitmap = (Bitmap) source.readParcelable(((Object) this).getClass().getClassLoader());
        if ( bitmap != null ) {
            markLogo = new BitmapDrawable(bitmap);
        }
        else {
            markLogo = null;
        }

    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(place);
        dest.writeString(coord);

        if (markLogo != null) {
            Bitmap bitmap = ((BitmapDrawable) markLogo).getBitmap();
            dest.writeParcelable(bitmap, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
    }

    public static final Parcelable.Creator<listPlaces> CREATOR  = new Parcelable.Creator<listPlaces>() {
        public listPlaces createFromParcel(Parcel in) {
            return new listPlaces(in);
        }

        public listPlaces[] newArray(int size) {
            return new listPlaces[size];
        }
    };

    @Override
    public int hashCode() {
        int result = place != null ? place.hashCode() : 0;
        result = 31 * result + (coord != null ? coord.hashCode() : 0);
        result = 31 * result + (markLogo != null ? markLogo.hashCode() : 0);
        return result;
    }

    public Drawable getMarkLogo() {
        return markLogo;
    }

    public void setMarkLogoLogo(Drawable compLogo) {
        this.markLogo = compLogo;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String company) {
        this.place = company;
    }

    public String getCoord() {
        return coord;
    }

    public void setCoord(String coord) {
        this.coord = coord;
    }


}