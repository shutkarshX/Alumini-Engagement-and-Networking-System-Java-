# Development Milestones

This file tracks how the application evolved as a software project. It is a concise technical changelog, not an academic report.

## Phase 1 — Core Java and OOP

**Goal:** Build the first working alumni/student profile directory using Java fundamentals and object-oriented design.

Implemented:
- Shared `User` base type with `Alumni` and `Student` profile types.
- Profile registration in the console application.
- Required-field and menu-input handling.
- Alphabetical listing and partial-name search.
- A shared in-memory profile directory.

**Key code:** `User.java`, `Alumni.java`, `Student.java`, `ProfileDirectory.java`, `Main.java`.

**Scope at this stage:** Profiles were held in memory. Persistent storage, the Swing desktop interface, and TCP networking were later additions.

## Phase 2 — Persistence and TCP Networking

**Goal:** Extend the core application with file storage and a small network directory service.

Implemented:
- `ProfileStorage` saves and reloads profiles using UTF-8 file I/O.
- Record fields are encoded independently to preserve commas, punctuation, and Unicode.
- Storage errors and malformed records are handled explicitly.
- `DirectoryServer` exposes read-only `LIST` and `SEARCH <name>` commands over TCP.
- `DirectoryClient` connects to the server and sends commands.
- Smoke tests cover validation, storage round trips, and TCP requests.

**Key code:** `ProfileStorage.java`, `DirectoryServer.java`, `DirectoryClient.java`.

**Scope at this stage:** The main user interface was still console-based. The TCP service is a learning prototype and does not provide authentication or encryption.

## Final Integration — Desktop GUI

**Goal:** Make the features accessible through a desktop interface while reusing the existing profile and storage layers.

Implemented:
- Java Swing desktop interface in `DesktopApp.java`.
- Registration dialog for alumni and student profiles.
- Live name search, **Clear**, and **Show all** controls.
- Profiles load from local storage at startup and newly registered profiles are saved.
- GitHub Actions compiles the project and runs the smoke tests.

**Current entry points:**
- Desktop: `com.alumni.DesktopApp`
- Console: `com.alumni.Main`
- TCP server: `com.alumni.DirectoryServer`
- TCP client: `com.alumni.DirectoryClient`

## Current limitations

This is an educational prototype. It does not currently implement authentication, encrypted network communication, database integration, profile editing/deletion, or integration with an institutional alumni platform.

## Build and run

Requires JDK 17 or later. From the repository root in Windows PowerShell:

```powershell
$files = Get-ChildItem -Recurse src/main/java -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
java -cp out com.alumni.DesktopApp
```

For the console interface, run `java -cp out com.alumni.Main` after compiling. See the root [README](../README.md) for TCP run instructions and test details.
