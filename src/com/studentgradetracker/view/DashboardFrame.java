package com.studentgradetracker.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.studentgradetracker.controller.AppController;
import com.studentgradetracker.util.AppColors;
import com.studentgradetracker.util.Constants;
import com.studentgradetracker.util.IconFactory;
import com.studentgradetracker.util.IconFactory.IconType;
import com.studentgradetracker.util.UIHelper;

public class DashboardFrame extends JFrame {
    private static final int SIDEBAR_WIDTH = 203;
    private static final int SIDEBAR_COLLAPSED = 64;

    private final AppController controller;
    private final JPanel contentPanel = new JPanel(new BorderLayout());
    private JLabel pageTitle;

    private JPanel sidebar;
    private JLabel brandName;
    private JButton collapseButton;
    private boolean collapsed = false;
    private final List<NavButton> navButtons = new ArrayList<>();

    private NavButton dashboardBtn, addBtn, recordsBtn, searchBtn, reportBtn, logoutNav;

    public DashboardFrame(AppController controller) {
        this.controller = controller;
        setTitle(Constants.APP_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1360, 740);
        setMinimumSize(new Dimension(1050, 680));
        setLocationRelativeTo(null);
        buildUI();
        showDashboard();
    }

    // ------------------------------------------------------------------
    // Layout
    // ------------------------------------------------------------------
    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(AppColors.BG);
        root.add(createSidebar(), BorderLayout.WEST);

        // Starry background behind the page content
        StarryPanel main = new StarryPanel(new BorderLayout());
        main.add(createTopbar(), BorderLayout.NORTH);

        contentPanel.setOpaque(false);
        main.add(contentPanel, BorderLayout.CENTER);

        root.add(main, BorderLayout.CENTER);
        add(root);
    }

    private JPanel createSidebar() {
        sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(SIDEBAR_WIDTH, 0));
        sidebar.setBackground(AppColors.SIDEBAR);
        sidebar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, AppColors.BORDER),
                new EmptyBorder(18, 6, 14, 6)));

        // ----- brand -----
        JPanel brand = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        brand.setOpaque(false);
        brand.setBorder(new EmptyBorder(0, 6, 0, 0));
        JLabel cap = new JLabel(IconFactory.get(IconType.CAP, 22, Color.WHITE));
        brandName = UIHelper.title("Grade Tracker", 15);
        brand.add(cap);
        brand.add(brandName);
        sidebar.add(brand, BorderLayout.NORTH);

        // ----- navigation -----
        JPanel nav = new JPanel();
        nav.setOpaque(false);
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBorder(new EmptyBorder(28, 0, 0, 0));

        dashboardBtn = navButton(IconType.DASHBOARD, "Dashboard");
        addBtn = navButton(IconType.ADD, "Add Student");
        recordsBtn = navButton(IconType.RECORDS, "Student Records");
        searchBtn = navButton(IconType.SEARCH, "Search / Update / Delete");
        reportBtn = navButton(IconType.REPORT, "Performance Report");

        dashboardBtn.addActionListener(e -> showDashboard());
        addBtn.addActionListener(e -> showAddStudent());
        recordsBtn.addActionListener(e -> showRecords());
        searchBtn.addActionListener(e -> showSearch());
        reportBtn.addActionListener(e -> showReport());

        for (NavButton b : new NavButton[]{dashboardBtn, addBtn, recordsBtn, searchBtn, reportBtn}) {
            nav.add(b);
            nav.add(Box.createVerticalStrut(6));
        }
        sidebar.add(nav, BorderLayout.CENTER);

        // ----- bottom: logout + collapse -----
        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        logoutNav = navButton(IconType.LOGOUT, "Logout");
        logoutNav.addActionListener(e -> logout());
        bottom.add(logoutNav);
        bottom.add(Box.createVerticalStrut(16));

        collapseButton = UIHelper.outlineButton("", new Color(28, 29, 59), AppColors.BORDER, Color.WHITE);
        collapseButton.setIcon(IconFactory.get(IconType.CHEV_LEFT, 18, AppColors.MUTED));
        collapseButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        collapseButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        collapseButton.addActionListener(e -> toggleSidebar());
        bottom.add(collapseButton);

        sidebar.add(bottom, BorderLayout.SOUTH);
        return sidebar;
    }

    private NavButton navButton(IconType icon, String text) {
        NavButton b = new NavButton(icon, text);
        navButtons.add(b);
        return b;
    }

    private void toggleSidebar() {
        collapsed = !collapsed;
        sidebar.setPreferredSize(new Dimension(collapsed ? SIDEBAR_COLLAPSED : SIDEBAR_WIDTH, 0));
        brandName.setVisible(!collapsed);
        for (NavButton b : navButtons) b.setCollapsed(collapsed);
        collapseButton.setIcon(IconFactory.get(collapsed ? IconType.CHEV_RIGHT : IconType.CHEV_LEFT, 18, AppColors.MUTED));
        sidebar.revalidate();
        sidebar.repaint();
    }

    private JPanel createTopbar() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(AppColors.SIDEBAR);
        top.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER),
                new EmptyBorder(12, 24, 12, 24)));

        pageTitle = UIHelper.title("Dashboard", 17);
        pageTitle.setIconTextGap(12);
        top.add(pageTitle, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 18, 0));
        right.setOpaque(false);

        JLabel admin = new JLabel("Admin");
        admin.setForeground(new Color(207, 205, 232));
        admin.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        admin.setIcon(IconFactory.get(IconType.PERSON, 16, new Color(207, 205, 232)));
        admin.setIconTextGap(8);
        JLabel arrow = new JLabel(IconFactory.get(IconType.DROPDOWN, 16, new Color(207, 205, 232)));
        JPanel user = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        user.setOpaque(false);
        user.add(admin);
        user.add(arrow);
        user.setCursor(new Cursor(Cursor.HAND_CURSOR));
        user.setToolTipText("Account menu");
        JPopupMenu menu = createAdminMenu();
        user.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mousePressed(java.awt.event.MouseEvent e) {
                // show the menu just below the Admin label, right-aligned with it
                menu.show(user, user.getWidth() - menu.getPreferredSize().width, user.getHeight() + 8);
            }
        });
        right.add(user);

        JButton logout = UIHelper.outlineButton("Logout", AppColors.LOGOUT_BG,
                AppColors.LOGOUT_BORDER, AppColors.LOGOUT_TEXT);
        logout.setBorder(new EmptyBorder(6, 14, 6, 14));
        logout.addActionListener(e -> logout());
        right.add(logout);

        top.add(right, BorderLayout.EAST);
        return top;
    }

    /** Dropdown opened by clicking "Admin". */
    private JPopupMenu createAdminMenu() {
        JPopupMenu menu = new JPopupMenu();
        menu.setBackground(AppColors.CARD);
        menu.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppColors.BORDER),
                new EmptyBorder(4, 4, 4, 4)));

        JLabel who = UIHelper.muted("Signed in as admin");
        who.setBorder(new EmptyBorder(6, 12, 8, 24));
        menu.add(who);
        menu.add(separator());

        MenuItem profile = new MenuItem("My Profile", IconType.PERSON);
        profile.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Username: admin\nRole: Administrator",
                "My Profile", JOptionPane.INFORMATION_MESSAGE));
        MenuItem about = new MenuItem("About", IconType.DASHBOARD);
        about.addActionListener(e -> JOptionPane.showMessageDialog(this,
                Constants.APP_NAME + "\nVersion " + Constants.VERSION,
                "About", JOptionPane.INFORMATION_MESSAGE));
        MenuItem out = new MenuItem("Logout", IconType.LOGOUT);
        out.addActionListener(e -> logout());

        menu.add(profile);
        menu.add(about);
        menu.add(separator());
        menu.add(out);
        return menu;
    }

    private static JSeparator separator() {
        JSeparator sep = new JSeparator();
        sep.setForeground(AppColors.BORDER);
        sep.setBackground(AppColors.CARD);
        return sep;
    }

    /** Menu entry with icon and a purple hover highlight. */
    private static final class MenuItem extends JMenuItem {
        MenuItem(String text, IconType icon) {
            super(text, IconFactory.get(icon, 16, AppColors.MUTED));
            setOpaque(false);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.PLAIN, 13));
            setIconTextGap(12);
            setBorder(new EmptyBorder(9, 12, 9, 28));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getModel().isArmed() ? AppColors.NAV_ACTIVE_BG : AppColors.CARD);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ------------------------------------------------------------------
    // Navigation helpers
    // ------------------------------------------------------------------
    private void logout() {
        dispose();
        new LoginFrame(controller).setVisible(true);
    }

    private void setPage(String title, IconType icon, NavButton active, JComponent component) {
        pageTitle.setText(title);
        pageTitle.setIcon(IconFactory.get(icon, 22, Color.WHITE));
        for (NavButton b : navButtons) b.setActive(b == active);
        contentPanel.removeAll();
        contentPanel.add(component, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showDashboard() {
        setPage("Dashboard", IconType.DASHBOARD, dashboardBtn, new DashboardPanel(controller));
    }

    private void showAddStudent() {
        setPage("Add Student", IconType.ADD, addBtn, new AddStudentPanel(controller, this::showRecords));
    }

    private void showRecords() {
        setPage("Student Records", IconType.RECORDS, recordsBtn, new StudentRecordsPanel(controller));
    }

    private void showSearch() {
        setPage("Search / Update / Delete", IconType.SEARCH, searchBtn, new SearchPanel(controller));
    }

    private void showReport() {
        setPage("Performance Report", IconType.REPORT, reportBtn, new PerformanceReportPanel(controller));
    }

    // ------------------------------------------------------------------
    // Sidebar button: icon + text, purple highlight when active
    // ------------------------------------------------------------------
    private static final class NavButton extends JButton {
        private final IconType iconType;
        private final String label;
        private boolean active;

        NavButton(IconType iconType, String label) {
            super(label);
            this.iconType = iconType;
            this.label = label;
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);
            setFocusPainted(false);
            setRolloverEnabled(true);
            setHorizontalAlignment(SwingConstants.LEFT);
            setIconTextGap(14);
            setFont(new Font("Segoe UI", Font.PLAIN, 13));
            setBorder(new EmptyBorder(10, 14, 10, 8));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            applyColors();
        }

        void setActive(boolean a) {
            active = a;
            applyColors();
            repaint();
        }

        void setCollapsed(boolean collapsed) {
            setText(collapsed ? "" : label);
            setHorizontalAlignment(collapsed ? SwingConstants.CENTER : SwingConstants.LEFT);
            setToolTipText(collapsed ? label : null);
        }

        private void applyColors() {
            Color c = active ? AppColors.PURPLE_LIGHT : AppColors.MUTED;
            setForeground(c);
            setIcon(IconFactory.get(iconType, 18, c));
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (active || getModel().isRollover()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(active ? AppColors.NAV_ACTIVE_BG : AppColors.NAV_HOVER_BG);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose();
            }
            super.paintComponent(g);
        }
    }
}
