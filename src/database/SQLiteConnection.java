package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {

	/**
	 * Connect to a sample database
	 *
	 * @param fileName the database file name
	 */
	public static void createNewDatabase(String fileName) {

		String url = "jdbc:sqlite:" + fileName;

		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("A new database has been created.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Connect to account_management database, that stores accounts, account holders
	 * and transactions information
	 */
	public static Connection connect() {
		Connection conn = null;
		String url = "jdbc:sqlite:account_management.db";
		try {
			conn = DriverManager.getConnection(url);
			System.out.println("Connection to SQLite has been established.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
}
