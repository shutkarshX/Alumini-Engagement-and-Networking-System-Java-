# Phase 1 — Units 1 and 2: Foundation and OOP Prototype

## Objective

Build the first working version of the Alumni Engagement and Networking System while applying the Java and object-oriented concepts covered in Units 1 and 2 of CCSE0355.

## Unit 1 alignment — Java fundamentals and OOP

The prototype includes a menu-driven console workflow, classes and objects, constructors, encapsulated fields, an abstract base class, and methods. The User class defines shared profile data and an abstract role method. The Main class handles console interaction.

## Unit 2 alignment — OOP features, arrays and lambda expressions

The Alumni and Student classes extend User and override role-specific behavior. The directory stores different profile subtypes through a shared User reference, demonstrating polymorphism. A Java collection stores profiles, and a lambda/method-reference comparator sorts them by name.

## Implemented features

- Register an alumni profile with name, graduation year, expertise, email, and organization.
- Register a student profile with name, expected graduation year, area of study, email, and semester.
- List profiles in case-insensitive alphabetical order.
- Search profiles by partial name.
- Reject empty required fields.
- Display a message when no profiles or search matches exist.

## Current limitations

- Data is stored in memory and is lost when the program exits.
- There is no graphical interface, persistent database/file storage, authentication, event management, mentorship request workflow, or socket communication yet.
- Email format validation and automated test coverage have not yet been added.
- Source files have been committed, but local compilation/runtime verification remains outstanding.

## Build command

Requires JDK 17 or later:

    javac -d out src/main/java/com/alumni/*.java
    java -cp out com.alumni.Main

## Next steps

1. Compile and run the project locally.
2. Test alumni registration, student registration, sorted listing, search, and invalid input.
3. Capture genuine screenshots of the running application.
4. Add automated tests and strengthen input validation.
5. Plan Units 3 and 4 for Phase 2, then Unit 5 and integration for the final phase.
