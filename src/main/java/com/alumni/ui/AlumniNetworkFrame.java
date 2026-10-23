package com.alumni.ui;

import com.alumni.Alumni;
import com.alumni.Student;
import com.alumni.User;
import com.alumni.repository.FileProfileRepository;
import com.alumni.repository.ProfileRepository;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/** Swing desktop interface for managing alumni and student profiles. */
public final class AlumniNetworkFrame extends JFrame {
    private final ProfileRepository repository;
    private final List<User> profiles = new ArrayList<>();
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"Type", "Name", "Graduation Year", "Area / Expertise", "Email", "Organization / Semester"},
            0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable table = new JTable(tableModel);
    private final JTextField searchField = new JTextField(20);
    private final JLabel statusLabel = new JLabel("Ready");

    public AlumniNetworkFrame() {
        this(new FileProfileRepository());
    }

    public AlumniNetworkFrame(ProfileRepository repository) {
        super("Alumni Engagement and Networking System");
        this.repository = repository;
        buildUi();
        loadProfiles();
    }

    private void buildUi() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(950, 560));
        setSize(1120, 650);
        setLocationRelativeTo(null);

        JLabel heading = new JLabel("Alumni Engagement & Networking");
        heading.setBorder(BorderFactory.createEmptyBorder(14, 16, 8, 16));
        heading.setFont(heading.getFont().deriveFont(22f));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        searchPanel.add(new JLabel("Search by name:"));
        searchPanel.add(searchField);
        JButton searchButton = new JButton("Search");
        JButton clearButton = new JButton("Clear");
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(26);
        table.setAutoCreateRowSorter(true);

        JButton addAlumniButton = new JButton("Register Alumni");
        JButton addStudentButton = new JButton("Register Student");
        JButton editButton = new JButton("Edit Selected");
        JButton deleteButton = new JButton("Delete Selected");
        JButton refreshButton = new JButton("Refresh");

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        actions.add(addAlumniButton);
        actions.add(addStudentButton);
        actions.add(editButton);
        actions.add(deleteButton);
        actions.add(refreshButton);

        JPanel north = new JPanel(new BorderLayout());
        north.add(heading, BorderLayout.NORTH);
        north.add(searchPanel, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout());
        south.setBorder(BorderFactory.createEmptyBorder(4, 10, 8, 10));
        south.add(actions, BorderLayout.CENTER);
        south.add(statusLabel, BorderLayout.SOUTH);
        add(south, BorderLayout.SOUTH);

        searchButton.addActionListener(event -> refreshTable(searchField.getText()));
        searchField.addActionListener(event -> refreshTable(searchField.getText()));
        clearButton.addActionListener(event -> {
            searchField.setText("");
            refreshTable("");
        });
        refreshButton.addActionListener(event -> loadProfiles());
        addAlumniButton.addActionListener(event -> addProfile(true));
        addStudentButton.addActionListener(event -> addProfile(false));
        editButton.addActionListener(event -> editSelected());
        deleteButton.addActionListener(event -> deleteSelected());
    }

    private void loadProfiles() {
        try {
            profiles.clear();
            profiles.addAll(repository.findAll());
            refreshTable(searchField.getText());
            statusLabel.setText("Loaded " + profiles.size() + " profile(s) · stored in data/profiles.csv");
        } catch (IOException exception) {
            showError("Could not load profiles: " + exception.getMessage());
        }
    }

    private void refreshTable(String query) {
        tableModel.setRowCount(0);
        String normalized = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
        profiles.stream()
                .filter(profile -> profile.getName().toLowerCase(Locale.ROOT).contains(normalized))
                .sorted(Comparator.comparing(User::getName, String.CASE_INSENSITIVE_ORDER))
                .forEach(profile -> tableModel.addRow(rowFor(profile)));
        statusLabel.setText(tableModel.getRowCount() + " profile(s) shown · " + profiles.size() + " total");
    }

    private Object[] rowFor(User profile) {
        String detail = profile instanceof Alumni alumni
                ? alumni.getCurrentOrganization()
                : ((Student) profile).getCurrentSemester();
        return new Object[]{profile.getRole(), profile.getName(), profile.getGraduationYear(),
                profile.getAreaOfStudyOrExpertise(), profile.getEmail(), detail};
    }

    private void addProfile(boolean alumni) {
        User profile = profileDialog(alumni ? "Register Alumni" : "Register Student", null, alumni);
        if (profile != null) {
            profiles.add(profile);
            persistAndRefresh("Profile registered.");
        }
    }

    private void editSelected() {
        int selected = table.getSelectedRow();
        if (selected < 0) {
            showInfo("Select a profile first.");
            return;
        }
        String name = String.valueOf(tableModel.getValueAt(table.convertRowIndexToModel(selected), 1));
        String role = String.valueOf(tableModel.getValueAt(table.convertRowIndexToModel(selected), 0));
        User current = profiles.stream()
                .filter(profile -> profile.getName().equals(name) && profile.getRole().equals(role))
                .findFirst().orElse(null);
        if (current == null) return;
        User replacement = profileDialog("Edit " + current.getRole() + " Profile", current,
                current instanceof Alumni);
        if (replacement != null) {
            int index = profiles.indexOf(current);
            profiles.set(index, replacement);
            persistAndRefresh("Profile updated.");
        }
    }

    private void deleteSelected() {
        int selected = table.getSelectedRow();
        if (selected < 0) {
            showInfo("Select a profile first.");
            return;
        }
        String name = String.valueOf(tableModel.getValueAt(table.convertRowIndexToModel(selected), 1));
        String role = String.valueOf(tableModel.getValueAt(table.convertRowIndexToModel(selected), 0));
        User target = profiles.stream()
                .filter(profile -> profile.getName().equals(name) && profile.getRole().equals(role))
                .findFirst().orElse(null);
        if (target != null && JOptionPane.showConfirmDialog(this,
                "Delete the profile for " + target.getName() + "?", "Confirm deletion",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            profiles.remove(target);
            persistAndRefresh("Profile deleted.");
        }
    }

    private User profileDialog(String title, User existing, boolean alumni) {
        JTextField name = new JTextField(existing == null ? "" : existing.getName());
        JTextField year = new JTextField(existing == null ? "" : existing.getGraduationYear());
        JTextField area = new JTextField(existing == null ? "" : existing.getAreaOfStudyOrExpertise());
        JTextField email = new JTextField(existing == null ? "" : existing.getEmail());
        String existingDetail = existing instanceof Alumni a ? a.getCurrentOrganization()
                : existing instanceof Student s ? s.getCurrentSemester() : "";
        JTextField detail = new JTextField(existingDetail);
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.add(new JLabel("Full name:")); form.add(name);
        form.add(new JLabel(alumni ? "Graduation year:" : "Expected graduation year:")); form.add(year);
        form.add(new JLabel(alumni ? "Area of expertise:" : "Area of study:")); form.add(area);
        form.add(new JLabel("Contact email:")); form.add(email);
        form.add(new JLabel(alumni ? "Current organization:" : "Current semester:")); form.add(detail);

        while (true) {
            int result = JOptionPane.showConfirmDialog(this, form, title,
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) return null;
            try {
                if (name.getText().isBlank() || year.getText().isBlank() || area.getText().isBlank()
                        || email.getText().isBlank() || detail.getText().isBlank()) {
                    throw new IllegalArgumentException("All fields are required.");
                }
                if (!email.getText().trim().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
                    throw new IllegalArgumentException("Enter a valid email address.");
                }
                return alumni
                        ? new Alumni(name.getText(), year.getText(), area.getText(), email.getText(), detail.getText())
                        : new Student(name.getText(), year.getText(), area.getText(), email.getText(), detail.getText());
            } catch (IllegalArgumentException exception) {
                showError(exception.getMessage());
            }
        }
    }

    private void persistAndRefresh(String message) {
        try {
            repository.saveAll(profiles);
            refreshTable(searchField.getText());
            statusLabel.setText(message + " Saved to local file.");
        } catch (IOException exception) {
            showError("The change is in memory but could not be saved: " + exception.getMessage());
        }
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Alumni Network", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Alumni Network", JOptionPane.ERROR_MESSAGE);
    }

    public static void launch() {
        SwingUtilities.invokeLater(() -> new AlumniNetworkFrame().setVisible(true));
    }
}
