package com.karolis.dailycalories;

public class BMICalcUtil {
    public static final BMICalcUtil instance = new BMICalcUtil();

    private static final int CENTIMETERS_IN_METER = 100;
    private static final int INCHES_IN_FOOT = 12;
    private static final int BMI_IMPERIAL_WEIGHT_SCALAR = 703;

    public static final String BMI_CATEGORY_UNDERWEIGHT = "Your BMI is less than 18.5, that indicates that you are underweight. You are recommended to see your doctor for advice";
    public static final String BMI_CATEGORY_HEALTHY = "Your BMI is at Healthy Weight Range,it indicates that you are at a healthy weight for your height!";
    public static final String BMI_CATEGORY_OVERWEIGHT = "A BMI of 25-29.9 indicates that you are slightly overweight, in order to avoid health issues in the future speak with your doctor";
    public static final String BMI_CATEGORY_OBESE = "A BMI over 30 indicates that you are heavily overweight. Your health might be at risk if you won't lose some weight. You are highly recommended to see doctor for advice";


    public static BMICalcUtil getInstance() {
        return instance;
    }

    public double calculateBMIMetric(double heightCm, double weightKg) {
        return (weightKg / ((heightCm / CENTIMETERS_IN_METER) * (heightCm / CENTIMETERS_IN_METER)));
    }

    public double calculateBMIImperial(double heightFeet, double heightInches, double weightLbs) {
        double totalHeightInInches = (heightFeet * INCHES_IN_FOOT) + heightInches;
        return (BMI_IMPERIAL_WEIGHT_SCALAR * weightLbs) / (totalHeightInInches * totalHeightInInches);
    }




    public double calculateBMRMetricMan(double heightCm, double weightKg, double age){
        return((10 * weightKg) + (6.25 * heightCm) - (5 * age) + 5);
    }


    public double calculateBMRImperialMan(double heightFeet, double heightInches, double weightLbs, double age){
        double totalHeightInInches = (heightFeet * INCHES_IN_FOOT) + heightInches;
        return((4.536 * weightLbs) + (15.88 * totalHeightInInches) - (5 * age) + 5);
    }

    public double calculateBMRMetricWoman(double heightCm, double weightKg, double age){
        return((10 * weightKg) + (6.25 * heightCm) - (5 * age) - 161);
    }


    public double calculateBMRImperialWoman(double heightFeet, double heightInches, double weightLbs, double age){
        double totalHeightInInches = (heightFeet * INCHES_IN_FOOT) + heightInches;
        return((4.536 * weightLbs) + (15.88 * totalHeightInInches) - (5 * age) -161);
    }





    public String classifyBMI(double bmi) {
        if (bmi < 18.5) {
            return BMI_CATEGORY_UNDERWEIGHT;
        } else if (bmi >= 18.5 && bmi < 25) {
            return BMI_CATEGORY_HEALTHY;
        } else if (bmi >= 25 && bmi < 30){
            return BMI_CATEGORY_OVERWEIGHT;
        } else {
            return BMI_CATEGORY_OBESE;
        }
    }
}
