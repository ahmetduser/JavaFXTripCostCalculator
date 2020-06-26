/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tripcostcalculator;

import java.io.Serializable;

/**
 *
 * @author ahmetduser
 */
public class Parameters implements Serializable {

    double distance;
    double MPG;
    String fuelType;
    double cost;

    public Parameters() {

    }

    public Parameters(double distance, double MPG, String fuelType, double cost) {
        this.distance = distance;
        this.MPG = MPG;
        this.fuelType = fuelType;
        this.cost = cost;
    }

    // setters
    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setMPG(double MPG) {
        this.MPG = MPG;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    // getters
    public double getDistance() {
        return distance;
    }

    public double getMPG() {
        return MPG;
    }

    public String getFuelType() {
        return fuelType;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "\nDistance:" + getDistance() + " MPG:" + getMPG() + " Fuel Type:" + getFuelType() + " Cost:" + getCost();
    }

}
