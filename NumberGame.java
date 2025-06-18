// TASK 1
// Number Game

import java.util.Random;
import java.util.Scanner;

public class NumberGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int minRange = 1;
        int maxRange = 100;
        int maxAttempts = 10;
        int totalScore = 0;
        int roundsPlayed = 0;
        
        System.out.println("Welcome to the Number Guessing Game!");
        
        while (true) {
            roundsPlayed++;
            int numberToGuess = random.nextInt(maxRange - minRange + 1) + minRange;
            int attemptsLeft = maxAttempts;
            boolean hasWon = false;
            
            System.out.println("\nRound " + roundsPlayed + ": I'm thinking of a number between " + minRange + " and " + maxRange);
            System.out.println("You have " + maxAttempts + " attempts to guess it!");
            
            while (attemptsLeft > 0) {
                System.out.print("Enter your guess (" + minRange + "-" + maxRange + "): ");
                int userGuess;
                
                // Check valid input
                if (scanner.hasNextInt()) {
                    userGuess = scanner.nextInt();
                    // Validate guess within range
                    if (userGuess < minRange || userGuess > maxRange) {
                        System.out.println("Please enter a number between " + minRange + " and " + maxRange + "!");
                        continue;
                    }
                } else {
                    System.out.println("Please enter a valid number!");
                    scanner.next(); // Clear invalid input
                    continue;
                }
                
                attemptsLeft--;
                
                if (userGuess == numberToGuess) {
                    hasWon = true;
                    int points = attemptsLeft + 1; // More points for fewer attempts
                    totalScore += points;
                    System.out.println("Congratulations! You guessed it right in " + (maxAttempts - attemptsLeft) + " attempts!");
                    System.out.println("You earned " + points + " points this round!");
                    break;
                } else if (userGuess < numberToGuess) {
                    System.out.println("Too low! Attempts left: " + attemptsLeft);
                } else {
                    System.out.println("Too high! Attempts left: " + attemptsLeft);
                }
                
                if (attemptsLeft == 0 && !hasWon) {
                    System.out.println("Game Over! The number was " + numberToGuess);
                }
            }
            
            System.out.println("Current Score: " + totalScore + " points after " + roundsPlayed + " rounds");
            
            // Ask to play another round
            System.out.print("Do you want to play another round? (yes/no): ");
            scanner.nextLine(); // Clear the buffer
            String playAgain = scanner.nextLine().toLowerCase();
            
            if (!playAgain.equals("yes")) {
                System.out.println("Thanks for playing!");
                System.out.println("Final Score: " + totalScore + " points in " + roundsPlayed + " rounds");
                break;
            }
        }
        
        scanner.close();
    }
}