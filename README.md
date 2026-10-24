# Alumni Engagement and Networking System

A Java application for managing alumni and student profiles, with a Swing desktop interface, local file persistence, and a read-only TCP directory service.

## Features

- Register alumni and student profiles.
- Validate profile details such as email, graduation year, and student semester.
- List profiles alphabetically and search by partial name.
- Save profiles locally and reload them when the application restarts.
- Preserve punctuation and Unicode in stored fields.
- Use the Swing desktop GUI with live search, **Clear**, **Show all**, and a registration dialog.
- Run a TCP directory service with `LIST` and `SEARCH <name>` commands.
- Run automated smoke tests for validation, storage, and TCP requests.

## Development milestones

The project evolved in three software increments:

1. **Phase 1 — Core Java and OOP:** profile types, registration, listing, and search.
2. **Phase 2 — Persistence and TCP:** file save/load and a read-only network directory service.
3. **Final integration — Desktop GUI:** Swing interface and integrated storage workflows.

See [Development Milestones](docs/DEVELOPMENT.md) for the technical changelog and the features associated with each stage.

This is an educational prototype. It does not include authentication, encrypted network communication, a database, profile editing/deletion, or institutional platform integration.

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

## Compile and run

Run from the repository root in Windows PowerShell:

```powershell
$files = Get-ChildItem -Recurse src/main/java -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
java -cp out com.alumni.DesktopApp
```

To launch the console interface instead:

```powershell
java -cp out com.alumni.Main
```

The application creates `data/profiles.csv` when a profile is first saved. Local profile data is excluded from Git; do not commit real personal information.

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
