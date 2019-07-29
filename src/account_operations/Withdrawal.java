package account_operations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import account.Account;
import account.AccountsManager.profileTypes;

public class Withdrawal extends Transaction {

	// TODO treat concurrency problems

	private Account account;
	static BigDecimal tax = new BigDecimal("0.1");
	static final int taxApplicationInterval = 1;

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
					w.waitAndApplyTax(taxApplicationInterval, currentBalance, w.getTax());
				}
			}
		}
		account.transactions.add(w);
		return w;
	}

	private void waitAndApplyTax(int minutesInterval, BigDecimal balance, BigDecimal tax) {
		try {
			Thread.sleep(60 * 1000 * minutesInterval);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		BigDecimal currentBalance = account.getBalance();
		account.setBalance(currentBalance.subtract(currentBalance.multiply(tax)));
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		Withdrawal.tax = tax;
	}

}
