package com.chl.studentdb.utils;

import java.sql.*;

public class MysqlConnection {

	// private static final String driverName = ReadProperties.getKey("jdbcDriver");
	// private static final String urlToConnect = ReadProperties.getKey("jdbcUrl");
	// private static final String USER = ReadProperties.getKey("jdbcUsername");
	// private static final String PASSWORD = ReadProperties.getKey("jdbcPassword");
	private static final String driverName = "com.mysql.jdbc.Driver";
	private static final String urlToConnect = "jdbc:mysql://localhost:3306/runoob?useUnicode=true&characterEncoding=utf-8&useSSL=true";
	private static final String USER = "root";
	private static final String PASSWORD = "0000";

	static {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("The Driver loaded error,please contact to your Software Designer!");
		}
	}

	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(urlToConnect, USER, PASSWORD);
		} catch (SQLException e) {
			System.err.println("open the connection failure");
			e.printStackTrace();
		}
		return connection;
	}

	public static void closeConnection(Connection connection, PreparedStatement pStatement, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (pStatement != null) {
				pStatement.close();
			}

			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			System.err.println("close the connection failure");
			e.printStackTrace();
		}
	}

	public static int updateSql(String sql) {
		Connection connection = MysqlConnection.getConnection();
		PreparedStatement ps = null;
		int update = 0;
		try {
			ps = connection.prepareStatement(sql);
			update = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MysqlConnection.closeConnection(connection, ps, null);
		}
		return update;
	}

	public static ResultSet selectSql(String sql) {
		Connection connection = MysqlConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
