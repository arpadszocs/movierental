package com.movierental.test;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.movierental.dao.MySQLConnection;
import com.movierental.dao.UserDAOImpl;
import com.movierental.pojo.User;

public class UserDAOTest {

	private User user;
	private UserDAOImpl userDAO;

	@Before
	public void setUp() {

		this.user = new User(9000, "Name", "Email", "Password", "Role");
		this.userDAO = new UserDAOImpl(MySQLConnection.getInstance());
	}

	@Test
	public void testUserDAOsaveMethod() throws SQLException {

		try {
			this.userDAO.save(this.user);
			Assert.assertTrue(this.userDAO.findByName("Name").equals(this.user));
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.userDAO.delete(this.user);
		}

	}

	@Test
	public void testUserDAOupdateMethod() throws SQLException {
		try {
			this.userDAO.save(this.user);
			this.userDAO.update(this.user);
			Assert.assertTrue(this.userDAO.findByName("Name").equals(this.user));
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.userDAO.delete(this.user);
		}
	}

	@Test
	public void testUserDAOdeleteMethod() {
		try {
			final User userToUse = new User(9001, "UName", "UEmail", "UPw", "URole");
			this.userDAO.save(userToUse);
			this.userDAO.delete(userToUse);
			Assert.assertTrue(this.userDAO.findById(9001) == null);
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testUserDAOfindbyID() throws SQLException {
		try {
			this.userDAO.save(this.user);
			final User result = this.userDAO.findById(9000);
			Assert.assertEquals(result, this.user);
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.userDAO.delete(this.user);
		}
	}

	@Test
	public void testUserDAOfindbyName() throws SQLException {
		try {
			this.userDAO.save(this.user);
			final User result = this.userDAO.findByName("Name");
			Assert.assertEquals(result, this.user);
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.userDAO.delete(this.user);
		}

	}

}
