# Phase 1 — Modules 1 and 2: Java Fundamentals and OOP

## Objective

Build a small, working first increment of the Alumni Engagement and Networking System using the Java and object-oriented concepts taught in Modules 1 and 2 of CCSE0355.

## Scope of the first increment

The console prototype includes:
- Register an alumni profile with name, graduation year, expertise, email and current organization.
- Register a student profile with name, expected graduation year, area of study, email and current semester.
- List profiles in case-insensitive alphabetical order.
- Search profiles by partial name.
- Reject blank required fields and handle invalid menu input.

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

## Verification checklist

- [ ] Compile successfully on JDK 17+.
- [ ] Register one alumni profile.
- [ ] Register one student profile.
- [ ] List both profiles and verify alphabetical ordering.
- [ ] Search by full and partial name.
- [ ] Try blank fields and invalid menu input.
- [ ] Capture genuine screenshots of the running prototype.
- [ ] Record only the checks actually completed.

## Exit criteria

Phase 1 is complete when the scoped prototype compiles, the core flows above are demonstrated, genuine evidence is captured, and Report 1 describes the work accurately. Phase 2 then extends this codebase with Modules 3 and 4.
