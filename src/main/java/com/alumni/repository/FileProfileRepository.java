package com.alumni.repository;

import com.alumni.Alumni;
import com.alumni.Student;
import com.alumni.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * CSV-like UTF-8 storage. Each field is Base64-encoded so commas, quotes,
 * and newlines in user-entered values cannot corrupt the file structure.
 */
public final class FileProfileRepository implements ProfileRepository {
    private static final String HEADER = "# Alumni Engagement and Networking System data v1";
    private final Path file;

    public FileProfileRepository() {
        this(Paths.get("data", "profiles.csv"));
    }

    public FileProfileRepository(Path file) {
        if (file == null) {
            throw new IllegalArgumentException("Storage path cannot be null.");
        }
        this.file = file;
    }

    @Override
    public List<User> findAll() throws IOException {
        List<User> profiles = new ArrayList<>();
        if (!Files.exists(file)) {
            return profiles;
        }

        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank() || line.startsWith("#")) {
                    continue;
                }
                String[] fields = line.split(",", -1);
                if (fields.length != 6) {
                    throw new IOException("Invalid profile record at line " + lineNumber + ".");
                }
                try {
                    String role = decode(fields[0]);
                    String name = decode(fields[1]);
                    String year = decode(fields[2]);
                    String area = decode(fields[3]);
                    String email = decode(fields[4]);
                    String detail = decode(fields[5]);
                    if ("ALUMNI".equals(role)) {
                        profiles.add(new Alumni(name, year, area, email, detail));
                    } else if ("STUDENT".equals(role)) {
                        profiles.add(new Student(name, year, area, email, detail));
                    } else {
                        throw new IOException("Unknown profile type at line " + lineNumber + ".");
                    }
                } catch (IllegalArgumentException exception) {
                    throw new IOException("Could not read profile at line " + lineNumber + ".", exception);
                }
            }
        }
        return profiles;
    }

    @Override
    public void saveAll(List<User> profiles) throws IOException {
        if (profiles == null) {
            throw new IllegalArgumentException("Profiles cannot be null.");
        }
        Path parent = file.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            writer.write(HEADER);
            writer.newLine();
            for (User profile : profiles) {
                if (profile instanceof Alumni alumni) {
                    writeRecord(writer, "ALUMNI", profile, alumni.getCurrentOrganization());
                } else if (profile instanceof Student student) {
                    writeRecord(writer, "STUDENT", profile, student.getCurrentSemester());
                } else {
                    throw new IOException("Unsupported profile type: " + profile.getClass().getName());
                }
            }
        }
    }

    private static void writeRecord(BufferedWriter writer, String role, User user, String detail)
            throws IOException {
        writer.write(encode(role) + "," + encode(user.getName()) + ","
                + encode(user.getGraduationYear()) + "," + encode(user.getAreaOfStudyOrExpertise())
                + "," + encode(user.getEmail()) + "," + encode(detail));
        writer.newLine();
    }

    private static String encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private static String decode(String value) {
        return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
    }
}
