package com.example.myhealth1;

public class HealthStatus {
    private double weight;
    private double height;
    private double bmi;
    private String status;
    private String lastupdate;

    public HealthStatus() {
        this(65.0, 170.0);
    }

    public HealthStatus(double weight, double height) {
        this.weight = weight;
        this.height = height;
        Bmi bmiObj = new Bmi(weight, height);
        bmi = bmiObj.bmi();
        status = bmiObj.getFatStatus();
    }

    // getter, setter
    public double getWeight() { return weight; }
    public double getHeight() { return height; }
    public double getBmi() { return bmi; }
    public String getStatus() { return status; }
    public String getLastupdate() { return lastupdate; }
    public void setLastupdate(String lastupdate) { this.lastupdate = lastupdate; }
}
