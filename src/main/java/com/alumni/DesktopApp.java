package com.alumni;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Swing desktop interface for the Alumni Engagement and Networking System.
 * Reuses the existing domain, directory, validation, and file-storage classes.
 */
public final class DesktopApp {
    private final ProfileDirectory directory = new ProfileDirectory();
    private final ProfileStorage storage = new ProfileStorage(Path.of("data", "profiles.csv"));
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JTextField searchField = new JTextField(22);
    private final JLabel statusLabel = new JLabel("Ready");
    private JFrame frame;

    private DesktopApp() { }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DesktopApp app = new DesktopApp();
            if (!app.loadProfiles()) return;
            app.createAndShow();
        });
    }

    private boolean loadProfiles() {
        try {
            storage.load().forEach(directory::add);
            return true;
        } catch (IOException | IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(null,
                    "Saved profiles could not be loaded. The application will stop to protect existing data.\n"
                            + exception.getMessage(),
                    "Storage error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void createAndShow() {
        frame = new JFrame("Alumni Engagement and Networking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(780, 520);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(12, 12));

        JLabel heading = new JLabel("Alumni Engagement and Networking System");
        heading.setBorder(BorderFactory.createEmptyBorder(16, 16, 4, 16));
        frame.add(heading, BorderLayout.NORTH);

        JList<String> profiles = new JList<>(listModel);
        profiles.setVisibleRowCount(14);
        JPanel center = new JPanel(new BorderLayout(8, 8));
        center.setBorder(BorderFactory.createEmptyBorder(0, 16, 8, 16));
        JPanel searchPanel = new JPanel(new BorderLayout(8, 8));
        searchPanel.add(new JLabel("Search by name:"), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(event -> searchField.setText(""));
        searchPanel.add(clearButton, BorderLayout.EAST);
        center.add(searchPanel, BorderLayout.NORTH);
        center.add(new JScrollPane(profiles), BorderLayout.CENTER);
        frame.add(center, BorderLayout.CENTER);

        JButton addButton = new JButton("Register profile");
        addButton.addActionListener(event -> showRegistrationDialog());
        JButton refreshButton = new JButton("Show all");
        refreshButton.addActionListener(event -> {
            searchField.setText("");
            refreshProfiles("");
        });
        JPanel actions = new JPanel();
        actions.add(addButton);
        actions.add(refreshButton);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(8, 16, 12, 16));
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(actions, BorderLayout.CENTER);
        bottom.add(statusLabel, BorderLayout.SOUTH);
        frame.add(bottom, BorderLayout.SOUTH);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent event) { refreshProfiles(searchField.getText()); }
            @Override public void removeUpdate(DocumentEvent event) { refreshProfiles(searchField.getText()); }
            @Override public void changedUpdate(DocumentEvent event) { refreshProfiles(searchField.getText()); }
        });

        refreshProfiles("");
        frame.setVisible(true);
    }

    private void showRegistrationDialog() {
        JComboBox<String> role = new JComboBox<>(new String[]{"Alumni", "Student"});
        JTextField name = new JTextField();
        JTextField year = new JTextField();
        JTextField area = new JTextField();
        JTextField email = new JTextField();
        JTextField detail = new JTextField();
        JLabel detailLabel = new JLabel("Current organization:");
        role.addActionListener(event -> detailLabel.setText(
                role.getSelectedIndex() == 0 ? "Current organization:" : "Current semester (1–8):"));

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.add(new JLabel("Profile type:")); form.add(role);
        form.add(new JLabel("Full name:")); form.add(name);
        form.add(new JLabel("Graduation year:")); form.add(year);
        form.add(new JLabel("Area of study/expertise:")); form.add(area);
        form.add(new JLabel("Email:")); form.add(email);
        form.add(detailLabel); form.add(detail);

        int choice = JOptionPane.showConfirmDialog(frame, form, "Register a profile",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (choice != JOptionPane.OK_OPTION) return;

        try {
            User profile;
            if (role.getSelectedIndex() == 0) {
                profile = new Alumni(name.getText(), year.getText(), area.getText(),
                        email.getText(), detail.getText());
            } else {
                profile = new Student(name.getText(), year.getText(), area.getText(),
                        email.getText(), detail.getText());
            }
            directory.add(profile);
            try {
                storage.save(directory.getAllSortedByName());
            } catch (IOException exception) {
                statusLabel.setText("Save failed; this new profile is only in memory for this session.");
                JOptionPane.showMessageDialog(frame,
                        "The profile was added to this session but could not be saved.\n"
                                + exception.getMessage(),
                        "Save error", JOptionPane.ERROR_MESSAGE);
                refreshProfiles(searchField.getText());
                return;
            }
            statusLabel.setText("Profile registered and saved. Total profiles: " + directory.size());
            refreshProfiles(searchField.getText());
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(frame, exception.getMessage(),
                    "Please correct the profile details", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void refreshProfiles(String query) {
        listModel.clear();
        List<User> profiles = query == null || query.isBlank()
                ? directory.getAllSortedByName()
                : directory.searchByName(query);
        for (User profile : profiles) listModel.addElement(profile.getSummary());
        if (profiles.isEmpty()) {
            statusLabel.setText(query == null || query.isBlank()
                    ? "No profiles yet. Register the first profile."
                    : "No profiles match \"" + query.trim() + "\".");
        } else {
            statusLabel.setText(profiles.size() + " profile(s) shown · " + directory.size() + " total");
        }
    }
}
