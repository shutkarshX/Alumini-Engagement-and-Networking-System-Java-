# Alumni Engagement and Networking System (Java)

A Java desktop application developed for **Object Oriented Techniques using Java (OOTS)**. The project grew in stages from a console-based profile directory into a desktop GUI with local persistence and a read-only TCP directory service.

## Project details

- **Course:** Object Oriented Techniques using Java
- **Course code:** CCSE0355
- **Approved topic:** Alumni Engagement and Networking System
- **Domain:** Intelligent Java-Based Educational Management System
- **Language:** Java (JDK 17)
- **Interface:** Java Swing desktop GUI and console interface
- **Storage:** Local file at `data/profiles.csv`
- **Network feature:** Read-only TCP commands for listing and searching profiles

## What it does

- Register alumni and student profiles.
- Validate profile details, including email format, graduation year and student semester.
- View profiles in alphabetical order and search by partial name.
- Save profiles locally and reload them after restarting the application.
- Preserve punctuation and Unicode characters in stored fields.
- Provide a Swing desktop interface with live search, **Clear**, **Show all**, and a registration dialog.
- Run a read-only TCP directory service that accepts `LIST` and `SEARCH <name>` requests from clients.
- Run dependency-free smoke tests for profile validation, file storage and TCP directory requests.

This is an educational prototype. It does not currently provide authentication, encrypted network communication, a database, profile editing/deletion, or integration with an institutional alumni platform.

## Screenshots and report

The consolidated OOTS final report is available in the repository:

- [Final report (PDF)](docs/OOTS_Final_Report.pdf)
- [Final report (Word)](docs/OOTS_Final_Report.docx)

Screenshots in the report are intended to document observed application behaviour; they should not be treated as evidence of features that are not listed as implemented above.

## Source layout

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
├── PHASE_1.md
├── PHASE_2.md
├── OOTS_Final_Report.pdf
└── OOTS_Final_Report.docx
```

## Requirements

- JDK 17 or later
- A desktop environment for the Swing GUI

## Compile and launch

Run these commands from the repository root.

**Windows PowerShell:**

```powershell
$files = Get-ChildItem -Recurse src/main/java -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
java -cp out com.alumni.DesktopApp
```

To use the console interface instead:

```powershell
java -cp out com.alumni.Main
```

The application creates the `data` directory and profile file when a profile is first saved. If no saved file exists, the profile directory starts empty. The local profile data file is excluded from Git; do not commit real personal information.

## Run the TCP directory service

Compile the source first, then open two terminals from the repository root.

Terminal 1:

```powershell
java -cp out com.alumni.DirectoryServer
```

Terminal 2:

```powershell
java -cp out com.alumni.DirectoryClient
```

The server defaults to port `5050`. The client supports `LIST`, `SEARCH <name>`, and `EXIT`. The service is read-only and intended for learning and demonstration, not production use.

## Tests and continuous integration

The GitHub Actions workflow compiles the application and smoke tests using JDK 17, then runs:

- `ProfileValidationSmokeTest`
- `ProfileStorageSmokeTest`
- `DirectoryServerSmokeTest`

See the [Actions tab](https://github.com/shutkarshX/Alumini-Engagement-and-Networking-System-Java-/actions) for the result associated with a specific commit. A workflow file being present is not, by itself, proof that every future run passes.

## Development notes

The work was developed incrementally:

- **Phase 1:** Java fundamentals, classes and objects, inheritance, polymorphism, registration, listing and search.
- **Phase 2:** file persistence, input/data error handling, and TCP LIST/SEARCH commands.
- **Final integration:** Swing GUI, saved-profile reload, and integrated verification.

See [Phase 1 notes](docs/PHASE_1.md) and [Phase 2 notes](docs/PHASE_2.md) for the scope and recorded checks of the earlier increments.
