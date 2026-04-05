package frontend.admin;

import frontend.common.UITheme;
import javax.swing.*;
import java.awt.*;

public class AdminLoginUI extends JFrame {

    public AdminLoginUI() {
        setTitle("Admin Login");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel bgPanel = UITheme.createGradientPanel();
        bgPanel.setLayout(new GridBagLayout());

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setOpaque(false);

        JLabel lblTitle = new JLabel("ADMIN LOGIN", SwingConstants.CENTER);
        lblTitle.setFont(UITheme.TITLE_FONT);
        lblTitle.setForeground(UITheme.TEXT_LIGHT);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContent.add(lblTitle);
        mainContent.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(UITheme.DARK_PANEL);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        loginPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtId = new JTextField();
        JPanel pnlId = UITheme.createStyledTextField(txtId);
        pnlId.setMaximumSize(new Dimension(300, 45));
        loginPanel.add(pnlId);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPasswordField txtPassword = new JPasswordField();
        JPanel pnlPassword = UITheme.createStyledTextField(txtPassword);
        pnlPassword.setMaximumSize(new Dimension(300, 45));
        loginPanel.add(pnlPassword);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JButton btnLogin = UITheme.createPrimaryButton("LOGIN");
        btnLogin.setMaximumSize(new Dimension(300, 45));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(btnLogin);

        mainContent.add(loginPanel);

        bgPanel.add(mainContent);
        add(bgPanel);

        btnLogin.addActionListener(e -> {
            String adminId = txtId.getText();
            String password = new String(txtPassword.getPassword());
            if (adminId.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Admin ID and Password");
                return;
            }

            if (adminId.equals("admin") && password.equals("admin123")) {
                JOptionPane.showMessageDialog(this, "Admin Login Successful");
                dispose();
                new AdminDashboardUI().showDashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Admin ID or Password", "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void login() {
        this.setVisible(true);
    }
}
