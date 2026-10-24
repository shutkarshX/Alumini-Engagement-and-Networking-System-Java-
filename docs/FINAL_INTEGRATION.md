# Final Integration — Desktop GUI and Verification

## Purpose

This note records the final integration layer added after the console and TCP increments. It describes the checked-in implementation, not a claim that the prototype is production-ready.

## Desktop interface

`DesktopApp` provides a Java Swing interface that loads saved profiles at startup and displays them in alphabetical order. Users can search by name as they type, clear the search, show all profiles, and open a registration dialog for an alumni or student profile.

The GUI reuses the existing `User`, `Alumni`, `Student`, `ProfileDirectory`, and `ProfileStorage` classes. It saves newly registered profiles through the same local storage layer. If saved profiles cannot be loaded, the app displays a storage error and stops rather than silently opening an empty directory.

## Build and launch

Requires JDK 17 or later. From the repository root:

```powershell
$files = Get-ChildItem -Recurse src/main/java -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
java -cp out com.alumni.DesktopApp
```

Launch the console version with `java -cp out com.alumni.Main` after compiling.

## Verification record

The repository's GitHub Actions workflow compiles the main source and smoke-test source, then runs the profile validation, storage, and TCP directory smoke tests. The workflow run linked from the repository's Actions tab is the source of truth for the commit and result it reports.

Manual GUI evidence recorded during project work includes:
- the desktop window showing saved profiles after restart;
- the profile-registration flow;
- rejection of an invalid graduation year.

Manual GUI checks and automated smoke tests are different kinds of evidence; one should not be described as the other.

## Scope and limitations

The application is a local educational prototype. The desktop interface uses local file storage and does not connect to an institutional database. TCP commands are read-only. Authentication, encrypted network communication, profile editing/deletion, deployment, and evaluation with real alumni users are not implemented.

## Report

The consolidated report is available at [OOTS_Final_Report.pdf](OOTS_Final_Report.pdf) and [OOTS_Final_Report.docx](OOTS_Final_Report.docx).
