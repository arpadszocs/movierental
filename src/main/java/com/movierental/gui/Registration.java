package com.movierental.gui;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.movierental.bs.UserBusinessServiceImpl;
import com.movierental.bs.UserBusisessService;
import com.movierental.dao.factory.DAOFactory;
import com.movierental.dao.factory.HibernateDAOFactory;
import com.movierental.pojo.User;

@SuppressWarnings("serial")
public class Registration extends JFrame {
	private final JTextField nameField;
	private final JTextField emailField;
	private final JPasswordField passwordField;
	private final JLabel name;
	private final JLabel email;
	private final JLabel password;
	private final JLabel confirmPassword;
	private final JButton register;
	private final JPasswordField confirmPasswordField;
	private final JButton cancel;
	private final UserBusisessService ubs;

	@SuppressWarnings("static-access")
	public Registration() {

		final DAOFactory daoFactory = new HibernateDAOFactory();

		this.ubs = new UserBusinessServiceImpl(daoFactory.getUserDAO());
		this.getContentPane().setLayout(null);

		this.name = new JLabel("Name");
		this.name.setBounds(180, 11, 46, 14);
		this.getContentPane().add(this.name);

		this.nameField = new JTextField();
		this.nameField.setBounds(140, 36, 133, 20);
		this.getContentPane().add(this.nameField);
		this.nameField.setColumns(10);

		this.email = new JLabel("E-mail");
		this.email.setBounds(180, 67, 46, 14);
		this.getContentPane().add(this.email);

		this.emailField = new JTextField();
		this.emailField.setBounds(140, 92, 133, 20);
		this.getContentPane().add(this.emailField);
		this.emailField.setColumns(10);

		this.password = new JLabel("Password");
		this.password.setBounds(180, 123, 46, 14);
		this.getContentPane().add(this.password);

		this.passwordField = new JPasswordField();
		this.passwordField.setBounds(147, 147, 111, 20);
		this.getContentPane().add(this.passwordField);

		this.register = new JButton("Register");
		this.register.setBounds(161, 227, 89, 23);
		this.getContentPane().add(this.register);

		this.confirmPasswordField = new JPasswordField();
		this.confirmPasswordField.setBounds(147, 196, 111, 20);
		this.getContentPane().add(this.confirmPasswordField);

		this.confirmPassword = new JLabel("Confirm Password");
		this.confirmPassword.setBounds(157, 171, 93, 14);
		this.getContentPane().add(this.confirmPassword);

		this.cancel = new JButton("Cancel");
		this.cancel.setBounds(161, 273, 89, 23);
		this.getContentPane().add(this.cancel);

		this.register.addActionListener(arg0 -> {
			final String password = new String(Registration.this.passwordField.getPassword());
			final String confirmPassword = new String(Registration.this.confirmPasswordField.getPassword());
			try {
				final int id = Registration.this.ubs.getLastId();
				if (Registration.this.nameField.getText().length() < 4) {
					new JOptionPane().showMessageDialog(Registration.this, "Name must have at least 4 characters");
				} else if (Registration.this.emailField.getText().length() < 5) {
					new JOptionPane().showMessageDialog(Registration.this, "Email must have at least 5 characters");
				} else if (password.length() < 5 || password.length() > 20) {
					new JOptionPane().showMessageDialog(Registration.this,
							"Password must be between 5 and 20 characters");
				} else if (!password.equals(confirmPassword)) {
					new JOptionPane().showMessageDialog(Registration.this, "Passwords must match");
				} else {
					final User user = new User(id + 1, Registration.this.nameField.getText(),
							Registration.this.emailField.getText(), password, "user");
					Registration.this.ubs.save(user);
					new JOptionPane().showMessageDialog(Registration.this, "Done");
					new LoginWindow().setupWindow();
					Registration.this.dispose();
				}
			} catch (final SQLException e2) {
				e2.printStackTrace();
			}
		});

		this.cancel.addActionListener(arg0 -> {
			new LoginWindow().setupWindow();
			Registration.this.dispose();

		});
	}

	public void setupWindow() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(450, 400);
		this.setLocation(400, 200);
		this.setVisible(true);
		this.setResizable(false);
	}
}
