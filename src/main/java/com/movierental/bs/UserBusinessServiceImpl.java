package com.movierental.bs;

import java.sql.SQLException;
import java.util.List;

import com.movierental.dao.UserDAO;
import com.movierental.pojo.User;

public class UserBusinessServiceImpl implements UserBusisessService {

	private final UserDAO userDAO;

	public UserBusinessServiceImpl(final UserDAO userDAO) {
		super();
		this.userDAO = userDAO;
	}

	@Override
	public void save(final User user) throws SQLException {
		this.userDAO.save(user);
	}

	@Override
	public void update(final User user) throws SQLException {
		this.userDAO.update(user);

	}

	@Override
	public void delete(final User user) throws SQLException {
		this.userDAO.delete(user);

	}

	@Override
	public User findById(final Integer id) throws SQLException {
		return this.userDAO.findById(id);

	}

	@Override
	public User findByName(final String name) throws SQLException {
		return this.userDAO.findByName(name);

	}

	@Override
	public User findByEmail(final String email) throws SQLException {
		return this.userDAO.findByEmail(email);
	}

	@Override
	public int getLastId() throws SQLException {
		return this.userDAO.getLastId();

	}

	@Override
	public List<User> selectAll() throws SQLException {
		return this.userDAO.selectAll();

	}

	@Override
	public void renumber() throws SQLException {
		this.userDAO.renumber();

	}

}
