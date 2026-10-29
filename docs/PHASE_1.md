# ADC Phase 1 — Alumni Information Management Platform

## Approved details
- Course: Advances in Computing (CCSDS0302)
- Faculty / coordinator: Mr. Archit
- Topic: Alumni Information Management Platform
- Domain: Quality Education (SDG 4)
- Team listed in approval form: Yugansh Goyal (Project Manager), Utkarsh Sharma (UI/UX Designer), Vishesh (Lead Developer), Vishal Tiwari (Lead Researcher), Vaibhav Sharma (Backend).

## Phase 1 implementation
- Responsive web-first dashboard.
- Alumni directory with search and branch, batch, and status filters.
- Add/edit/delete profile flows with required-field and email validation.
- Events & Networking: create, edit, delete events.
- Mentorship-interest directory and locally recorded demo connection requests.
- Analytics calculated from editable records.
- CSV export.

## Location
`web/index.html` is a self-contained responsive web application. Existing Java desktop sources are retained; this Phase 1 adds a web-first implementation rather than deleting history.

## Data boundary
The app uses fictional seeded records and browser `localStorage`. It has no shared backend/database, authentication, real email sending, institutional integration, or multi-user synchronization yet. Do not describe demo records as actual NIET alumni data.

## Course-module note
Available ADC teaching material identifies Module 1 as Structure of Computer & ALU and Module 2 as Control Unit & Memory Unit. Those are course syllabus modules, distinct from application feature modules. The current web app implements the approved alumni-platform foundation; any separate course-topic demonstrations should be added only if the instructor requires them.

## Manual smoke-test checklist
- [ ] Open `web/index.html` in a modern browser.
- [ ] Add a profile, refresh, and confirm it persists.
- [ ] Edit a profile and verify changes.
- [ ] Search and apply branch/batch/status filters.
- [ ] Delete a profile after confirmation.
- [ ] Create, edit, and delete an event.
- [ ] Record a mentorship request.
- [ ] Export CSV.
- [ ] Check desktop and mobile layout.

Runtime tests have not yet been independently executed for this commit.