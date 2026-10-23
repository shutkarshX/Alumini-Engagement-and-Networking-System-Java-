package com.alumni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/** Dependency-free integration smoke test for the TCP directory service. */
public final class DirectoryServerSmokeTest {
    private DirectoryServerSmokeTest() { }

    public static void main(String[] args) throws Exception {
        ProfileDirectory directory = new ProfileDirectory();
        directory.add(new Alumni("Aditi Sharma", "2026", "Data Science",
                "aditi@example.com", "Example Tech"));
        directory.add(new Student("Rahul Verma", "2028", "Computer Science",
                "rahul@example.com", "Semester 3"));

        try (DirectoryServer server = new DirectoryServer(directory, 0)) {
            server.start();
            String list = request(server.getPort(), "LIST");
            check(list.contains("Aditi Sharma") && list.contains("Rahul Verma"),
                    "LIST should return both directory profiles.");

            String search = request(server.getPort(), "SEARCH aditi");
            check(search.contains("Aditi Sharma") && !search.contains("Rahul Verma"),
                    "SEARCH should return only matching profiles.");

            String invalid = request(server.getPort(), "SEARCH   ");
            check(invalid.contains("ERROR:") || invalid.contains("No matching profiles."),
                    "Blank search should be handled without crashing.");
            System.out.println("PASS: TCP LIST and SEARCH smoke tests");
        }
    }

    private static String request(int port, String command) throws IOException {
        StringBuilder responseText = new StringBuilder();
        try (Socket socket = new Socket("127.0.0.1", port);
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
             BufferedReader input = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
            output.println(command);
            String line;
            while ((line = input.readLine()) != null && !line.equals("END")) {
                responseText.append(line).append('\n');
            }
        }
        return responseText.toString();
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
