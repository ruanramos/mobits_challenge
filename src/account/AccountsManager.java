package account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

import bank_management.BusinessRules;
import bank_management.BusinessRules.profileTypes;

public class AccountsManager {

	static ArrayList<Account> existingAccounts;
	static ArrayList<AccountHolder> existingAccountHolders;

	static AccountHolder createAccountHolder(profileTypes profileType) {
		String password = generateSimplePassword(BusinessRules.getLenPassword());
		AccountHolder accHolder = new AccountHolder(profileType, password);
		existingAccountHolders.add(accHolder);
		return accHolder;
	}

	/**
	 * initial balance = 0
	 */
	static Account createAccount(AccountHolder accountHolder) {
		long accountNumber = generateAccountNumber(BusinessRules.getLenAccountnumber());
		Account acc = new Account(accountNumber, accountHolder);
		existingAccounts.add(acc);
		return acc;
	}

	/**
	 * given initial balance
	 */
	static Account createAccount(BigDecimal startingBalance, AccountHolder accountHolder) {
		long accountNumber = generateAccountNumber(BusinessRules.getLenAccountnumber());
		Account acc = new Account(accountNumber, accountHolder, startingBalance);
		existingAccounts.add(acc);
		return acc;
	}

	/**
	 * Just a simple account number generator for creating an account
	 */
	static long generateAccountNumber(int length) {
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
	static String generateSimplePassword(int length) {

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
	 * Getters and setters
	 */

	/**
	 * Kind of a getter, so that, even with existingAccounts being public, other
	 * classes can't access it directly, only get a copy
	 */
	public ArrayList<Account> listExistingAccounts() {
		ArrayList<Account> accounts = new ArrayList<Account>(existingAccounts);
		return accounts;
	}

	/**
	 * Kind of a getter, so that, even with existingAccountHolders being public,
	 * other classes can't access it directly, only get a copy
	 */
	public ArrayList<AccountHolder> listExistingAccountHolders() {
		ArrayList<AccountHolder> accountHolders = new ArrayList<AccountHolder>(existingAccountHolders);
		return accountHolders;
	}
}
