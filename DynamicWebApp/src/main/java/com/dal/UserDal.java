package com.dal;

import java.sql.SQLException;

import com.pojo.User;

public interface UserDal {
	
	User ValidateUser(String username,String password) throws SQLException;
	
	int addUser(User obj);
	
	int editUser(User obj);
	
	int deleteUser(User obj);

}
