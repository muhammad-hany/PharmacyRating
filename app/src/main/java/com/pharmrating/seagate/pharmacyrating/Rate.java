package com.pharmrating.seagate.pharmacyrating;

/**
 * Created by Muhammad Workstation on 25/09/2016.
 */

public class Rate {

    float rateValue;
    String name;
    String description;
    String phoneNumber,date;
    String pharmacyType;

    public Rate (){}
    public Rate (float rateValue,String name,String description,String phoneNumber,String date,String pharmacyType){
        this.rateValue=rateValue;
        this.name=name;
        this.description=description;
        this.phoneNumber=phoneNumber;
        this.date=date;
        this.pharmacyType=pharmacyType;
    }

    public String getDate() {
        return date;
    }

    public String getPharmacyType() {
        return pharmacyType;
    }

    public void setPharmacyType(String pharmacyType) {
        this.pharmacyType = pharmacyType;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getRateValue() {
        return rateValue;
    }

    public void setRateValue(float rateValue) {
        this.rateValue = rateValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
