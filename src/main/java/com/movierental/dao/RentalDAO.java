package com.movierental.dao;

import java.sql.SQLException;
import java.util.List;

import com.movierental.pojo.Rental;

public interface RentalDAO extends DAO<Rental> {

	public List<Rental> findByFilmId(final Integer filmId) throws SQLException;

	public List<Rental> findByUserId(final Integer userId) throws SQLException;
}
