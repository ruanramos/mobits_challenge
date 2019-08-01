package account;

import java.math.BigDecimal;
import java.util.ArrayList;

import account_operations.Transaction;
import database.AccountHolderStorage;
import database.AccountStorage;

public class Account {

	private long accountNumber;
	private int accountHolderId;
	/**
	 * 
	 * The decision of using BigDecimal is to avoid the inaccuracy of float and
	 * double on monetary calculation. Every value that can be or will be used on
	 * monetary calculation is set to BigDecimal
	 * 
	 */
	private BigDecimal balance;
	public ArrayList<Transaction> transactions;

	private static AccountStorage accStorage = new AccountStorage();

	public Account(long accountNumber, int accountHolderId) {
		this.accountNumber = accountNumber;
		this.accountHolderId = accountHolderId;

		this.balance = BigDecimal.ZERO;
		this.transactions = new ArrayList<Transaction>();
	}

	public Account(long accountNumber, int accountHolderId, BigDecimal balance) {
		this.accountNumber = accountNumber;
		this.setAccountHolderId(accountHolderId);
		this.balance = balance;

		this.transactions = new ArrayList<Transaction>();
	}

	/**
	 * compareTo returns 0 if equals, 1 if grater than value, -1 if lesser than
	 * value
	 */
	public static boolean checkEnoughFunds(BigDecimal balance, BigDecimal value) {
		int enoughFunds = balance.compareTo(value);
		if (enoughFunds < 0)
			return false;
		return true;
	}

	// TODO treat errors better
	public static void subtractBalance(Account account, BigDecimal value) {
		try {
			BigDecimal newBalance = account.getBalance().subtract(value);
			account.setBalance(newBalance);
			accStorage.updateAccountBalance(account.getAccountNumber(), newBalance);
		} catch (Exception e) {
			System.out.println("Error: could not complete operation");
		}
	}

	// TODO treat errors better
	public static void addBalance(Account account, BigDecimal value) {
		try {
			BigDecimal newBalance = account.getBalance().add(value);
			account.setBalance(newBalance);
			accStorage.updateAccountBalance(account.getAccountNumber(), newBalance);
		} catch (Exception e) {
			System.out.println("Error: could not complete operation");
		}
	}

	/**
	 * Getters and setters
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * Kind of a getter, so that, even with transactions being public, other classes
	 * can't access it directly, only get a copy
	 */
	public ArrayList<Transaction> listTransactions() {
		ArrayList<Transaction> transactionsList = new ArrayList<Transaction>(transactions);
		return transactionsList;
	}

	/**
	 * Getter and Setter of account holder queries from the db using acc holder id
	 */

	public AccountHolder getAccountHolder() {
		AccountHolderStorage accHolderStorage = new AccountHolderStorage();
		return accHolderStorage.selectAccountHolder(getAccountHolderId());
	}

	public int getAccountHolderId() {
		return accountHolderId;
	}

	public void setAccountHolderId(int accountHolderId) {
		this.accountHolderId = accountHolderId;
	}

}