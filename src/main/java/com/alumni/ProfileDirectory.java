package com.alumni;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * In-memory directory for alumni and student profiles.
 * Uses collections and a lambda-based comparator (OOTS Unit 2).
 */
public final class ProfileDirectory {
    private final List<User> profiles = new ArrayList<>();

    public void add(User profile) {
        if (profile == null) {
            throw new IllegalArgumentException("Profile cannot be null.");
        }
        profiles.add(profile);
    }

    public List<User> getAllSortedByName() {
        return profiles.stream()
                .sorted(Comparator.comparing(User::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public List<User> searchByName(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String normalizedQuery = query.trim().toLowerCase(Locale.ROOT);
        return profiles.stream()
                .filter(profile -> profile.getName().toLowerCase(Locale.ROOT).contains(normalizedQuery))
                .collect(Collectors.toList());
    }

    public int size() {
        return profiles.size();
    }
}
