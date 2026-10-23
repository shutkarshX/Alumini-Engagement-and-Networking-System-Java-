# Phase 2 — File I/O and Exception Handling

## Objective
Extend the Phase 1 console prototype with local profile persistence and explicit file-error handling while preserving the alumni/student class hierarchy and existing search/list workflows.

## Implementation
- `ProfileStorage` reads and writes profile records using UTF-8 buffered readers and writers.
- Alumni and student records include a type marker. Each field is Base64-encoded independently so commas and punctuation in user-entered text are preserved.
- The application loads saved records at startup from `data/profiles.csv`.
- Registering a profile saves the sorted directory to the local file.
- The storage layer creates the parent directory and writes a temporary file before replacing the current file.
- If saved data is malformed or cannot be read, startup stops with an error instead of silently starting with an empty directory.
- The local profile file is excluded from Git by the repository ignore rule.

## Current scope and limitations
This increment adds file persistence to the console application. GUI, database integration, authentication, edit/delete operations, concurrency and socket networking are not claimed as complete.

## Verification checklist
Run these locally and record observed outcomes before using them as report evidence:
1. Compile all Java sources with JDK 17 or later.
2. Start without `data/profiles.csv` and confirm zero saved profiles load.
3. Register an alumni profile, exit, restart and confirm it reloads.
4. Register a student profile, restart and confirm both profile types reload.
5. List and search profiles after restart.
6. Test commas and non-ASCII characters in a field and confirm they survive a restart.
7. Test a malformed data file and confirm the application reports the read error rather than silently starting empty.
8. Confirm the local profile file is not staged for Git.

These checks are planned until actually executed; do not mark them as passed based only on code inspection.
