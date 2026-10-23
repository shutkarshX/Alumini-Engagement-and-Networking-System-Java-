# Phase 2 — File I/O, Exception Handling and TCP Networking

## Objective
Extend the Phase 1 console prototype with local profile persistence, explicit file-error handling, and a small read-only TCP service while preserving the alumni/student class hierarchy and existing search/list workflows.

## Implementation
- `ProfileStorage` reads and writes profile records using UTF-8 buffered readers and writers.
- Alumni and student records include a type marker. Each field is Base64-encoded independently so commas and punctuation in user-entered text are preserved.
- The console application loads saved records at startup from `data/profiles.csv` and saves after profile registration.
- The storage layer creates the parent directory and writes a temporary file before replacing the current file.
- If saved data is malformed or cannot be read, startup stops with an error instead of silently starting with an empty directory.
- `DirectoryServer` exposes read-only `LIST` and `SEARCH <name>` commands over TCP sockets. It accepts multiple clients using a worker-thread pool and uses UTF-8 line-based I/O.
- `DirectoryClient` provides a command-line client for the TCP service.
- The local profile file is excluded from Git by the repository ignore rule.

## Run the TCP feature
Compile the project from the repository root using JDK 17 or later:

```bash
javac -d out src/main/java/com/alumni/*.java
java -cp out com.alumni.DirectoryServer
```

In a second terminal:

```bash
java -cp out com.alumni.DirectoryClient
```

Client commands: `LIST`, `SEARCH Aditi`, or `EXIT`. The default server port is 5050. A custom port can be supplied to the server; the client accepts host and port arguments.

## Verification
GitHub Actions compiles the application and runs dependency-free smoke tests for file persistence and the TCP directory service. The tests cover missing storage files, alumni/student save-load round trips, punctuation and Unicode, malformed records, and TCP LIST/SEARCH requests. Use the Actions run result as evidence for the exact commit tested.

## Current scope and limitations
The TCP service is read-only and intended as a learning prototype, not a production network service. It has no authentication, encryption, database, GUI, or profile write operations over the network. The final GUI and broader integration remain future work. Do not claim these features are complete.

## Evidence checklist
For report screenshots, run the actual application and capture:
1. Console menu and profile listing/search.
2. Registration followed by restart and successful reload.
3. TCP server showing its listening port.
4. TCP client issuing LIST and SEARCH commands.
5. Successful GitHub Actions run for the commit being reported.

Only describe screenshots and tests that were actually observed.