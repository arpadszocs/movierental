package com.movierental.dao.factory;

import com.movierental.dao.FilmDAO;
import com.movierental.dao.FilmDAOJDBCImpl;
import com.movierental.dao.MySQLConnection;
import com.movierental.dao.RentalDAO;
import com.movierental.dao.RentalDAOJDBCImpl;
import com.movierental.dao.UserDAO;
import com.movierental.dao.UserDAOJDBCImpl;

public class JDBCDAOFactory implements DAOFactory {

	@Override
	public UserDAO getUserDAO() {
		return new UserDAOJDBCImpl(MySQLConnection.getInstance());
	}

	@Override
	public FilmDAO getFilmDAO() {
		return new FilmDAOJDBCImpl(MySQLConnection.getInstance());
	}

	@Override
	public RentalDAO getRentalDAO() {
		return new RentalDAOJDBCImpl(MySQLConnection.getInstance());
	}

}
