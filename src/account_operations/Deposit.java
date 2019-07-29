package account_operations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import account.Account;

public class Deposit extends Transaction {

	// TODO treat concurrency problems
	
	private Account destinationAccount;

	public Deposit(LocalDate date, LocalTime time, BigDecimal value, String description, Account destinationAccount) {
		super(date, time, value, description);
		this.destinationAccount = destinationAccount;
	}
}
