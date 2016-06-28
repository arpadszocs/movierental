package com.movierental.bs;

import java.sql.SQLException;
import java.util.List;

import com.movierental.pojo.Rental;

public interface RentalBusinessService {

	void save(Rental rental) throws SQLException;

	void update(Rental rental) throws SQLException;

	void delete(Rental rental) throws SQLException;

	List<Rental> findByFilmId(Integer filmId) throws SQLException;

	List<Rental> findByUserId(Integer userId) throws SQLException;

	Rental findById(Integer id) throws SQLException;

	int getLastId() throws SQLException;

	List<Rental> selectAll() throws SQLException;

	void renumber() throws SQLException;

}
