package account_operations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import account.Account;

public class Statement extends Transaction {

	private Account account;

	private Statement(LocalDate date, LocalTime time, BigDecimal value, String description, Account account) {
		super(date, time, value, description);
		this.account = account;
	}

	static Statement generateStatement(LocalDate date, LocalTime time, BigDecimal value, String description,
			Account account) {

		BigDecimal balance = account.getBalance();

		for (Object transaction : account.listTransactions()) {
			System.out.println(transaction.toString());
		}
		Statement s = new Statement(date, time, value, description, account);
		s.getAccount().transactions.add(s);
		return s;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
