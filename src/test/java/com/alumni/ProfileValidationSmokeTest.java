package com.alumni;

/** Dependency-free checks for profile field validation. */
public final class ProfileValidationSmokeTest {
    private ProfileValidationSmokeTest() { }

    public static void main(String[] args) {
        testValidProfiles();
        testInvalidGraduationYear();
        testInvalidEmail();
        testInvalidSemester();
        System.out.println("PASS: all profile validation smoke tests");
    }

    private static void testValidProfiles() {
        new Alumni("Aditi Sharma", "2026", "Data Science",
                "aditi@example.com", "Example Tech");
        new Student("Rahul Verma", "2028", "Computer Science",
                "rahul@example.com", "Semester 3");
        new Student("Meera Singh", "2029", "Computer Science",
                "meera@example.com", "4");
        System.out.println("PASS: valid alumni/student profiles accepted");
    }

    private static void testInvalidGraduationYear() {
        expectInvalid(() -> new Alumni("Test User", "ALU2026001", "Data Science",
                "test@example.com", "Example Tech"), "Graduation year");
        expectInvalid(() -> new Student("Test User", "20x8", "Computer Science",
                "test@example.com", "2"), "Graduation year");
        System.out.println("PASS: invalid graduation years rejected");
    }

    private static void testInvalidEmail() {
        expectInvalid(() -> new Alumni("Test User", "2026", "Data Science",
                "not-an-email", "Example Tech"), "Email");
        expectInvalid(() -> new Student("Test User", "2028", "Computer Science",
                "student@invalid", "2"), "Email");
        System.out.println("PASS: invalid email addresses rejected");
    }

    private static void testInvalidSemester() {
        expectInvalid(() -> new Student("Test User", "2028", "Computer Science",
                "student@example.com", "9"), "semester");
        expectInvalid(() -> new Student("Test User", "2028", "Computer Science",
                "student@example.com", "second"), "semester");
        System.out.println("PASS: invalid student semesters rejected");
    }

    private static void expectInvalid(Runnable action, String expectedMessage) {
        try {
            action.run();
        } catch (IllegalArgumentException exception) {
            if (exception.getMessage().toLowerCase().contains(expectedMessage.toLowerCase())) {
                return;
            }
            throw new AssertionError("Unexpected validation message: " + exception.getMessage());
        }
        throw new AssertionError("Expected validation to reject invalid input: " + expectedMessage);
    }
}
