package com.example.saifil.geotollcontroller;

public class TollCharge {

    double truck;
    double fourWheeler;
    double twoWheeler;

    public TollCharge() {
    }

    public TollCharge(double truck, double fourWheeler, double twoWheeler) {
        this.truck = truck;
        this.fourWheeler = fourWheeler;
        this.twoWheeler = twoWheeler;
    }

    public void setTruck(double truck) {
        this.truck = truck;
    }

    public void setFourWheeler(double fourWheeler) {
        this.fourWheeler = fourWheeler;
    }

    public void setTwoWheeler(double twoWheeler) {
        this.twoWheeler = twoWheeler;
    }

    public double getTruck() {
        return truck;
    }

    public double getFourWheeler() {
        return fourWheeler;
    }

    public double getTwoWheeler() {
        return twoWheeler;
    }
}
