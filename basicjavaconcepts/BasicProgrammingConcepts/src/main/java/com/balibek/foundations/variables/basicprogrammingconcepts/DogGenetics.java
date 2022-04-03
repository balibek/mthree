package com.balibek.foundations.variables.basicprogrammingconcepts;

import java.util.Scanner;
import java.util.Random;

public class DogGenetics {

    public static void main(String[] args) {

        Scanner inputReader = new Scanner(System.in);

        String dogName;

        System.out.println("What is your dog's name? ");
        dogName = inputReader.nextLine();

        System.out.println("Well then, I have this highly reliable report on " + dogName + "'s prestigious background right here.");

        System.out.println(dogName + " is:");

        Random rng = new Random();

        int[] per = new int[5];

        per[0] = rng.nextInt(100);
        per[1] = rng.nextInt(100 - per[0]);
        per[2] = rng.nextInt(100 - per[0] - per[1]);
        per[3] = rng.nextInt(100 - per[0] - per[1] - per[2]);
        per[4] = 100 - per[1] - per[0] - per[2] - per[3];

        String[] breeds = new String[]{"St. Bernard", "Chihuahua", "Dramatic RedNosed Asian Pug", "Common Cur", "King Doberman", "Sloughi",
            "Afador", "Akbash", "Barbet", "Beage", "Borzoi", "Labradoodle", "Mudi", "Puli", "Pug", "Welsh Terrier",
            "Barbet", "Basenji", "Chipin", "Kuvasz", "Norwegian Buhund", "Otterhound", "Pointer", "Vizsla"};
        for (int i = 0; i < 5; i++) {
            int randomIndex = rng.nextInt(breeds.length);
            String randomName = breeds[randomIndex];

            System.out.println("% " + per[i] + " " + randomName);

        }

    }

}
