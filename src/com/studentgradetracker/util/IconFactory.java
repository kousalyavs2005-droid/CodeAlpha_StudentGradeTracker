package com.studentgradetracker.util;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.Icon;

/**
 * Draws the app's line icons with Java2D (no image files or libraries needed).
 * All icons are designed on a 24x24 grid and scaled to the requested size.
 */
public final class IconFactory {
    private IconFactory() {}

    public enum IconType {
        CAP, DASHBOARD, ADD, RECORDS, SEARCH, REPORT, LOGOUT,
        CHEV_LEFT, CHEV_RIGHT, PERSON, DROPDOWN,
        TROPHY, TREND_DOWN, CHECK, CLOSE, EDIT, TRASH
    }

    public static Icon get(IconType type, int size, Color color) {
        return new VectorIcon(type, size, color);
    }

    private static final class VectorIcon implements Icon {
        private final IconType type;
        private final int size;
        private final Color color;

        VectorIcon(IconType type, int size, Color color) {
            this.type = type;
            this.size = size;
            this.color = color;
        }

        @Override public int getIconWidth()  { return size; }
        @Override public int getIconHeight() { return size; }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2.translate(x, y);
            double s = size / 24.0;
            g2.scale(s, s);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            switch (type) {
                case ADD:
                    g2.draw(new Line2D.Double(12, 5, 12, 19));
                    g2.draw(new Line2D.Double(5, 12, 19, 12));
                    break;
                case DASHBOARD:
                    g2.draw(new RoundRectangle2D.Double(3, 3, 18, 18, 4, 4));
                    g2.draw(new Line2D.Double(8, 17, 8, 12));
                    g2.draw(new Line2D.Double(12, 17, 12, 7));
                    g2.draw(new Line2D.Double(16, 17, 16, 13));
                    break;
                case RECORDS:
                    g2.draw(new RoundRectangle2D.Double(5, 4.5, 14, 17, 3, 3));
                    g2.draw(new RoundRectangle2D.Double(9, 2.5, 6, 4, 2, 2));
                    g2.draw(new Line2D.Double(9, 11, 15, 11));
                    g2.draw(new Line2D.Double(9, 15, 15, 15));
                    g2.draw(new Line2D.Double(9, 18.5, 12.5, 18.5));
                    break;
                case SEARCH:
                    g2.draw(new Ellipse2D.Double(4, 4, 12, 12));
                    g2.draw(new Line2D.Double(14.5, 14.5, 20, 20));
                    break;
                case REPORT:
                    g2.draw(new RoundRectangle2D.Double(3, 3, 18, 18, 4, 4));
                    Path2D.Double p = new Path2D.Double();
                    p.moveTo(7, 15); p.lineTo(11, 11); p.lineTo(14, 13); p.lineTo(17, 8);
                    g2.draw(p);
                    break;
                case LOGOUT:
                    Path2D.Double door = new Path2D.Double();
                    door.moveTo(10, 4); door.lineTo(6, 4); door.lineTo(4, 6);
                    door.lineTo(4, 18); door.lineTo(6, 20); door.lineTo(10, 20);
                    g2.draw(door);
                    g2.draw(new Line2D.Double(9, 12, 20, 12));
                    Path2D.Double head = new Path2D.Double();
                    head.moveTo(16, 8); head.lineTo(20, 12); head.lineTo(16, 16);
                    g2.draw(head);
                    break;
                case CAP:
                    Path2D.Double top = new Path2D.Double();
                    top.moveTo(12, 4); top.lineTo(22, 9); top.lineTo(12, 14); top.lineTo(2, 9); top.closePath();
                    g2.draw(top);
                    Path2D.Double base = new Path2D.Double();
                    base.moveTo(6, 11.5); base.lineTo(6, 16); base.lineTo(12, 19); base.lineTo(18, 16); base.lineTo(18, 11.5);
                    g2.draw(base);
                    g2.draw(new Line2D.Double(22, 9, 22, 15));
                    break;
                case CHEV_LEFT:
                    Path2D.Double l = new Path2D.Double();
                    l.moveTo(15, 5); l.lineTo(8, 12); l.lineTo(15, 19);
                    g2.draw(l);
                    break;
                case CHEV_RIGHT:
                    Path2D.Double r = new Path2D.Double();
                    r.moveTo(9, 5); r.lineTo(16, 12); r.lineTo(9, 19);
                    g2.draw(r);
                    break;
                case PERSON:
                    g2.draw(new Ellipse2D.Double(8, 3.5, 8, 8));
                    g2.draw(new Arc2D.Double(4, 14, 16, 14, 0, 180, Arc2D.OPEN));
                    break;
                case TROPHY:
                    g2.draw(new Line2D.Double(8, 4, 16, 4));
                    g2.draw(new Line2D.Double(8, 4, 8, 9));
                    g2.draw(new Line2D.Double(16, 4, 16, 9));
                    g2.draw(new Arc2D.Double(8, 5, 8, 8, 180, 180, Arc2D.OPEN));
                    Path2D.Double hl = new Path2D.Double();
                    hl.moveTo(8, 6); hl.lineTo(4, 6); hl.lineTo(4, 8); hl.lineTo(8, 11);
                    g2.draw(hl);
                    Path2D.Double hr = new Path2D.Double();
                    hr.moveTo(16, 6); hr.lineTo(20, 6); hr.lineTo(20, 8); hr.lineTo(16, 11);
                    g2.draw(hr);
                    g2.draw(new Line2D.Double(12, 13, 12, 17));
                    g2.draw(new Line2D.Double(8, 20, 16, 20));
                    break;
                case TREND_DOWN:
                    Path2D.Double td = new Path2D.Double();
                    td.moveTo(3, 7); td.lineTo(9, 13); td.lineTo(13, 9); td.lineTo(21, 17);
                    g2.draw(td);
                    Path2D.Double tda = new Path2D.Double();
                    tda.moveTo(15, 17); tda.lineTo(21, 17); tda.lineTo(21, 11);
                    g2.draw(tda);
                    break;
                case CHECK:
                    Path2D.Double ck = new Path2D.Double();
                    ck.moveTo(5, 12.5); ck.lineTo(10, 17.5); ck.lineTo(19, 7);
                    g2.draw(ck);
                    break;
                case CLOSE:
                    g2.draw(new Line2D.Double(6, 6, 18, 18));
                    g2.draw(new Line2D.Double(18, 6, 6, 18));
                    break;
                case EDIT:
                    Path2D.Double pen = new Path2D.Double();
                    pen.moveTo(4, 20); pen.lineTo(5, 15); pen.lineTo(16, 4);
                    pen.lineTo(20, 8); pen.lineTo(9, 19); pen.closePath();
                    g2.draw(pen);
                    g2.draw(new Line2D.Double(13.5, 6.5, 17.5, 10.5));
                    break;
                case TRASH:
                    g2.draw(new Line2D.Double(4, 7, 20, 7));
                    Path2D.Double lid = new Path2D.Double();
                    lid.moveTo(9, 7); lid.lineTo(9, 4); lid.lineTo(15, 4); lid.lineTo(15, 7);
                    g2.draw(lid);
                    Path2D.Double body = new Path2D.Double();
                    body.moveTo(6, 7); body.lineTo(7, 20); body.lineTo(17, 20); body.lineTo(18, 7);
                    g2.draw(body);
                    g2.draw(new Line2D.Double(10, 11, 10, 16));
                    g2.draw(new Line2D.Double(14, 11, 14, 16));
                    break;
                case DROPDOWN:
                    Path2D.Double tri = new Path2D.Double();
                    tri.moveTo(7, 10); tri.lineTo(17, 10); tri.lineTo(12, 15.5); tri.closePath();
                    g2.fill(tri);
                    break;
            }
            g2.dispose();
        }
    }
}
