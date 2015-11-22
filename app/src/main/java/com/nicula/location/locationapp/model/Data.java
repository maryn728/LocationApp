package com.nicula.location.locationapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonGetter;

/**
 * To be used for mapping data from web service
 */
public class Data implements Parcelable {

    private String countryCode;
    private int year;
    private float value;

    public Data(String countryCode, int year, float value) {
        this.countryCode = countryCode;
        this.year = year;
        this.value = value;
    }

    public Data() {
    }

    @JsonGetter("iso-2")
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @JsonGetter("year")
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @JsonGetter("value")
    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Data)) return false;

        Data data = (Data) o;

        if (year != data.year) return false;
        return Double.compare(data.value, value) != 0 ? false : countryCode.equals(data.countryCode);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = countryCode.hashCode();
        result = 31 * result + year;
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Data{" +
                "countryCode='" + countryCode + '\'' +
                ", year=" + year +
                ", value=" + value +
                '}';
    }

    protected Data(Parcel in) {
        countryCode = in.readString();
        year = in.readInt();
        value = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(countryCode);
        dest.writeInt(year);
        dest.writeFloat(value);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}