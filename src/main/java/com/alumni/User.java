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
        this.graduationYear = requireText(graduationYear, "Graduation year");
        this.areaOfStudyOrExpertise = requireText(areaOfStudyOrExpertise, "Area of study/expertise");
        this.email = requireText(email, "Email");
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " cannot be empty.");
        }
        return value.trim();
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
