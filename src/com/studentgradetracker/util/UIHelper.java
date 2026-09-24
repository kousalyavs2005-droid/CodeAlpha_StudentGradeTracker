package com.studentgradetracker.util;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public final class UIHelper {
    private UIHelper() {}

    public static JLabel title(String text, int size) {
        JLabel label = new JLabel(text);
        label.setForeground(AppColors.TEXT);
        label.setFont(new Font("Segoe UI", Font.BOLD, size));
        return label;
    }

    public static JLabel muted(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(AppColors.MUTED);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        return label;
    }

    /** Small uppercase label shown above form fields. */
    public static JLabel fieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(AppColors.MUTED);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        return label;
    }

    public static void styleTable(JTable table) {
        table.setBackground(AppColors.CARD);
        table.setForeground(AppColors.TEXT);
        table.setGridColor(AppColors.BORDER);
        table.setRowHeight(38);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setSelectionBackground(AppColors.PURPLE);
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setBackground(AppColors.SIDEBAR);
        table.getTableHeader().setForeground(AppColors.TEXT);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
    }

    /** Rounded card with a subtle border. */
    public static JPanel cardLayoutPanel() {
        return cardLayoutPanel(AppColors.CARD, AppColors.BORDER, 12, 18);
    }

    /** Card with custom colours, corner radius (0 = square) and padding. */
    public static JPanel cardLayoutPanel(Color bg, Color border, int radius, int pad) {
        JPanel p = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
                g2.setColor(border);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        p.setOpaque(false);
        p.setBackground(bg);
        p.setBorder(new EmptyBorder(pad, pad, pad, pad));
        return p;
    }

    /** Colour used for a grade badge (A+/A green, B blue, C amber, D orange, F red). */
    public static Color gradeColor(String grade) {
        if (grade == null) return AppColors.MUTED;
        switch (grade) {
            case "A+":
            case "A": return AppColors.GREEN;
            case "B": return AppColors.BLUE;
            case "C": return AppColors.ORANGE;
            case "D": return new Color(249, 115, 22);
            default:  return AppColors.RED;
        }
    }

    /** Purple rounded primary button. */
    public static JButton button(String text) {
        RoundedButton b = new RoundedButton(text, null);
        b.setBackground(AppColors.PURPLE);
        return b;
    }

    /** Dark rounded secondary button with an outline (e.g. "Clear"). */
    public static JButton secondaryButton(String text) {
        RoundedButton b = new RoundedButton(text, AppColors.BORDER);
        b.setBackground(new Color(36, 37, 61));
        return b;
    }

    /** Rounded password field, same look as {@link #input(String)}. */
    public static JPasswordField passwordInput() {
        RoundedPasswordField field = new RoundedPasswordField();
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBackground(AppColors.INPUT);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(new EmptyBorder(10, 12, 10, 12));
        return field;
    }

    /** Fully custom rounded button (background, outline and text colours). */
    public static JButton outlineButton(String text, Color bg, Color border, Color fg) {
        RoundedButton b = new RoundedButton(text, border);
        b.setBackground(bg);
        b.setForeground(fg);
        return b;
    }

    /** Rounded text field that highlights in purple when focused. */
    public static JTextField input(String placeholder) {
        RoundedTextField field = new RoundedTextField();
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBackground(AppColors.INPUT);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(new EmptyBorder(10, 12, 10, 12));
        field.setToolTipText(placeholder);
        return field;
    }

    // ---------------------------------------------------------------
    private static final class RoundedButton extends JButton {
        private final Color outline;

        RoundedButton(String text, Color outline) {
            super(text);
            this.outline = outline;
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);
            setFocusPainted(false);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
            setBorder(new EmptyBorder(10, 20, 10, 20));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setRolloverEnabled(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color fill = getBackground();
            if (getModel().isPressed()) fill = fill.darker();
            else if (getModel().isRollover()) fill = brighten(fill);
            g2.setColor(fill);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            if (outline != null) {
                g2.setColor(outline);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            }
            g2.dispose();
            super.paintComponent(g);
        }

        private static Color brighten(Color c) {
            return new Color(Math.min(255, c.getRed() + 14),
                             Math.min(255, c.getGreen() + 14),
                             Math.min(255, c.getBlue() + 14));
        }
    }

    private static final class RoundedTextField extends JTextField {
        RoundedTextField() {
            setOpaque(false);
            addFocusListener(new FocusAdapter() {
                @Override public void focusGained(FocusEvent e) { repaint(); }
                @Override public void focusLost(FocusEvent e)   { repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(hasFocus() ? AppColors.PURPLE : AppColors.BORDER);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
            g2.dispose();
        }
    }

    private static final class RoundedPasswordField extends JPasswordField {
        RoundedPasswordField() {
            setOpaque(false);
            addFocusListener(new FocusAdapter() {
                @Override public void focusGained(FocusEvent e) { repaint(); }
                @Override public void focusLost(FocusEvent e)   { repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(hasFocus() ? AppColors.PURPLE : AppColors.BORDER);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
            g2.dispose();
        }
    }
}
