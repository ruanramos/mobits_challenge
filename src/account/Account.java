package account;

import java.math.BigDecimal;
import java.util.ArrayList;

import account_operations.Transaction;

public class Account {

	private long accountNumber;
	private String profileType;
	/**
	 * 
	 * The decision of using BigDecimal is to avoid the inaccuracy of float and
	 * double on monetary calculation
	 * 
	 */
	private BigDecimal balance;
	public ArrayList<Transaction> transactions;

	public Account(long accountNumber, String profileType, BigDecimal balance, ArrayList<Transaction> transactions) {
		this.accountNumber = accountNumber;
		this.setProfileType(profileType);
		this.balance = balance;
		this.transactions = transactions;
	}

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

	public String getProfileType() {
		return profileType;
	}

	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}

	/**
	 * Kind of a getter, so that, even with transactions being public, other classes
	 * can't access it directly, only get a copy
	 */
	public ArrayList<Transaction> listTransactions() {
		ArrayList<Transaction> transactionsList = new ArrayList<Transaction>(transactions);
		return transactionsList;
	}

}