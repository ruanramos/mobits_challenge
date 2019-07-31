package account_operations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import account.Account;

public class Statement {

	private LocalDateTime time;
	private BigDecimal value;
	private String description;
	private Account account;

	/**
	 * Used a private constructor, instantiating the class with the static method
	 * generateStatement() for encapsulation
	 */
	private Statement(LocalDateTime time, BigDecimal value, String description, Account account) {
		this.time = time;
		this.value = value;
		this.description = description;
		this.account = account;
	}

	static Statement generateStatement(LocalDateTime time, BigDecimal value, String description, Account account) {

		for (Transaction transaction : account.listTransactions()) {
			System.out.println(transaction.toString()); // TODO Still needs to put parentheses on negative values
		}
		Statement s = new Statement(time, value, description, account);
		return s;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
