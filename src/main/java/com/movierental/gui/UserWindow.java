package com.movierental.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.movierental.bs.FilmBusinessService;
import com.movierental.bs.FilmBusinessServiceImpl;
import com.movierental.bs.RentalBusinessService;
import com.movierental.bs.RentalBusinessServiceImpl;
import com.movierental.dao.FilmDAOImpl;
import com.movierental.dao.MySQLConnection;
import com.movierental.dao.RentalDAOImpl;
import com.movierental.pojo.Film;
import com.movierental.pojo.Rental;
import com.movierental.pojo.User;

public class UserWindow extends JFrame {
	private JTable filmTable;

	private JTable rentalTable;

	private final JPanel buttonPanel;

	private final JPanel labelPanel;

	private JScrollPane filmScrollPane;

	private JScrollPane rentalScrollPane;

	private final JButton rent;

	private final JButton myRentals;

	private final JButton logout;

	private final JLabel label;

	private DefaultTableModel model;

	private final FilmBusinessService fbs;

	private final RentalBusinessService rbs;

	public UserWindow(final User user) {

		this.fbs = new FilmBusinessServiceImpl(new FilmDAOImpl(MySQLConnection.getInstance()));
		this.rbs = new RentalBusinessServiceImpl(new RentalDAOImpl(MySQLConnection.getInstance()));

		this.buttonPanel = new JPanel();
		this.getContentPane().add(this.buttonPanel, BorderLayout.SOUTH);

		this.rent = new JButton("Rent");
		this.buttonPanel.add(this.rent);

		this.myRentals = new JButton("My rentals");
		this.buttonPanel.add(this.myRentals);

		this.logout = new JButton("Logout");
		this.buttonPanel.add(this.logout);

		this.labelPanel = new JPanel();
		this.getContentPane().add(this.labelPanel, BorderLayout.NORTH);

		this.label = new JLabel(user.getName());
		this.labelPanel.add(this.label);

		this.filmTable();

		this.logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				new LoginWindow().setupWindow();
				UserWindow.this.dispose();

			}
		});

		this.rent.addActionListener(new ActionListener() {

			@SuppressWarnings({ "deprecation", "static-access" })
			@Override
			public void actionPerformed(final ActionEvent e) {
				final Calendar calendar = Calendar.getInstance();
				try {
					final int id = UserWindow.this.rbs.getLastId();
					UserWindow.this.rbs.save(new Rental(id + 1,
							Integer.parseInt(UserWindow.this.model
									.getValueAt(UserWindow.this.filmTable.getSelectedRow(), 0).toString()),
							user.getId(),
							new Date(calendar.getTime().getYear(), calendar.getTime().getMonth(),
									calendar.getTime().getDay()),
							new Date(calendar.getTime().getYear(), calendar.getTime().getMonth(),
									calendar.getTime().getDay() + 14)));

					new JOptionPane().showMessageDialog(UserWindow.this, "Done");

				} catch (final SQLException e2) {
					e2.printStackTrace();
				}

			}
		});

		this.myRentals.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				UserWindow.this.rentalTable();
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
			// TODO Auto-generated catch block
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
