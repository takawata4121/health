package com.example.myhealth1;

public class Bmi {
    private static final double[] bmiList = { 18.5, 25.0, 30.0, 35.0, 40.0 };
    private static final String[] statusList = {"低体重", "普通体重", "肥満1度", "肥満2度", "肥満3度", "肥満4度"};
    private double height;
    private double weight;
    public Bmi() {
        this(65.0, 175.0);
    }
    public Bmi(double weight, double height) {
        this.weight = weight;
        this.height = height;
    }
    public double bmi() {
        return weight/Math.pow(height*0.01, 2);
    }
    public String getFatStatus() {
        int i = 0;
        for (; i<bmiList.length; i++)
            if (bmi()<bmiList[i])
                break;
        return statusList[i];
    }
}
