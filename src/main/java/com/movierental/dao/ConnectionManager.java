package com.movierental.dao;

public class ConnectionManager {

	public DBConnection getConnection(final String dbType) {
		if (dbType == "MySQL") {
			return MySQLConnection.getInstance();

		} else if (dbType == "MsSQL") {
			return MSSQLConnection.getInstance();
		}
		return null;

	}

}
