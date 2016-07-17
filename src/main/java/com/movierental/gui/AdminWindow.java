package com.movierental.gui;

import java.awt.BorderLayout;
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

@SuppressWarnings("serial")
public class AdminWindow extends JFrame {
	private JTable userTable;
	private JTable filmTable;
	private JTable rentalTable;
	private JScrollPane filmScrollPane;
	private JScrollPane userScrollPane;
	private JScrollPane rentalScrollPane;
	private final JPanel buttonPanel;
	private final JButton add;
	private final JButton delete;
	private final JButton rentals;
	private final JPanel header;
	private final JLabel adminLabel;
	private final JButton logout;
	private final JButton users;
	private final JButton films;
	private int selection; // user - 0 | film - 1 | rental - 2 |
	private DefaultTableModel userModel;
	private DefaultTableModel filmModel;
	private JPanel addPanel;
	private JTextField filmName;
	private JTextField filmLength;
	private JTextField filmGenre;
	private JTextField filmYear;
	private final FilmBusinessService fbs;
	private final UserBusisessService ubs;
	private final RentalBusinessService rbs;
	private DefaultTableModel rentalModel;

	@SuppressWarnings("static-access")
	public AdminWindow() {
		final DAOFactory daoFactory = new JDBCDAOFactory();
		this.fbs = new FilmBusinessServiceImpl(daoFactory.getFilmDAO());
		this.ubs = new UserBusinessServiceImpl(daoFactory.getUserDAO());
		this.rbs = new RentalBusinessServiceImpl(daoFactory.getRentalDAO());
		this.buttonPanel = new JPanel();
		this.getContentPane().add(this.buttonPanel, BorderLayout.SOUTH);

		this.add = new JButton("Add");
		this.buttonPanel.add(this.add);

		this.delete = new JButton("Delete");
		this.buttonPanel.add(this.delete);

		this.users = new JButton("Users");
		this.buttonPanel.add(this.users);

		this.films = new JButton("Films");
		this.buttonPanel.add(this.films);

		this.rentals = new JButton("Rentals");
		this.buttonPanel.add(this.rentals);

		this.logout = new JButton("Logout");
		this.buttonPanel.add(this.logout);

		this.header = new JPanel();
		this.getContentPane().add(this.header, BorderLayout.NORTH);

		this.adminLabel = new JLabel("Admin");
		this.header.add(this.adminLabel);

		this.userScrollPane = new JScrollPane(this.userTable());
		this.getContentPane().add(this.userScrollPane, BorderLayout.CENTER);
		this.selection = 0;

		this.delete.setEnabled(false);
		this.add.setEnabled(false);

		this.rentals.addActionListener(e -> {
			this.setupRentalTable();
		});
		this.users.addActionListener(e -> {
			this.setupUserTable();
		});
		this.films.addActionListener(e -> {
			this.setupFilmTable();
		});

		this.addPanel();
		this.add.addActionListener(e -> {

			final int result = JOptionPane.showConfirmDialog(AdminWindow.this, AdminWindow.this.addPanel,
					"Enter a new film", JOptionPane.OK_CANCEL_OPTION);

			if (result == JOptionPane.OK_OPTION) {

				try {
					// final int id = this.fbs.getLastId();
					AdminWindow.this.fbs.save(new Film(AdminWindow.this.filmName.getText(),
							Integer.parseInt(AdminWindow.this.filmLength.getText()),
							AdminWindow.this.filmGenre.getText(),
							Integer.parseInt(AdminWindow.this.filmYear.getText())));
					AdminWindow.this.getContentPane().revalidate();
					AdminWindow.this.getContentPane().repaint();
				} catch (NumberFormatException | SQLException exception) {
					new JOptionPane().showMessageDialog(AdminWindow.this, "Wrong input", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				AdminWindow.this.remove(AdminWindow.this.filmScrollPane);
				this.selection = -1;
				this.setupFilmTable();
			}

		});

		this.delete.addActionListener(e -> {
			try {
				if (AdminWindow.this.selection == 0) {
					final int selectedRow1 = AdminWindow.this.userTable.getSelectedRow();
					final int id1 = Integer.parseInt(AdminWindow.this.userModel.getValueAt(selectedRow1, 0).toString());
					AdminWindow.this.ubs.delete(AdminWindow.this.ubs.findById(id1));
					if (AdminWindow.this.rbs.findByUserId(id1) != null) {
						for (final Rental rental1 : AdminWindow.this.rbs.findByUserId(id1)) {
							AdminWindow.this.rbs.delete(rental1);
						}
					}
					AdminWindow.this.userModel.removeRow(selectedRow1);
					AdminWindow.this.getContentPane().revalidate();
					AdminWindow.this.getContentPane().repaint();
				} else if (AdminWindow.this.selection == 1) {
					final int selectedRow2 = AdminWindow.this.filmTable.getSelectedRow();
					final int id2 = Integer.parseInt(AdminWindow.this.filmModel.getValueAt(selectedRow2, 0).toString());
					AdminWindow.this.fbs.delete(AdminWindow.this.fbs.findById(id2));
					if (AdminWindow.this.rbs.findByFilmId(id2) != null) {
						for (final Rental rental2 : AdminWindow.this.rbs.findByFilmId(id2)) {
							AdminWindow.this.rbs.delete(rental2);
						}
					}
					AdminWindow.this.filmModel.removeRow(selectedRow2);
					AdminWindow.this.getContentPane().revalidate();
					AdminWindow.this.getContentPane().repaint();
				}
			} catch (final SQLException e1) {
				e1.printStackTrace();
			}

		});

		this.logout.addActionListener(e -> {
			new LoginWindow().setupWindow();
			AdminWindow.this.dispose();

		});
	}

	public void setupWindow() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(550, 500);
		this.setLocation(400, 200);
		this.setVisible(true);
		this.setResizable(false);
	}

	public JTable filmTable() {

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
				final Object[] data = new Object[] { film.getId(), film.getName(), film.getLength(), film.getGenre(),
						film.getYear() };
				this.filmModel.addRow(data);
			}

		} catch (final SQLException e) {
			e.printStackTrace();
		}

		this.filmModel.fireTableDataChanged();
		if (this.selection != 1) {
			AdminWindow.this.getContentPane().repaint();
			AdminWindow.this.revalidate();
		}
		final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.filmTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		this.filmTable.setAutoCreateRowSorter(true);
		this.filmTable.getTableHeader().setReorderingAllowed(false);
		return this.filmTable;

	}

	public void setupFilmTable() {
		if (this.userScrollPane != null) {
			AdminWindow.this.remove(AdminWindow.this.userScrollPane);
		}
		if (this.rentalScrollPane != null) {
			AdminWindow.this.remove(AdminWindow.this.rentalScrollPane);
		}
		if (this.selection != 1) {
			this.filmScrollPane = new JScrollPane(this.filmTable());
			this.getContentPane().add(this.filmScrollPane, BorderLayout.CENTER);
			AdminWindow.this.getContentPane().repaint();
			AdminWindow.this.revalidate();
		}
		AdminWindow.this.selection = 1;
		AdminWindow.this.delete.setEnabled(true);
		AdminWindow.this.add.setEnabled(true);
	}

	public JTable userTable() {

		final String[] colName = { "Id", "Name", "Email", "Password" };
		this.userModel = new DefaultTableModel() {
			@Override
			public Class<?> getColumnClass(final int column) {
				if (column == 0) {
					return Integer.class;
				}
				return super.getColumnClass(column);
			}
		};
		this.userTable = new JTable(this.userModel) {
			@Override
			public boolean isCellEditable(final int nRow, final int nCol) {
				return false;
			}
		};
		this.userModel = (DefaultTableModel) this.userTable.getModel();
		this.userModel.setColumnIdentifiers(colName);
		this.userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		try {
			for (final User user : this.ubs.selectAll()) {
				final Object[] data = new Object[] { user.getId(), user.getName(), user.getEmail(),
						user.getPassword() };
				this.userModel.addRow(data);
			}

		} catch (final SQLException e) {
			e.printStackTrace();
		}

		this.userModel.fireTableDataChanged();
		if (this.selection != 0) {
			AdminWindow.this.getContentPane().repaint();
			AdminWindow.this.revalidate();
		}
		final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.userTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		this.userTable.setAutoCreateRowSorter(true);
		this.userTable.getTableHeader().setReorderingAllowed(false);
		return this.userTable;

	}

	public void setupUserTable() {
		if (this.rentalScrollPane != null) {
			AdminWindow.this.remove(AdminWindow.this.rentalScrollPane);
		}
		if (this.filmScrollPane != null) {
			AdminWindow.this.remove(AdminWindow.this.filmScrollPane);
		}
		if (this.selection != 0) {
			this.userScrollPane = new JScrollPane(this.userTable());
			this.getContentPane().add(this.userScrollPane, BorderLayout.CENTER);
			AdminWindow.this.getContentPane().repaint();
			AdminWindow.this.revalidate();
		}
		AdminWindow.this.selection = 0;
		AdminWindow.this.delete.setEnabled(true);
		AdminWindow.this.add.setEnabled(false);
	}

	public JTable rentalTable() {

		final String[] colName = { "Id", "User", "Film", "StartDate", "EndDate" };
		this.rentalModel = new DefaultTableModel() {
			@Override
			public Class<?> getColumnClass(final int column) {
				if (column == 0) {
					return Integer.class;
				}
				return super.getColumnClass(column);
			}
		};
		if (this.rentalTable == null) {
			this.rentalTable = new JTable(this.rentalModel) {
				@Override
				public boolean isCellEditable(final int nRow, final int nCol) {
					return false;
				}
			};
			this.rentalModel.setColumnIdentifiers(colName);
			this.rentalTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			try {
				for (final Rental rental : this.rbs.selectAll()) {
					final Object[] data = new Object[] { rental.getId(),
							this.ubs.findById(rental.getUserId()).getName(),
							this.fbs.findById(rental.getFilmId()).getName(), rental.getStartDate(),
							rental.getEndDate() };
					this.rentalModel.addRow(data);
				}

			} catch (final SQLException e) {
				e.printStackTrace();
			}
		}

		this.rentalModel.fireTableDataChanged();
		if (this.selection != 2) {
			AdminWindow.this.getContentPane().repaint();
			AdminWindow.this.revalidate();
		}
		final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.rentalTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		this.rentalTable.setAutoCreateRowSorter(true);
		this.rentalTable.getTableHeader().setReorderingAllowed(false);
		return this.rentalTable;

	}

	public void setupRentalTable() {
		if (this.userScrollPane != null) {
			AdminWindow.this.remove(AdminWindow.this.userScrollPane);
		}
		if (this.filmScrollPane != null) {
			AdminWindow.this.remove(AdminWindow.this.filmScrollPane);
		}
		if (this.selection != 2) {
			this.rentalScrollPane = new JScrollPane(this.rentalTable());
			this.getContentPane().add(this.rentalScrollPane, BorderLayout.CENTER);
			AdminWindow.this.getContentPane().repaint();
			AdminWindow.this.revalidate();
		}
		AdminWindow.this.selection = 2;
		AdminWindow.this.delete.setEnabled(false);
		AdminWindow.this.add.setEnabled(false);
	}

	public void addPanel() {
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

	}

}
