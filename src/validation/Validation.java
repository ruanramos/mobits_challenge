package validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.SQLiteConnection;

public class Validation {

	public static boolean validateAccountNumber(long accountNumber) {
		String sql = "SELECT * FROM Accounts WHERE number = ?";

		try (Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, accountNumber);
			ResultSet rs = pstmt.executeQuery();

			if (!rs.next()) {
				System.out.println("Account number not found");
				return false;
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public static boolean validatePassword(long accountNumber, String hashedPassword) {
		String sql = "SELECT * " + "FROM AccountHolders WHERE account_number = ? AND password = ?";

		try (Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, accountNumber);
			pstmt.setString(2, hashedPassword);
			ResultSet rs = pstmt.executeQuery();

			if (!rs.next()) {
				System.out.println("Wrong password");
				return false;
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

}
