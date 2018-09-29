package com.wunder.test.wunderapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mmahfouz on 9/28/2018.
 */
public class Car implements Parcelable{

    private String name;
    private float latitude;
    private float longitude;
    private String address;
    // TODO declare other car attributes when used


    public Car(){

    }

    public Car(String name, float latitude, float longitude, String address){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        // TODO pass & init other car attributes when used
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Car> CREATOR = new Parcelable.Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel source) {
            Car car = new Car();
            car.setName(source.readString());
            car.setLatitude(source.readFloat());
            car.setLongitude(source.readFloat());
            car.setAddress(source.readString());
            // TODO use other car attributes as well
            return car;
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getName());
        dest.writeFloat(getLatitude());
        dest.writeFloat(getLongitude());
        dest.writeString(getAddress());
        // TODO use other car attributes as well
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", address='" + address + '\'' +
                '}';
        // TODO use other car attributes as well
    }
}
