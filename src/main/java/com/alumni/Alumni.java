package com.alumni;

/** Alumni-specific profile; extends the shared User abstraction. */
public final class Alumni extends User {
    private final String currentOrganization;

    public Alumni(String name, String graduationYear, String expertise,
                  String email, String currentOrganization) {
        super(name, graduationYear, expertise, email);
        if (currentOrganization == null || currentOrganization.trim().isEmpty()) {
            throw new IllegalArgumentException("Organization cannot be empty.");
        }
        this.currentOrganization = currentOrganization.trim();
    }

    public String getCurrentOrganization() { return currentOrganization; }

    @Override
    public String getRole() { return "Alumni"; }

    @Override
    public String getSummary() {
        return super.getSummary() + " | Organization: " + currentOrganization;
    }
}
