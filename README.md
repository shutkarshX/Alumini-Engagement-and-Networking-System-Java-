# Alumni Engagement and Networking System (Java)

A Java-based Object-Oriented Techniques (OOTS) project for managing alumni/student profiles and supporting future networking activities.

## Course and project context

- Course: Object Oriented Techniques using Java
- Course code: CCSE0355
- Topic: Alumni Engagement and Networking System
- Domain: Intelligent Java-Based Educational Management System

## Phase and module mapping

The project is developed as one continuous application across five syllabus units/modules:

- **Phase 1 — Report 1:** Unit/Module 1 (Java fundamentals, OOP, classes/objects, abstraction, UML modelling) and Unit/Module 2 (inheritance, access modifiers, polymorphism, arrays, lambda expressions).
- **Phase 2 — Report 2:** Unit/Module 3 (packages, exception handling, assertions/localization, string handling) and Unit/Module 4 (concurrency, I/O streams, TCP/UDP socket programming).
- **Final phase — Final Report:** Unit/Module 5 (GUI, generics, collections) plus integration, validation, testing, and consolidated documentation for Units 1–5.

The syllabus unit topics guide the implementation; features will be added only when they fit the project and can be demonstrated.

## Current implementation

Phase 1 prototype currently supports:
- Registering alumni profiles.
- Registering student profiles.
- Listing profiles sorted by name.
- Searching profiles by name.
- Shared abstract User model with Alumni and Student subclasses.
- In-memory profile directory using a Java collection and a lambda-based sorting comparator.

Profiles are held in memory and disappear when the program exits. Persistent storage, GUI, authentication, events, mentorship requests, and networking are not yet implemented.

## Project structure

    src/main/java/com/alumni/
    ├── Main.java
    ├── User.java
    ├── Alumni.java
    ├── Student.java
    └── ProfileDirectory.java
    docs/
    └── PHASE_1.md

## Compile and run

Requires JDK 17 or later. From the repository root:

    javac -d out src/main/java/com/alumni/*.java
    java -cp out com.alumni.Main

## Verification status

The source has been committed to GitHub. Compile/runtime verification and repeatable automated tests still need to be run in a local Java environment. Do not treat the prototype as verified until those checks pass.

## Project integrity

The phase checklist and report statements must distinguish implemented features from planned features. Screenshots and test outcomes will be captured from the real running application.
