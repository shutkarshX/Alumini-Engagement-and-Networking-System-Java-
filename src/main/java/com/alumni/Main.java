package com.alumni;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * First runnable milestone for the Alumni Engagement and Networking System.
 * This prototype currently stores profiles in memory; persistence is planned
 * for a later phase.
 */
public class Main {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final List<AlumniProfile> PROFILES = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;
        System.out.println("==========================================");
        System.out.println(" Alumni Engagement and Networking System ");
        System.out.println("==========================================");

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> addProfile();
                case 2 -> listProfiles();
                case 3 -> searchProfiles();
                case 0 -> {
                    running = false;
                    System.out.println("Thank you for using the system.");
                }
                default -> System.out.println("Invalid option. Please choose 0, 1, 2, or 3.");
            }
        }
        INPUT.close();
    }

    private static void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add alumni/student profile");
        System.out.println("2. View all profiles");
        System.out.println("3. Search profiles by name");
        System.out.println("0. Exit");
    }

    private static void addProfile() {
        System.out.println("\n--- Add Profile ---");
        String name = readNonBlank("Full name: ");
        String role = readNonBlank("Role (Alumni/Student): ");
        String graduationYear = readNonBlank("Graduation year (or expected year): ");
        String expertise = readNonBlank("Area of expertise / study: ");
        String email = readNonBlank("Contact email: ");

        PROFILES.add(new AlumniProfile(name, role, graduationYear, expertise, email));
        System.out.println("Profile added successfully. Total profiles: " + PROFILES.size());
    }

    private static void listProfiles() {
        System.out.println("\n--- Profiles ---");
        if (PROFILES.isEmpty()) {
            System.out.println("No profiles have been added yet.");
            return;
        }

        for (int i = 0; i < PROFILES.size(); i++) {
            System.out.println((i + 1) + ". " + PROFILES.get(i));
        }
    }

    private static void searchProfiles() {
        System.out.println("\n--- Search Profiles ---");
        String query = readNonBlank("Enter a name or part of a name: ").toLowerCase();
        int matches = 0;

        for (AlumniProfile profile : PROFILES) {
            if (profile.getName().toLowerCase().contains(query)) {
                System.out.println(profile);
                matches++;
            }
        }

        if (matches == 0) {
            System.out.println("No matching profiles found.");
        } else {
            System.out.println("Matches found: " + matches);
        }
    }

    private static String readNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = INPUT.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
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

    private static final class AlumniProfile {
        private final String name;
        private final String role;
        private final String graduationYear;
        private final String expertise;
        private final String email;

        private AlumniProfile(String name, String role, String graduationYear,
                              String expertise, String email) {
            this.name = name;
            this.role = role;
            this.graduationYear = graduationYear;
            this.expertise = expertise;
            this.email = email;
        }

        private String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name + " | " + role + " | Graduation: " + graduationYear
                    + " | Expertise: " + expertise + " | Email: " + email;
        }
    }
}
