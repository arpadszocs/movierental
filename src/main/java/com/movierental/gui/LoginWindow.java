package com.movierental.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.movierental.bs.UserBusinessServiceImpl;
import com.movierental.bs.UserBusisessService;
import com.movierental.dao.MySQLConnection;
import com.movierental.dao.UserDAOImpl;
import com.movierental.pojo.User;

@SuppressWarnings("serial")
public class LoginWindow extends JFrame {
	private final JTextField emailField;
	private final JPasswordField passwordField;
	private final JLabel email;
	private final JLabel passwordLabel;
	private final JButton login;
	private final JButton register;
	private final JButton exit;
	private final UserBusisessService ubs;

	public LoginWindow() {
		this.ubs = new UserBusinessServiceImpl(new UserDAOImpl(MySQLConnection.getInstance()));

		this.getContentPane().setLayout(null);

		this.emailField = new JTextField();
		this.emailField.setBounds(128, 65, 146, 20);
		this.getContentPane().add(this.emailField);
		this.emailField.setColumns(10);

		this.email = new JLabel("E-mail");
		this.email.setBounds(177, 38, 64, 20);
		this.getContentPane().add(this.email);

		this.passwordLabel = new JLabel("Password");
		this.passwordLabel.setBounds(171, 104, 103, 14);
		this.getContentPane().add(this.passwordLabel);

		this.login = new JButton("Login");
		this.login.setBounds(165, 173, 89, 23);
		this.getContentPane().add(this.login);

		this.register = new JButton("Register");
		this.register.setBounds(335, 11, 89, 23);
		this.getContentPane().add(this.register);

		this.passwordField = new JPasswordField();
		this.passwordField.setBounds(153, 129, 101, 20);
		this.getContentPane().add(this.passwordField);

		this.exit = new JButton("Exit");
		this.exit.setBounds(335, 330, 89, 23);
		this.getContentPane().add(this.exit);

		this.getRootPane().setDefaultButton(this.login);

		this.login.addActionListener(new ActionListener() {

			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(final ActionEvent arg0) {

				try {
					final User user = LoginWindow.this.ubs.findByEmail(LoginWindow.this.emailField.getText());
					final String password = new String(LoginWindow.this.passwordField.getPassword());
					if (LoginWindow.this.emailField.getText().equals("")
							|| LoginWindow.this.ubs.findByEmail(LoginWindow.this.emailField.getText()) == null) {
						new JOptionPane().showMessageDialog(LoginWindow.this, "Wrong Username or Password", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else

					if (user.getPassword().equals(password)) {
						if (user.getRole().equals("user")) {
							new UserWindow(user).setupWindow();
							LoginWindow.this.dispose();
						} else {
							new AdminWindow().setupWindow();
							LoginWindow.this.dispose();
						}
					} else {
						new JOptionPane().showMessageDialog(LoginWindow.this, "Wrong Password", "Password error",
								JOptionPane.ERROR_MESSAGE);
					}

				} catch (final SQLException e) {
					e.printStackTrace();
				}

			}
		});

		this.login.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(final KeyEvent e) {

			}

			@Override
			public void keyReleased(final KeyEvent e) {

			}

			@SuppressWarnings("static-access")
			@Override
			public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						final User user = LoginWindow.this.ubs.findByEmail(LoginWindow.this.emailField.getText());
						final String password = new String(LoginWindow.this.passwordField.getPassword());
						if (LoginWindow.this.emailField.getText().equals("")
								|| LoginWindow.this.ubs.findByEmail(LoginWindow.this.emailField.getText()) == null) {
							new JOptionPane().showMessageDialog(LoginWindow.this, "Wrong Username or Password", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else

						if (user.getPassword().equals(password)) {
							if (user.getRole().equals("user")) {
								new UserWindow(user).setupWindow();
								LoginWindow.this.dispose();
							} else {
								new AdminWindow().setupWindow();
								LoginWindow.this.dispose();
							}
						} else {
							new JOptionPane().showMessageDialog(LoginWindow.this, "Wrong Username or Password",
									"Password error", JOptionPane.ERROR_MESSAGE);
						}

					} catch (final SQLException e1) {
						e1.printStackTrace();
					}

				}

			}
		});

		this.register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				new Registration().setupWindow();
				LoginWindow.this.dispose();

			}
		});

		this.exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				System.exit(0);

			}
		});

	}

	public void setupWindow() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(450, 400);
		this.setLocation(400, 200);
		this.setVisible(true);
		this.setResizable(false);
	}

	public static void main(final String[] args) {
		new LoginWindow().setupWindow();
	}

}
