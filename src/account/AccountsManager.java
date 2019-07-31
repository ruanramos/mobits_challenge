package account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

import bank_management.BusinessRules;
import bank_management.BusinessRules.profileTypes;
import database.AccountHolderStorage;
import database.AccountStorage;

public class AccountsManager {

	private static int incrementalHolderId = 0;

	static AccountHolder createAccountHolder(profileTypes profileType) {
		int id = getIncrementalHolderId();
		String password = generateSimplePassword(BusinessRules.getLenPassword());

		AccountHolder accHolder = new AccountHolder(profileType.ordinal(), password, id);

		AccountHolderStorage accHolderStorage = new AccountHolderStorage();
		accHolderStorage.insertAccountHolder(id, password, profileType.ordinal());
		setIncrementalHolderId(id + 1);
		return accHolder;
	}

	/**
	 * initial balance = 0
	 */
	static Account createAccount(AccountHolder accountHolder) {
		long accountNumber = generateAccountNumber(BusinessRules.getLenAccountnumber());
		Account acc = new Account(accountNumber, accountHolder.getId()); // initial balance = 0

		/**
		 * insertion on bd
		 */
		AccountStorage accStorage = new AccountStorage();
		accStorage.insertAccount(accountNumber, BigDecimal.ZERO, accountHolder.getId());
		return acc;
	}

	static Account createAccount(int accountHolderId) {
		long accountNumber = generateAccountNumber(BusinessRules.getLenAccountnumber());
		Account acc = new Account(accountNumber, accountHolderId); // initial balance = 0

		/**
		 * insertion on bd
		 */
		AccountStorage accStorage = new AccountStorage();
		accStorage.insertAccount(accountNumber, BigDecimal.ZERO, accountHolderId);
		return acc;
	}

	/**
	 * given initial balance
	 */
	static Account createAccount(BigDecimal startingBalance, AccountHolder accountHolder) {
		long accountNumber = generateAccountNumber(BusinessRules.getLenAccountnumber());
		Account acc = new Account(accountNumber, accountHolder.getId(), startingBalance);

		/**
		 * insertion on bd
		 */
		AccountStorage accStorage = new AccountStorage();
		accStorage.insertAccount(accountNumber, startingBalance, accountHolder.getId());

		return acc;
	}

	static Account createAccount(BigDecimal startingBalance, int accountHolderId) {
		long accountNumber = generateAccountNumber(BusinessRules.getLenAccountnumber());
		Account acc = new Account(accountNumber, accountHolderId, startingBalance);

		/**
		 * insertion on bd
		 */
		AccountStorage accStorage = new AccountStorage();
		accStorage.insertAccount(accountNumber, startingBalance, accountHolderId);

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
	 * It's a getter, but querying from bd
	 */
	public ArrayList<Account> listExistingAccounts() {
		AccountStorage accStorage = new AccountStorage();
		return accStorage.selectAllAccounts();
	}

	/**
	 * It's a getter, but querying from bd
	 */
	public ArrayList<AccountHolder> listExistingAccountHolders() {
		AccountHolderStorage accHoldersStorage = new AccountHolderStorage();
		return accHoldersStorage.selectAllAccountHolders();
	}

	public static int getIncrementalHolderId() {
		return incrementalHolderId;
	}

	public static void setIncrementalHolderId(int incrementalHolderId) {
		AccountsManager.incrementalHolderId = incrementalHolderId;
	}
}
