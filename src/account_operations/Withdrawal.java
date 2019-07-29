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
		Withdrawal w = new Withdrawal(date, time, value, description, account);

		profileTypes profileType = account.getAccountHolder().getProfileType();
		BigDecimal balance = account.getBalance();
		boolean enoughFounds = Transaction.checkEnoughFounds(balance, value);

		if (profileType == profileTypes.NORMAL) {
			if (enoughFounds) {
				Transaction.subtractBalance(account, value);
			} else {
				System.out.println(String.format("%s %o.",
						"Error: Not enough balance for the operation on account number ", account.getAccountNumber()));
			}
		} else if (profileType == profileTypes.VIP) {
			if (enoughFounds) {
				Transaction.subtractBalance(account, value);
			} else {
				BigDecimal currentBalance = account.getBalance();
				while (currentBalance.compareTo(BigDecimal.ZERO) < 0) {
					Transaction.waitAndApplyTax(account, BusinessRules.getTaxapplicationinterval(), currentBalance,
							BusinessRules.getNegativebalancetax());
				}
			}
		}
		account.transactions.add(w);
		return w;
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
