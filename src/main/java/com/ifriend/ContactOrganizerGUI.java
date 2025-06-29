package com.ifriend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactOrganizerGUI extends JFrame {
    private List<Contact> contacts;
    private DefaultTableModel tableModel;
    private JTable contactTable;
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;
    
    // Form fields
    private JTextField nameField, phoneField, companyField, salaryField, birthdayField;
    private JDialog addDialog, updateDialog;
    private Contact selectedContact;
    
    public ContactOrganizerGUI() {
        contacts = new ArrayList<>();
        initializeGUI();
        setupEventHandlers();
    }
    
    private void initializeGUI() {
        setTitle("iFRIEND Contact Organizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Create main content panel
        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Set window properties
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 500));
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(41, 128, 185));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("iFRIEND Contact Organizer", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);
        
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        
        panel.add(titleLabel, BorderLayout.CENTER);
        panel.add(searchPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create table
        String[] columnNames = {"ID", "Name", "Phone Number", "Company", "Salary", "Birthday"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        contactTable = new JTable(tableModel);
        contactTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactTable.setRowHeight(25);
        contactTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        contactTable.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Set column widths
        contactTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        contactTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        contactTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        contactTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        contactTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        contactTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        
        // Add sorting capability
        sorter = new TableRowSorter<>(tableModel);
        contactTable.setRowSorter(sorter);
        
        JScrollPane scrollPane = new JScrollPane(contactTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Contacts"));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(new Color(236, 240, 241));
        
        JButton addBtn = createStyledButton("Add Contact", new Color(46, 204, 113));
        JButton updateBtn = createStyledButton("Update Contact", new Color(52, 152, 219));
        JButton deleteBtn = createStyledButton("Delete Contact", new Color(231, 76, 60));
        JButton sortNameBtn = createStyledButton("Sort by Name", new Color(155, 89, 182));
        JButton sortSalaryBtn = createStyledButton("Sort by Salary", new Color(230, 126, 34));
        JButton sortBirthdayBtn = createStyledButton("Sort by Birthday", new Color(26, 188, 156));
        
        panel.add(addBtn);
        panel.add(updateBtn);
        panel.add(deleteBtn);
        panel.add(new JSeparator(SwingConstants.VERTICAL));
        panel.add(sortNameBtn);
        panel.add(sortSalaryBtn);
        panel.add(sortBirthdayBtn);
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
        
        return button;
    }
    
    private void setupEventHandlers() {
        // Search functionality
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String searchText = searchField.getText().trim();
                if (searchText.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
                }
            }
        });
        
        // Button event handlers
        Component[] components = ((JPanel) getContentPane().getComponent(2)).getComponents();
        for (Component comp : components) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                String text = btn.getText();
                
                switch (text) {
                    case "Add Contact":
                        btn.addActionListener(e -> showAddContactDialog());
                        break;
                    case "Update Contact":
                        btn.addActionListener(e -> showUpdateContactDialog());
                        break;
                    case "Delete Contact":
                        btn.addActionListener(e -> deleteContact());
                        break;
                    case "Sort by Name":
                        btn.addActionListener(e -> sortContacts(1));
                        break;
                    case "Sort by Salary":
                        btn.addActionListener(e -> sortContacts(4));
                        break;
                    case "Sort by Birthday":
                        btn.addActionListener(e -> sortContacts(5));
                        break;
                }
            }
        }
    }
    
    private void showAddContactDialog() {
        addDialog = createContactDialog("Add New Contact", true);
        addDialog.setVisible(true);
    }
    
    private void showUpdateContactDialog() {
        int selectedRow = contactTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a contact to update.", 
                                        "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int modelRow = contactTable.convertRowIndexToModel(selectedRow);
        selectedContact = contacts.get(modelRow);
        
        updateDialog = createContactDialog("Update Contact", false);
        
        // Populate fields with existing data
        nameField.setText(selectedContact.getName());
        phoneField.setText(selectedContact.getPhoneNumber());
        companyField.setText(selectedContact.getCompanyName());
        salaryField.setText(String.valueOf(selectedContact.getSalary()));
        birthdayField.setText(selectedContact.getBirthday());
        
        updateDialog.setVisible(true);
    }
    
    private JDialog createContactDialog(String title, boolean isAdd) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        
        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Form fields
        nameField = new JTextField(20);
        phoneField = new JTextField(20);
        companyField = new JTextField(20);
        salaryField = new JTextField(20);
        birthdayField = new JTextField(20);
        
        // Add components to form
        addFormField(formPanel, "Name:", nameField, gbc, 0);
        addFormField(formPanel, "Phone Number:", phoneField, gbc, 1);
        addFormField(formPanel, "Company Name:", companyField, gbc, 2);
        addFormField(formPanel, "Salary:", salaryField, gbc, 3);
        addFormField(formPanel, "Birthday (YYYY-MM-DD):", birthdayField, gbc, 4);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveBtn = new JButton(isAdd ? "Add Contact" : "Update Contact");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.setBackground(new Color(46, 204, 113));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        
        cancelBtn.setBackground(new Color(149, 165, 166));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        
        saveBtn.addActionListener(e -> {
            if (isAdd) {
                addContact();
            } else {
                updateContact();
            }
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        return dialog;
    }
    
    private void addFormField(JPanel panel, String labelText, JTextField field, 
                             GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(field, gbc);
        gbc.fill = GridBagConstraints.NONE;
    }
    
    private void addContact() {
        if (validateInput()) {
            String id = generateId();
            Contact contact = new Contact(
                id,
                nameField.getText().trim(),
                phoneField.getText().trim(),
                companyField.getText().trim(),
                Double.parseDouble(salaryField.getText().trim()),
                birthdayField.getText().trim()
            );
            
            contacts.add(contact);
            refreshTable();
            addDialog.dispose();
            
            JOptionPane.showMessageDialog(this, "Contact added successfully!", 
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void updateContact() {
        if (validateInput()) {
            selectedContact.setName(nameField.getText().trim());
            selectedContact.setPhoneNumber(phoneField.getText().trim());
            selectedContact.setCompanyName(companyField.getText().trim());
            selectedContact.setSalary(Double.parseDouble(salaryField.getText().trim()));
            selectedContact.setBirthday(birthdayField.getText().trim());
            
            refreshTable();
            updateDialog.dispose();
            
            JOptionPane.showMessageDialog(this, "Contact updated successfully!", 
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void deleteContact() {
        int selectedRow = contactTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a contact to delete.", 
                                        "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int modelRow = contactTable.convertRowIndexToModel(selectedRow);
        Contact contact = contacts.get(modelRow);
        
        int result = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete contact: " + contact.getName() + "?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            contacts.remove(modelRow);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Contact deleted successfully!", 
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void sortContacts(int column) {
        sorter.setSortKeys(List.of(new RowSorter.SortKey(column, SortOrder.ASCENDING)));
    }
    
    private boolean validateInput() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String company = companyField.getText().trim();
        String salaryText = salaryField.getText().trim();
        String birthday = birthdayField.getText().trim();
        
        if (name.isEmpty() || phone.isEmpty() || company.isEmpty() || 
            salaryText.isEmpty() || birthday.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", 
                                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!isValidPhoneNumber(phone)) {
            JOptionPane.showMessageDialog(this, 
                "Phone number must be 10 digits and start with 0!", 
                "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            double salary = Double.parseDouble(salaryText);
            if (salary <= 0) {
                JOptionPane.showMessageDialog(this, "Salary must be a positive number!", 
                                            "Invalid Salary", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid salary!", 
                                        "Invalid Salary", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!isValidBirthday(birthday)) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid birthday in YYYY-MM-DD format!", 
                "Invalid Birthday", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 10 || phoneNumber.charAt(0) != '0') {
            return false;
        }
        
        for (int i = 1; i < phoneNumber.length(); i++) {
            if (!Character.isDigit(phoneNumber.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isValidBirthday(String birthday) {
        try {
            LocalDate date = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate currentDate = LocalDate.now();
            return date.isBefore(currentDate) || date.isEqual(currentDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    private String generateId() {
        if (contacts.isEmpty()) {
            return "C0001";
        }
        
        String lastId = contacts.get(contacts.size() - 1).getId();
        int lastNo = Integer.parseInt(lastId.substring(1));
        return String.format("C%04d", lastNo + 1);
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Contact contact : contacts) {
            Object[] row = {
                contact.getId(),
                contact.getName(),
                contact.getPhoneNumber(),
                contact.getCompanyName(),
                String.format("%.2f", contact.getSalary()),
                contact.getBirthday()
            };
            tableModel.addRow(row);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            new ContactOrganizerGUI().setVisible(true);
        });
    }
}