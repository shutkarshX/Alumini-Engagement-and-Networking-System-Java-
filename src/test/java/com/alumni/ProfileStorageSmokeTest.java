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
