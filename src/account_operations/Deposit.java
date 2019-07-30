package account_operations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import account.Account;

public class Deposit extends Transaction {

	// TODO treat concurrency problems

	private Account destinationAccount;

	/**
	 * Used a private constructor, instantiating the class with the static method
	 * makeDeposit() for encapsulation
	 */
	private Deposit(LocalDate date, LocalTime time, BigDecimal value, String description, Account destinationAccount) {
		super(date, time, value, description);
		this.destinationAccount = destinationAccount;
	}

	// TODO treat errors better
	static Deposit makeDeposit(LocalDate date, LocalTime time, BigDecimal value, String description,
			Account destinationAccount) {
		Deposit d = new Deposit(date, time, value, description, destinationAccount);

		try {
			Account.addBalance(destinationAccount, value);
			System.out.println(
					String.format("Deposit of %oR$ to account number %o finished successfully.\nNew balance: %o",
							value.toString(), destinationAccount.getAccountNumber(), destinationAccount.getBalance()));
		} catch (Exception e) {
			System.out.println(String.format("Error: Could not complete the deposit"));
		}

		d.destinationAccount.transactions.add(d);
		return d;
	}
}
