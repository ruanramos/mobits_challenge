package account_operations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import account.Account;

/**
 * This class will be the base class for any transaction, such as Withdrawal or
 * Transfer
 */

public abstract class Transaction {

	private LocalDate date;
	private LocalTime time;
	private BigDecimal value;
	private String description;

	public Transaction(LocalDate date, LocalTime time, BigDecimal value, String description) {
		this.date = date;
		this.time = time;
		this.value = value;
		this.description = description;
	}

	/**
	 * compareTo returns 0 if equals, 1 if grater than value, -1 if lesser than
	 * value
	 */
	static boolean checkEnoughFounds(BigDecimal balance, BigDecimal value) {
		int enoughFounds = balance.compareTo(value);
		if (enoughFounds < 0)
			return false;
		return true;
	}

	// TODO treat errors better
	static void subtractBalance(Account account, BigDecimal value) {
		try {
			BigDecimal newBalance = account.getBalance().subtract(value);
			account.setBalance(newBalance);
			System.out.println("Operation successfully completed. New balance: " + newBalance);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}

	// TODO treat errors better
	static void addBalance(Account account, BigDecimal value) {
		try {
			BigDecimal newBalance = account.getBalance().add(value);
			account.setBalance(newBalance);
			System.out.println("Operation successfully completed. New balance: " + newBalance);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}

	static BigDecimal applyFixedTax(BigDecimal value, BigDecimal fixedTax) {
		return value.add(fixedTax);
	}

	static BigDecimal calculatePercentageFee(BigDecimal value, BigDecimal percentageTax) {
		return value.multiply(percentageTax);
	}
	
	static BigDecimal applyPercentageTax(BigDecimal value, BigDecimal percentageTax) {
		BigDecimal multiplier = BigDecimal.ONE.add(percentageTax);
		return value.multiply(multiplier);
	}

	static void waitAndApplyTax(Account account, int minutesInterval, BigDecimal balance, BigDecimal tax) {
		try {
			Thread.sleep(60 * 1000 * minutesInterval);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		BigDecimal currentBalance = account.getBalance();
		if (currentBalance.compareTo(BigDecimal.ZERO) < 0) {
			account.setBalance(currentBalance.subtract(currentBalance.multiply(tax)));
		}
	}

	/**
	 * Getters and setters
	 */
	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
