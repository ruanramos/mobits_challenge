package account;

import java.util.ArrayList;
import java.util.Random;

public class AccountsManager {

	private ArrayList<Account> existingAccounts;

	public void createAccount() {

	}

	private long generateAccountNumber(int length) {
		String number = "";
		Random rand = new Random();
		for (int i = 0; i < length; i++) {
			number += rand.nextInt(10);
		}
		return Long.parseLong(number);
	}

	/**
	 * Kind of a getter, so that, even with transactions being public, other classes
	 * can't access it directly, only get a copy
	 */
	public ArrayList<Account> listExistingAccounts() {
		ArrayList<Account> accounts = new ArrayList<Account>(existingAccounts);
		return accounts;
	}
}
