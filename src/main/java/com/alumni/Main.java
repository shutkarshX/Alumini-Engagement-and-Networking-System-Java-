package com.alumni;

import java.util.List;
import java.util.Scanner;

/**
 * Phase 1 console application for the Alumni Engagement and Networking System.
 * The first increment focuses on Java fundamentals and core OOP concepts.
 */
public final class Main {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final ProfileDirectory DIRECTORY = new ProfileDirectory();

    private Main() { }

    public static void main(String[] args) {
        boolean running = true;
        System.out.println("==========================================");
        System.out.println(" Alumni Engagement and Networking System ");
        System.out.println("==========================================");

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");
            try {
                switch (choice) {
                    case 1 -> addAlumni();
                    case 2 -> addStudent();
                    case 3 -> showAllProfiles();
                    case 4 -> searchProfiles();
                    case 0 -> {
                        running = false;
                        System.out.println("Thank you for using the system.");
                    }
                    default -> System.out.println("Invalid option. Please choose 0–4.");
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Could not register profile: " + exception.getMessage());
            }
        }
        INPUT.close();
    }

    private static void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Register alumni profile");
        System.out.println("2. Register student profile");
        System.out.println("3. View all profiles (sorted by name)");
        System.out.println("4. Search profiles by name");
        System.out.println("0. Exit");
    }

    private static void addAlumni() {
        System.out.println("\n--- Alumni Registration ---");
        String name = readNonBlank("Full name: ");
        String year = readNonBlank("Graduation year: ");
        String expertise = readNonBlank("Area of expertise: ");
        String email = readNonBlank("Contact email: ");
        String organization = readNonBlank("Current organization: ");

        DIRECTORY.add(new Alumni(name, year, expertise, email, organization));
        System.out.println("Alumni profile registered. Total profiles: " + DIRECTORY.size());
    }

    private static void addStudent() {
        System.out.println("\n--- Student Registration ---");
        String name = readNonBlank("Full name: ");
        String expectedYear = readNonBlank("Expected graduation year: ");
        String area = readNonBlank("Area of study: ");
        String email = readNonBlank("Contact email: ");
        String semester = readNonBlank("Current semester: ");

        DIRECTORY.add(new Student(name, expectedYear, area, email, semester));
        System.out.println("Student profile registered. Total profiles: " + DIRECTORY.size());
    }

    private static void showAllProfiles() {
        System.out.println("\n--- Profile Directory ---");
        List<User> profiles = DIRECTORY.getAllSortedByName();
        if (profiles.isEmpty()) {
            System.out.println("No profiles have been added yet.");
            return;
        }
        profiles.forEach(profile -> System.out.println("- " + profile.getSummary()));
        System.out.println("Total profiles: " + profiles.size());
    }

    private static void searchProfiles() {
        String query = readNonBlank("\nEnter a name or part of a name: ");
        List<User> matches = DIRECTORY.searchByName(query);
        if (matches.isEmpty()) {
            System.out.println("No matching profiles found.");
            return;
        }
        matches.forEach(profile -> System.out.println("- " + profile.getSummary()));
        System.out.println("Matches found: " + matches.size());
    }

    private static String readNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = INPUT.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("This field cannot be empty.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = INPUT.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }
}
