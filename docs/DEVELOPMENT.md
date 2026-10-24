# Development Milestones

This file records how the project evolved from its original PBL vision into the current working software. It is a technical project history, not an academic report.

## 1. Original vision — an alumni engagement platform

The original PBL proposal aimed to address the difficulty of keeping alumni information organized and helping an institution connect alumni with current students and faculty.

The broader proposed system envisioned:
- A centralized alumni directory with profiles and professional details.
- Searching alumni by information such as batch, branch, or company.
- Support for institutional events and announcements.
- Mentorship and job-referral requests between students and relevant alumni.
- More organized records for alumni engagement.

The original concept also identified different user roles and possible entities such as Admin, Alumni, Student, Event, and Request.

**Important scope distinction:** these points describe the original vision and proposed functionality. They are not all implemented in the current codebase.

## 2. Phase 1 — Core Java and OOP

**Goal:** Build the first working alumni/student profile directory using Java fundamentals and object-oriented design.

Implemented:
- Shared abstract `User` type with `Alumni` and `Student` profile types.
- Profile registration in the console application.
- Required-field and menu-input handling.
- Email and year validation, plus student semester validation.
- Alphabetical listing and partial-name search.
- A shared in-memory profile directory.

**Key code:** `User.java`, `Alumni.java`, `Student.java`, `ProfileDirectory.java`, `Main.java`.

**Scope at this stage:** profiles were held in memory. Persistent storage, the Swing desktop interface, and TCP networking were later additions. The implemented first version focused on the core directory rather than all features from the broader proposal.

## 3. Phase 2 — Persistence and TCP Networking

**Goal:** Extend the core application with file storage and a small network directory service.

Implemented:
- `ProfileStorage` saves and reloads profiles using UTF-8 file I/O.
- Record fields are encoded independently to preserve commas, punctuation, and Unicode.
- Storage errors and malformed records are handled explicitly.
- `DirectoryServer` exposes read-only `LIST` and `SEARCH <name>` commands over TCP.
- `DirectoryClient` connects to the server and sends commands.
- Smoke tests cover validation, storage round trips, and TCP requests.

**Key code:** `ProfileStorage.java`, `DirectoryServer.java`, `DirectoryClient.java`.

**Scope at this stage:** the main user interface was still console-based. The TCP service is a learning prototype and does not provide authentication or encryption.

## 4. Final integration — Desktop GUI

**Goal:** Make the implemented features accessible through a desktop interface while reusing the existing profile and storage layers.

Implemented:
- Java Swing desktop interface in `DesktopApp.java`.
- Registration dialog for alumni and student profiles.
- Live name search, **Clear**, and **Show all** controls.
- Profiles load from local storage at startup and newly registered profiles are saved.
- GitHub Actions compiles the project and runs the smoke tests.

**Key code:** `DesktopApp.java`, integrated with the profile model and storage classes.

## 5. Where the project stands today

The current application is a working educational prototype for registering, validating, listing, searching, and locally storing alumni/student profiles, with a Swing GUI and a separate read-only TCP directory service.

The broader original vision is **partially realized**, not fully complete. The current code does not implement:
- Admin accounts, authentication, or profile verification workflows.
- Events and announcements.
- Mentorship or job-referral request workflows.
- A database or institutional platform integration.
- Profile editing/deletion or encrypted network communication.

These are possible future extensions, not current features.

## Current entry points

- Desktop: `com.alumni.DesktopApp`
- Console: `com.alumni.Main`
- TCP server: `com.alumni.DirectoryServer`
- TCP client: `com.alumni.DirectoryClient`

## Build and run

Requires JDK 17 or later. From the repository root in Windows PowerShell:

```powershell
$files = Get-ChildItem -Recurse src/main/java -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
java -cp out com.alumni.DesktopApp
```

For the console interface, run `java -cp out com.alumni.Main` after compiling. See the root [README](../README.md) for TCP run instructions and test details.
