package com.movierental.dao.factory;

import com.movierental.dao.FilmDAO;
import com.movierental.dao.RentalDAO;
import com.movierental.dao.UserDAO;

public interface DAOFactory {

	UserDAO getUserDAO();

	FilmDAO getFilmDAO();

	RentalDAO getRentalDAO();

}
