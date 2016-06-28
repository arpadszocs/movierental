package com.movierental.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.movierental.pojo.Film;

public class FilmDAOImpl implements FilmDAO {

	private final DBConnection dbConnection;

	public FilmDAOImpl(final DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	@Override
	public void save(final Film film) throws SQLException {
		final String saveQuery = "INSERT INTO film(ID,Name, Length, Genre, Year) VALUES (" + film.getId() + ",'"
				+ film.getName() + "'," + film.getLength() + ",'" + film.getGenre() + "'," + film.getYear() + ")";
		this.dbConnection.insert(saveQuery);
	}

	@Override
	public void update(final Film film) throws SQLException {
		final String updateQuery = "UPDATE film SET Name='" + film.getName() + "',Length=" + film.getLength()
				+ ", Genre='" + film.getGenre() + "', Year=" + film.getYear() + " WHERE ID=" + film.getId();
		this.dbConnection.insert(updateQuery);
	}

	@Override
	public void delete(final Film film) throws SQLException {
		final String deleteQuery = "DELETE FROM film WHERE ID = " + film.getId();
		this.dbConnection.insert(deleteQuery);
	}

	@Override
	public Film findById(final Integer id) throws SQLException {
		final String selectQuery = "SELECT * FROM film WHERE ID = " + id;
		final ResultSet resultSet = this.dbConnection.select(selectQuery);
		if (resultSet.next()) {
			return new Film(id, resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4),
					resultSet.getInt(5));
		}
		return null;
	}

	@Override
	public List<Film> findByName(final String name) throws SQLException {
		final List<Film> filmList = new ArrayList<>();
		final String selectQuery = "SELECT * FROM film WHERE Name = '" + name + "'";
		final ResultSet resultSet = this.dbConnection.select(selectQuery);
		while (resultSet.next()) {
			filmList.add(new Film(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),
					resultSet.getString(4), resultSet.getInt(5)));
		}
		return filmList;
	}

	@Override
	public List<Film> findByGenre(final String genre) throws SQLException {
		final List<Film> filmList = new ArrayList<>();
		final String selectQuery = "SELECT * FROM film WHERE Genre = '" + genre + "'";
		final ResultSet resultSet = this.dbConnection.select(selectQuery);
		while (resultSet.next()) {
			filmList.add(new Film(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),
					resultSet.getString(4), resultSet.getInt(5)));
		}
		return filmList;
	}

	@Override
	public List<Film> findByYear(final Integer year) throws SQLException {
		final List<Film> filmList = new ArrayList<>();
		final String selectQuery = "SELECT * FROM film WHERE Year = " + year;
		final ResultSet resultSet = this.dbConnection.select(selectQuery);
		while (resultSet.next()) {
			filmList.add(new Film(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),
					resultSet.getString(4), resultSet.getInt(5)));
		}
		return filmList;
	}

	@Override
	public int getLastId() throws SQLException {
		final ResultSet resultSet = this.dbConnection.select("SELECT MAX(ID) FROM film ");
		if (resultSet.next()) {
			return resultSet.getInt(1);
		}
		return -1;

	}

	@Override
	public List<Film> selectAll() throws SQLException {
		final List<Film> filmList = new ArrayList<>();
		final String selectQuery = "SELECT * FROM film ";
		final ResultSet resultSet = this.dbConnection.select(selectQuery);
		while (resultSet.next()) {
			filmList.add(new Film(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),
					resultSet.getString(4), resultSet.getInt(5)));
		}
		return filmList;
	}

	@Override
	public void renumber() throws SQLException {
		this.dbConnection.insert("SET @i=0;");
		this.dbConnection.insert("UPDATE film SET ID=(@i:=@i+1);");
	}

}
