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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.movierental.bs.FilmBusinessService;
import com.movierental.bs.FilmBusinessServiceImpl;
import com.movierental.bs.RentalBusinessService;
import com.movierental.bs.RentalBusinessServiceImpl;
import com.movierental.bs.UserBusinessServiceImpl;
import com.movierental.bs.UserBusisessService;
import com.movierental.dao.factory.DAOFactory;
import com.movierental.dao.factory.JDBCDAOFactory;
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

	private final JButton filmButton;

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

	private DefaultTableModel filmModel;

	private int selection; // 1 - film | 2 - rental |

	private final FilmBusinessService fbs;

	private final RentalBusinessService rbs;

	private final UserBusisessService ubs;

	private final DAOFactory daoFactory;

	private DefaultTableModel rentalModel;

	private final User user;

	@SuppressWarnings({ "deprecation", "static-access" })
	public UserWindow(final User user) {

		this.user = user;
		this.daoFactory = new JDBCDAOFactory();

		this.fbs = new FilmBusinessServiceImpl(this.daoFactory.getFilmDAO());
		this.rbs = new RentalBusinessServiceImpl(this.daoFactory.getRentalDAO());
		this.ubs = new UserBusinessServiceImpl(this.daoFactory.getUserDAO());

		final java.util.Date today = new java.util.Date();

		this.buttonPanel = new JPanel();
		this.getContentPane().add(this.buttonPanel, BorderLayout.SOUTH);

		this.rent = new JButton("Rent");
		this.buttonPanel.add(this.rent);

		this.filmButton = new JButton("Films");
		this.buttonPanel.add(this.filmButton);

		this.myRentals = new JButton("My rentals");
		this.buttonPanel.add(this.myRentals);

		this.changePass = new JButton("Change Pass");
		this.buttonPanel.add(this.changePass);

		this.logout = new JButton("Logout");
		this.buttonPanel.add(this.logout);

		this.labelPanel = new JPanel();
		this.getContentPane().add(this.labelPanel, BorderLayout.NORTH);

		this.label = new JLabel(this.user.getName());
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

		this.filmScrollPane = new JScrollPane(this.filmTable());
		this.getContentPane().add(this.filmScrollPane, BorderLayout.CENTER);
		this.selection = 1;

		this.rent.addActionListener(e -> {
			if (this.filmTable.getSelectedRow() == -1) {
				new JOptionPane().showMessageDialog(UserWindow.this, "Please select a film", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				final String filmName = this.filmModel.getValueAt(this.filmTable.getSelectedRow(), 1).toString();
				final int result = JOptionPane.showConfirmDialog(UserWindow.this, UserWindow.this.datePanel, filmName,
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					try {
						// final int id = UserWindow.this.rbs.getLastId();
						final Date startDate = new Date(this.dateChoserS.getDate().getTime());
						final Date endDate = new Date(this.dateChoserE.getDate().getTime());
						if (startDate.compareTo(endDate) <= 0) {
							final int filmId = Integer.parseInt(UserWindow.this.filmModel
									.getValueAt(UserWindow.this.filmTable.getSelectedRow(), 0).toString());
							UserWindow.this.rbs.save(new Rental(filmId, this.user.getId(), startDate, endDate));

							new JOptionPane().showMessageDialog(UserWindow.this, "Done");
							this.dateChoserS.setCalendar(null);
							this.dateChoserE.setCalendar(null);

						} else {
							new JOptionPane().showMessageDialog(UserWindow.this,
									"The end of you rental is befor the start of your rental", "Date selection error",
									JOptionPane.ERROR_MESSAGE);
						}

					} catch (final SQLException e2) {
						e2.printStackTrace();
					}
					UserWindow.this.getContentPane().revalidate();
					UserWindow.this.getContentPane().repaint();
				}
			}
		});

		this.myRentals.addActionListener(e -> {
			UserWindow.this.setupRentalTable();
			this.rent.setEnabled(false);
		});
		this.filmButton.addActionListener(e -> UserWindow.this.setupFilmTable());

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
							this.oldPass.setText(null);
							this.newPass.setText(null);
							this.confirmPass.setText(null);
						} catch (final Exception e1) {
							e1.printStackTrace();
						}
					} else {
						new JOptionPane().showMessageDialog(UserWindow.this, "Passwords must match");
					}

				} else {
					new JOptionPane().showMessageDialog(UserWindow.this, "Wrong Password");
				}
			}
		});

		this.logout.addActionListener(arg0 -> {
			new LoginWindow().setupWindow();
			UserWindow.this.dispose();

		});
	}

	public void setupWindow() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(550, 500);
		this.setLocation(300, 150);
		this.setVisible(true);
		this.setResizable(false);
	}

	public JTable filmTable() {
		int nr = 1;
		final String[] colName = { "Id", "Name", "Length", "Genre", "Year" };
		this.filmModel = new DefaultTableModel() {
			@Override
			public Class<?> getColumnClass(final int column) {
				if (column == 0) {
					return Integer.class;
				}
				return super.getColumnClass(column);
			}
		};
		this.filmTable = new JTable(this.filmModel) {
			@Override
			public boolean isCellEditable(final int nRow, final int nCol) {
				return false;
			}
		};
		this.filmModel.setColumnIdentifiers(colName);
		this.filmTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		try {
			for (final Film film : this.fbs.selectAll()) {
				final Object[] data = new Object[] { nr++, film.getName(), film.getLength(), film.getGenre(),
						film.getYear() };
				this.filmModel.addRow(data);
			}

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			nr = 1;
		}

		this.filmModel.fireTableDataChanged();
		if (this.selection != 1) {
			UserWindow.this.getContentPane().repaint();
			UserWindow.this.revalidate();
		}
		final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.filmTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		this.filmTable.setAutoCreateRowSorter(true);
		this.filmTable.getTableHeader().setReorderingAllowed(false);
		return this.filmTable;

	}

	public void setupFilmTable() {
		if (this.rentalScrollPane != null) {
			UserWindow.this.remove(UserWindow.this.rentalScrollPane);
		}
		if (this.selection != 1) {
			this.filmScrollPane = new JScrollPane(this.filmTable());
			this.getContentPane().add(this.filmScrollPane, BorderLayout.CENTER);
			UserWindow.this.getContentPane().repaint();
			UserWindow.this.revalidate();
		}
		UserWindow.this.selection = 1;
		this.rent.setEnabled(true);
	}

	public JTable rentalTable() {
		int nr = 1;
		final String[] colName = { "Nr", "Film", "StartDate", "EndDate" };
		this.rentalModel = new DefaultTableModel() {
			@Override
			public Class<?> getColumnClass(final int column) {
				if (column == 0) {
					return Integer.class;
				}
				return super.getColumnClass(column);
			}
		};
		this.rentalTable = new JTable(this.rentalModel) {
			@Override
			public boolean isCellEditable(final int nRow, final int nCol) {
				return false;
			}
		};
		this.rentalModel.setColumnIdentifiers(colName);
		this.rentalTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		try {
			for (final Rental rental : this.rbs.findByUserId(this.user.getId())) {
				final Object[] data = new Object[] { nr++, this.fbs.findById(rental.getFilmId()).getName(),
						rental.getStartDate(), rental.getEndDate() };
				this.rentalModel.addRow(data);
			}

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			nr = 1;
		}

		this.rentalModel.fireTableDataChanged();
		if (this.selection != 2) {
			UserWindow.this.getContentPane().repaint();
			UserWindow.this.revalidate();
		}
		final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.rentalTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		this.rentalTable.setAutoCreateRowSorter(true);
		this.rentalTable.getTableHeader().setReorderingAllowed(false);
		return this.rentalTable;

	}

	public void setupRentalTable() {
		if (this.filmScrollPane != null) {
			UserWindow.this.remove(UserWindow.this.filmScrollPane);
		}
		if (this.selection != 2) {
			this.rentalScrollPane = new JScrollPane(this.rentalTable());
			this.getContentPane().add(this.rentalScrollPane, BorderLayout.CENTER);
			UserWindow.this.getContentPane().repaint();
			UserWindow.this.revalidate();
		}
		UserWindow.this.selection = 2;
	}

}
