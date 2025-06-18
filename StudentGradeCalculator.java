// TASK 2
// Student Grade Calculator

import java.util.Scanner;

public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Number of Subjects
        System.out.print("Enter the number of subjects: ");
        int numSubjects;
        
        // Checking correct number of subject
        if (scanner.hasNextInt()) {
            numSubjects = scanner.nextInt();
            if (numSubjects <= 0) {
                System.out.println("Number of subjects must be greater than 0!");
                scanner.close();
                return;
            }
        } else {
            System.out.println("Please enter a valid number!");
            scanner.close();
            return;
        }
        
        // Array to store marks
        int[] marks = new int[numSubjects];
        int totalMarks = 0;
        
        // Marks of Each Subjects
        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter marks for subject " + (i + 1) + " (out of 100): ");
            if (scanner.hasNextInt()) {
                int mark = scanner.nextInt();
                // Checking correct marks within range
                if (mark >= 0 && mark <= 100) {
                    marks[i] = mark;
                    totalMarks += mark;
                } else {
                    System.out.println("Marks must be between 0 and 100! Please try again.");
                    i--; // Retry for the same subject
                }
            } else {
                System.out.println("Please enter a valid number!");
                scanner.next(); // Clear invalid input
                i--; // Retry for the same subject
            }
        }
        
        // Calculate average percentage
        double averagePercentage = (double) totalMarks / numSubjects;
        
        // Grade Calculation
        String grade;
        if (averagePercentage >= 90) {
            grade = "A+";
        } else if (averagePercentage >= 80) {
            grade = "A";
        } else if (averagePercentage >= 70) {
            grade = "B";
        } else if (averagePercentage >= 60) {
            grade = "C";
        } else if (averagePercentage >= 50) {
            grade = "D";
        } else {
            grade = "F";
        }
        
        // Display Results
        System.out.println("\n--- Results ---");
        System.out.println("Total Marks: " + totalMarks + " out of " + (numSubjects * 100));
        System.out.printf("Average Percentage: %.2f%%\n", averagePercentage);
        System.out.println("Grade: " + grade);
        
        scanner.close();
    }
}