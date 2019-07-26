package operations;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import account.Account;

public class Withdrawal extends Transaction {

	private Account account;
	private BigDecimal tax;

	
	/**
	 * Used a private constructor, instantiating the class with the static method 
	 * makeWithdrawal() for encapsulation
	 */
	private Withdrawal(LocalDate date, LocalTime time, BigDecimal value, String description, Account account) {
		super(date, time, value, description);
		this.account = account;
		this.tax = new BigDecimal("0.1");
	}

	static Withdrawal makeWithdrawal(LocalDate date, LocalTime time, BigDecimal value, String description,
			Account account) {
		Withdrawal w = new Withdrawal(date, time, value, description, account);

		String profileType = account.getProfileType();
		BigDecimal balance = account.getBalance();
		int balanceStatusAfter = w.checkEnoughFounds(balance, value);

		if (profileType == "Normal") {
			if (balanceStatusAfter >= 0) {
				w.subtractBalance(account, value);
			} else {
				System.out.println(String.format("%s %o.",
						"Error: Not enough balance for the operation on account number ", account.getAccountNumber()));
			}
		} else if (profileType == "VIP") {
			if (balanceStatusAfter >= 0) {
				w.subtractBalance(account, value);
			} else {
				BigDecimal currentBalance = account.getBalance();
				while (currentBalance.compareTo(new BigDecimal("0")) < 0) {
					w.waitAndApplyTax(1, currentBalance, w.getTax());
				}
			}
		}
		return w;
	}

	/**
	 * compareTo returns 0 if equals, 1 if grater than value, -1 if lesser than
	 * value
	 */
	private int checkEnoughFounds(BigDecimal balance, BigDecimal value) {
		return balance.compareTo(value);
	}

	private void subtractBalance(Account account, BigDecimal value) {
		try {
			BigDecimal newBalance = account.getBalance().subtract(value);
			account.setBalance(newBalance);
			System.out.println("Operation successfully completed. New balance: " + newBalance);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}

	private void waitAndApplyTax(int minutes, BigDecimal balance, BigDecimal tax) {
		try {
			Thread.sleep(60 * 1000 * minutes);
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
		this.tax = tax;
	}

}
