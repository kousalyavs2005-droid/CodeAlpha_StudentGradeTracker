package com.studentgradetracker.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.studentgradetracker.controller.AppController;
import com.studentgradetracker.util.AppColors;
import com.studentgradetracker.util.IconFactory;
import com.studentgradetracker.util.UIHelper;

public class LoginFrame extends JFrame {
    private final AppController controller;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame(AppController controller) {
        this.controller = controller;
        setTitle("Student Grade Tracker — Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 650);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setResizable(true); // enables the maximize button and window resizing
        buildUI();
    }

    private void buildUI() {
        JPanel root = new JPanel(new GridLayout(1, 2));
        root.setBackground(AppColors.BG);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(AppColors.PURPLE);
        left.setBorder(new EmptyBorder(80, 55, 60, 55));

        JLabel icon = new JLabel(IconFactory.get(IconFactory.IconType.CAP, 52, Color.WHITE));
        icon.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel brand = UIHelper.title("Student Grade", 30);
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel brand2 = UIHelper.title("Tracker", 30);
        brand2.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel desc = new JLabel("<html>Track performance, manage grades,<br>and celebrate student success.</html>");
        desc.setForeground(new Color(235, 230, 255));
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);

        left.add(icon);
        left.add(Box.createVerticalStrut(18));
        left.add(brand);
        left.add(brand2);
        left.add(Box.createVerticalStrut(15));
        left.add(desc);
        left.add(Box.createVerticalGlue());

        JLabel features = new JLabel("<html>✓ Real-time grade analytics<br><br>✓ Student performance tracking<br><br>✓ Search, update and delete<br><br>✓ Class performance report</html>");
        features.setForeground(Color.WHITE);
        features.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        features.setAlignmentX(Component.LEFT_ALIGNMENT);
        left.add(features);

        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(AppColors.BG);

        JPanel form = UIHelper.cardLayoutPanel();
        form.setPreferredSize(new Dimension(430, 390));

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel welcome = UIHelper.title("Welcome back", 26);
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel sub = UIHelper.muted("Sign in to manage student performance");
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(welcome);
        content.add(Box.createVerticalStrut(5));
        content.add(sub);
        content.add(Box.createVerticalStrut(28));

        JLabel userLabel = UIHelper.fieldLabel("EMAIL / USERNAME");
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(userLabel);
        usernameField = UIHelper.input("Enter username");
        usernameField.setText("admin");
        sizeField(usernameField);
        content.add(Box.createVerticalStrut(7));
        content.add(usernameField);
        content.add(Box.createVerticalStrut(18));

        JLabel passLabel = UIHelper.fieldLabel("PASSWORD");
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(passLabel);
        passwordField = UIHelper.passwordInput();
        passwordField.setText("admin123");
        sizeField(passwordField);
        content.add(Box.createVerticalStrut(7));
        content.add(passwordField);
        content.add(Box.createVerticalStrut(25));

        JButton login = UIHelper.button("LOGIN  →");
        login.setAlignmentX(Component.LEFT_ALIGNMENT);
        login.addActionListener(e -> doLogin());
        content.add(login);

        JLabel hint = UIHelper.muted("Demo login: admin / admin123");
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(Box.createVerticalStrut(18));
        content.add(hint);

        form.add(content, BorderLayout.CENTER);
        right.add(form);

        root.add(left);
        root.add(right);
        add(root);

        getRootPane().setDefaultButton(login);
    }

    /** Keeps a field one line tall, full width and left-aligned inside the BoxLayout. */
    private static void sizeField(JTextField f) {
        f.setAlignmentX(Component.LEFT_ALIGNMENT);
        f.setPreferredSize(new Dimension(300, 42));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
    }

    private void doLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (controller.login(username, password)) {
            dispose();
            controller.showDashboard();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.\nUse admin / admin123 for the demo.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
