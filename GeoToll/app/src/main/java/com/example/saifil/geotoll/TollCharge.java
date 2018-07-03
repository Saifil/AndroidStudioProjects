package com.example.saifil.geotoll;

public class TollCharge {

    double fourWheeler;
    double truck;
    double twoWheeler;

    public TollCharge() {
    }

    public TollCharge(double fourWheeler, double truck, double twoWheeler) {
        this.fourWheeler = fourWheeler;
        this.truck = truck;
        this.twoWheeler = twoWheeler;
    }

    public void setFourWheeler(double fourWheeler) {
        this.fourWheeler = fourWheeler;
    }

    public void setTruck(double truck) {
        this.truck = truck;
    }

    public void setTwoWheeler(double twoWheeler) {
        this.twoWheeler = twoWheeler;
    }

    public double getFourWheeler() {
        return fourWheeler;
    }

    public double getTruck() {
        return truck;
    }

    public double getTwoWheeler() {
        return twoWheeler;
    }
}
