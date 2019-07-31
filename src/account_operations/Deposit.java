package account_operations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import account.Account;
import bank_management.BusinessRules;
import database.AccountStorage;

public class Deposit extends Transaction {

	// TODO treat concurrency problems

	private long destinationAccountNumber;

	public Deposit(int id, LocalDateTime time, BigDecimal value, String description, long destinationAccountNumber) {
		super(id, time, value, description);
		this.type = BusinessRules.TransactionTypes.DEPOSIT.ordinal();
		this.setDestinationAccountNumber(destinationAccountNumber);
	}

	/**
	 * Used the static method makeDeposit() because the class will only be
	 * instantiated when the operation is completed,
	 */
	// TODO treat errors better
	static Deposit makeDeposit(int id, LocalDateTime time, BigDecimal value, String description,
			long destinationAccountNumber) {
		Deposit d = new Deposit(id, time, value, description, destinationAccountNumber);
		Account destinationAccount = d.getDestinationAccount();

		try {
			Account.addBalance(destinationAccount, value);
			System.out.println(
					String.format("Deposit of %oR$ to account number %o finished successfully.\nNew balance: %o",
							value.toString(), destinationAccount.getAccountNumber(), destinationAccount.getBalance()));
		} catch (Exception e) {
			System.out.println(String.format("Error: Could not complete the deposit"));
		}

		d.getDestinationAccount().transactions.add(d);
		return d;
	}

	@Override
	public String toString() {
		BigDecimal value = getValue();
		Account destAccount = getDestinationAccount();
		BigDecimal destBalance = destAccount.getBalance();

		return String.format(
				"Transaction Type: Deposit\nDate: %o\nValue: %oR$\nDescription: %o\nAccount number: %o\nAccount Balance: %oR$ + %oR$ = %oR$\n",
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
