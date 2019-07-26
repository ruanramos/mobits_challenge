import java.math.BigDecimal;

public class Account {

	private long accountNumber;

	/**
	 * 
	 * The decision of using BigDecimal is to avoid the inaccuracy of float and
	 * double on monetary calculation
	 * 
	 */
	private BigDecimal balance;

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

}