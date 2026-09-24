package com.studentgradetracker.view;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import com.studentgradetracker.controller.AppController;
import com.studentgradetracker.model.Student;
import com.studentgradetracker.util.AppColors;
import com.studentgradetracker.util.IconFactory;
import com.studentgradetracker.util.ModernScrollBarUI;
import com.studentgradetracker.util.UIHelper;

/** Student Records page: a clean table with grade circles and Pass/Fail pills. */
public class StudentRecordsPanel extends JPanel {
    private static final Color ROW_LINE = new Color(38, 40, 74);
    private static final Color HEADER_LINE = new Color(58, 62, 100);
    private static final Color HEADER_TEXT = new Color(148, 160, 196);
    private static final Color CELL_TEXT = new Color(222, 224, 244);
    private static final DecimalFormat AVG = new DecimalFormat("0.#");

    private static final int COL_ID = 0, COL_NAME = 1, COL_AVG = 7, COL_GRADE = 8, COL_STATUS = 9;

    private final AppController controller;
    private final DefaultTableModel model;
    private final JTable table;

    public StudentRecordsPanel(AppController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setOpaque(false); // show the starry background
        setBorder(new EmptyBorder(10, 24, 24, 24));

        JPanel card = UIHelper.cardLayoutPanel(AppColors.CARD, AppColors.BORDER, 12, 18);
        card.setBorder(new EmptyBorder(16, 20, 12, 8));

        // ----- card header -----
        JLabel heading = UIHelper.title("Student Records", 14);
        heading.setIcon(IconFactory.get(IconFactory.IconType.RECORDS, 16, Color.WHITE));
        heading.setIconTextGap(10);
        heading.setBorder(new EmptyBorder(0, 0, 14, 0));
        card.add(heading, BorderLayout.NORTH);

        // ----- table -----
        String[] cols = {"ID", "NAME", "JAVA", "PYTHON", "SQL", "HTML",
                "MACHINE LEARNING", "AVERAGE", "GRADE", "STATUS"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
            @Override public Class<?> getColumnClass(int c) {
                if (c >= 2 && c <= 6) return Integer.class;
                if (c == COL_AVG) return Double.class;
                return String.class;
            }
        };

        table = new JTable(model);
        styleTable();
        table.setAutoCreateRowSorter(true);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(AppColors.CARD);
        scroll.setBackground(AppColors.CARD);
        ModernScrollBarUI.apply(scroll);
        JPanel corner = new JPanel();
        corner.setBackground(AppColors.CARD);
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, corner);
        card.add(scroll, BorderLayout.CENTER);

        add(card, BorderLayout.CENTER);
        loadData();
    }

    // ------------------------------------------------------------------
    private void styleTable() {
        table.setBackground(AppColors.CARD);
        table.setForeground(CELL_TEXT);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(44);
        table.setShowGrid(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(ROW_LINE);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFillsViewportHeight(true);
        table.setFocusable(false);
        table.setRowSelectionAllowed(true);
        table.setSelectionBackground(new Color(36, 36, 82));
        table.setSelectionForeground(Color.WHITE);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // column widths act as relative weights
        int[] widths = {90, 220, 80, 90, 70, 80, 170, 100, 80, 100};
        TableColumnModel cm = table.getColumnModel();
        for (int i = 0; i < widths.length; i++) cm.getColumn(i).setPreferredWidth(widths[i]);

        // cell renderers
        cm.getColumn(COL_ID).setCellRenderer(new TextRenderer(SwingConstants.LEFT, AppColors.MUTED, false, 12));
        cm.getColumn(COL_NAME).setCellRenderer(new TextRenderer(SwingConstants.LEFT, Color.WHITE, false, 0));
        for (int i = 2; i <= 6; i++)
            cm.getColumn(i).setCellRenderer(new TextRenderer(SwingConstants.CENTER, CELL_TEXT, false, 0));
        cm.getColumn(COL_AVG).setCellRenderer(new AverageRenderer());
        cm.getColumn(COL_GRADE).setCellRenderer(new GradeRenderer());
        cm.getColumn(COL_STATUS).setCellRenderer(new StatusRenderer());

        // header
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setBackground(AppColors.CARD);
        header.setPreferredSize(new Dimension(0, 42));
        header.setDefaultRenderer(new HeaderRenderer());
    }

    private void loadData() {
        model.setRowCount(0);
        List<Student> students = controller.getStudents();
        for (Student s : students) {
            model.addRow(new Object[]{
                    s.getStudentId(), s.getName(), s.getJava(), s.getPython(),
                    s.getSql(), s.getHtml(), s.getMachineLearning(),
                    s.getAverage(), s.getGrade(), s.isPass() ? "Pass" : "Fail"
            });
        }
    }

    // ------------------------------------------------------------------
    // Renderers
    // ------------------------------------------------------------------
    private static class TextRenderer extends DefaultTableCellRenderer {
        private final int align;
        private final Color color;
        private final boolean bold;
        private final int leftPad;

        TextRenderer(int align, Color color, boolean bold, int leftPad) {
            this.align = align;
            this.color = color;
            this.bold = bold;
            this.leftPad = leftPad;
        }

        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean sel,
                                                       boolean focus, int r, int c) {
            super.getTableCellRendererComponent(t, v, sel, false, r, c);
            setHorizontalAlignment(align);
            setForeground(color);
            setFont(t.getFont().deriveFont(bold ? Font.BOLD : Font.PLAIN));
            setBorder(new EmptyBorder(0, leftPad + 6, 0, 6));
            setBackground(sel ? t.getSelectionBackground() : t.getBackground());
            return this;
        }
    }

    private static class AverageRenderer extends TextRenderer {
        AverageRenderer() { super(SwingConstants.CENTER, Color.WHITE, true, 0); }

        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean sel,
                                                       boolean focus, int r, int c) {
            Object shown = (v instanceof Number) ? AVG.format(((Number) v).doubleValue()) + "%" : v;
            return super.getTableCellRendererComponent(t, shown, sel, focus, r, c);
        }
    }

    /** Coloured circle containing the grade letter. */
    private static class GradeRenderer extends JLabel implements TableCellRenderer {
        private Color circle = AppColors.MUTED;

        GradeRenderer() { setOpaque(false); setHorizontalAlignment(CENTER); }

        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean sel,
                                                       boolean focus, int r, int c) {
            String grade = v == null ? "-" : v.toString();
            setText(grade);
            circle = UIHelper.gradeColor(grade);
            setBackground(sel ? t.getSelectionBackground() : t.getBackground());
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, grade.length() > 1 ? 10 : 12));
            return this;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRect(0, 0, getWidth(), getHeight());
            int d = 24;
            int x = (getWidth() - d) / 2, y = (getHeight() - d) / 2;
            g2.setColor(circle);
            g2.fillOval(x, y, d, d);
            g2.dispose();
            // draw only the text on top (background already painted)
            super.paintComponent(g);
        }
    }

    /** Green "Pass" / red "Fail" pill. */
    private static class StatusRenderer extends JLabel implements TableCellRenderer {
        private boolean pass = true;

        StatusRenderer() { setOpaque(false); setHorizontalAlignment(CENTER); }

        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean sel,
                                                       boolean focus, int r, int c) {
            String text = v == null ? "" : v.toString();
            pass = "Pass".equalsIgnoreCase(text);
            setText(text);
            setBackground(sel ? t.getSelectionBackground() : t.getBackground());
            setForeground(pass ? new Color(74, 222, 128) : new Color(255, 123, 141));
            setFont(new Font("Segoe UI", Font.BOLD, 11));
            return this;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRect(0, 0, getWidth(), getHeight());
            int w = 52, h = 22;
            int x = (getWidth() - w) / 2, y = (getHeight() - h) / 2;
            g2.setColor(pass ? new Color(16, 58, 46) : new Color(64, 24, 40));
            g2.fillRoundRect(x, y, w, h, 12, 12);
            g2.setColor(pass ? new Color(34, 140, 84) : new Color(160, 50, 70));
            g2.drawRoundRect(x, y, w, h, 12, 12);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class HeaderRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean sel,
                                                       boolean focus, int r, int c) {
            super.getTableCellRendererComponent(t, v, false, false, r, c);
            setOpaque(true);
            setBackground(AppColors.CARD);
            setForeground(HEADER_TEXT);
            setFont(new Font("Segoe UI", Font.BOLD, 11));
            boolean left = c == COL_ID || c == COL_NAME;
            setHorizontalAlignment(left ? SwingConstants.LEFT : SwingConstants.CENTER);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, HEADER_LINE),
                    new EmptyBorder(0, c == COL_ID ? 18 : 6, 0, 6)));
            return this;
        }
    }
}
