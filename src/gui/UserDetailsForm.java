package gui;

import database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UserDetailsForm extends JFrame {
    private JLabel fullNameLabel, emailLabel, phoneLabel, addressLabel;
    private int userId;

    public UserDetailsForm(int userId) {
        this.userId = userId;
        setTitle("Hospital Management System - User Details");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Initialize labels
        fullNameLabel = new JLabel();
        emailLabel = new JLabel();
        phoneLabel = new JLabel();
        addressLabel = new JLabel();

        // Add components
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Full Name: "), gbc);
        gbc.gridx = 1;
        panel.add(fullNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Email: "), gbc);
        gbc.gridx = 1;
        panel.add(emailLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Phone: "), gbc);
        gbc.gridx = 1;
        panel.add(phoneLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Address: "), gbc);
        gbc.gridx = 1;
        panel.add(addressLabel, gbc);

        // Logout button
        gbc.gridx = 1;
        gbc.gridy = 4;
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            new LoginForm().setVisible(true);
            this.dispose();
        });
        panel.add(logoutButton, gbc);

        add(panel);
        loadUserDetails();
    }

    private void loadUserDetails() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                fullNameLabel.setText(rs.getString("full_name"));
                emailLabel.setText(rs.getString("email"));
                phoneLabel.setText(rs.getString("phone"));
                addressLabel.setText(rs.getString("address"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading user details: " + ex.getMessage());
        }
    }
}
