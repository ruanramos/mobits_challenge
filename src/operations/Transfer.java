package operations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import account.Account;

public class Transfer extends Transaction {

	private Account originAccount;
	private Account destinationAccount;
	private BigDecimal maxTransferValue;
	private BigDecimal normalTransferTax;
	private BigDecimal VipTransferTax;

	/**
	 * Used a private constructor, instantiating the class with the static method
	 * makeWithdrawal() for encapsulation
	 */
	private Transfer(LocalDate date, LocalTime time, BigDecimal value, String description, Account originAccount,
			Account destinationAccount) {
		super(date, time, value, description);
		this.originAccount = originAccount;
		this.destinationAccount = destinationAccount;
		this.maxTransferValue = new BigDecimal("1000");
		this.normalTransferTax = new BigDecimal("8");
		this.VipTransferTax = new BigDecimal("0.008");
	}

	static Transfer makeTransfer(LocalDate date, LocalTime time, BigDecimal value, String description,
			Account originAccount, Account destinationAccount) {
		Transfer t = new Transfer(date, time, value, description, originAccount, destinationAccount);
		
		return t;
	}

	public Account getDestinationAccount() {
		return destinationAccount;
	}

	public void setDestinationAccount(Account destinationAccount) {
		this.destinationAccount = destinationAccount;
	}

	public Account getOriginAccount() {
		return originAccount;
	}

	public void setOriginAccount(Account originAccount) {
		this.originAccount = originAccount;
	}

	public BigDecimal getMaxTransferValue() {
		return maxTransferValue;
	}

	public void setMaxTransferValue(BigDecimal maxTransferValue) {
		this.maxTransferValue = maxTransferValue;
	}

	public BigDecimal getNormalTransferTax() {
		return normalTransferTax;
	}

	public void setNormalTransferTax(BigDecimal normalTransferTax) {
		this.normalTransferTax = normalTransferTax;
	}

	public BigDecimal getVipTransferTax() {
		return VipTransferTax;
	}

	public void setVipTransferTax(BigDecimal vipTransferTax) {
		VipTransferTax = vipTransferTax;
	}

}
