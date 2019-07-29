package account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

public class AccountsManager {

	private ArrayList<Account> existingAccounts;
	private final int lenAccountNumber = 8;
	private final int lenPassword = 6;

	private enum profileTypes {
		NORMAL, VIP;
	}

	private AccountHolder createAccountHolder(profileTypes profileType) {
		String password = generateSimplePassword(lenPassword);
		AccountHolder accHolder = new AccountHolder(profileType.toString(), password);
		return accHolder;
	}

	/**
	 * initial balance = 0
	 */
	private Account createAccount(AccountHolder accountHolder) {
		long accountNumber = generateAccountNumber(lenAccountNumber);
		return new Account(accountNumber, accountHolder);
	}
	
	/**
	 * given initial balance
	 */
	private Account createAccount(BigDecimal startingBalance, AccountHolder accountHolder) {
		long accountNumber = generateAccountNumber(lenAccountNumber);
		return new Account(accountNumber, accountHolder, startingBalance);
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
	 * Just a simple password generator for creating an accountHolder
	 */
	public String generateSimplePassword(int length) {

		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = length;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();

		return generatedString;
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
