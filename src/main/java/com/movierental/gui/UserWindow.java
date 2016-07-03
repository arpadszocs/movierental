package com.movierental.gui;

import java.awt.BorderLayout;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.movierental.bs.FilmBusinessService;
import com.movierental.bs.FilmBusinessServiceImpl;
import com.movierental.bs.RentalBusinessService;
import com.movierental.bs.RentalBusinessServiceImpl;
import com.movierental.bs.UserBusinessServiceImpl;
import com.movierental.bs.UserBusisessService;
import com.movierental.dao.FilmDAOJDBCImpl;
import com.movierental.dao.MySQLConnection;
import com.movierental.dao.RentalDAOJDBCImpl;
import com.movierental.dao.UserDAOJDBCImpl;
import com.movierental.pojo.Film;
import com.movierental.pojo.Rental;
import com.movierental.pojo.User;
import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class UserWindow extends JFrame {
	private JTable filmTable;

	private JTable rentalTable;

	private final JPanel buttonPanel;

	private final JPanel labelPanel;

	private JScrollPane filmScrollPane;

	private JScrollPane rentalScrollPane;

	private final JButton rent;

	private final JButton myRentals;

	private final JButton changePass;

	private final JButton logout;

	private final JLabel label;

	private final JLabel oldPassLabel;

	private final JLabel newPassLabel;

	private final JLabel confirmPassLabel;

	private final JLabel rentDateS;

	private final JLabel rentDateE;

	private final JPasswordField oldPass;

	private final JPasswordField newPass;

	private final JPasswordField confirmPass;

	private final JPanel passChg;

	private final JPanel datePanel;

	private final JDateChooser dateChoserS;

	private final JDateChooser dateChoserE;

	private DefaultTableModel model;

	private final FilmBusinessService fbs;

	private final RentalBusinessService rbs;

	private final UserBusisessService ubs;

	@SuppressWarnings({ "deprecation", "static-access" })
	public UserWindow(final User user) {

		this.fbs = new FilmBusinessServiceImpl(new FilmDAOJDBCImpl(MySQLConnection.getInstance()));
		this.rbs = new RentalBusinessServiceImpl(new RentalDAOJDBCImpl(MySQLConnection.getInstance()));
		this.ubs = new UserBusinessServiceImpl(new UserDAOJDBCImpl(MySQLConnection.getInstance()));

		final java.util.Date today = new java.util.Date();

		this.buttonPanel = new JPanel();
		this.getContentPane().add(this.buttonPanel, BorderLayout.SOUTH);

		this.rent = new JButton("Rent");
		this.buttonPanel.add(this.rent);

		this.myRentals = new JButton("My rentals");
		this.buttonPanel.add(this.myRentals);

		this.changePass = new JButton("Change Pass");
		this.buttonPanel.add(this.changePass);

		this.logout = new JButton("Logout");
		this.buttonPanel.add(this.logout);

		this.labelPanel = new JPanel();
		this.getContentPane().add(this.labelPanel, BorderLayout.NORTH);

		this.label = new JLabel(user.getName());
		this.labelPanel.add(this.label);

		this.datePanel = new JPanel();
		this.rentDateS = new JLabel("Start Date");
		this.rentDateE = new JLabel("End Date");

		this.datePanel.setLayout(new BoxLayout(this.datePanel, BoxLayout.Y_AXIS));

		this.dateChoserS = new JDateChooser();
		this.dateChoserS.setLocale(this.getLocale());
		this.dateChoserS.getJCalendar().setMinSelectableDate(today);
		this.dateChoserE = new JDateChooser();
		this.dateChoserE.setLocale(this.getLocale());
		this.dateChoserE.getJCalendar().setMinSelectableDate(today);
		this.datePanel.add(this.rentDateS);
		this.datePanel.add(this.dateChoserS);
		this.datePanel.add(this.rentDateE);
		this.datePanel.add(this.dateChoserE);

		this.filmTable();

		this.oldPassLabel = new JLabel("Enter your Password");
		this.newPassLabel = new JLabel("Enter your new Password");
		this.confirmPassLabel = new JLabel("Confirm your Password");

		this.oldPass = new JPasswordField(10);
		this.newPass = new JPasswordField(10);
		this.confirmPass = new JPasswordField(10);
		this.passChg = new JPanel();
		this.passChg.add(this.oldPassLabel);
		this.passChg.add(this.oldPass);
		this.passChg.add(this.newPassLabel);
		this.passChg.add(this.newPass);
		this.passChg.add(this.confirmPassLabel);
		this.passChg.add(this.confirmPass);
		this.passChg.setLayout(new BoxLayout(this.passChg, BoxLayout.Y_AXIS));

		this.logout.addActionListener(arg0 -> {
			new LoginWindow().setupWindow();
			UserWindow.this.dispose();

		});

		this.rent.addActionListener(e -> {
			final int result = JOptionPane.showConfirmDialog(UserWindow.this, UserWindow.this.datePanel, "Rent",
					JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				try {
					final int id = UserWindow.this.rbs.getLastId();
					final Date startDate = new Date(this.dateChoserS.getDate().getTime());
					final Date endDate = new Date(this.dateChoserE.getDate().getTime());
					UserWindow.this.rbs.save(new Rental(id + 1,
							Integer.parseInt(UserWindow.this.model
									.getValueAt(UserWindow.this.filmTable.getSelectedRow(), 0).toString()),
							user.getId(), startDate, endDate));

					new JOptionPane().showMessageDialog(UserWindow.this, "Done");

				} catch (final SQLException e2) {
					e2.printStackTrace();
				}
			}

		});

		this.myRentals.addActionListener(e -> UserWindow.this.rentalTable());

		this.changePass.addActionListener(e -> {
			final int result = JOptionPane.showConfirmDialog(UserWindow.this, UserWindow.this.passChg,
					"Change your Password", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				if (this.oldPass.getText().equals(user.getPassword())) {
					if (this.newPass.getText().equals(this.confirmPass.getText())) {
						try {
							user.setPassword(this.newPass.getText());
							this.ubs.update(user);
							new JOptionPane().showMessageDialog(UserWindow.this, "Done");
						} catch (final Exception e1) {
							e1.printStackTrace();
						}
					} else {
						new JOptionPane().showMessageDialog(UserWindow.this, "Passwords must match");
					}

				} else {
					new JOptionPane().showMessageDialog(UserWindow.this, "Wreong Password");
				}
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

	public void filmTable() {
		this.filmTable = new JTable(new DefaultTableModel(new Object[] { "Id", "Name", "Length", "Genre", "Year" }, 0));
		this.model = (DefaultTableModel) this.filmTable.getModel();

		this.filmTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		try {
			for (final Film film : this.fbs.selectAll()) {
				this.model.addRow(new Object[] { film.getId(), film.getName(), film.getLength(), film.getGenre(),
						film.getYear() });
			}
		} catch (final SQLException e2) {
			e2.printStackTrace();
		}

		this.filmScrollPane = new JScrollPane(this.filmTable);
		this.getContentPane().add(this.filmScrollPane, BorderLayout.CENTER);

	}

	public void rentalTable() {

		this.rentalTable = new JTable(
				new DefaultTableModel(new Object[] { "Id", "FilmId", "UserId", "StartDate", "EndDate" }, 0));
		this.model = (DefaultTableModel) this.rentalTable.getModel();
		this.rentalTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		try {
			for (final Rental rental : this.rbs.selectAll()) {
				this.model.addRow(new Object[] { rental.getId(), rental.getFilmId(), rental.getUserId(),
						rental.getStartDate(), rental.getEndDate() });
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		this.rentalScrollPane = new JScrollPane(this.rentalTable);
		this.getContentPane().add(this.rentalScrollPane, BorderLayout.WEST);
	}

}
