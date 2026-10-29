# ADC Phase 1 — Report 1 and Initial Foundation

## 1. Approved project details

- **Course:** Advances in Computing (CCSDS0302)
- **Faculty / coordinator:** Mr. Archit
- **Approved topic:** Alumni Information Management Platform
- **Domain:** Quality Education (SDG 4)
- **Team:** Refer to the submitted topic-approval form for the official team list and roles.

This document records Phase 1 work only. It does not claim that the final platform is complete.

## 2. Problem statement

Alumni details and professional connections can become difficult to organize when information is scattered across separate records or informal communication channels. A centralized alumni information platform is proposed to organize alumni and student profiles and provide a foundation for searching and future engagement.

This is the problem framing of the approved proposal, not a claim that the institution's current records have been audited.

## 3. Objectives

1. Organize alumni and student profiles in a consistent structure.
2. Support validation and searching of profile information.
3. Establish a foundation that can later be extended for alumni engagement.
4. Develop the project incrementally, keeping each phase's deliverables verifiable.
5. Reserve the polished, fully integrated web application for the final product after the planned phases.

## 4. Phase 1 scope

Phase 1 is the initial report and foundation stage. It includes:
- Recording the approved topic and its problem statement.
- Defining objectives, intended users, initial scope, and limitations.
- Reviewing the existing Java project as the current starting point.
- Recording learning from ADC Modules 1 and 2 without claiming unrelated syllabus topics are already implemented in the application.
- Identifying what can be evidenced now and what remains future work.

**Out of scope for this phase:** presenting a finished web product, claiming complete end-to-end workflows, or describing unverified features as implemented.

## 5. Existing project foundation reviewed

The repository's README and development history describe an existing Java application with:
- Alumni and student profile registration.
- Profile validation, including email and year/semester checks.
- Alphabetical listing and partial-name search.
- Local UTF-8 file persistence.
- A read-only TCP directory service with `LIST` and `SEARCH <name>`.
- A Java Swing desktop interface.
- Smoke-test classes for validation, storage, and TCP requests.

These are repository-documented capabilities. This report does not independently claim that tests were run during this update. Check the GitHub Actions run for the specific commit before reporting a passing result.

### Known limitations in the existing foundation

The repository's development notes identify these as not implemented in the current Java application:
- Admin authentication and profile verification.
- Event and announcement workflows.
- Mentorship and job-referral workflows.
- Database or institutional integration.
- Profile editing/deletion and encrypted network communication.

These remain possible later-phase work, not Phase 1 accomplishments.

## 6. ADC Modules 1 and 2 — learning record

The available ADC teaching material labels the modules as follows.

### Module 1: Structure of Computer & ALU

Topics in the supplied material include functional units and their interconnections, buses and bus arbitration, register/bus/memory transfers, processor organization, register and stack organization, addressing modes, arithmetic operations, Booth's algorithm, array multiplication, division, and logic operations.

**Phase 1 treatment:** study and document the concepts covered by the course material. Do not claim the alumni application implements an ALU or processor-organization feature.

### Module 2: Control Unit & Memory Unit

Topics in the supplied material include the control unit, instruction types and processing, memory hierarchy, cache memory and performance, address mapping and replacement, auxiliary storage, and virtual memory.

**Phase 1 treatment:** study and document the concepts covered by the course material. Do not claim the alumni application implements a control unit, cache simulator, or virtual-memory feature unless a separately specified and evidenced component is actually built.

### Scope distinction

The approved project is an **Alumni Information Management Platform**. The ADC syllabus modules above are course-learning topics; they are not the platform's feature modules. This report keeps those two scopes separate rather than inventing a technical connection between them. If the instructor has provided a required integration mapping, that source should be added before asserting one.

## 7. Initial functional scope for the proposed platform

The approved topic supports the following high-level direction:
- Maintain alumni and student profile information.
- Find relevant profiles through search.
- Provide a foundation for future alumni engagement.

Events, mentorship, referrals, administrator workflows, institutional integration, and a final web interface are potential later-stage scope. Add them only when their phase requirements are defined and implementation can be demonstrated.

## 8. Evidence and verification

Evidence available from the repository review:
- Root README documenting the existing Java application and how to build/run it.
- `docs/DEVELOPMENT.md` describing the implementation history and limitations.
- Existing Java source and smoke-test files listed in the README.
- ADC topic-approval form and supplied Module 1 / Module 2 teaching materials, held separately from this repository review.

No new runtime test result, real alumni dataset, institutional integration, or instructor approval is claimed by this document.

## 9. Phase 1 completion checklist

- [x] Record the approved project title, course, faculty, and domain.
- [x] Define the problem and initial objectives.
- [x] Review the documented state of the existing Java foundation.
- [x] Record Module 1 and Module 2 topics from the available ADC teaching material.
- [x] Separate syllabus learning topics from application feature scope.
- [x] State known limitations and avoid presenting future work as complete.
- [ ] Attach required screenshots/run evidence from the student's own environment, if required by the institutional report format.
- [ ] Confirm any instructor-specific mapping between ADC syllabus concepts and the approved project, if such a mapping is required.

## 10. Next phase

Proceed only after the Phase 1 deliverables and required evidence are accepted. Later phases can extend the existing foundation toward the intended platform. The polished, fully integrated web application belongs at the end of the planned phases—not as a substitute for Phase 1 work.
