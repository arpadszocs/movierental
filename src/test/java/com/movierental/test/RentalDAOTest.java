package com.movierental.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.movierental.dao.MySQLConnection;
import com.movierental.dao.RentalDAOJDBCImpl;
import com.movierental.pojo.Rental;

public class RentalDAOTest {
	private Rental rental;
	private RentalDAOJDBCImpl rentalDAO;
	@SuppressWarnings("deprecation")
	private static final Date START_DATE = new Date(116, 5, 11);
	@SuppressWarnings("deprecation")
	private static final Date END_DATE = new Date(116, 5, 11);

	@Before
	public void setUp() {
		this.rental = new Rental(100, 10, 10, START_DATE, END_DATE);
		this.rentalDAO = new RentalDAOJDBCImpl(MySQLConnection.getInstance());
	}

	@Test
	public void testRentalDAOsaveMethod() {
		try {
			this.rentalDAO.save(this.rental);
			this.rentalDAO.delete(this.rental);
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRentalDAOupdateMethod() throws SQLException {
		try {
			this.rentalDAO.save(this.rental);
			// TODO: change start date
			this.rentalDAO.update(this.rental);
			// TODO: retrieve from db and check for equality (.equals())
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			this.rentalDAO.delete(this.rental);
		}
	}

	@Test
	public void testRentalDAOdeleteMethod() {
		try {
			final Rental rentalToBeDeleted = new Rental(200, 20, 20, START_DATE, END_DATE);
			this.rentalDAO.save(rentalToBeDeleted);

			this.rentalDAO.delete(rentalToBeDeleted);

			final List<Rental> rentalList = this.rentalDAO.findByFilmId(20);
			assertTrue(rentalList.isEmpty());
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRentalDAOfindByUserId() throws SQLException {
		try {
			this.rentalDAO.save(this.rental);
			final List<Rental> rentalList = this.rentalDAO.findByUserId(10);
			final Rental result = rentalList.get(0);
			assertEquals(result, this.rental);
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			this.rentalDAO.delete(this.rental);
		}
	}

	@Test
	public void testRentalDAOfindByFilmId() throws SQLException {
		try {
			this.rentalDAO.save(this.rental);
			final List<Rental> rentalList = this.rentalDAO.findByFilmId(10);
			final Rental result = rentalList.get(0);
			assertEquals(result, this.rental);
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			this.rentalDAO.delete(this.rental);
		}
	}
}
