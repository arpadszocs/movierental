package com.movierental.bs;

import java.sql.SQLException;

import com.movierental.pojo.User;

public interface UserBusisessService extends BusinessService<User> {

	User findByName(String name) throws SQLException;

	User findByEmail(String email) throws SQLException;

}
