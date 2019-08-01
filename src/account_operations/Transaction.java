package account_operations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import account.Account;

/**
 * This class will be the base class for any transaction, such as Withdrawal or
 * Transfer
 */

public abstract class Transaction {

	private LocalDateTime time;
	private BigDecimal value;
	private String description;
	protected int type;
	private int id;

	public static int idCounter = 0;

	public Transaction(LocalDateTime time, BigDecimal value, String description) {
		this.id = nextId();
		this.time = time;
		this.value = value;
		this.description = description;
	}

	private static synchronized int nextId() {
		idCounter++;
		return idCounter;
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

	@Override
	public abstract String toString();

	/**
	 * Getters and setters
	 */

	public BigDecimal getValue() {
		return value;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
