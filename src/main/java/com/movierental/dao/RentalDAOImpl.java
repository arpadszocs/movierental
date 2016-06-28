package com.movierental.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.movierental.pojo.Rental;

public class RentalDAOImpl implements RentalDAO {

	private final DBConnection dbConnection;
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public RentalDAOImpl(final DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	@Override
	public void save(final Rental rental) throws SQLException {
		final String saveQuery = "INSERT INTO rental(ID , FilmID, UserID, StartDate, EndDate) VALUES (" + rental.getId()
				+ "," + rental.getFilmId() + "," + rental.getUserId() + ",'" + this.sdf.format(rental.getStartDate())
				+ "','" + this.sdf.format(rental.getEndDate()) + "')";
		this.dbConnection.insert(saveQuery);
	}

	@Override
	public void update(final Rental rental) throws SQLException {
		final String updateQuery = "UPDATE rental SET FilmID=" + rental.getFilmId() + ",UserID=" + rental.getUserId()
				+ ", StartDate='" + this.sdf.format(rental.getStartDate()) + "',EndDate='"
				+ this.sdf.format(rental.getEndDate()) + "' WHERE ID = " + rental.getId();
		this.dbConnection.insert(updateQuery);
	}

	@Override
	public void delete(final Rental rental) throws SQLException {
		final String deleteQuey = "DELETE FROM rental WHERE ID = " + rental.getId();
		this.dbConnection.insert(deleteQuey);
	}

	@Override
	public List<Rental> findByFilmId(final Integer filmId) throws SQLException {
		final List<Rental> rentalList = new ArrayList<>();
		final String selectQuery = "SELECT * FROM rental WHERE FIlmID = " + filmId;
		final ResultSet resultSet = this.dbConnection.select(selectQuery);
		while (resultSet.next()) {
			rentalList.add(new Rental(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3),
					resultSet.getDate(4), resultSet.getDate(5)));
		}
		return rentalList;
	}

	@Override
	public List<Rental> findByUserId(final Integer userId) throws SQLException {
		final List<Rental> rentalList = new ArrayList<>();
		final String selectQuery = "SELECT * FROM rental WHERE UserID = " + userId;

		final ResultSet resultSet = this.dbConnection.select(selectQuery);
		while (resultSet.next()) {
			rentalList.add(new Rental(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3),
					resultSet.getDate(4), resultSet.getDate(5)));
		}
		return rentalList;
	}

	@Override
	public Rental findById(final Integer id) throws SQLException {
		final String selectQuery = "SELECT * FROM rental WHERE ID = " + id;
		final ResultSet resultSet = this.dbConnection.select(selectQuery);
		if (resultSet.next()) {
			return new Rental(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getDate(4),
					resultSet.getDate(5));
		}
		return null;
	}

	@Override
	public int getLastId() throws SQLException {
		final ResultSet resultSet = this.dbConnection.select("SELECT MAX(ID) FROM rental");
		if (resultSet.next()) {
			return resultSet.getInt(1);
		}
		return -1;

	}

	@Override
	public List<Rental> selectAll() throws SQLException {
		final List<Rental> rentalList = new ArrayList<>();
		final String selectQuery = "SELECT * FROM rental ";
		final ResultSet resultSet = this.dbConnection.select(selectQuery);
		while (resultSet.next()) {
			rentalList.add(new Rental(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3),
					resultSet.getDate(4), resultSet.getDate(5)));
		}
		return rentalList;
	}

	@Override
	public void renumber() throws SQLException {
		this.dbConnection.insert("SET @i=0;");
		this.dbConnection.insert("UPDATE rental SET ID=(@i:=@i+1);");
	}

}
