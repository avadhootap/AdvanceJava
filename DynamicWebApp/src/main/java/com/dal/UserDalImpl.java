package com.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.protocol.Resultset;
import com.pojo.User;

import DBUtils.DBConectivity;

public class UserDalImpl implements UserDal {
	private Connection con;
	private PreparedStatement stmt;
	private PreparedStatement pstmt1; //For Add
	private PreparedStatement pstmt2; //for Edit
	private PreparedStatement pstmt3; //for delete
	
	public UserDalImpl()
	{
		try {
			con=DBConectivity.getCon();
			stmt=con.prepareStatement("select * from users where email=? and password=?");
			System.out.println("Validation Query");
			
			pstmt1=con.prepareStatement("insert into users (custName,city,email,password) values (?,?,?,?)");
			System.out.println("Insert Query");
			
			pstmt2=con.prepareStatement("update users set custName=?,city=?,email=?,password=? where userid=?");
			System.out.println("Update Query");
			
			pstmt3=con.prepareStatement("delete from users where userid=?");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public User ValidateUser(String username, String password) throws SQLException {
		stmt.setString(1, username);
		stmt.setString(2, password);
		
		ResultSet rset=stmt.executeQuery();
		User user=null;
		while(rset.next())
		{
			user=new User(rset.getInt("userid"),
					rset.getString("custName"),
					rset.getString("city"), 
					rset.getString("email"),
					rset.getString("password"));
		}
		
		return user;
	}
	public int addUser(User obj) {
		try {
			pstmt1.setString(1, obj.getCustName());
			pstmt1.setString(2, obj.getCity());
			pstmt1.setString(3, obj.getEmail());
			pstmt1.setString(4, obj.getPassword());
			
			int i=pstmt1.executeUpdate();
			System.out.println("Successfully Added New User");
			return i;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int editUser(User obj) {
		try {
			pstmt2.setString(1, obj.getCustName());
			pstmt2.setString(2, obj.getCity());
			pstmt2.setString(3, obj.getEmail());
			pstmt2.setString(4, obj.getPassword());
			pstmt2.setInt(5, obj.getUserid());
			
			int i=pstmt2.executeUpdate();
			System.out.println("Updated.....");
			return i;
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int deleteUser(User obj) {
		
		try {
			pstmt3.setInt(1,obj.getUserid());
			int i=pstmt3.executeUpdate();
			return i;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
		
	}
	
	public void cleanUp() throws SQLException
	{
		System.out.println("---userDal cleanup-----");
		if(stmt!=null)
			stmt.close();
	}

	
	

}
