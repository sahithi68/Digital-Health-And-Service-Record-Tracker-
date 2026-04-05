package frontend.admin;

import backend.dao.AdminDAO;
import frontend.common.UITheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddServiceRecordUI extends JFrame {

    public AddServiceRecordUI() {
        setTitle("Add Service Record");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel bgPanel = UITheme.createGradientPanel();
        bgPanel.setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("ADD SERVICE RECORD", SwingConstants.CENTER);
        lblTitle.setFont(UITheme.TITLE_FONT);
        lblTitle.setForeground(UITheme.TEXT_LIGHT);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        bgPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 40));

        // Patient ID
        JLabel lblPatient = new JLabel("Patient ID");
        lblPatient.setForeground(UITheme.TEXT_LIGHT);
        formPanel.add(lblPatient);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        JTextField txtPatient = new JTextField();
        JPanel pnlPatient = UITheme.createStyledTextField(txtPatient);
        pnlPatient.setMaximumSize(new Dimension(300, 45));
        formPanel.add(pnlPatient);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Service Name
        JLabel lblService = new JLabel("Service Name");
        lblService.setForeground(UITheme.TEXT_LIGHT);
        formPanel.add(lblService);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        JTextField txtService = new JTextField();
        JPanel pnlService = UITheme.createStyledTextField(txtService);
        pnlService.setMaximumSize(new Dimension(300, 45));
        formPanel.add(pnlService);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Amount
        JLabel lblAmount = new JLabel("Amount");
        lblAmount.setForeground(UITheme.TEXT_LIGHT);
        formPanel.add(lblAmount);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        JTextField txtAmount = new JTextField();
        JPanel pnlAmount = UITheme.createStyledTextField(txtAmount);
        pnlAmount.setMaximumSize(new Dimension(300, 45));
        formPanel.add(pnlAmount);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Status
        JLabel lblStatus = new JLabel("Status");
        lblStatus.setForeground(UITheme.TEXT_LIGHT);
        formPanel.add(lblStatus);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        String[] statuses = {"Pending", "Paid"};
        JComboBox<String> comboStatus = new JComboBox<>(statuses);
        comboStatus.setMaximumSize(new Dimension(300, 45));
        formPanel.add(comboStatus);
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JButton btnSubmit = UITheme.createPrimaryButton("SUBMIT");
        btnSubmit.setMaximumSize(new Dimension(300, 45));
        btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(btnSubmit);

        bgPanel.add(formPanel, BorderLayout.CENTER);
        add(bgPanel);

        btnSubmit.addActionListener((ActionEvent e) -> {
            String pId = txtPatient.getText().trim();
            String sName = txtService.getText().trim();
            String amtStr = txtAmount.getText().trim();
            String status = (String) comboStatus.getSelectedItem();

            if (pId.isEmpty() || sName.isEmpty() || amtStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
                return;
            }

            try {
                double amt = Double.parseDouble(amtStr);
                if (AdminDAO.addServiceRecord(pId, sName, amt, status)) {
                    JOptionPane.showMessageDialog(this, "Service Record added successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add record. Check Patient ID.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Amount must be a valid number.");
            }
        });
    }
}
