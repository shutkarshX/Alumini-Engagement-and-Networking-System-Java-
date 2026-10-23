package com.alumni;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** Dependency-free smoke tests for ProfileStorage; run with assertions enabled. */
public final class ProfileStorageSmokeTest {
    private ProfileStorageSmokeTest() { }

    public static void main(String[] args) throws Exception {
        Path temporaryDirectory = Files.createTempDirectory("alumni-storage-test-");
        try {
            testMissingFileLoadsEmptyList(temporaryDirectory);
            testAlumniAndStudentRoundTrip(temporaryDirectory);
            testLegacyIdRecordsLoadAndRoundTrip(temporaryDirectory);
            testMalformedRecordFailsClearly(temporaryDirectory);
            System.out.println("PASS: all ProfileStorage smoke tests");
        } finally {
            try (var paths = Files.walk(temporaryDirectory)) {
                paths.sorted((left, right) -> right.compareTo(left)).forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException exception) {
                        throw new RuntimeException(exception);
                    }
                });
            }
        }
    }

    private static void testMissingFileLoadsEmptyList(Path directory) throws IOException {
        ProfileStorage storage = new ProfileStorage(directory.resolve("missing.csv"));
        check(storage.load().isEmpty(), "A missing data file should load as an empty list.");
        System.out.println("PASS: missing file loads empty list");
    }

    private static void testAlumniAndStudentRoundTrip(Path directory) throws IOException {
        Path file = directory.resolve("nested").resolve("profiles.csv");
        ProfileStorage storage = new ProfileStorage(file);
        Alumni alumni = new Alumni("Aditi, Sharma", "2026", "Data Science / 分析",
                "aditi@example.com", "Example, Inc.");
        Student student = new Student("Rahul Verma", "2028", "Computer Science",
                "rahul@example.com", "Semester 3");

        storage.save(List.of(alumni, student));
        List<User> loaded = storage.load();
        check(loaded.size() == 2, "Expected two profiles after reload.");
        check(loaded.get(0) instanceof Alumni, "First profile should remain an Alumni.");
        Alumni restoredAlumni = (Alumni) loaded.get(0);
        check(restoredAlumni.getName().equals("Aditi, Sharma"), "Comma in name was not preserved.");
        check(restoredAlumni.getAreaOfStudyOrExpertise().equals("Data Science / 分析"),
                "Unicode field was not preserved.");
        check(restoredAlumni.getCurrentOrganization().equals("Example, Inc."),
                "Comma in organization was not preserved.");
        check(loaded.get(1) instanceof Student, "Second profile should remain a Student.");
        Student restoredStudent = (Student) loaded.get(1);
        check(restoredStudent.getCurrentSemester().equals("Semester 3"),
                "Student-specific field was not preserved.");
        System.out.println("PASS: alumni/student round-trip, punctuation and Unicode");
    }


    private static void testLegacyIdRecordsLoadAndRoundTrip(Path directory) throws IOException {
        Path file = directory.resolve("legacy.csv");
        String alumni = String.join(",", "A", enc("Test Alumni"), enc("ALU2026001"),
                enc("alumni.test@example.com"), enc("alumni.test@example.com"), enc("Example Technologies"));
        String student = String.join(",", "S", enc("Test Student"), enc("STU2026001"),
                enc("student.test@example.com"), enc("Computer Science"), enc("2"));
        Files.writeString(file, alumni + System.lineSeparator() + student + System.lineSeparator(),
                StandardCharsets.UTF_8);
        ProfileStorage storage = new ProfileStorage(file);
        List<User> loaded = storage.load();
        check(loaded.size() == 2, "Both legacy profiles should load.");
        Alumni a = (Alumni) loaded.get(0);
        check("ALU2026001".equals(a.getProfileId()), "Alumni ID must be preserved.");
        check("Not provided".equals(a.getGraduationYear()), "Missing year should be explicit.");
        check("Not provided".equals(a.getAreaOfStudyOrExpertise()), "Duplicated email must not be treated as expertise.");
        Student s = (Student) loaded.get(1);
        check("STU2026001".equals(s.getProfileId()), "Student ID must be preserved.");
        check("Computer Science".equals(s.getAreaOfStudyOrExpertise()), "Student area should be preserved.");
        check("2".equals(s.getCurrentSemester()), "Student semester should be preserved.");
        storage.save(loaded);
        List<User> roundTrip = storage.load();
        check(roundTrip.size() == 2, "Legacy profiles must survive save/reload.");
        check("ALU2026001".equals(roundTrip.get(0).getProfileId()), "Alumni ID lost on round-trip.");
        check("STU2026001".equals(roundTrip.get(1).getProfileId()), "Student ID lost on round-trip.");
        System.out.println("PASS: legacy ID records load and round-trip");
    }

    private static String enc(String value) {
        return java.util.Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private static void testMalformedRecordFailsClearly(Path directory) throws IOException {
        Path file = directory.resolve("malformed.csv");
        Files.writeString(file, "not,a,valid,profile\n", StandardCharsets.UTF_8);
        boolean failed = false;
        try {
            new ProfileStorage(file).load();
        } catch (IOException exception) {
            failed = exception.getMessage().contains("line 1");
        }
        check(failed, "Malformed data should raise an IOException identifying its line.");
        System.out.println("PASS: malformed record raises a clear IOException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
