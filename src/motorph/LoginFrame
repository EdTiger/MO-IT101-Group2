
// ============================================
// LoginFrame.java
// ============================================

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame implements ActionListener {

    JLabel titleLabel;
    JLabel usernameLabel;
    JLabel passwordLabel;

    JTextField usernameField;
    JPasswordField passwordField;

    JButton loginButton;
    JButton exitButton;

    JCheckBox showPasswordCheckBox;

    public LoginFrame() {

        setTitle("MotorPH Login");

        setSize(500,400);

        setLayout(null);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(
                new Color(245,245,245));

        // TITLE
        titleLabel =
                new JLabel("MotorPH Payroll System");

        titleLabel.setFont(
                new Font("Arial",
                        Font.BOLD,
                        24));

        titleLabel.setForeground(Color.RED);

        titleLabel.setBounds(
                90,
                40,
                350,
                40);

        add(titleLabel);

        // USERNAME
        usernameLabel =
                new JLabel("Username:");

        usernameLabel.setBounds(
                70,
                120,
                100,
                30);

        add(usernameLabel);

        usernameField =
                new JTextField();

        usernameField.setBounds(
                180,
                120,
                180,
                30);

        add(usernameField);

        // PASSWORD
        passwordLabel =
                new JLabel("Password:");

        passwordLabel.setBounds(
                70,
                170,
                100,
                30);

        add(passwordLabel);

        passwordField =
                new JPasswordField();

        passwordField.setBounds(
                180,
                170,
                180,
                30);

        add(passwordField);

        // SHOW PASSWORD
        showPasswordCheckBox =
                new JCheckBox("Show Password");

        showPasswordCheckBox.setBounds(
                180,
                210,
                150,
                30);

        showPasswordCheckBox.setBackground(
                new Color(245,245,245));

        add(showPasswordCheckBox);

        showPasswordCheckBox.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e) {

                        if (showPasswordCheckBox.isSelected()) {

                            passwordField.setEchoChar((char)0);
                        }

                        else {

                            passwordField.setEchoChar('*');
                        }
                    }
                });

        // LOGIN BUTTON
        loginButton =
                new JButton("Login");

        loginButton.setBounds(
                120,
                270,
                100,
                40);

        loginButton.setBackground(
                new Color(65,105,225));

        loginButton.setForeground(Color.WHITE);

        loginButton.addActionListener(this);

        add(loginButton);

        // EXIT BUTTON
        exitButton =
                new JButton("Exit");

        exitButton.setBounds(
                250,
                270,
                100,
                40);

        exitButton.setBackground(
                new Color(220,20,60));

        exitButton.setForeground(Color.WHITE);

        exitButton.addActionListener(this);

        add(exitButton);

        setVisible(true);
    }

    
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loginButton) {

            String username =
                    usernameField.getText();

            String password =
                    String.valueOf(
                            passwordField.getPassword());

            // EMPLOYEE
            if (username.equals("employee")
                    && password.equals("12345")) {

                new EmployeeFrame();

                dispose();
            }

            // PAYROLL STAFF
            else if (username.equals("payroll_staff")
                    && password.equals("12345")) {

                new PayrollStaffFrame();

                dispose();
            }

            else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Username or Password");
            }
        }

        // EXIT
        if (e.getSource() == exitButton) {

            System.exit(0);
        }
    }
}
