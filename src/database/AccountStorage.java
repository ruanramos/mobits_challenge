package database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import account.Account;

public class AccountStorage {
	/**
	 * Insert a new row into the warehouses table
	 *
	 * @param number    account number, primary key
	 * @param balance   initial balance on account creation
	 * @param holder_id id of the owner of the account
	 */
	public void insertAccount(long number, BigDecimal balance, int holder_id) {
		String sql = "INSERT INTO Accounts(number, balance, holder_id) VALUES(?,?,?)";

		try (Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, number);
			pstmt.setBigDecimal(2, balance);
			pstmt.setInt(3, holder_id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * select all rows in the Accounts table
	 */
	public ArrayList<Account> selectAllAccounts() {
		String sql = "SELECT * FROM Accounts";
		ArrayList<Account> accounts = new ArrayList<Account>();

		try (Connection conn = SQLiteConnection.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			// loop through the result set
			while (rs.next()) {
				Account acc = new Account(rs.getLong("number"), rs.getInt("holder_id"), rs.getBigDecimal("balance"));
				accounts.add(acc);
			}
			return accounts;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
