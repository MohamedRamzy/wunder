package com.wunder.test.wunderapp.model;

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
    private String carEngineType;
    private String carEnterior;
    private String carExterior;
    private String carFuel;
    // TODO declare other car attributes when used

    public Car(){

    }

    public Car(String name, float latitude, float longitude, String address, String carEngineType, String carEnterior, String carExterior, String carFuel) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.carEngineType = carEngineType;
        this.carEnterior = carEnterior;
        this.carExterior = carExterior;
        this.carFuel = carFuel;
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

    public String getCarEngineType() {
        return carEngineType;
    }

    public void setCarEngineType(String carEngineType) {
        this.carEngineType = carEngineType;
    }

    public String getCarEnterior() {
        return carEnterior;
    }

    public void setCarEnterior(String carEnterior) {
        this.carEnterior = carEnterior;
    }

    public String getCarExterior() {
        return carExterior;
    }

    public void setCarExterior(String carExterior) {
        this.carExterior = carExterior;
    }

    public String getCarFuel() {
        return carFuel;
    }

    public void setCarFuel(String carFuel) {
        this.carFuel = carFuel;
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
            // TODO use other car attributes as well if needed
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
        // TODO use other car attributes as well
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", address='" + address + '\'' +
                ", carEngineType='" + carEngineType + '\'' +
                ", carEnterior='" + carEnterior + '\'' +
                ", carExterior='" + carExterior + '\'' +
                ", carFuel='" + carFuel + '\'' +
                '}';
    }
}
