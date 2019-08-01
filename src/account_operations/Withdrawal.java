package account_operations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import account.Account;
import bank_management.BusinessRules;
import bank_management.BusinessRules.TransactionTypes;
import bank_management.BusinessRules.profileTypes;
import database.AccountStorage;
import database.TransactionStorage;

public class Withdrawal extends Transaction {

	// TODO treat concurrency problems

	private long accountNumber;

	public Withdrawal(LocalDateTime time, BigDecimal value, String description, long accountNumber) {
		super(time, value, description);
		this.type = BusinessRules.TransactionTypes.WITHDRAWAL.ordinal();
		this.accountNumber = accountNumber;
	}

	/**
	 * Used the static method makeWithdrawal() because the class will only be
	 * instantiated when the operation is completed,
	 */
	// TODO treat errors better
	public static Withdrawal makeWithdrawal(LocalDateTime time, BigDecimal value, String description,
			long accountNumber) {
		Withdrawal w = new Withdrawal(time, value, description, accountNumber);

		Account account = w.getAccount();

		int profileType = account.getAccountHolder().getProfileType();
		BigDecimal balance = account.getBalance();
		boolean enoughFunds = Account.checkEnoughFunds(balance, value);

		if (profileType == profileTypes.NORMAL.ordinal()) {
			if (enoughFunds) {
				Account.subtractBalance(account, value);
				System.out.println(String.format(
						"Withdrawal of %sR$ from account number %d finished successfully.\nNew balance: %.2fR$.",
						value.toString(), account.getAccountNumber(), account.getBalance()));
			} else {
				System.out.println(String.format("%s %o.",
						"Error: Not enough balance for the operation on account number ", account.getAccountNumber()));
			}
		} else if (profileType == profileTypes.VIP.ordinal()) {
			if (enoughFunds) {
				Account.subtractBalance(account, value);
				System.out.println(String.format(
						"Withdrawal of %sR$ from account number %d finished successfully.\nNew balance: %fR$.",
						value.toString(), account.getAccountNumber(), account.getBalance()));
			} else {
				/**
				 * Negative balance treatment for VIP account holder
				 */
				Account.subtractBalance(account, value);
				System.out.println(String.format(
						"Withdrawal of %sR$ from account number %d finished successfully.\nNew balance: %fR$. You are beeing taxed, negative balance!",
						value.toString(), account.getAccountNumber(), account.getBalance()));
				BigDecimal currentBalance;
				while (account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
					currentBalance = account.getBalance();
					Transaction.waitAndApplyTax(account, BusinessRules.getTaxapplicationInterval(), currentBalance,
							BusinessRules.getNegativebalanceTax());
				}
			}
		}

		w.getAccount().transactions.add(w);
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		String formattedDateTime = time.format(formatter);

		TransactionStorage.insertTransaction(w.getId(), accountNumber, formattedDateTime, value, description,
				TransactionTypes.TRANSFER.toString());
		return w;
	}

	@Override
	public String toString() {
		BigDecimal value = getValue();
		Account account = getAccount();
		BigDecimal balance = account.getBalance();

		return String.format(
				"Transaction Type: Withdrawal\nDate: %s\nValue: %fR$\nDescription: %s\nAccount number: %d\nBalance: %fR$ - %fR$ = %fR$\n",
				getTime().toString(), value, getDescription(), account.getAccountNumber(), balance, value,
				balance.subtract(value));
	}

	/**
	 * Getters and setters
	 */
	public Account getAccount() {
		AccountStorage accStorage = new AccountStorage();
		return accStorage.selectAccount(accountNumber);
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
}
