package com.movierental.bs;

import java.sql.SQLException;
import java.util.List;

import com.movierental.dao.RentalDAO;
import com.movierental.pojo.Rental;

public class RentalBusinessServiceImpl implements RentalBusinessService {

	private final RentalDAO rentalDAO;

	public RentalBusinessServiceImpl(final RentalDAO rentalDAO) {
		super();
		this.rentalDAO = rentalDAO;
	}

	@Override
	public void save(final Rental rental) throws SQLException {
		this.rentalDAO.save(rental);
	}

	@Override
	public void update(final Rental rental) throws SQLException {
		this.rentalDAO.update(rental);
	}

	@Override
	public void delete(final Rental rental) throws SQLException {
		this.rentalDAO.delete(rental);
	}

	@Override
	public List<Rental> findByFilmId(final Integer filmId) throws SQLException {
		return this.rentalDAO.findByFilmId(filmId);
	}

	@Override
	public List<Rental> findByUserId(final Integer userId) throws SQLException {
		return this.rentalDAO.findByUserId(userId);
	}

	@Override
	public Rental findById(final Integer id) throws SQLException {
		return this.rentalDAO.findById(id);
	}

	@Override
	public int getLastId() throws SQLException {
		return this.rentalDAO.getLastId();
	}

	@Override
	public List<Rental> selectAll() throws SQLException {
		return this.rentalDAO.selectAll();
	}

	@Override
	public void renumber() throws SQLException {
		this.rentalDAO.renumber();
	}

}
