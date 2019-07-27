package operations;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import account.Account;

/**
 * This class will be the base class for any transaction, 
 * such as Withdrawal or Transfer
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
	public boolean checkEnoughFounds(BigDecimal balance, BigDecimal value) {
		int enoughFounds = balance.compareTo(value);
		if (enoughFounds < 0) return false;
		return true;
	}

	public void subtractBalance(Account account, BigDecimal value) {
		try {
			BigDecimal newBalance = account.getBalance().subtract(value);
			account.setBalance(newBalance);
			System.out.println("Operation successfully completed. New balance: " + newBalance);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void addBalance(Account account, BigDecimal value) {
		try {
			BigDecimal newBalance = account.getBalance().add(value);
			account.setBalance(newBalance);
			System.out.println("Operation successfully completed. New balance: " + newBalance);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}

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
