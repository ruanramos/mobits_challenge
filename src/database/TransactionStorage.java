package database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

import bank_management.BusinessRules;

public class TransactionStorage {

	/**
	 * Insert a new row into the warehouses table
	 *
	 * @param id            transaction id
	 * @param accountNumber number of the account involved in operation.
	 *                      Transactions like transfer that involves 2 accounts have
	 *                      2 entries on the table, transactions pk is (id, account)
	 * @param time          time of transaction
	 * @param value         value involved in the transaction
	 * @param description   description of the transaction
	 * @param type          type of transaction
	 */
	public void insertTransaction(int id, long accountNumber, String time, BigDecimal value, String description,
			String type) {
		String sql = "INSERT INTO Transactions(id, account_number, time, value, description, type) VALUES(?,?,?,?,?,?)";

		try (Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			pstmt.setLong(2, accountNumber);
			pstmt.setString(3, time);
			pstmt.setBigDecimal(4, value);
			pstmt.setString(5, description);
			pstmt.setString(6, type);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		TransactionStorage test = new TransactionStorage();

		test.insertTransaction(1, (long) 00003, LocalDateTime.now().toString(), new BigDecimal("20"),
				"testando a transação", BusinessRules.TransactionTypes.DEPOSIT.toString());
	}
}
