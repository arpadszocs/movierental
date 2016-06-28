package com.movierental.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

	void save(T film) throws SQLException;

	void update(T film) throws SQLException;

	void delete(T film) throws SQLException;

	T findById(Integer id) throws SQLException;

	int getLastId() throws SQLException;

	List<T> selectAll() throws SQLException;

	void renumber() throws SQLException;

}
