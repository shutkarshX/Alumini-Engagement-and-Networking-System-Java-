package com.alumni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/** Minimal command-line client for the read-only TCP directory service. */
public final class DirectoryClient {
    private DirectoryClient() { }

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = 5050;
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException exception) {
                System.err.println("Usage: DirectoryClient [host] [port]");
                return;
            }
        }

        try (Scanner input = new Scanner(System.in)) {
            System.out.println("Connected to alumni directory at " + host + ":" + port);
            System.out.println("Commands: LIST, SEARCH <name>, EXIT");
            while (true) {
                System.out.print("> ");
                if (!input.hasNextLine()) break;
                String command = input.nextLine().trim();
                if (command.equalsIgnoreCase("EXIT")) break;
                if (command.isEmpty()) continue;
                try {
                    sendCommand(host, port, command);
                } catch (IOException exception) {
                    System.out.println("Request failed: " + exception.getMessage());
                }
            }
        }
    }

    static void sendCommand(String host, int port, String command) throws IOException {
        try (Socket socket = new Socket(host, port);
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
             BufferedReader response = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
            output.println(command);
            String line;
            while ((line = response.readLine()) != null && !line.equals("END")) {
                System.out.println(line);
            }
        }
    }
}
