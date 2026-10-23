# Alumni Engagement and Networking System (Java)

A Java project for Object-Oriented Techniques using Java (OOTS), developed incrementally according to the five-module phase plan.

## Project context
- **Course:** Object Oriented Techniques using Java
- **Course code:** CCSE0355
- **Approved topic:** Alumni Engagement and Networking System
- **Domain:** Intelligent Java-Based Educational Management System
- **Final application target:** Desktop GUI
- **Current development stage:** Phase 2 console application with local profile persistence and a read-only TCP directory service

## Development phases
| Stage | Modules | Goal |
|---|---|---|
| Phase 1 — Report 1 | Modules 1 and 2 | Java fundamentals, classes/objects, encapsulation, abstraction, inheritance, polymorphism and relevant Unit 2 concepts |
| Phase 2 — Report 2 | Modules 3 and 4 | Packages, exception handling, string handling, I/O streams, concurrency and relevant socket concepts |
| Final phase — Final Report | Module 5 and integration | GUI, generics, collections, integrated testing and final evidence |

Each phase extends the previous working version. The project is not completed in advance and then retrospectively divided into phases.

## Console features
- Register alumni and student profiles.
- List profiles alphabetically and search by partial name.
- Reject blank required inputs and handle nonnumeric menu input.
- Load saved profiles at startup from `data/profiles.csv`.
- Save the profile directory after registering a profile.
- Use UTF-8 file I/O and report load/save errors.
- Run a read-only TCP directory server supporting `LIST` and `SEARCH <name>` from multiple client connections.
- Use a command-line TCP client to query the directory.

The data file uses Base64-encoded fields so commas and punctuation in profile text are preserved. The local data file is ignored by Git; do not commit real profile information.

## Source structure
```text
src/main/java/com/alumni/
├── Main.java
├── User.java
├── Alumni.java
├── Student.java
├── ProfileDirectory.java
├── ProfileStorage.java
├── DirectoryServer.java
└── DirectoryClient.java
docs/
├── PHASE_1.md
└── PHASE_2.md
```

## Compile and run
Requires JDK 17 or later:
```bash
javac -d out src/main/java/com/alumni/*.java
java -cp out com.alumni.Main
```

To run the TCP directory service, use two terminals:

```bash
java -cp out com.alumni.DirectoryServer
java -cp out com.alumni.DirectoryClient
```

The server defaults to port `5050`; the client accepts optional host and port arguments.
Windows PowerShell:
```powershell
$files = Get-ChildItem -Recurse src/main/java -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
java -cp out com.alumni.Main
```

The program creates the `data` directory and profile file when the first profile is saved. If no saved file exists, it starts with an empty directory.

## Verification status
The repository includes a GitHub Actions workflow (`.github/workflows/java-verification.yml`) that compiles the application and runs the dependency-free profile-storage smoke tests on pushes to `main` and pull requests. A committed workflow or test file is not itself proof of a passing run; check the Actions tab for the actual result. GitHub Actions compiles the application and runs smoke tests for file persistence and TCP `LIST`/`SEARCH` commands. Check the Actions result for the specific commit. The TCP service is a read-only learning prototype; authentication, encryption and GUI features are not implemented.

## Evidence integrity
Reports must distinguish implemented, tested features from future plans. Screenshots must be captured from the real running application.
