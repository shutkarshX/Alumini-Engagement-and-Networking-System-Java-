package com.alumni;

/** Student-specific profile; demonstrates inheritance and method overriding. */
public final class Student extends User {
    private final String currentSemester;

    public Student(String name, String expectedGraduationYear, String areaOfStudy,
                   String email, String currentSemester) {
        super(name, expectedGraduationYear, areaOfStudy, email);
        if (currentSemester == null || currentSemester.trim().isEmpty()) {
            throw new IllegalArgumentException("Current semester cannot be empty.");
        }
        this.currentSemester = currentSemester.trim();
    }

    public String getCurrentSemester() { return currentSemester; }

    @Override
    public String getRole() { return "Student"; }

    @Override
    public String getSummary() {
        return super.getSummary() + " | Current semester: " + currentSemester;
    }
}
