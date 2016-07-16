package com.movierental.bs;

import java.sql.SQLException;
import java.util.List;

public interface BusinessService<T> {

	void save(T type) throws SQLException;

	void update(T type) throws SQLException;

	void delete(T type) throws SQLException;

	T findById(Integer id) throws SQLException;

	int getLastId() throws SQLException;

	List<T> selectAll() throws SQLException;

	void renumber() throws SQLException;

}
