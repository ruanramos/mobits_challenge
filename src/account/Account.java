package account;

import java.math.BigDecimal;
import java.util.ArrayList;

import account_operations.Transaction;

public class Account {

	private long accountNumber;
	private AccountHolder accountHolder;
	/**
	 * 
	 * The decision of using BigDecimal is to avoid the inaccuracy of float and
	 * double on monetary calculation
	 * 
	 */
	private BigDecimal balance;
	public ArrayList<Transaction> transactions;

	public Account(long accountNumber, AccountHolder accountHolder) {
		this.accountNumber = accountNumber;
		this.accountHolder = accountHolder;

		this.balance = new BigDecimal("0");
		this.transactions = new ArrayList<Transaction>();
	}

	public Account(long accountNumber, AccountHolder accountHolder, BigDecimal balance) {
		this.accountNumber = accountNumber;
		this.accountHolder = accountHolder;
		this.balance = balance;

		this.transactions = new ArrayList<Transaction>();
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

	/**
	 * Kind of a getter, so that, even with transactions being public, other classes
	 * can't access it directly, only get a copy
	 */
	public ArrayList<Transaction> listTransactions() {
		ArrayList<Transaction> transactionsList = new ArrayList<Transaction>(transactions);
		return transactionsList;
	}

	public AccountHolder getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(AccountHolder accountHolder) {
		this.accountHolder = accountHolder;
	}

}