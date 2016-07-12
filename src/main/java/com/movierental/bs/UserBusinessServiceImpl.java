package com.movierental.bs;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.movierental.dao.UserDAO;
import com.movierental.pojo.User;

public class UserBusinessServiceImpl implements UserBusisessService {

	private final UserDAO userDAO;

	public UserBusinessServiceImpl(final UserDAO userDAO) {
		super();
		this.userDAO = userDAO;
	}

	@Override
	@Transactional(readOnly = false)
	public void save(final User user) throws SQLException {
		this.userDAO.save(user);
	}

	@Override
	@Transactional
	public void update(final User user) throws SQLException {
		this.userDAO.update(user);

	}

	@Override
	@Transactional
	public void delete(final User user) throws SQLException {
		this.userDAO.delete(user);

	}

	@Override
	@Transactional
	public User findById(final Integer id) throws SQLException {
		return this.userDAO.findById(id);

	}

	@Override
	@Transactional
	public User findByName(final String name) throws SQLException {
		return this.userDAO.findByName(name);

	}

	@Override
	@Transactional
	public User findByEmail(final String email) throws SQLException {
		return this.userDAO.findByEmail(email);
	}

	@Override
	@Transactional
	public int getLastId() throws SQLException {
		return this.userDAO.getLastId();

	}

	@Override
	@Transactional
	public List<User> selectAll() throws SQLException {
		return this.userDAO.selectAll();

	}

	@Override
	@Transactional(readOnly = false)
	public void renumber() throws SQLException {
		this.userDAO.renumber();

	}

}
