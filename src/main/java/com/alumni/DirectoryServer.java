package com.alumni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Small read-only TCP directory service.
 * Each connected client is handled on a worker thread.
 */
public final class DirectoryServer implements AutoCloseable {
    private final ProfileDirectory directory;
    private final int requestedPort;
    private final ExecutorService clients = Executors.newCachedThreadPool();
    private ServerSocket serverSocket;
    private volatile boolean running;

    public DirectoryServer(ProfileDirectory directory, int port) {
        if (directory == null) throw new IllegalArgumentException("Directory cannot be null.");
        if (port < 0 || port > 65535) throw new IllegalArgumentException("Port must be between 0 and 65535.");
        this.directory = directory;
        this.requestedPort = port;
    }

    /** Starts listening on the requested port; port 0 asks the OS to choose one. */
    public synchronized void start() throws IOException {
        if (running) throw new IllegalStateException("Server is already running.");
        serverSocket = new ServerSocket(requestedPort);
        running = true;
        Thread acceptor = new Thread(this::acceptClients, "alumni-directory-acceptor");
        acceptor.setDaemon(true);
        acceptor.start();
        System.out.println("Directory server listening on port " + getPort());
    }

    public int getPort() {
        if (serverSocket == null) throw new IllegalStateException("Server has not started.");
        return serverSocket.getLocalPort();
    }

    private void acceptClients() {
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                clients.submit(() -> handleClient(socket));
            } catch (IOException exception) {
                if (running) System.err.println("Could not accept client: " + exception.getMessage());
            }
        }
    }

    private void handleClient(Socket socket) {
        try (socket;
             BufferedReader input = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8)) {
            String request = input.readLine();
            if (request == null) return;
            String normalized = request.trim();
            if (normalized.equalsIgnoreCase("LIST")) {
                writeResults(directory.getAllSortedByName(), output);
            } else if (normalized.regionMatches(true, 0, "SEARCH ", 0, 7)) {
                String query = normalized.substring(7).trim();
                if (query.isEmpty()) {
                    output.println("ERROR: Search text cannot be empty.");
                } else {
                    writeResults(directory.searchByName(query), output);
                }
            } else {
                output.println("ERROR: Supported commands are LIST and SEARCH <name>.");
            }
            output.println("END");
        } catch (IOException exception) {
            System.err.println("Client connection failed: " + exception.getMessage());
        }
    }

    private void writeResults(List<User> profiles, PrintWriter output) {
        for (User profile : profiles) output.println(profile.getSummary());
        if (profiles.isEmpty()) output.println("No matching profiles.");
    }

    @Override
    public synchronized void close() throws IOException {
        running = false;
        if (serverSocket != null) serverSocket.close();
        clients.shutdownNow();
    }

    public static void main(String[] args) throws IOException {
        int port = 5050;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("Usage: DirectoryServer [port]", exception);
            }
        }
        ProfileStorage storage = new ProfileStorage(java.nio.file.Path.of("data", "profiles.csv"));
        ProfileDirectory directory = new ProfileDirectory();
        storage.load().forEach(directory::add);
        DirectoryServer server = new DirectoryServer(directory, port);
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                server.close();
            } catch (IOException exception) {
                System.err.println("Error while stopping server: " + exception.getMessage());
            }
        }));
        // Keep the server process alive; the acceptor itself is a daemon thread.
        try {
            Thread.currentThread().join();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            server.close();
        }
    }
}
