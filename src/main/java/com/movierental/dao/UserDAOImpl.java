package com.movierental.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.movierental.pojo.User;

public class UserDAOImpl implements UserDAO {

	private final DBConnection dbConnection;

	public UserDAOImpl(final DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	@Override
	public void save(final User user) throws SQLException {
		final String saveQuery = "INSERT INTO user (ID, Name, Email, Password, Role) VALUES (" + user.getId() + " ,'"
				+ user.getName() + "' ,'" + user.getEmail() + "' ,'" + user.getPassword() + "' ,'" + user.getRole()
				+ "')";
		this.dbConnection.insert(saveQuery);
	}

	@Override
	public void update(final User user) throws SQLException {
		final String updateQuery = "UPDATE user SET Name='" + user.getName() + "', Email='" + user.getEmail()
				+ "', Password='" + user.getPassword() + "', Role='" + user.getRole() + "' WHERE ID = " + user.getId();
		this.dbConnection.insert(updateQuery);
	}

	@Override
	public void delete(final User user) throws SQLException {
		final String deleteQuery = "DELETE FROM user WHERE ID = " + user.getId();
		this.dbConnection.insert(deleteQuery);
	}

	@Override
	public User findById(final Integer id) throws SQLException {
		final String selectQuery = "SELECT * FROM user WHERE ID = " + id;
		final ResultSet resultSet = this.dbConnection.select(selectQuery);
		if (resultSet.next()) {
			return new User(id, resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
					resultSet.getString(5));
		}
		return null;
	}

	@Override
	public User findByName(final String name) throws SQLException {
		final String selectQuery = "SELECT * FROM user WHERE Name = '" + name + "'";
		final ResultSet resultSet = this.dbConnection.select(selectQuery);
		if (resultSet.next()) {
			return new User(resultSet.getInt(1), name, resultSet.getString(3), resultSet.getString(4),
					resultSet.getString(5));
		}
		return null;
	}

	@Override
	public User findByEmail(final String email) throws SQLException {
		final String selectQuery = "SELECT * FROM user WHERE Email = '" + email + "'";
		final ResultSet resultSet = this.dbConnection.select(selectQuery);
		if (resultSet.next()) {
			return new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
					resultSet.getString(5));
		}
		return null;
	}

	@Override
	public int getLastId() throws SQLException {
		final ResultSet resultSet = this.dbConnection.select("SELECT MAX(ID) FROM user WHERE Role != 'admin'");
		if (resultSet.next()) {
			return resultSet.getInt(1);
		}
		return -1;

	}

	@Override
	public List<User> selectAll() throws SQLException {
		final List<User> userList = new ArrayList<>();
		final String selectQuery = "SELECT * FROM user WHERE Role != 'admin'";
		final ResultSet resultSet = this.dbConnection.select(selectQuery);
		while (resultSet.next()) {
			userList.add(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
					resultSet.getString(4), resultSet.getString(5)));
		}
		return userList;
	}

	@Override
	public void renumber() throws SQLException {
		this.dbConnection.insert("SET @i=0;");
		this.dbConnection.insert("UPDATE user SET ID=(@i:=@i+1); WHERE Role != 'admin'");
	}

}
