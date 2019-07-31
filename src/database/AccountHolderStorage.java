package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import account.AccountHolder;

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
	public void insertAccountHolder(int id, String passwordHash, int profileType) {
		String sql = "INSERT INTO AccountHolders(id, password, profile_type) VALUES(?,?,?)";

		try (Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			pstmt.setString(2, passwordHash);
			pstmt.setInt(3, profileType);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * select all rows in the AccountHolders table
	 */
	public ArrayList<AccountHolder> selectAllAccountHolders() {
		String sql = "SELECT * FROM AccountHolders";
		ArrayList<AccountHolder> accountHolders = new ArrayList<AccountHolder>();

		try (Connection conn = SQLiteConnection.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			// loop through the result set
			while (rs.next()) {
				AccountHolder accHolder = new AccountHolder(rs.getInt("profile_type"), rs.getString("password"),
						rs.getInt("id"));
				accountHolders.add(accHolder);
			}
			return accountHolders;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public AccountHolder selectAccountHolder(int holderId) {
		String sql = "SELECT * " + "FROM AccountHolders WHERE id = ?";
		AccountHolder accHolder = null;

		try (Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, holderId);
			ResultSet rs = pstmt.executeQuery();

			// loop through the result set
			while (rs.next()) {
				accHolder = new AccountHolder(rs.getInt("profile_type"), rs.getString("password"), rs.getInt("id"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return accHolder;
	}

	public static void main(String[] args) {
		AccountHolderStorage test = new AccountHolderStorage();
		test.insertAccountHolder(4, "NAGISNGO", 1);
		test.insertAccountHolder(5, "FNOAIS", 0);
		for (AccountHolder a : test.selectAllAccountHolders()) {
			System.out.println(a.getPassword());
		}
	}
}
