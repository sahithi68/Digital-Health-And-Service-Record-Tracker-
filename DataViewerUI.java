package frontend.admin;

import backend.dao.AdminDAO;
import frontend.common.UITheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminDashboardUI extends JFrame {

    public AdminDashboardUI() {
        setTitle("Admin Dashboard");
        setSize(400, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel bgPanel = UITheme.createGradientPanel();
        bgPanel.setLayout(new BorderLayout());

        // Header Label
        JLabel titleLabel = new JLabel("ADMIN DASHBOARD", SwingConstants.CENTER);
        titleLabel.setFont(UITheme.TITLE_FONT);
        titleLabel.setForeground(UITheme.TEXT_LIGHT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        bgPanel.add(titleLabel, BorderLayout.NORTH);

        // Center Buttons Stack
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 40, 50));

        String[] menuItems = {
            "View All Doctors", "Register Doctor", "Remove Doctor",
            "View All Patients", "View Appointments", "Remove Patient",
            "Add Service Record", "Logout"
        };

        for (String item : menuItems) {
            JButton btn = createDashboardButton(item);
            btn.setMaximumSize(new Dimension(300, 50));
            // Add spacing
            buttonPanel.add(btn);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            
            btn.addActionListener(e -> handleMenuClick(item));
        }

        bgPanel.add(buttonPanel, BorderLayout.CENTER);
        add(bgPanel);
    }

    private void handleMenuClick(String item) {
        switch(item) {
            case "View All Doctors":
                String[] headersDoc = {"Doctor ID", "Name", "Specialization", "Gender"};
                String[][] dataDoc = AdminDAO.getAllDoctors();
                new frontend.common.DataViewerUI(this, "All Doctors", headersDoc, dataDoc).setVisible(true);
                break;
            case "Register Doctor":
                new frontend.doctor.DoctorRegistrationUI().setVisible(true);
                break;
            case "View All Patients":
                String[] headersPat = {"Patient ID", "Name", "Age", "Blood Group", "Phone Number"};
                String[][] dataPat = AdminDAO.getAllPatients();
                new frontend.common.DataViewerUI(this, "All Patients", headersPat, dataPat).setVisible(true);
                break;
            case "View Appointments":
                String[] headersApp = {"Patient ID", "Doctor ID", "Date", "Status"};
                String[][] dataApp = AdminDAO.getAllAppointments();
                new frontend.common.DataViewerUI(this, "All Appointments", headersApp, dataApp).setVisible(true);
                break;
            case "Add Service Record":
                new AddServiceRecordUI().setVisible(true);
                break;
            case "Remove Doctor":
                String remDocId = JOptionPane.showInputDialog(this, "Enter Doctor ID to Remove:");
                if (remDocId != null && !remDocId.trim().isEmpty()) {
                    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to permanently delete Doctor " + remDocId + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        if (AdminDAO.removeDoctor(remDocId.trim())) {
                            JOptionPane.showMessageDialog(this, "Doctor removed successfully.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to remove doctor. Ensure ID is correct.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                break;
            case "Remove Patient":
                String remPatId = JOptionPane.showInputDialog(this, "Enter Patient ID to Remove:");
                if (remPatId != null && !remPatId.trim().isEmpty()) {
                    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to permanently delete Patient " + remPatId + " and ALL their historical connections?", "Confirm Wipe", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        if (AdminDAO.removePatient(remPatId.trim())) {
                            JOptionPane.showMessageDialog(this, "Patient and history wiped successfully.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to wipe patient. Ensure ID is correct.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                break;
            case "Logout":
                dispose();
                System.out.println("Logging out Admin...");
                break;
        }
    }

    private JButton createDashboardButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(UITheme.TEXT_LIGHT);
        button.setBackground(UITheme.DARK_PANEL);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(60, 80, 90), 1));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(UITheme.PRIMARY_BTN);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(UITheme.DARK_PANEL);
            }
        });

        return button;
    }

    public void showDashboard() {
        this.setVisible(true);
    }
}
