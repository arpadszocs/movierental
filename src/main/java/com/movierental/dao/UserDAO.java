package com.movierental.dao;

import java.sql.SQLException;

import com.movierental.pojo.User;

public interface UserDAO extends DAO<User> {

	public User findByName(final String name) throws SQLException;

	public User findByEmail(final String email) throws SQLException;
}
