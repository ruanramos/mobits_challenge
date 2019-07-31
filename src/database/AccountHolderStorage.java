package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountHolderStorage {

	/**
	 * Insert a new row into the warehouses table
	 *
	 * @param id            account holder id
	 * @param passwordHash  hashed password of the client
	 * @param profileType   VIP or NORMAL, which defines his operations
	 *                      possibilities
	 * @param accountNumber account number of the holder, only one per holder
	 */
	public void insertAccountHolder(int id, String passwordHash, String profileType, long accountNumber) {
		String sql = "INSERT INTO AccountHolders(id, password, profile_type, account_number) VALUES(?,?,?,?)";

		try (Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			pstmt.setString(2, passwordHash);
			pstmt.setString(3, profileType);
			pstmt.setLong(4, accountNumber);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
