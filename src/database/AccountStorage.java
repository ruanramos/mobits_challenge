package database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

	public static void main(String[] args) {
		AccountStorage test = new AccountStorage();

		test.insertAccount(2, BigDecimal.ZERO, 5);
	}
}
