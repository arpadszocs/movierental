package com.movierental.test;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.movierental.dao.FilmDAOJDBCImpl;
import com.movierental.dao.MySQLConnection;
import com.movierental.pojo.Film;

public class FilmDAOTest {
	private Film film;
	private FilmDAOJDBCImpl filmDAO;

	@Before
	public void setUp() {
		this.film = new Film(9999, "Film name", 999, "Film genre", 9999);
		this.filmDAO = new FilmDAOJDBCImpl(MySQLConnection.getInstance());
	}

	@Test
	public void testFilmDAOsaveMethod() {
		try {
			this.filmDAO.save(this.film);
			Assert.assertTrue(!this.filmDAO.findByName("Film name").isEmpty());
			this.filmDAO.delete(this.film);
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFilmDAOupdateMethod() throws SQLException {
		try {
			this.filmDAO.save(this.film);
			this.filmDAO.update(this.film);
			Assert.assertTrue(!this.filmDAO.findByName("Film name").equals(this.film));

		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			this.filmDAO.delete(this.film);
		}
	}

	@Test
	public void testFilmDAOfindByName() throws SQLException {
		try {
			this.filmDAO.save(this.film);
			final List<Film> filmList = this.filmDAO.findByName("Film name");
			final Film result = filmList.get(0);
			assertEquals(result, this.film);
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			this.filmDAO.delete(this.film);
		}
	}

	@Test
	public void testFilmDAOfindByGenre() throws SQLException {
		try {
			this.filmDAO.save(this.film);
			final List<Film> filmList = this.filmDAO.findByGenre("Film genre");
			final Film result = filmList.get(0);
			assertEquals(result, this.film);
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			this.filmDAO.delete(this.film);
		}
	}

	@Test
	public void testFilmDAOfindByYear() throws SQLException {
		try {
			this.filmDAO.save(this.film);
			final List<Film> filmList = this.filmDAO.findByYear(9999);
			final Film result = filmList.get(0);
			assertEquals(result, this.film);
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			this.filmDAO.delete(this.film);
		}
	}

	@Test
	public void testFilmDAOdeleteMethod() {
		try {
			final Film filmToUse = new Film(9998, "Name", 100, "Genre", 2016);
			this.filmDAO.save(filmToUse);
			this.filmDAO.delete(filmToUse);
			final List<Film> filmList = this.filmDAO.findByName("Name");
			Assert.assertTrue(filmList.isEmpty());
		} catch (final SQLException e) {
			e.printStackTrace();
		}

	}
}
