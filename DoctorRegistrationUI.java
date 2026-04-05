package frontend.doctor;

import backend.dao.DoctorDAO;
import frontend.common.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class AddHealthRecordUI extends JDialog {

    private String doctorId;
    private String selectedFilePath = "";
    private String selectedFileName = "";

    public AddHealthRecordUI(JFrame parent, String doctorId) {
        super(parent, "Add Health Record", true);
        this.doctorId = doctorId;

        setSize(500, 650);
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(UITheme.DARK_PANEL);
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        // Title
        JLabel lblTitle = new JLabel("NEW HEALTH RECORD", SwingConstants.CENTER);
        lblTitle.setFont(UITheme.TITLE_FONT);
        lblTitle.setForeground(UITheme.TEXT_LIGHT);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Patient ID
        mainPanel.add(createLabel("Patient ID"));
        JTextField txtPatId = new JTextField();
        mainPanel.add(UITheme.createStyledTextField(txtPatId));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Diagnosis
        mainPanel.add(createLabel("Diagnosis"));
        JTextArea txtDiag = new JTextArea(4, 20);
        txtDiag.setFont(UITheme.MAIN_FONT);
        txtDiag.setLineWrap(true);
        JScrollPane scrollDiag = new JScrollPane(txtDiag);
        scrollDiag.setMaximumSize(new Dimension(800, 80));
        mainPanel.add(scrollDiag);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Prescription
        mainPanel.add(createLabel("Prescription"));
        JTextArea txtPresc = new JTextArea(4, 20);
        txtPresc.setFont(UITheme.MAIN_FONT);
        txtPresc.setLineWrap(true);
        JScrollPane scrollPresc = new JScrollPane(txtPresc);
        scrollPresc.setMaximumSize(new Dimension(800, 80));
        mainPanel.add(scrollPresc);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // File Attachment
        mainPanel.add(createLabel("Attach Report (Optional)"));
        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        filePanel.setOpaque(false);
        filePanel.setMaximumSize(new Dimension(800, 40));
        
        JButton btnAttach = UITheme.createSecondaryButton("Browse...");
        JLabel lblFile = new JLabel(" No file selected");
        lblFile.setForeground(Color.GRAY);
        
        btnAttach.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                selectedFilePath = file.getAbsolutePath();
                selectedFileName = file.getName();
                lblFile.setText(" " + selectedFileName);
                lblFile.setForeground(UITheme.TEXT_LIGHT);
            }
        });

        filePanel.add(btnAttach);
        filePanel.add(lblFile);
        mainPanel.add(filePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setOpaque(false);

        JButton btnSubmit = UITheme.createPrimaryButton("SUBMIT");
        JButton btnCancel = UITheme.createSecondaryButton("CANCEL");

        btnCancel.addActionListener(e -> dispose());
        btnSubmit.addActionListener(e -> {
            String patId = txtPatId.getText().trim();
            String diag = txtDiag.getText().trim();
            String presc = txtPresc.getText().trim();

            if (patId.isEmpty() || diag.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Patient ID and Diagnosis are required!");
                return;
            }

            boolean success = DoctorDAO.addHealthRecord(AddHealthRecordUI.this.doctorId, patId, diag, presc, selectedFileName, selectedFilePath);
            if (success) {
                JOptionPane.showMessageDialog(this, "Health Record added successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Database Error. Ensure Patient ID exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnPanel.add(btnSubmit);
        btnPanel.add(btnCancel);
        mainPanel.add(btnPanel);

        add(mainPanel);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(UITheme.TEXT_LIGHT);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }
}
