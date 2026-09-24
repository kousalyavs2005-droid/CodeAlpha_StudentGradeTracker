package com.studentgradetracker.util;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

/** Thin, rounded, dark scrollbar with no arrow buttons. */
public class ModernScrollBarUI extends BasicScrollBarUI {

    /** Applies the modern look to both scrollbars of a scroll pane. */
    public static void apply(JScrollPane scroll) {
        scroll.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        scroll.getVerticalScrollBar().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(18);
        scroll.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        scroll.getHorizontalScrollBar().setOpaque(false);
    }

    @Override protected JButton createDecreaseButton(int o) { return zeroButton(); }
    @Override protected JButton createIncreaseButton(int o) { return zeroButton(); }

    private JButton zeroButton() {
        JButton b = new JButton();
        b.setPreferredSize(new Dimension(0, 0));
        b.setMinimumSize(new Dimension(0, 0));
        b.setMaximumSize(new Dimension(0, 0));
        return b;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
        // transparent track
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        if (r.isEmpty() || !scrollbar.isEnabled()) return;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(isThumbRollover() || isDragging ? new Color(110, 113, 165) : new Color(74, 77, 122));
        g2.fillRoundRect(r.x + 2, r.y + 2, r.width - 4, r.height - 4, 8, 8);
        g2.dispose();
    }
}
