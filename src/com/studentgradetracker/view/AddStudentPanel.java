package com.studentgradetracker.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.studentgradetracker.controller.AppController;
import com.studentgradetracker.model.Student;
import com.studentgradetracker.util.AppColors;
import com.studentgradetracker.util.IconFactory;
import com.studentgradetracker.util.UIHelper;
import com.studentgradetracker.util.ValidationUtil;

public class AddStudentPanel extends JPanel {
    private final AppController controller;
    private JTextField idField, nameField, javaField, pythonField, sqlField, htmlField, mlField;

    public AddStudentPanel(AppController controller, Runnable afterSave) {
        this.controller = controller;
        // afterSave is accepted for API symmetry with the other panels but is
        // intentionally not invoked here: the reference UI stays on the Add
        // Student form (with a confirmation dialog) after a save, rather than
        // navigating away automatically.
        setLayout(new BorderLayout());
        setOpaque(false); // lets the starry background of the main area show through
        setBorder(new EmptyBorder(10, 24, 24, 24));
        build();
    }

    private void build() {
        JPanel card = UIHelper.cardLayoutPanel();
        card.setBorder(new EmptyBorder(14, 20, 22, 20));

        // ----- card header:  "+  Student Details" -----
        JLabel heading = UIHelper.title("Student Details", 14);
        heading.setIcon(IconFactory.get(IconFactory.IconType.ADD, 16, Color.WHITE));
        heading.setIconTextGap(10);
        heading.setBorder(new EmptyBorder(0, 0, 10, 0));
        card.add(heading, BorderLayout.NORTH);

        // ----- form grid -----
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 0, 6, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;

        idField = UIHelper.input("e.g. STU014");
        nameField = UIHelper.input("e.g. Kousalya V");
        javaField = UIHelper.input("0 - 100");
        pythonField = UIHelper.input("0 - 100");
        sqlField = UIHelper.input("0 - 100");
        htmlField = UIHelper.input("0 - 100");
        mlField = UIHelper.input("0 - 100");

        addField(form, c, 0, "STUDENT ID", idField, 0);
        addField(form, c, 0, "FULL NAME", nameField, 1);
        addField(form, c, 1, "JAVA (0-100)", javaField, 0);
        addField(form, c, 1, "PYTHON (0-100)", pythonField, 1);
        addField(form, c, 2, "SQL (0-100)", sqlField, 0);
        addField(form, c, 2, "HTML (0-100)", htmlField, 1);
        addField(form, c, 3, "MACHINE LEARNING (0-100)", mlField, 0);

        // ----- buttons -----
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        actions.setOpaque(false);
        JButton add = UIHelper.button("Add Student");
        JButton clear = UIHelper.secondaryButton("Clear");
        add.addActionListener(e -> save());
        clear.addActionListener(e -> clearFields());
        actions.add(add);
        actions.add(clear);

        c.gridx = 0; c.gridy = 4; c.gridwidth = 2;
        c.insets = new Insets(18, 0, 0, 0);
        form.add(actions, c);

        card.add(form, BorderLayout.CENTER);
        add(card, BorderLayout.NORTH);
    }

    private void addField(JPanel form, GridBagConstraints c, int row,
                          String label, JTextField field, int col) {
        JPanel p = new JPanel(new BorderLayout(0, 5));
        p.setOpaque(false);
        p.add(UIHelper.fieldLabel(label), BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        c.gridx = col;
        c.gridy = row;
        c.gridwidth = 1;
        // gap between the two columns
        c.insets = new Insets(6, col == 0 ? 0 : 8, 6, col == 0 ? 8 : 0);
        form.add(p, c);
    }

    private void save() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();

        if (!ValidationUtil.validId(id) || !ValidationUtil.validName(name)) {
            error("Student ID and student name are required.");
            return;
        }

        if (controller.findById(id) != null) {
            error("A student with this ID already exists.");
            return;
        }

        int java = ValidationUtil.parseMark(javaField.getText());
        int python = ValidationUtil.parseMark(pythonField.getText());
        int sql = ValidationUtil.parseMark(sqlField.getText());
        int html = ValidationUtil.parseMark(htmlField.getText());
        int ml = ValidationUtil.parseMark(mlField.getText());

        if (java < 0 || python < 0 || sql < 0 || html < 0 || ml < 0) {
            error("All marks must be whole numbers from 0 to 100.");
            return;
        }

        Student student = new Student(id, name, java, python, sql, html, ml);
        controller.addStudent(student);

        JOptionPane.showMessageDialog(this,
                "Student added successfully!\nAverage: " +
                String.format("%.2f%%", student.getAverage()) +
                "\nGrade: " + student.getGrade(),
                "Success", JOptionPane.INFORMATION_MESSAGE);

        clearFields();
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        javaField.setText("");
        pythonField.setText("");
        sqlField.setText("");
        htmlField.setText("");
        mlField.setText("");
    }

    private void error(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error",
                JOptionPane.WARNING_MESSAGE);
    }
}
