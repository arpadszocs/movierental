package com.movierental.bs;

import java.sql.SQLException;
import java.util.List;

import com.movierental.pojo.User;

public interface UserBusisessService {

	void save(User user) throws SQLException;

	void update(User user) throws SQLException;

	void delete(User user) throws SQLException;

	User findById(Integer id) throws SQLException;

	User findByName(String name) throws SQLException;

	User findByEmail(String email) throws SQLException;

	int getLastId() throws SQLException;

	List<User> selectAll() throws SQLException;

	void renumber() throws SQLException;

}
