# Alumni Engagement and Networking System

A Java application for managing alumni and student profiles, with a Swing desktop interface, local file persistence, and a read-only TCP directory service.

## Project goal and scope

The goal of the project is to make alumni information easier to organize and provide a foundation for connections between alumni and current students. The initial PBL proposal described a broader platform with a centralized directory, searching by details such as batch, branch, or company, and networking possibilities such as events, mentorship, and job referrals.

This repository focuses on the working foundation: profile management, validation, searchable directory features, local persistence, and a TCP-based read-only directory service, integrated through a Java Swing interface. Event management, mentorship/referral workflows, admin authentication, and institutional integration are not part of the current implementation and can be considered future extensions. See [Development Milestones](docs/DEVELOPMENT.md) for the development history.

## Current features

- Register alumni and student profiles.
- Validate profile details such as email, graduation year, and student semester.
- List profiles alphabetically and search by partial name.
- Save profiles locally and reload them when the application restarts.
- Preserve punctuation and Unicode in stored fields.
- Use the Swing desktop GUI with live search, **Clear**, **Show all**, and a registration dialog.
- Run a TCP directory service with `LIST` and `SEARCH <name>` commands.
- Run automated smoke tests for validation, storage, and TCP requests.

## Quick start (Windows)

You need **Git** and **JDK 17 or later** installed. The desktop interface requires a graphical desktop environment.

1. Open PowerShell.
2. Clone the repository and enter its folder:

```powershell
git clone https://github.com/shutkarshX/Alumini-Engagement-and-Networking-System-Java-.git
cd Alumini-Engagement-and-Networking-System-Java-
```

3. Check that Java and the Java compiler are available:

```powershell
java -version
javac -version
```

Both commands should report version 17 or later. If either command is not recognized, install a JDK (not just a JRE), configure your PATH, and reopen PowerShell.

4. Compile the application:

```powershell
$files = Get-ChildItem -Recurse src/main/java -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
```

5. Launch the desktop application:

```powershell
java -cp out com.alumni.DesktopApp
```

To run the console interface instead, use:

```powershell
java -cp out com.alumni.Main
```

These commands are for Windows PowerShell and should be run from the repository root. On Linux or macOS, install JDK 17+, clone the repository, and use your shell's equivalent source-file listing and classpath commands.

## Development milestones

Implementation was developed in three software increments:

1. **Phase 1 — Core Java and OOP:** profile types, registration, listing, and search.
2. **Phase 2 — Persistence and TCP:** file save/load and a read-only network directory service.
3. **Final integration — Desktop GUI:** Swing interface and integrated storage workflows.

See [Development Milestones](docs/DEVELOPMENT.md) for the original vision, phase-by-phase implementation, and current limitations.

## Tech stack

- Java (JDK 17 or later)
- Java Swing
- File I/O (UTF-8)
- TCP sockets
- GitHub Actions

## Project structure

```text
src/
├── main/java/com/alumni/
│   ├── Main.java
│   ├── DesktopApp.java
│   ├── User.java
│   ├── Alumni.java
│   ├── Student.java
│   ├── ProfileDirectory.java
│   ├── ProfileStorage.java
│   ├── DirectoryServer.java
│   └── DirectoryClient.java
└── test/java/com/alumni/
    ├── ProfileValidationSmokeTest.java
    ├── ProfileStorageSmokeTest.java
    └── DirectoryServerSmokeTest.java
docs/
└── DEVELOPMENT.md
.github/
└── workflows/
    └── java-verification.yml
```

## Requirements

- JDK 17 or later
- A desktop environment to run the Swing GUI

## TCP directory service

After compiling the source, open two terminals from the repository root.

Terminal 1 — start the server:

```powershell
java -cp out com.alumni.DirectoryServer
```

Terminal 2 — start the client:

```powershell
java -cp out com.alumni.DirectoryClient
```

The server defaults to port `5050`. The client supports `LIST`, `SEARCH <name>`, and `EXIT`. The service is read-only and intended for learning and demonstration.

## Tests

GitHub Actions compiles the application and smoke tests with JDK 17, then runs:

- `ProfileValidationSmokeTest`
- `ProfileStorageSmokeTest`
- `DirectoryServerSmokeTest`

Check the [Actions tab](https://github.com/shutkarshX/Alumini-Engagement-and-Networking-System-Java-/actions) for the result associated with a particular commit.

## Local data

The application creates `data/profiles.csv` when a profile is first saved. Local profile data is excluded from Git; do not commit real personal information.
