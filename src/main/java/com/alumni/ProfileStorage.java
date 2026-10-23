package com.alumni;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/** UTF-8 file persistence for alumni and student profiles. */
public final class ProfileStorage {
    private final Path file;

    public ProfileStorage(Path file) {
        if (file == null) throw new IllegalArgumentException("Storage path cannot be null.");
        this.file = file;
    }

    public List<User> load() throws IOException {
        List<User> loaded = new ArrayList<>();
        if (!Files.exists(file)) return loaded;
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) continue;
                try {
                    loaded.add(decode(line));
                } catch (RuntimeException exception) {
                    throw new IOException("Invalid profile record at line " + lineNumber + ".", exception);
                }
            }
        }
        return loaded;
    }

    public void save(List<User> profiles) throws IOException {
        if (profiles == null) throw new IllegalArgumentException("Profiles cannot be null.");
        Path absoluteFile = file.toAbsolutePath();
        Path parent = absoluteFile.getParent();
        if (parent != null) Files.createDirectories(parent);
        Path temporaryFile = Files.createTempFile(parent, "profiles-", ".tmp");
        boolean moved = false;
        try {
            try (BufferedWriter writer = Files.newBufferedWriter(temporaryFile, StandardCharsets.UTF_8)) {
                for (User profile : profiles) {
                    writer.write(encode(profile));
                    writer.newLine();
                }
            }
            try {
                Files.move(temporaryFile, absoluteFile, StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException exception) {
                Files.move(temporaryFile, absoluteFile, StandardCopyOption.REPLACE_EXISTING);
            }
            moved = true;
        } finally {
            if (!moved) Files.deleteIfExists(temporaryFile);
        }
    }

    private String encode(User profile) {
        String type;
        String detail;
        if (profile instanceof Alumni alumni) {
            type = "A";
            detail = alumni.getCurrentOrganization();
        } else if (profile instanceof Student student) {
            type = "S";
            detail = student.getCurrentSemester();
        } else {
            throw new IllegalArgumentException("Unsupported profile type: " + profile.getClass().getName());
        }
        return String.join(",", "V2", type, field(profile.getName()), field(profile.getGraduationYear()),
                field(profile.getAreaOfStudyOrExpertise()), field(profile.getEmail()), field(detail),
                field(profile.getProfileId() == null ? "" : profile.getProfileId()));
    }

    private User decode(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length == 8 && "V2".equals(parts[0])) {
            String type = parts[1], name = value(parts[2]), year = value(parts[3]);
            String area = value(parts[4]), email = value(parts[5]), detail = value(parts[6]), id = value(parts[7]);
            boolean legacy = "Not provided".equals(year) || "Not provided".equals(area);
            return switch (type) {
                case "A" -> new Alumni(name, year, area, email, detail, id, legacy);
                case "S" -> new Student(name, year, area, email, detail, id, legacy);
                default -> throw new IllegalArgumentException("Unknown profile type.");
            };
        }
        if (parts.length != 6) throw new IllegalArgumentException("Expected six fields or eight V2 fields.");
        String type = parts[0], name = value(parts[1]), second = value(parts[2]);
        String third = value(parts[3]), fourth = value(parts[4]), fifth = value(parts[5]);
        if (isYear(second)) {
            return switch (type) {
                case "A" -> new Alumni(name, second, third, fourth, fifth);
                case "S" -> new Student(name, second, third, fourth, fifth);
                default -> throw new IllegalArgumentException("Unknown profile type.");
            };
        }
        if (("A".equals(type) && second.startsWith("ALU")) || ("S".equals(type) && second.startsWith("STU"))) {
            String year = "Not provided";
            if ("A".equals(type)) {
                String area = third.equals(fourth) ? "Not provided" : fourth;
                return new Alumni(name, year, area, third, fifth, second, true);
            }
            return new Student(name, year, fourth, third, fifth, second, true);
        }
        throw new IllegalArgumentException("Unrecognized profile record format.");
    }

    private boolean isYear(String value) {
        return value.matches("\\d{4}") && Integer.parseInt(value) >= 1900 && Integer.parseInt(value) <= 2100;
    }

    private String field(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private String value(String encoded) {
        return new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
    }
}
