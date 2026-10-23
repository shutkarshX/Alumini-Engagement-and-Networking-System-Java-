# Phase 1 — Modules 1 and 2: Java and OOP Foundation

## Objective

Develop the first increment of the Alumni Engagement and Networking System using Java fundamentals and core object-oriented techniques from CCSE0355.

## Concepts represented in the code

- **Classes and objects:** `User`, `Alumni`, `Student` and the application entry point.
- **Encapsulation:** profile fields are private and exposed through getters.
- **Abstraction:** `User` is abstract and defines the common profile contract.
- **Inheritance and overriding:** `Alumni` and `Student` specialize `User`.
- **Polymorphism:** both profile types are represented as `User` values in a shared directory/table.
- **Collections and lambda/method references:** profiles are sorted by name and rendered from a collection.
- **Input validation:** required values and a basic email format are checked before constructing a profile.

## Current runnable application direction

The project now targets a Java Swing desktop application. The UI supports registering, viewing, searching, editing and deleting alumni/student profiles. A file repository stores profiles locally and reloads them on launch.

The GUI and persistence are being introduced early as the agreed product target; this does not change the reporting map: Report 1 documents Modules 1–2, Report 2 documents Modules 3–4, and the Final Report documents Module 5 and integrated verification. The report should describe only the features and syllabus concepts actually demonstrated at submission time.

## Local build

Requires JDK 17 or later:

```bash
javac -d out $(find src/main/java -name "*.java")
java -cp out com.alumni.Main
```

Windows PowerShell:

```powershell
$files = Get-ChildItem -Recurse src/main/java -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
java -cp out com.alumni.Main
```

## Verification status

Compilation, manual workflow checks and automated tests still need to be run locally. No test result or screenshot should be claimed until captured from an actual run.

## Next development checkpoints

1. Compile and manually verify profile CRUD, search, validation, and file persistence.
2. Add focused automated tests for the model, repository and service behaviour.
3. Expand Module 3 handling for malformed records and meaningful exception messages.
4. Evaluate Module 4 I/O/concurrency/socket concepts only where they serve a real requirement.
5. Complete the GUI, generics/collections coverage, integration and evidence for the final phase.
