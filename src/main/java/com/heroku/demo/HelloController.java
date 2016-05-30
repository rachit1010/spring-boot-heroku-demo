package com.heroku.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HelloController {

	@RequestMapping("/")
	public String getWelcomeMsg() {
		return "Hello its from retro consumable app";
	}

	@RequestMapping("/getUser", produces = "application/json")
	@ResponseBody
	public CustomUser getUserData() {
		CustomUser usr = new CustomUser();
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			stmt = c.createStatement();
			String sql = "Select * from USER";
			System.out.println("Statement being executed is:" + sql);
			ResultSet rs = stmt.executeQuery("SELECT * FROM user;");
			while (rs.next()) {
				usr = new CustomUser();
				usr.setID(rs.getInt("ID"));
				usr.setUserName(rs.getString("NAME"));
				usr.setStart_time(rs.getString("START_TIME"));
				usr.setEnd_time(rs.getString("END_TIME"));
			}
			rs.close();
			stmt.close();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return usr;
	}

	@RequestMapping(path="/addUser")
	public void setUser(@RequestBody CustomUser usr)
	{
		System.out.println(usr.getID()+" "+usr.getUserName());
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			stmt = c.createStatement();
			String sql = "Insert into user values("
			+usr.getID()
			+",'"+usr.getUserName()+"'"
			+",'"+usr.getPwd()+"'"
			+",'"+usr.getStart_time()+"'"
			+",'"+usr.getEnd_time()+"')";
			System.out.println("Statement being executed is:" + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	
	@RequestMapping(path = "/getUsers", produces = "application/json")
	@ResponseBody
	public List<CustomUser> getUsers() {
		List<CustomUser> users = new ArrayList<CustomUser>();
		CustomUser usr;
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			stmt = c.createStatement();
			String sql = "Select * from USER";
			System.out.println("Statement being executed is:" + sql);
			ResultSet rs = stmt.executeQuery("SELECT * FROM user;");
			while (rs.next()) {
				usr = new CustomUser();
				usr.setID(rs.getInt("ID"));
				usr.setUserName(rs.getString("NAME"));
				usr.setStart_time(rs.getString("START_TIME"));
				usr.setEnd_time(rs.getString("END_TIME"));
				users.add(usr);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return users;
	}
	
	
}
