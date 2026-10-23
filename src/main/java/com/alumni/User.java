package com.alumni;

/**
 * Shared profile information for people using the alumni network.
 * Demonstrates abstraction and encapsulation (OOTS Unit 1).
 */
public abstract class User {
    private final String name;
    private final String graduationYear;
    private final String areaOfStudyOrExpertise;
    private final String email;

    protected User(String name, String graduationYear,
                   String areaOfStudyOrExpertise, String email) {
        this.name = requireText(name, "Name");
        this.graduationYear = requireYear(graduationYear);
        this.areaOfStudyOrExpertise = requireText(areaOfStudyOrExpertise, "Area of study/expertise");
        this.email = requireEmail(email);
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " cannot be empty.");
        }
        return value.trim();
    }

    private static String requireYear(String value) {
        String year = requireText(value, "Graduation year");
        if (!year.matches("\\d{4}")) {
            throw new IllegalArgumentException("Graduation year must contain exactly four digits.");
        }
        int numericYear = Integer.parseInt(year);
        if (numericYear < 1900 || numericYear > 2100) {
            throw new IllegalArgumentException("Graduation year must be between 1900 and 2100.");
        }
        return year;
    }

    private static String requireEmail(String value) {
        String email = requireText(value, "Email");
        if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException("Enter a valid email address (for example, name@example.com).");
        }
        return email;
    }

    public String getName() { return name; }
    public String getGraduationYear() { return graduationYear; }
    public String getAreaOfStudyOrExpertise() { return areaOfStudyOrExpertise; }
    public String getEmail() { return email; }

    /** Subclasses provide their own profile label and details. */
    public abstract String getRole();

    public String getSummary() {
        return String.format("%s | %s | Year: %s | Area: %s | Email: %s",
                name, getRole(), graduationYear, areaOfStudyOrExpertise, email);
    }
}
