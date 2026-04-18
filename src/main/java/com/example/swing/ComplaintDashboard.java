package com.example.swing;

import com.example.entity.Complaint;
import com.example.service.ComplaintService;
import com.example.service.impl.ComplaintServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ComplaintDashboard extends JFrame {
    // Service instance
    private final ComplaintService service = new ComplaintServiceImpl();

    // UI Components
    private JTextField nameField, descField, statusField, idField;
    private JTextArea displayArea;

    public ComplaintDashboard() {
        setTitle("COMPLAINT MANAGEMENT SYSTEM");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("ID (for Update/Delete):"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Description:"));
        descField = new JTextField();
        panel.add(descField);

        panel.add(new JLabel("Status:"));
        statusField = new JTextField();
        panel.add(statusField);

        add(panel, BorderLayout.NORTH);

        // Display Area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Button Panel
        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton viewBtn = new JButton("View");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton procBtn = new JButton("Update via Procedure");

        btnPanel.add(addBtn);
        btnPanel.add(viewBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(procBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // --- Action Listeners ---

        addBtn.addActionListener(e -> {
            String name = nameField.getText();
            String desc = descField.getText();
            String status = statusField.getText().isEmpty() ? "OPEN" : statusField.getText();
            service.addComplaint(new Complaint(name, desc, status));
            showMessage("Complaint Added");
            clearFields();
        });

        viewBtn.addActionListener(e -> {
            List<Complaint> list = service.getAllComplaints();
            displayArea.setText("ID | Name | Description | Status\n");
            displayArea.append("--------------------------------------------------\n");
            list.forEach(c -> displayArea.append(
                    c.getId() + " | " +
                            c.getName() + " | " +
                            c.getDescription() + " | " +
                            c.getStatus() + "\n"
            ));
        });

        updateBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String status = statusField.getText();
                service.updateComplaint(id, status);
                showMessage("Updated Successfully");
            } catch (Exception ex) {
                showMessage("Error: " + ex.getMessage());
            }
        });

        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                service.deleteComplaint(id);
                showMessage("Deleted Successfully");
            } catch (Exception ex) {
                showMessage("Error: " + ex.getMessage());
            }
        });

        procBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String status = statusField.getText();
                service.updateStatusUsingProcedure(id, status);
                showMessage("Updated via Procedure");
            } catch (Exception ex) {
                showMessage("Error: " + ex.getMessage());
            }
        });

        // Initialize with data
        loadDummyData();
    }

    // --- Helper Methods ---
    private void loadDummyData() {
        // Only add if table is empty to avoid duplicates every time you run
        if (service.getAllComplaints().isEmpty()) {
            service.addComplaint(new Complaint("Sony", "TV not working", "OPEN"));
            service.addComplaint(new Complaint("Amit", "Internet slow", "OPEN"));
        }
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        descField.setText("");
        statusField.setText("");
    }

    // --- Main Method ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ComplaintDashboard().setVisible(true);
        });
    }
}