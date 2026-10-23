package com.alumni;

/** Student-specific profile; demonstrates inheritance and method overriding. */
public final class Student extends User {
    private final String currentSemester;

    public Student(String name, String expectedGraduationYear, String areaOfStudy,
                   String email, String currentSemester) {
        super(name, expectedGraduationYear, areaOfStudy, email);
        this.currentSemester = requireSemester(currentSemester);
    }

    private static String requireSemester(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Current semester cannot be empty.");
        }
        String semester = value.trim();
        if (!semester.matches("(?i)(?:semester\\s*)?[1-8]")) {
            throw new IllegalArgumentException("Current semester must be from 1 to 8 (for example, 3 or Semester 3).");
        }
        return semester;
    }

    public String getCurrentSemester() { return currentSemester; }

    @Override
    public String getRole() { return "Student"; }

    @Override
    public String getSummary() {
        return super.getSummary() + " | Current semester: " + currentSemester;
    }
}
