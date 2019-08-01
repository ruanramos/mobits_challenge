package account_operations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import account.Account;
import bank_management.BusinessRules;
import bank_management.BusinessRules.TransactionTypes;
import database.AccountStorage;
import database.TransactionStorage;

public class Deposit extends Transaction {

	// TODO treat concurrency problems

	private long destinationAccountNumber;

	public Deposit(LocalDateTime time, BigDecimal value, String description, long destinationAccountNumber) {
		super(time, value, description);
		this.type = BusinessRules.TransactionTypes.DEPOSIT.ordinal();
		this.setDestinationAccountNumber(destinationAccountNumber);
	}

	/**
	 * Used the static method makeDeposit() because the class will only be
	 * instantiated when the operation is completed,
	 */
	// TODO treat errors better
	public static Deposit makeDeposit(LocalDateTime time, BigDecimal value, String description,
			long destinationAccountNumber) {
		Deposit d = new Deposit(time, value, description, destinationAccountNumber);
		Account destinationAccount = d.getDestinationAccount();

		try {
			Account.addBalance(destinationAccount, value);
			System.out.println(
					String.format("Deposit of %sR$ to account number %d finished successfully.\nNew balance: %f",
							value.toString(), destinationAccount.getAccountNumber(), destinationAccount.getBalance()));
		} catch (Exception e) {
			System.out.println(String.format("Error: Could not complete the deposit"));
		}

		d.getDestinationAccount().transactions.add(d);
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		String formattedDateTime = time.format(formatter);

		TransactionStorage.insertTransaction(d.getId(), destinationAccountNumber, formattedDateTime, value, description,
				TransactionTypes.DEPOSIT.toString());
		return d;
	}

	@Override
	public String toString() {
		BigDecimal value = getValue();
		Account destAccount = getDestinationAccount();
		BigDecimal destBalance = destAccount.getBalance();

		return String.format(
				"Transaction Type: Deposit\nDate: %s\nValue: %fR$\nDescription: %s\nAccount number: %d\nAccount Balance: %fR$ + %fR$ = %fR$\n",
				getTime().toString(), value, getDescription(), destAccount.getAccountNumber(), destBalance, value,
				destBalance.add(value));
	}

	/**
	 * Getters and Setters
	 */

	public Account getDestinationAccount() {
		AccountStorage accStorage = new AccountStorage();
		return accStorage.selectAccount(getDestinationAccountNumber());
	}

	public long getDestinationAccountNumber() {
		return destinationAccountNumber;
	}

	public void setDestinationAccountNumber(long destinationAccountNumber) {
		this.destinationAccountNumber = destinationAccountNumber;
	}
}
