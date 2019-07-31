package account_operations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import account.Account;
import bank_management.BusinessRules.profileTypes;
import bank_management.BusinessRules;

public class Withdrawal extends Transaction {

	// TODO treat concurrency problems

	private Account account;

	/**
	 * Used a private constructor, instantiating the class with the static method
	 * makeWithdrawal() for encapsulation
	 */
	private Withdrawal(LocalDate date, LocalTime time, BigDecimal value, String description, Account account) {
		super(date, time, value, description);
		this.account = account;
	}

	// TODO treat errors better
	static Withdrawal makeWithdrawal(LocalDate date, LocalTime time, BigDecimal value, String description,
			Account account) {

		profileTypes profileType = account.getAccountHolder().getProfileType();
		BigDecimal balance = account.getBalance();
		boolean enoughFounds = Account.checkEnoughFounds(balance, value);

		if (profileType == profileTypes.NORMAL) {
			if (enoughFounds) {
				Account.subtractBalance(account, value);
				System.out.println(String.format(
						"Withdrawal of %oR$ from account number %o finished successfully.\nNew balance: %oR$.",
						value.toString(), account.getAccountNumber(), account.getBalance()));
			} else {
				System.out.println(String.format("%s %o.",
						"Error: Not enough balance for the operation on account number ", account.getAccountNumber()));
			}
		} else if (profileType == profileTypes.VIP) {
			if (enoughFounds) {
				Account.subtractBalance(account, value);
				System.out.println(String.format(
						"Withdrawal of %oR$ from account number %o finished successfully.\nNew balance: %oR$.",
						value.toString(), account.getAccountNumber(), account.getBalance()));
			} else {
				/**
				 * Negative balance treatment for VIP account holder
				 */
				Account.subtractBalance(account, value);
				System.out.println(String.format(
						"Withdrawal of %oR$ from account number %o finished successfully.\nNew balance: %oR$. You are beeing taxed, negative balance!",
						value.toString(), account.getAccountNumber(), account.getBalance()));
				BigDecimal currentBalance;
				while (account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
					currentBalance = account.getBalance();
					Transaction.waitAndApplyTax(account, BusinessRules.getTaxapplicationInterval(), currentBalance,
							BusinessRules.getNegativebalanceTax());
				}
			}
		}
		Withdrawal w = new Withdrawal(date, time, value, description, account);
		w.getAccount().transactions.add(w);
		return w;
	}

	@Override
	public String toString() {
		BigDecimal value = getValue();
		Account account = getAccount();
		BigDecimal balance = account.getBalance();

		return String.format(
				"Transaction Type: Withdrawal\nDate: %o\nHour: %o\nValue: %o\nDescription: %o\nAccount number: %o\nBalance: %o - %o = %o\n",
				getDate(), getTime(), value, getDescription(), account.getAccountNumber(), balance, value,
				balance.subtract(value));
	}

	/**
	 * Getters and setters
	 */
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
