package com.studentgradetracker.view;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import javax.swing.*;
import com.studentgradetracker.util.AppColors;

/**
 * A JPanel that paints a subtle starfield over the app's dark background.
 * Star positions are generated once with a fixed seed so the field is
 * stable across repaints (no flicker while resizing/scrolling).
 */
public class StarryPanel extends JPanel {
    private static final int STAR_COUNT = 140;
    private float[] xs;
    private float[] ys;
    private float[] sizes;
    private float[] alphas;

    public StarryPanel(LayoutManager layout) {
        super(layout);
        setBackground(AppColors.BG);
        generateStars();
    }

    public StarryPanel() {
        this(new BorderLayout());
    }

    private void generateStars() {
        Random rnd = new Random(42);
        xs = new float[STAR_COUNT];
        ys = new float[STAR_COUNT];
        sizes = new float[STAR_COUNT];
        alphas = new float[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            xs[i] = rnd.nextFloat();
            ys[i] = rnd.nextFloat();
            sizes[i] = 1f + rnd.nextFloat() * 1.6f;
            alphas[i] = 0.25f + rnd.nextFloat() * 0.55f;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        for (int i = 0; i < STAR_COUNT; i++) {
            float alpha = alphas[i];
            g2.setColor(new Color(1f, 1f, 1f, alpha));
            float d = sizes[i];
            float x = xs[i] * w;
            float y = ys[i] * h;
            g2.fill(new Ellipse2D.Float(x, y, d, d));
        }
        g2.dispose();
    }
}
