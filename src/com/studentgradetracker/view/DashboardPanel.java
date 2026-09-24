package com.studentgradetracker.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.studentgradetracker.controller.AppController;
import com.studentgradetracker.model.StudentStatistics;
import com.studentgradetracker.util.AppColors;

public class DashboardPanel extends StarryPanel {
    private final AppController controller;

    public DashboardPanel(AppController controller) {
        super(new BorderLayout());
        this.controller = controller;
        setBorder(new EmptyBorder(8, 24, 24, 24));
        build();
    }

    private void build() {
        StudentStatistics stats = controller.getStatistics();

        JPanel cards = new JPanel(new GridLayout(1, 4, 16, 0));
        cards.setOpaque(false);
        cards.add(new DashboardStatCard("\uD83D\uDC65", "Total Students",
                String.valueOf(stats.getTotalStudents()), AppColors.PURPLE_LIGHT));
        cards.add(new DashboardStatCard("\uD83D\uDCC8", "Class Average",
                String.format("%.2f%%", stats.getClassAverage()), AppColors.CYAN));
        cards.add(new DashboardStatCard("\uD83C\uDFC6", "Highest Score",
                stats.getHighestStudent() == null ? "-" :
                        String.format("%.1f%%", stats.getHighestStudent().getAverage()), AppColors.GREEN));
        cards.add(new DashboardStatCard("\uD83D\uDCC9", "Lowest Score",
                stats.getLowestStudent() == null ? "-" :
                        String.format("%.1f%%", stats.getLowestStudent().getAverage()), AppColors.RED));

        add(cards, BorderLayout.NORTH);
    }
}
