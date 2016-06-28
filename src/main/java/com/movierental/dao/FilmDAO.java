package com.movierental.dao;

import java.sql.SQLException;
import java.util.List;

import com.movierental.pojo.Film;

public interface FilmDAO extends DAO<Film> {

	public List<Film> findByName(final String name) throws SQLException;

	public List<Film> findByGenre(final String genre) throws SQLException;

	public List<Film> findByYear(final Integer year) throws SQLException;

}
