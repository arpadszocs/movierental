package com.movierental.bs;

import java.sql.SQLException;
import java.util.List;

import com.movierental.pojo.Film;

public interface FilmBusinessService {

	void save(Film film) throws SQLException;

	void update(Film film) throws SQLException;

	void delete(Film film) throws SQLException;

	Film findById(Integer id) throws SQLException;

	List<Film> findByName(String name) throws SQLException;

	List<Film> findByGenre(String genre) throws SQLException;

	List<Film> findByYear(Integer year) throws SQLException;

	int getLastId() throws SQLException;

	List<Film> selectAll() throws SQLException;

	void renumber() throws SQLException;

}