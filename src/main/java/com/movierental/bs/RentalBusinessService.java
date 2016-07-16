package com.movierental.bs;

import java.sql.SQLException;
import java.util.List;

import com.movierental.pojo.Rental;

public interface RentalBusinessService extends BusinessService<Rental> {

	List<Rental> findByFilmId(Integer filmId) throws SQLException;

	List<Rental> findByUserId(Integer userId) throws SQLException;

}
