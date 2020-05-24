package com.petrolpump.util;

import java.util.Date;

public class Totalizer {

    private String id;
    private double startTotalizer;
    private double endTotalizer;
    private double fuelPrice;
    private Date createdDate;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public double getStartTotalizer() {
        return startTotalizer;
    }

    public void setStartTotalizer(double startTotalizer) {
        this.startTotalizer = startTotalizer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getEndTotalizer() {
        return endTotalizer;
    }

    public void setEndTotalizer(double endTotalizer) {
        this.endTotalizer = endTotalizer;
    }

    public double getFuelPrice() {
        return fuelPrice;
    }

    public void setFuelPrice(double fuelPrice) {
        this.fuelPrice = fuelPrice;
    }
}
