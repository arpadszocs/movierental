package com.movierental.dao.factory;

import com.movierental.dao.FilmDAO;
import com.movierental.dao.FilmDAOImpl;
import com.movierental.dao.MySQLConnection;
import com.movierental.dao.RentalDAO;
import com.movierental.dao.RentalDAOImpl;
import com.movierental.dao.UserDAO;
import com.movierental.dao.UserDAOJDBCImpl;

public class JDBCDAOFactory implements DAOFactory {

	@Override
	public UserDAO getUserDAO() {
		return new UserDAOJDBCImpl(MySQLConnection.getInstance());
	}

	@Override
	public FilmDAO getFilmDAO() {
		return new FilmDAOImpl(MySQLConnection.getInstance());
	}

	@Override
	public RentalDAO getRentalDAO() {
		return new RentalDAOImpl(MySQLConnection.getInstance());
	}

}
