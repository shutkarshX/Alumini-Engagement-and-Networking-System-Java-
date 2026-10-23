# Phase 1 — Modules 1 and 2: Java Fundamentals and OOP

## Objective

Build a small, working first increment of the Alumni Engagement and Networking System using the Java and object-oriented concepts taught in Modules 1 and 2 of CCSE0355.

## Scope of the first increment

The console prototype includes:
- Register an alumni profile with name, graduation year, expertise, email and current organization.
- Register a student profile with name, expected graduation year, area of study, email and current semester.
- List profiles in case-insensitive alphabetical order.
- Search profiles by partial name.
- Reject blank required inputs and handle invalid menu input.

## Syllabus concepts represented

- **Classes and objects:** separate classes model users, alumni and students.
- **Constructors:** initialize profile objects with their required data.
- **Encapsulation:** profile data is stored in private fields and exposed through getters.
- **Abstraction:** the shared `User` class is abstract.
- **Inheritance:** `Alumni` and `Student` extend `User`.
- **Method overriding and polymorphism:** role-specific methods are overridden and both types can be handled as `User` objects.
- **Collections and lambda/method references:** a shared collection stores profiles and supports sorted listing/search.

The final syllabus-to-code mapping should be checked against the concepts actually demonstrated in the final Phase 1 version. A topic appearing in the syllabus is not by itself evidence that it has been implemented.

## Explicitly deferred

These are not required to call Phase 1 complete:
- Desktop GUI implementation.
- Persistent local-file storage.
- Edit/delete profile workflows.
- Authentication or multi-user accounts.
- Events, mentorship, chat or socket networking.
- Database integration.

The final product is still intended to be a desktop GUI, and local files remain the selected storage direction. Those features will be introduced when they fit the relevant later modules and project requirements.

## Build and run

Requires JDK 17 or later:

```bash
javac -d out src/main/java/com/alumni/*.java
java -cp out com.alumni.Main
```

## Manual verification record

The following checks were run manually in the console during Phase 1 development. These are manual checks, not automated unit tests.

| Check | Observed result | Status |
|---|---|---|
| Compile Java source files | Compilation completed without errors | Passed |
| Launch console application | Main menu appeared | Passed |
| Register alumni profile | Aditi Sharma profile was registered | Passed |
| Register student profile | Rahul Verma profile was registered | Passed |
| List profiles | Both profiles appeared; Aditi Sharma was listed before Rahul Verma | Passed |
| Search by partial name | Searching for `Aditi` returned Aditi Sharma | Passed |
| Invalid menu input | Text entered at the numeric menu prompt produced a whole-number warning and invalid-option message; app returned to the menu | Passed |
| Empty search query | Blank query produced `This field cannot be empty.` and prompted again | Passed |
| Search with no matching result | Searching for `abcd` produced `No matching profiles found.` and returned to the menu | Passed |
| Blank required field during profile registration | Not yet confirmed in the recorded run | Pending |
| Capture genuine screenshots | Not yet recorded in this document | Pending |

The sample profiles and email addresses used in the console test were demonstration data. No claim is made here that the app has persistent storage; profile data is held in the running prototype.

## Exit criteria

Before marking Phase 1 fully complete for reporting, verify blank-field validation during profile registration and capture genuine screenshots of the running prototype. Once those are done, Report 1 can describe the demonstrated implementation accurately. Phase 2 then extends this codebase with Modules 3 and 4.
