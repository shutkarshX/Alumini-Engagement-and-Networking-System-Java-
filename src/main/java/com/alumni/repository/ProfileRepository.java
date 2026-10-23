package com.alumni.repository;

import com.alumni.User;
import java.io.IOException;
import java.util.List;

/**
 * Storage boundary for profiles. A database-backed implementation can replace
 * the file implementation later without changing the UI.
 */
public interface ProfileRepository {
    List<User> findAll() throws IOException;
    void saveAll(List<User> profiles) throws IOException;
}
