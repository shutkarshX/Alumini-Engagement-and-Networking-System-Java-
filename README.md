# Alumni Engagement and Networking System (Java)

A Java project for Object-Oriented Techniques using Java (OOTS), developed incrementally according to the five-module phase plan.

## Project context

- **Course:** Object Oriented Techniques using Java
- **Course code:** CCSE0355
- **Approved topic:** Alumni Engagement and Networking System
- **Domain:** Intelligent Java-Based Educational Management System
- **Final application target:** Desktop GUI
- **Initial development stage:** Console prototype focused on Modules 1 and 2

## Development phases and module mapping

| Stage | Modules | Goal |
|---|---|---|
| Phase 1 — Report 1 | Modules 1 and 2 | Java fundamentals, classes/objects, encapsulation, abstraction, inheritance, polymorphism and relevant Unit 2 concepts |
| Phase 2 — Report 2 | Modules 3 and 4 | Packages, exception handling, string handling, I/O streams, concurrency and relevant socket concepts |
| Final phase — Final Report | Module 5 and integration | GUI, generics, collections, integrated testing and final evidence |

Each phase extends the previous working version. The project will not be completed in advance and then retrospectively divided into phases.

## Phase 1 scope

The Phase 1 console prototype is intended to support:
- Registering alumni profiles.
- Registering student profiles.
- Listing profiles sorted by name.
- Searching profiles by partial name.
- Required-field validation.
- Demonstrating classes, constructors, encapsulation, abstraction, inheritance, method overriding and polymorphism.
- Using a collection and a lambda/method-reference comparator where appropriate to the syllabus.

The profile directory is currently in memory; profiles are lost when the program exits. This is intentional for the current checkpoint. GUI, persistent local-file storage, edit/delete workflows, authentication, events and mentorship are not Phase 1 completion claims.

## Source structure

```text
src/main/java/com/alumni/
├── Main.java
├── User.java
├── Alumni.java
├── Student.java
└── ProfileDirectory.java
docs/
└── PHASE_1.md
```

## Compile and run

Requires JDK 17 or later. From the repository root:

```bash
javac -d out src/main/java/com/alumni/*.java
java -cp out com.alumni.Main
```

Windows PowerShell:

```powershell
$files = Get-ChildItem -Recurse src/main/java -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
java -cp out com.alumni.Main
```

## Verification status

The source has been committed to GitHub. Compile/runtime verification and repeatable tests must be run before recording test outcomes or using screenshots as evidence.

## Evidence integrity

Reports must distinguish implemented, tested features from future plans. Screenshots must be captured from the real running application.
