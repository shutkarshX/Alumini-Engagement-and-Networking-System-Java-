# Alumni Engagement and Networking System (Java)

A Java desktop application for the Object-Oriented Techniques using Java (OOTS) course. The project is being built incrementally, with each phase tied to two of the five syllabus modules.

## Course and project context

- **Course:** Object Oriented Techniques using Java
- **Course code:** CCSE0355
- **Approved topic:** Alumni Engagement and Networking System
- **Domain:** Intelligent Java-Based Educational Management System
- **Application target:** Desktop GUI using Java Swing
- **Initial storage:** Local UTF-8 file at `data/profiles.csv`

## Five-module phase mapping

| Delivery stage | Syllabus modules | Report |
|---|---|---|
| Phase 1 | Module 1: Java fundamentals, classes/objects, abstraction and UML; Module 2: inheritance, access control, polymorphism and lambda expressions | Report 1 |
| Phase 2 | Module 3: packages, exceptions and string handling; Module 4: concurrency, I/O streams and socket concepts | Report 2 |
| Final phase | Module 5: GUI, generics and collections, plus integration and verification across Modules 1–5 | Final Report |

The current desktop shell is an early working target, not a claim that all Module 5 learning outcomes are complete. Phase reports must describe the real work and concepts demonstrated at that checkpoint.

## Current implementation

- Register alumni and student profiles.
- View profiles in a sortable table.
- Search profiles by partial name.
- Edit and delete selected profiles.
- Validate required fields and basic email format.
- Save and reload profiles from a local file.
- Separate the storage contract from its file-based implementation so a database or another repository can be added later.

The app is a local prototype: it has no authentication, cloud sync, multi-user access, or server-side security. The local file is not encrypted; avoid putting sensitive personal information into demo data.

## Project structure

```text
src/main/java/com/alumni/
├── Main.java
├── User.java
├── Alumni.java
├── Student.java
├── ProfileDirectory.java
├── repository/
│   ├── ProfileRepository.java
│   └── FileProfileRepository.java
└── ui/
    └── AlumniNetworkFrame.java
data/
└── profiles.csv   (created automatically when the first change is saved)
docs/
└── PHASE_1.md
```

## Build and run

Requires **JDK 17 or later**. From the repository root:

```bash
javac -d out $(find src/main/java -name "*.java")
java -cp out com.alumni.Main
```

On Windows PowerShell, use:

```powershell
$files = Get-ChildItem -Recurse src/main/java -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
java -cp out com.alumni.Main
```

## Verification status

The implementation has been committed to GitHub, but it still needs compilation and runtime checks in a local Java 17+ environment. Do not report tests or screenshots as completed until they have actually been produced.

## Integrity rule

Reports and screenshots must reflect the real code and observed behaviour. Features that are only planned must be labelled as planned.
