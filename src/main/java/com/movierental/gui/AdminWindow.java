package com.movierental.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.movierental.bs.FilmBusinessService;
import com.movierental.bs.FilmBusinessServiceImpl;
import com.movierental.bs.RentalBusinessService;
import com.movierental.bs.RentalBusinessServiceImpl;
import com.movierental.bs.UserBusinessServiceImpl;
import com.movierental.bs.UserBusisessService;
import com.movierental.dao.FilmDAOImpl;
import com.movierental.dao.MySQLConnection;
import com.movierental.dao.RentalDAOImpl;
import com.movierental.dao.UserDAOImpl;
import com.movierental.pojo.Film;
import com.movierental.pojo.Rental;
import com.movierental.pojo.User;

@SuppressWarnings("serial")
public class AdminWindow extends JFrame {
	private JTable userTable;
	private JTable filmTable;
	private JTable rentalTable;
	private JScrollPane filmScrollPane;
	private JScrollPane userScrollPane;
	private JScrollPane rentalScrollPane;
	private final JPanel panel;
	private final JButton add;
	private final JButton delete;
	private final JButton rentals;
	private final JPanel header;
	private final JLabel adminLabel;
	private final JButton logout;
	private final JButton users;
	private final JButton films;
	private int selection;
	private DefaultTableModel userModel;
	private DefaultTableModel filmModel;
	private final JPanel addPanel;
	private final JTextField filmName;
	private final JTextField filmLength;
	private final JTextField filmGenre;
	private final JTextField filmYear;
	private final FilmBusinessService fbs;
	private final UserBusisessService ubs;
	private final RentalBusinessService rbs;
	private DefaultTableModel rentalModel;

	public AdminWindow() {

		this.fbs = new FilmBusinessServiceImpl(new FilmDAOImpl(MySQLConnection.getInstance()));
		this.ubs = new UserBusinessServiceImpl(new UserDAOImpl(MySQLConnection.getInstance()));
		this.rbs = new RentalBusinessServiceImpl(new RentalDAOImpl(MySQLConnection.getInstance()));
		this.panel = new JPanel();
		this.getContentPane().add(this.panel, BorderLayout.SOUTH);

		this.add = new JButton("Add");
		this.panel.add(this.add);

		this.delete = new JButton("Delete");
		this.panel.add(this.delete);

		this.users = new JButton("Users");
		this.panel.add(this.users);

		this.films = new JButton("Films");
		this.panel.add(this.films);

		this.rentals = new JButton("Rentals");
		this.panel.add(this.rentals);

		this.logout = new JButton("Logout");
		this.panel.add(this.logout);

		this.header = new JPanel();
		this.getContentPane().add(this.header, BorderLayout.NORTH);

		this.adminLabel = new JLabel("Admin");
		this.header.add(this.adminLabel);

		this.filmScrollPane = new JScrollPane(this.filmTable);
		this.getContentPane().add(this.filmScrollPane, BorderLayout.CENTER);
		this.userScrollPane = new JScrollPane(this.userTable);
		this.getContentPane().add(this.userScrollPane, BorderLayout.CENTER);
		this.rentalScrollPane = new JScrollPane(this.rentalTable);
		this.getContentPane().add(this.rentalScrollPane, BorderLayout.CENTER);

		this.users.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				AdminWindow.this.remove(AdminWindow.this.filmScrollPane);
				AdminWindow.this.remove(AdminWindow.this.rentalScrollPane);
				AdminWindow.this.userTable();
				AdminWindow.this.getContentPane().repaint();
				AdminWindow.this.revalidate();
				AdminWindow.this.selection = 0;
				AdminWindow.this.delete.setEnabled(true);
				AdminWindow.this.add.setEnabled(false);
			}
		});

		this.films.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				AdminWindow.this.remove(AdminWindow.this.userScrollPane);
				AdminWindow.this.remove(AdminWindow.this.rentalScrollPane);
				AdminWindow.this.filmTable();
				AdminWindow.this.getContentPane().repaint();
				AdminWindow.this.revalidate();
				AdminWindow.this.selection = 1;
				AdminWindow.this.delete.setEnabled(true);
				AdminWindow.this.add.setEnabled(true);

			}
		});
		this.rentals.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				AdminWindow.this.remove(AdminWindow.this.userScrollPane);
				AdminWindow.this.remove(AdminWindow.this.filmScrollPane);
				AdminWindow.this.rentalTable();
				AdminWindow.this.getContentPane().repaint();
				AdminWindow.this.revalidate();
				AdminWindow.this.delete.setEnabled(false);
				AdminWindow.this.add.setEnabled(false);
			}
		});

		this.addPanel = new JPanel();
		this.filmName = new JTextField(10);
		this.filmLength = new JTextField(5);
		this.filmGenre = new JTextField(10);
		this.filmYear = new JTextField(5);

		this.filmName.setText("Film name");
		this.filmLength.setText("Length");
		this.filmGenre.setText("Genre");
		this.filmYear.setText("Year");

		this.filmName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				AdminWindow.this.filmName.setText("");
			}
		});

		this.filmLength.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				AdminWindow.this.filmLength.setText("");
			}
		});

		this.filmGenre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				AdminWindow.this.filmGenre.setText("");
			}
		});

		this.filmYear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				AdminWindow.this.filmYear.setText("");
			}
		});

		this.addPanel.add(this.filmName);
		this.addPanel.add(this.filmLength);
		this.addPanel.add(this.filmGenre);
		this.addPanel.add(this.filmYear);

		this.add.addActionListener(new ActionListener() {

			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(final ActionEvent e) {

				final int result = JOptionPane.showConfirmDialog(AdminWindow.this, AdminWindow.this.addPanel,
						"Enter a new film", JOptionPane.OK_CANCEL_OPTION);

				if (result == JOptionPane.OK_OPTION) {

					try {
						final int id = AdminWindow.this.fbs.getLastId();
						AdminWindow.this.fbs.save(new Film(id + 1, AdminWindow.this.filmName.getText(),
								Integer.parseInt(AdminWindow.this.filmLength.getText()),
								AdminWindow.this.filmGenre.getText(),
								Integer.parseInt(AdminWindow.this.filmYear.getText())));
						AdminWindow.this.getContentPane().revalidate();
						AdminWindow.this.getContentPane().repaint();
					} catch (NumberFormatException | SQLException exception) {
						new JOptionPane().showMessageDialog(AdminWindow.this, "Wrong input", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});

		this.delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					if (AdminWindow.this.selection == 0) {
						final int selectedRow = AdminWindow.this.userTable.getSelectedRow();
						final int id = Integer
								.parseInt(AdminWindow.this.userModel.getValueAt(selectedRow, 0).toString());
						AdminWindow.this.ubs.delete(AdminWindow.this.ubs.findById(id));
						AdminWindow.this.ubs.renumber();
						if (AdminWindow.this.rbs.findByUserId(id) != null) {
							for (final Rental rental : AdminWindow.this.rbs.findByUserId(id)) {
								AdminWindow.this.rbs.delete(rental);
							}
							AdminWindow.this.rbs.renumber();
						}
						AdminWindow.this.userTable.remove(selectedRow);
						AdminWindow.this.getContentPane().revalidate();
						AdminWindow.this.getContentPane().repaint();
					} else if (AdminWindow.this.selection == 1) {
						final int selectedRow = AdminWindow.this.filmTable.getSelectedRow();
						final int id = Integer
								.parseInt(AdminWindow.this.filmModel.getValueAt(selectedRow, 0).toString());
						AdminWindow.this.fbs.delete(AdminWindow.this.fbs.findById(id));
						AdminWindow.this.fbs.renumber();
						if (AdminWindow.this.rbs.findByFilmId(id) != null) {
							for (final Rental rental : AdminWindow.this.rbs.findByFilmId(id)) {
								AdminWindow.this.rbs.delete(rental);
							}
							AdminWindow.this.rbs.renumber();
						}
						System.out.println(selectedRow);
						AdminWindow.this.filmTable.remove(selectedRow);
						AdminWindow.this.getContentPane().revalidate();
						AdminWindow.this.getContentPane().repaint();
					}
				} catch (final SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		this.logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				new LoginWindow().setupWindow();
				AdminWindow.this.dispose();

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

	@SuppressWarnings("static-access")
	public void filmTable() {

		this.filmTable = new JTable(new DefaultTableModel(new Object[] { "Id", "Name", "Length", "Genre", "Year" }, 0));
		this.filmModel = (DefaultTableModel) this.filmTable.getModel();

		this.filmTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		try {
			for (final Film film : this.fbs.selectAll()) {
				this.filmModel.addRow(new Object[] { film.getId(), film.getName(), film.getLength(), film.getGenre(),
						film.getYear() });
			}

		} catch (

		final SQLException e2)

		{
			new JOptionPane().showMessageDialog(AdminWindow.this, "Cannot add films", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		this.filmScrollPane = new JScrollPane(this.filmTable);
		this.getContentPane().add(this.filmScrollPane, BorderLayout.CENTER);

	}

	public void userTable() {

		this.userTable = new JTable(new DefaultTableModel(new Object[] { "Id", "Name", "email", "password" }, 0));
		this.userModel = (DefaultTableModel) this.userTable.getModel();
		this.userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		try {
			for (final User user : this.ubs.selectAll()) {
				this.userModel
						.addRow(new Object[] { user.getId(), user.getName(), user.getEmail(), user.getPassword() });
			}

		} catch (final SQLException e) {
			e.printStackTrace();
		}
		this.userScrollPane = new JScrollPane(this.userTable);
		this.getContentPane().add(this.userScrollPane, BorderLayout.CENTER);
	}

	public void rentalTable() {

		this.rentalTable = new JTable(
				new DefaultTableModel(new Object[] { "Id", "FilmId", "UserId", "StartDate", "EndDate" }, 0));
		this.rentalModel = (DefaultTableModel) this.rentalTable.getModel();
		this.rentalTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		try {
			for (final Rental rental : this.rbs.selectAll()) {
				this.rentalModel.addRow(new Object[] { rental.getId(), rental.getFilmId(), rental.getUserId(),
						rental.getStartDate(), rental.getEndDate() });
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		this.rentalScrollPane = new JScrollPane(this.rentalTable);
		this.getContentPane().add(this.rentalScrollPane, BorderLayout.CENTER);
	}

}
