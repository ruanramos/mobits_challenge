package database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import account_operations.CallManager;
import account_operations.Deposit;
import account_operations.Transaction;
import account_operations.Transfer;
import account_operations.Withdrawal;

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
	public static void insertTransaction(int id, long accountNumber, String time, BigDecimal value, String description,
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

	public static long[] selectAccountsInvolvedInTransaction(int transactionId) {
		String sql = "SELECT account_number " + "FROM Transactions WHERE id = ?";
		long[] accountNumbers = { (long) 0, (long) 0 }; // default initialization
		int index = 0;

		try (Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setLong(1, transactionId);
			ResultSet rs = pstmt.executeQuery();

			// loop through the result set
			while (rs.next()) {
				accountNumbers[index] = rs.getLong("account_number");
				index++;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return accountNumbers;
	}

	public static ArrayList<Transaction> selectTransactionsFromAccount(long accountNumber) {
		String sql = "SELECT * " + "FROM Transactions WHERE account_number = ?";
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();

		try (Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setLong(1, accountNumber);
			ResultSet rs = pstmt.executeQuery();

			// loop through the result set
			while (rs.next()) {
				int transactionType = rs.getInt("type");
				String time = rs.getString("time");
				long accountNumber1 = rs.getLong("account_number");
				int transactionId = rs.getInt("id");
				BigDecimal value = rs.getBigDecimal("value");
				String description = rs.getString("description");

				DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
				LocalDateTime dateTime = LocalDateTime.parse(time, formatter);

				switch (transactionType) {
				case 0:
					Withdrawal w = new Withdrawal(dateTime, value, description, accountNumber1);
					transactions.add(w);
					break;
				case 1:
					Deposit d = new Deposit(dateTime, value, description, accountNumber1);
					transactions.add(d);
					break;
				case 2:
					long[] accounts = selectAccountsInvolvedInTransaction(transactionId);
					// TODO make sure origin account gets queried before destination account
					Transfer t = new Transfer(dateTime, value, description, accounts[0], accounts[1]);
					transactions.add(t);
					break;
				case 3:
					CallManager cm = new CallManager(dateTime, value, description, accountNumber1);
					transactions.add(cm);
					break;
				default:
					break;
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return transactions;
	}
}
