package com.balibek.foundations.variables.basicprogrammingconcepts;

import java.util.Scanner;
import java.util.Random;

public class RockPaperScissors {

    public static void main(String[] argv) {

        int round, choice;

        int tie = 0;
        int comWin = 0;
        int userWin = 0;

        Scanner inputReader = new Scanner(System.in);
        System.out.println("Welcome to Rock Paper Scissors!");

        //game begins here
        boolean startGame = true;

        while (startGame) {

            System.out.println("How many rounds do you want to play between 1 and 10?");
            round = Integer.parseInt(inputReader.nextLine());

            if (round < 11 && round > 0) {

                for (int i = 0; i < round; i++) {

                    System.out.println("What's your choice? Please Enter: 1 for Rock, 2 for Paper, 3 for Scissors.");
                    choice = Integer.parseInt(inputReader.nextLine());

                    Random rng = new Random();
                    int x = rng.nextInt(3) + 1;

                    if (choice == x) {
                        System.out.println("Tie");
                        tie++;
                    } else if (choice == 1 && x == 2 || choice == 2 && x == 3 || choice == 3 && x == 1) {
                        System.out.println("I win the round!");
                        comWin++;
                    } else if (x == 1 && choice == 2 || x == 2 && choice == 3 || x == 3 && choice == 1) {
                        System.out.println("You win the round!");
                        userWin++;
                    }
                }
                System.out.println("Tie " + tie + ". Computer win " + comWin + ". User win " + userWin + ".");

                if (comWin > userWin) {
                    System.out.println("I win!");
                } else if (comWin < userWin) {
                    System.out.println("You win!");
                }
            } else {
                System.out.println("Error");
                System.exit(0);
            }
            String answer;
            System.out.println("Do you want to play again? Please enter:  Yes or  No.");

            answer = inputReader.nextLine();

            if (answer.equals("No"))  {

                System.out.println("Thanks for playing!");
                startGame = false;
                
            } else if (answer.equals("Yes")) {
                startGame = true;
            }
        }
    }

}
