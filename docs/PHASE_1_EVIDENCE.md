# ADC Phase 1 — Evidence Checklist

This file tracks evidence needed to support the Phase 1 report. Check an item only after the evidence has actually been collected.

## Repository and automated verification

- [x] Existing source tree and README reviewed.
- [x] Development history and known limitations reviewed in `docs/DEVELOPMENT.md`.
- [x] ADC Phase 1 scope documented in `docs/PHASE_1.md`.
- [x] GitHub Actions Java verification passed on the repository's latest checked commit at the time this checklist was written.
  - Workflow run: https://github.com/shutkarshX/Alumini-Engagement-and-Networking-System-Java-/actions/runs/40952016763
  - Commit: `5997519ee9ad3a04d63b03ef2d37a1552d28f85e`
  - This run compiles the Java application and smoke tests, then runs profile-validation, storage, and TCP directory smoke tests.
- [ ] Capture a fresh screenshot of the Actions run showing the successful conclusion for the commit used in the final submission, if screenshots are required.

## Local application evidence — collect on the student's own machine

Only mark these complete after running the commands and capturing real output. Do not create mock screenshots or claim these steps passed without running them.

From Windows PowerShell, at the repository root:

```powershell
$files = Get-ChildItem -Recurse src/main/java,src/test/java -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
java -ea -cp out com.alumni.ProfileValidationSmokeTest
java -ea -cp out com.alumni.ProfileStorageSmokeTest
java -ea -cp out com.alumni.DirectoryServerSmokeTest
```

- [ ] Save the terminal output showing compilation and each smoke test completing successfully.
- [ ] Launch the existing Swing application with `java -cp out com.alumni.DesktopApp`.
- [ ] Capture a screenshot of the running desktop application.
- [ ] If allowed, use fictional/demo profiles only and capture evidence of registration, listing, and search. Do not include real personal information in screenshots or commit local profile data.

## Course source material

- [x] Module 1 is identified in the available ADC material as **Structure of Computer & ALU**.
- [x] Module 2 is identified in the available ADC material as **Control Unit & Memory Unit**.
- [ ] If the instructor requires the syllabus concepts to be directly mapped to the project, attach the instructor's actual instructions or obtain clarification. Do not invent a mapping.

## Approval form

- [x] Approved topic recorded as **Alumni Information Management Platform**, domain **Quality Education (SDG 4)**.
- [ ] Attach the actual approved topic form in the institution's required report submission location, if needed.
- [ ] Verify the exact team names and roles against the original form before reproducing them in a submitted report.

## Submission readiness

- [ ] Match the institution's prescribed Phase 1 report template and required sections.
- [ ] Add only screenshots/evidence that were genuinely collected.
- [ ] Check that no future-phase feature is described as already implemented.
- [ ] Review the final exported report before submission.

## Current status

The report and repository review are documented. Automated verification is evidenced by the linked GitHub Actions run. Local screenshots and any institution-specific report-format requirements still need to be supplied or confirmed; this checklist intentionally leaves them incomplete rather than fabricating evidence.
