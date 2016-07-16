package com.movierental.bs;

import java.sql.SQLException;
import java.util.List;

import com.movierental.pojo.Film;

public interface FilmBusinessService extends BusinessService<Film> {

	List<Film> findByName(String name) throws SQLException;

	List<Film> findByGenre(String genre) throws SQLException;

	List<Film> findByYear(Integer year) throws SQLException;

}