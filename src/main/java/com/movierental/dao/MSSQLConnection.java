package com.movierental.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MSSQLConnection implements DBConnection {

	public static MSSQLConnection getInstance() {
		return null;

	}

	@Override
	public ResultSet select(final String selectQuery) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(final String insertQuery) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
}
