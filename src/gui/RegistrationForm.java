package gui;

import database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegistrationForm extends JFrame {
    private JTextField usernameField, fullNameField, emailField, phoneField;
    private JPasswordField passwordField;
    private JTextArea addressArea;

    public RegistrationForm() {
        setTitle("Hospital Management System - Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add form fields
        addFormField(panel, gbc, "Username:", usernameField = new JTextField(20), 0);
        addFormField(panel, gbc, "Password:", passwordField = new JPasswordField(20), 1);
        addFormField(panel, gbc, "Full Name:", fullNameField = new JTextField(20), 2);
        addFormField(panel, gbc, "Email:", emailField = new JTextField(20), 3);
        addFormField(panel, gbc, "Phone:", phoneField = new JTextField(20), 4);

        // Address area
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Address:"), gbc);

        gbc.gridx = 1;
        addressArea = new JTextArea(3, 20);
        addressArea.setLineWrap(true);
        panel.add(new JScrollPane(addressArea), gbc);

        // Register button
        gbc.gridx = 1;
        gbc.gridy = 6;
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> register());
        panel.add(registerButton, gbc);

        // Back to login button
        gbc.gridy = 7;
        JButton backButton = new JButton("Back to Login");
        backButton.addActionListener(e -> {
            new LoginForm().setVisible(true);
            this.dispose();
        });
        panel.add(backButton, gbc);

        add(panel);
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String label, JComponent field, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void register() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO users (username, password, full_name, email, phone, address) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, usernameField.getText());
            pstmt.setString(2, new String(passwordField.getPassword()));
            pstmt.setString(3, fullNameField.getText());
            pstmt.setString(4, emailField.getText());
            pstmt.setString(5, phoneField.getText());
            pstmt.setString(6, addressArea.getText());

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registration successful!");
            new LoginForm().setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Registration failed: " + ex.getMessage());
        }
    }
}
