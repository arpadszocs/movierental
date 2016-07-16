package com.movierental.bs;

import java.sql.SQLException;
import java.util.List;

import com.movierental.dao.FilmDAO;
import com.movierental.pojo.Film;

public class FilmBusinessServiceImpl implements FilmBusinessService {

	private final FilmDAO filmDAO;

	public FilmBusinessServiceImpl(final FilmDAO filmDAO) {
		super();
		this.filmDAO = filmDAO;
	}

	@Override
	public void save(final Film film) throws SQLException {
		this.filmDAO.save(film);
	}

	@Override
	public void update(final Film film) throws SQLException {
		this.filmDAO.update(film);

	}

	@Override
	public void delete(final Film film) throws SQLException {
		this.filmDAO.delete(film);
	}

	@Override
	public Film findById(final Integer id) throws SQLException {
		return this.filmDAO.findById(id);
	}

	@Override
	public int getLastId() throws SQLException {
		return this.filmDAO.getLastId();
	}

	@Override
	public List<Film> selectAll() throws SQLException {
		return this.filmDAO.selectAll();
	}

	@Override
	public void renumber() throws SQLException {
		this.filmDAO.renumber();
	}

	@Override
	public List<Film> findByName(final String name) throws SQLException {
		return this.filmDAO.findByName(name);
	}

	@Override
	public List<Film> findByGenre(final String genre) throws SQLException {
		return this.filmDAO.findByGenre(genre);
	}

	@Override
	public List<Film> findByYear(final Integer year) throws SQLException {
		return this.filmDAO.findByYear(year);
	}

}
