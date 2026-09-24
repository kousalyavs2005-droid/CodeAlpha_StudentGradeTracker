package com.studentgradetracker.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.studentgradetracker.util.AppColors;

/**
 * Dashboard-page stat card: a thin colored bar across the top, a centered
 * colored icon, a large centered value in white, and a small muted
 * centered label underneath.
 */
public class DashboardStatCard extends JPanel {
    private static final int TOP_BAR_HEIGHT = 4;
    private final JLabel value = new JLabel();

    public DashboardStatCard(String icon, String title, String initialValue, Color accent) {
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel body = new JPanel();
        body.setBackground(AppColors.CARD);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(new EmptyBorder(20, 16, 22, 16));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        iconLabel.setForeground(accent);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        value.setText(initialValue);
        value.setForeground(AppColors.TEXT);
        value.setFont(new Font("Segoe UI", Font.BOLD, 26));
        value.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(title.toUpperCase());
        titleLabel.setForeground(AppColors.MUTED);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        body.add(iconLabel);
        body.add(Box.createVerticalStrut(12));
        body.add(value);
        body.add(Box.createVerticalStrut(6));
        body.add(titleLabel);

        JPanel topBar = new JPanel();
        topBar.setPreferredSize(new Dimension(10, TOP_BAR_HEIGHT));
        topBar.setBackground(accent);

        add(topBar, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
    }

    public void setValue(String text) {
        value.setText(text);
    }
}
