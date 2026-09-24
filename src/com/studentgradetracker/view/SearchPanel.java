package com.studentgradetracker.view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.studentgradetracker.controller.AppController;
import com.studentgradetracker.model.Student;
import com.studentgradetracker.util.AppColors;
import com.studentgradetracker.util.IconFactory;
import com.studentgradetracker.util.IconFactory.IconType;
import com.studentgradetracker.util.ModernScrollBarUI;
import com.studentgradetracker.util.UIHelper;
import com.studentgradetracker.util.ValidationUtil;

/** Search / Update / Delete page: search bar on top and one card per student. */
public class SearchPanel extends JPanel {
    private final AppController controller;
    private final ScrollableColumn listPanel = new ScrollableColumn();
    private JTextField searchField;

    public SearchPanel(AppController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setOpaque(false); // show the starry background
        setBorder(new EmptyBorder(10, 24, 24, 24));
        build();
        refresh(controller.getStudents());
    }

    // ------------------------------------------------------------------
    private void build() {
        JPanel root = new JPanel(new BorderLayout(0, 14));
        root.setOpaque(false);

        // ----- search bar card -----
        JPanel searchCard = UIHelper.cardLayoutPanel(AppColors.SLATE_CARD, AppColors.SLATE_BORDER, 6, 18);
        JPanel searchRow = new JPanel(new BorderLayout(10, 0));
        searchRow.setOpaque(false);

        searchField = UIHelper.input("Search by Student ID or Name...");
        searchField.setBackground(AppColors.SLATE_INPUT);

        JButton search = UIHelper.button("Search");
        search.setIcon(IconFactory.get(IconType.SEARCH, 15, Color.WHITE));
        search.setIconTextGap(8);
        JButton showAll = UIHelper.button("Show All");

        Runnable doSearch = () -> refresh(controller.searchStudents(searchField.getText()));
        search.addActionListener(e -> doSearch.run());
        searchField.addActionListener(e -> doSearch.run()); // Enter key
        showAll.addActionListener(e -> {
            searchField.setText("");
            refresh(controller.getStudents());
        });

        searchRow.add(showAll, BorderLayout.WEST);
        searchRow.add(searchField, BorderLayout.CENTER);
        searchRow.add(search, BorderLayout.EAST);
        searchCard.add(searchRow, BorderLayout.CENTER);

        // ----- scrolling list -----
        listPanel.setOpaque(false);
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(listPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        ModernScrollBarUI.apply(scroll);

        root.add(searchCard, BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);
        add(root, BorderLayout.CENTER);
    }

    private void refresh(List<Student> students) {
        listPanel.removeAll();

        if (students.isEmpty()) {
            JPanel empty = UIHelper.cardLayoutPanel(AppColors.SLATE_CARD, AppColors.SLATE_BORDER, 6, 18);
            empty.add(UIHelper.title("No students found", 16), BorderLayout.CENTER);
            listPanel.add(empty);
        }

        for (Student s : students) {
            listPanel.add(createStudentCard(s));
            listPanel.add(Box.createVerticalStrut(12));
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    // ------------------------------------------------------------------
    private JPanel createStudentCard(Student s) {
        JPanel card = UIHelper.cardLayoutPanel(AppColors.SLATE_CARD, AppColors.SLATE_BORDER, 6, 18);
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // header: name + PASS / FAIL badge
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 0, 8, 0));

        JLabel name = UIHelper.title(s.getName(), 16);
        JLabel badge = new JLabel(s.isPass() ? "PASS" : "FAIL", SwingConstants.CENTER);
        badge.setOpaque(true);
        badge.setForeground(Color.WHITE);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 12));
        badge.setBackground(s.isPass() ? AppColors.GREEN : AppColors.RED);
        badge.setBorder(new EmptyBorder(6, 12, 6, 12));

        header.add(name, BorderLayout.WEST);
        header.add(badge, BorderLayout.EAST);

        // five mark tiles
        JPanel marks = new JPanel(new GridLayout(1, 5, 12, 0));
        marks.setOpaque(false);
        addMark(marks, "Java", s.getJava());
        addMark(marks, "Python", s.getPython());
        addMark(marks, "SQL", s.getSql());
        addMark(marks, "HTML", s.getHtml());
        addMark(marks, "Machine Learning", s.getMachineLearning());

        // bottom: summary + action buttons
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.setBorder(new EmptyBorder(8, 0, 0, 0));

        JLabel summary = UIHelper.muted(
                "ID: " + s.getStudentId() +
                "    •    Average: " + String.format("%.1f%%", s.getAverage()) +
                "    •    Grade: " + s.getGrade() +
                "    •    Total: " + s.getTotal());

        JButton update = UIHelper.button("Update Marks");
        update.setIcon(IconFactory.get(IconType.EDIT, 14, Color.WHITE));
        update.setIconTextGap(8);
        JButton delete = UIHelper.button("Delete");
        delete.setIcon(IconFactory.get(IconType.TRASH, 14, Color.WHITE));
        delete.setIconTextGap(8);

        update.addActionListener(e -> editStudent(s));
        delete.addActionListener(e -> deleteStudent(s));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        actions.add(update);
        actions.add(delete);

        bottom.add(summary, BorderLayout.WEST);
        bottom.add(actions, BorderLayout.EAST);

        card.add(header, BorderLayout.NORTH);
        card.add(marks, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);
        return card;
    }

    private void addMark(JPanel parent, String label, int mark) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setBackground(AppColors.SLATE_TILE);
        p.setBorder(new EmptyBorder(10, 12, 12, 12));

        JLabel l = UIHelper.fieldLabel(label);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel v = new JLabel(String.valueOf(mark));
        v.setForeground(Color.WHITE);
        v.setFont(new Font("Segoe UI", Font.BOLD, 16));

        p.add(l, BorderLayout.NORTH);
        p.add(v, BorderLayout.CENTER);
        parent.add(p);
    }

    // ------------------------------------------------------------------
    private void editStudent(Student s) {
        JTextField name = UIHelper.input("Name");
        name.setText(s.getName());

        JTextField[] fields = {
                field(s.getJava()), field(s.getPython()), field(s.getSql()),
                field(s.getHtml()), field(s.getMachineLearning())
        };
        String[] labels = {"Java", "Python", "SQL", "HTML", "Machine Learning"};

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBackground(AppColors.BG);
        form.setBorder(new EmptyBorder(14, 14, 14, 14));
        form.add(UIHelper.muted("Name"));
        form.add(name);

        for (int i = 0; i < labels.length; i++) {
            form.add(UIHelper.muted(labels[i]));
            form.add(fields[i]);
        }

        int result = JOptionPane.showConfirmDialog(
                this, form, "Update " + s.getStudentId(),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (!ValidationUtil.validName(name.getText())) {
                JOptionPane.showMessageDialog(this, "Name is required.",
                        "Invalid Name", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int[] marks = new int[5];
            for (int i = 0; i < 5; i++) {
                marks[i] = ValidationUtil.parseMark(fields[i].getText());
                if (marks[i] < 0) {
                    JOptionPane.showMessageDialog(this,
                            "Marks must be between 0 and 100.",
                            "Invalid Mark", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            controller.updateStudent(s, name.getText().trim(),
                    marks[0], marks[1], marks[2], marks[3], marks[4]);
            refresh(searchField.getText().trim().isEmpty()
                    ? controller.getStudents()
                    : controller.searchStudents(searchField.getText()));
        }
    }

    private JTextField field(int value) {
        JTextField f = UIHelper.input("");
        f.setText(String.valueOf(value));
        return f;
    }

    private void deleteStudent(Student s) {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Delete " + s.getName() + " (" + s.getStudentId() + ")?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            controller.deleteStudent(s);
            refresh(searchField.getText().trim().isEmpty()
                    ? controller.getStudents()
                    : controller.searchStudents(searchField.getText()));
        }
    }

    // ------------------------------------------------------------------
    /** Vertical list that always matches the width of the scroll viewport. */
    private static class ScrollableColumn extends JPanel implements Scrollable {
        @Override public Dimension getPreferredScrollableViewportSize() { return getPreferredSize(); }
        @Override public int getScrollableUnitIncrement(Rectangle r, int o, int d) { return 24; }
        @Override public int getScrollableBlockIncrement(Rectangle r, int o, int d) { return 120; }
        @Override public boolean getScrollableTracksViewportWidth() { return true; }
        @Override public boolean getScrollableTracksViewportHeight() { return false; }
    }
}
