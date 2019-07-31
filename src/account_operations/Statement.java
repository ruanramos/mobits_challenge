package account_operations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import account.Account;

public class Statement {

	private LocalDate date;
	private LocalTime time;
	private BigDecimal value;
	private String description;
	private Account account;

	/**
	 * Used a private constructor, instantiating the class with the static method
	 * generateStatement() for encapsulation
	 */
	private Statement(LocalDate date, LocalTime time, BigDecimal value, String description, Account account) {
		this.date = date;
		this.time = time;
		this.value = value;
		this.description = description;
		this.account = account;
	}

	static Statement generateStatement(LocalDate date, LocalTime time, BigDecimal value, String description,
			Account account) {

		for (Transaction transaction : account.listTransactions()) {
			System.out.println(transaction.toString()); // TODO Still needs to put parentheses on negative values
		}
		Statement s = new Statement(date, time, value, description, account);
		return s;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
