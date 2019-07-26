package operations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import account.Account;

public class Transfer extends Transaction {

	private Account destinationAccount;
	private Account originAccount;
	private BigDecimal maxTransferValue;
	private BigDecimal normalTransferTax;
	private BigDecimal VipTransferTax;

	public Transfer(LocalDate date, LocalTime time, BigDecimal value, String description, Account destinationAccount,
			Account originAccount) {
		super(date, time, value, description);
		this.destinationAccount = destinationAccount;
		this.originAccount = originAccount;
		this.maxTransferValue = new BigDecimal("1000");
		this.normalTransferTax = new BigDecimal("8");
		this.VipTransferTax = new BigDecimal("0.008");
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
