package com.movierental.dao.factory;

import com.movierental.dao.FilmDAO;
import com.movierental.dao.FilmDAOImpl;
import com.movierental.dao.RentalDAO;
import com.movierental.dao.RentalDAOImpl;
import com.movierental.dao.UserDAO;
import com.movierental.dao.UserDAOImpl;

public class HibernateDAOFactory implements DAOFactory {
	@Override
	public UserDAO getUserDAO() {
		return new UserDAOImpl();
	}

	@Override
	public FilmDAO getFilmDAO() {
		return new FilmDAOImpl();
	}

	@Override
	public RentalDAO getRentalDAO() {
		return new RentalDAOImpl();
	}
}
