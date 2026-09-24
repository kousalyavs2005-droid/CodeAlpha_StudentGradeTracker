package com.studentgradetracker.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.studentgradetracker.controller.AppController;
import com.studentgradetracker.model.Student;
import com.studentgradetracker.model.StudentStatistics;
import com.studentgradetracker.util.AppColors;
import com.studentgradetracker.util.IconFactory;
import com.studentgradetracker.util.IconFactory.IconType;
import com.studentgradetracker.util.UIHelper;

/** Performance Report page: five stat cards, grade breakdown bars, top / lowest performer. */
public class PerformanceReportPanel extends JPanel {
    private static final int CONTENT_WIDTH = 820;
    private static final Color BAR_TEXT = new Color(108, 128, 208);

    private final AppController controller;

    public PerformanceReportPanel(AppController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setOpaque(false); // show the starry background
        setBorder(new EmptyBorder(10, 24, 24, 24));
        build();
    }

    private void build() {
        StudentStatistics s = controller.getStatistics();

        // ----- five stat cards -----
        JPanel cards = new JPanel(new GridLayout(1, 5, 10, 0));
        cards.setOpaque(false);
        cards.add(new StatCard(IconType.TROPHY, "Highest Score",
                s.getHighestStudent() == null ? "-" :
                        String.format("%.1f%%", s.getHighestStudent().getAverage()), AppColors.GREEN));
        cards.add(new StatCard(IconType.TREND_DOWN, "Lowest Score",
                s.getLowestStudent() == null ? "-" :
                        String.format("%.1f%%", s.getLowestStudent().getAverage()), AppColors.RED));
        cards.add(new StatCard(IconType.DASHBOARD, "Class Average",
                String.format("%.2f%%", s.getClassAverage()), AppColors.CYAN));
        cards.add(new StatCard(IconType.CHECK, "Pass Count",
                String.valueOf(s.getPassCount()), AppColors.GREEN));
        cards.add(new StatCard(IconType.CLOSE, "Fail Count",
                String.valueOf(s.getFailCount()), AppColors.RED));

        // ----- grade breakdown card -----
        JPanel center = UIHelper.cardLayoutPanel(AppColors.SLATE_CARD, AppColors.SLATE_BORDER, 0, 18);
        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JLabel title = UIHelper.title("Grade Breakdown", 17);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        body.add(title);
        body.add(Box.createVerticalStrut(18));

        String[] grades = {"A+", "A", "B", "C", "D", "F"};
        Color[] colors = {AppColors.GREEN, AppColors.GREEN, AppColors.BLUE,
                AppColors.CYAN, AppColors.ORANGE, AppColors.RED};
        for (int i = 0; i < grades.length; i++) {
            addGradeBar(body, grades[i], controller.countGrade(grades[i]),
                    s.getTotalStudents(), colors[i]);
        }

        body.add(Box.createVerticalStrut(28));

        JPanel performers = new JPanel(new GridLayout(1, 2, 12, 0));
        performers.setOpaque(false);
        if (s.getHighestStudent() != null) {
            performers.add(personCard(IconType.TROPHY, "Top Performer",
                    s.getHighestStudent(), AppColors.GREEN));
            performers.add(personCard(IconType.TREND_DOWN, "Lowest Performer",
                    s.getLowestStudent(), AppColors.RED));
        }
        body.add(performers);
        center.add(body, BorderLayout.CENTER);

        // ----- stack + centre horizontally (max 780px wide) -----
        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setOpaque(false);
        content.add(cards, BorderLayout.NORTH);
        content.add(center, BorderLayout.CENTER);

        JPanel column = new JPanel(new BorderLayout());
        column.setOpaque(false);
        column.add(content, BorderLayout.NORTH);
        column.setPreferredSize(new Dimension(CONTENT_WIDTH, 100));
        column.setMaximumSize(new Dimension(CONTENT_WIDTH, Integer.MAX_VALUE));

        JPanel row = new JPanel();
        row.setOpaque(false);
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.add(Box.createHorizontalGlue());
        row.add(column);
        row.add(Box.createHorizontalGlue());

        add(row, BorderLayout.CENTER);
    }

    private void addGradeBar(JPanel parent, String grade, int count, int total, Color color) {
        JPanel row = new JPanel(new BorderLayout(12, 0));
        row.setOpaque(false);
        row.setBorder(new EmptyBorder(5, 0, 5, 0));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        JLabel badge = new JLabel(grade, SwingConstants.CENTER);
        badge.setOpaque(true);
        badge.setBackground(color);
        badge.setForeground(Color.WHITE);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 12));
        badge.setPreferredSize(new Dimension(40, 28));

        row.add(badge, BorderLayout.WEST);
        row.add(new GradeBar(count, total, color), BorderLayout.CENTER);
        parent.add(row);
    }

    private JPanel personCard(IconType icon, String heading, Student st, Color color) {
        JPanel p = UIHelper.cardLayoutPanel(AppColors.SLATE_CARD, AppColors.SLATE_BORDER, 0, 16);
        JPanel inner = new JPanel();
        inner.setOpaque(false);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));

        JLabel t = new JLabel(heading);
        t.setIcon(IconFactory.get(icon, 14, color));
        t.setIconTextGap(6);
        t.setForeground(color);
        t.setFont(new Font("Segoe UI", Font.BOLD, 12));

        JLabel n = UIHelper.title(st.getName(), 17);
        JLabel d = UIHelper.muted(String.format("%.1f%% • Grade %s", st.getAverage(), st.getGrade()));

        inner.add(t);
        inner.add(Box.createVerticalStrut(10));
        inner.add(n);
        inner.add(Box.createVerticalStrut(4));
        inner.add(d);

        p.add(inner, BorderLayout.CENTER);
        return p;
    }

    /** Horizontal bar: coloured fill proportional to count, "N student(s)" centred on top. */
    private static class GradeBar extends JComponent {
        private final int count, total;
        private final Color color;

        GradeBar(int count, int total, Color color) {
            this.count = count;
            this.total = total;
            this.color = color;
            setPreferredSize(new Dimension(100, 28));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            g2.setColor(AppColors.SLATE_TILE);
            g2.fillRect(0, 0, w, h);
            if (total > 0 && count > 0) {
                g2.setColor(color);
                g2.fillRect(1, 1, (int) ((w - 2) * (count / (double) total)), h - 2);
            }
            g2.setColor(new Color(148, 163, 184, 200));
            g2.drawRect(0, 0, w - 1, h - 1);

            String text = count + " student(s)";
            g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
            FontMetrics fm = g2.getFontMetrics();
            g2.setColor(BAR_TEXT);
            g2.drawString(text, (w - fm.stringWidth(text)) / 2, (h - fm.getHeight()) / 2 + fm.getAscent());
            g2.dispose();
        }
    }
}
