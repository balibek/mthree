package com.balibek.foundations.variables.basicprogrammingconcepts;

import java.util.Scanner;

public class HealthyHearts {

    public static void main(String[] args) {

        Scanner inputReader = new Scanner(System.in);

        int age;

        System.out.println("What is your age?");
        age = Integer.parseInt(inputReader.nextLine());

        double heartRate;

        heartRate = 220 - age;
        System.out.println("Your maximum heart rate should be " + heartRate + " beats per minute");

        double rateZoneLow = heartRate * 50 / 100;;
        double rateZoneHigh = heartRate * 85 / 100;

        int resultRZL = (int) Math.round(rateZoneLow);
        int resultRZH = (int) Math.round(rateZoneHigh);

        System.out.println("Your target HR Zone is " + resultRZL + " - " + resultRZH + " beats per minute");

    }

}
