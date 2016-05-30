package com.heroku.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RetroConsumableApplication {

	public static void main(String[] args) {
		runPreRequisites();
		SpringApplication.run(RetroConsumableApplication.class, args);
	}

	static void runPreRequisites() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			// 1.Created db
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			// 2.Drop existing table
			stmt = c.createStatement();
			String sql = "Drop table if exists user";
			stmt.execute(sql);
			stmt.close();
			// 3.Created table
			stmt = c.createStatement();
			sql = "CREATE TABLE USER ("
			+ "ID INT PRIMARY KEY NOT NULL,"
			+ "NAME  TEXT,"
			+ "pwd TEXT,"
			+ "START_TIME TEXT,"
			+ "END_TIME TEXT)";
			System.out.println("Statement being executed is:" + sql);
			stmt.executeUpdate(sql);
			stmt.close();
			// 3.Inserted one record
			stmt = c.createStatement();
			sql = "insert into user values(10,'rachit','rachit','2010-05-28T15:36:56.200','2010-05-28T15:36:56.200')";
			System.out.println("Statement being executed is:" + sql);
			stmt.executeUpdate(sql);
			sql = "insert into user values(15,'aayush','aayush','2010-05-28T15:36:56.200','2010-05-28T15:36:56.200')";
			System.out.println("Statement being executed is:" + sql);
			stmt.executeUpdate(sql);
			// c.commit();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}
}
