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
        if (profile instanceof Alumni alumni) {
            return String.join(",", "A", field(alumni.getName()), field(alumni.getGraduationYear()),
                    field(alumni.getAreaOfStudyOrExpertise()), field(alumni.getEmail()),
                    field(alumni.getCurrentOrganization()));
        }
        if (profile instanceof Student student) {
            return String.join(",", "S", field(student.getName()), field(student.getGraduationYear()),
                    field(student.getAreaOfStudyOrExpertise()), field(student.getEmail()),
                    field(student.getCurrentSemester()));
        }
        throw new IllegalArgumentException("Unsupported profile type: " + profile.getClass().getName());
    }

    private User decode(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 6) throw new IllegalArgumentException("Expected six fields.");
        String name = value(parts[1]);
        String year = value(parts[2]);
        String area = value(parts[3]);
        String email = value(parts[4]);
        String detail = value(parts[5]);
        return switch (parts[0]) {
            case "A" -> new Alumni(name, year, area, email, detail);
            case "S" -> new Student(name, year, area, email, detail);
            default -> throw new IllegalArgumentException("Unknown profile type.");
        };
    }

    private String field(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private String value(String encoded) {
        return new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
    }
}
