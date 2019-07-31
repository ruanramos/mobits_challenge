package account_operations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import account.Account;
import bank_management.BusinessRules;
import bank_management.BusinessRules.profileTypes;
import database.AccountStorage;

public class Withdrawal extends Transaction {

	// TODO treat concurrency problems

	private long accountNumber;

	public Withdrawal(int id, LocalDateTime time, BigDecimal value, String description, long accountNumber) {
		super(id, time, value, description);
		this.type = BusinessRules.TransactionTypes.WITHDRAWAL.ordinal();
		this.setAccountNumber(accountNumber);
	}

	/**
	 * Used the static method makeWithdrawal() because the class will only be
	 * instantiated when the operation is completed,
	 */
	// TODO treat errors better
	static Withdrawal makeWithdrawal(int id, LocalDateTime time, BigDecimal value, String description,
			long accountNumber) {
		Withdrawal w = new Withdrawal(id, time, value, description, accountNumber);

		Account account = w.getAccount();
		int profileType = account.getAccountHolder().getProfileType();
		BigDecimal balance = account.getBalance();
		boolean enoughFunds = Account.checkEnoughFunds(balance, value);

		if (profileType == profileTypes.NORMAL.ordinal()) {
			if (enoughFunds) {
				Account.subtractBalance(account, value);
				System.out.println(String.format(
						"Withdrawal of %oR$ from account number %o finished successfully.\nNew balance: %oR$.",
						value.toString(), account.getAccountNumber(), account.getBalance()));
			} else {
				System.out.println(String.format("%s %o.",
						"Error: Not enough balance for the operation on account number ", account.getAccountNumber()));
			}
		} else if (profileType == profileTypes.VIP.ordinal()) {
			if (enoughFunds) {
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

		w.getAccount().transactions.add(w);
		return w;
	}

	@Override
	public String toString() {
		BigDecimal value = getValue();
		Account account = getAccount();
		BigDecimal balance = account.getBalance();

		return String.format(
				"Transaction Type: Withdrawal\nDate: %o\nValue: %oR$\nDescription: %o\nAccount number: %o\nBalance: %oR$ - %oR$ = %oR$\n",
				getTime().toString(), value, getDescription(), account.getAccountNumber(), balance, value,
				balance.subtract(value));
	}

	/**
	 * Getters and setters
	 */
	public Account getAccount() {
		AccountStorage accStorage = new AccountStorage();
		return accStorage.selectAccount(getAccountNumber());
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
}
