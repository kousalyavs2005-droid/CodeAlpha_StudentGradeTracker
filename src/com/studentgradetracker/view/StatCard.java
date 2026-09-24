package com.studentgradetracker.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.studentgradetracker.util.AppColors;
import com.studentgradetracker.util.IconFactory;
import com.studentgradetracker.util.IconFactory.IconType;

/** Performance Report stat card: coloured outline, faded icon, small title, big coloured value. */
public class StatCard extends JPanel {
    private final JLabel value = new JLabel();
    private final Color accent;

    public StatCard(IconType icon, String title, String initialValue, Color accent) {
        this.accent = accent;
        setLayout(new BorderLayout(8, 0));
        setOpaque(false);
        setBackground(AppColors.SLATE_CARD);
        setBorder(new EmptyBorder(14, 10, 14, 8));

        Color faded = new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 90);
        JLabel iconLabel = new JLabel(IconFactory.get(icon, 22, faded));
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);

        JLabel titleLabel = new JLabel(title.toUpperCase());
        titleLabel.setForeground(AppColors.MUTED);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));

        value.setText(initialValue);
        value.setForeground(accent);
        value.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.add(titleLabel);
        text.add(Box.createVerticalStrut(2));
        text.add(value);

        add(iconLabel, BorderLayout.WEST);
        add(text, BorderLayout.CENTER);
    }

    public void setValue(String text) {
        value.setText(text);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(accent);
        g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g2.dispose();
        super.paintComponent(g);
    }
}
