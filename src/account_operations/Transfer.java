package account_operations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import account.Account;

public class Transfer extends Transaction {

	// TODO treat concurrency problems

	private Account originAccount;
	private Account destinationAccount;
	static BigDecimal maxTransferValue;
	static BigDecimal normalTransferTax;
	static BigDecimal VipTransferTax;

	/**
	 * Used a private constructor, instantiating the class with the static method
	 * makeWithdrawal() for encapsulation
	 */
	private Transfer(LocalDate date, LocalTime time, BigDecimal value, String description, Account originAccount,
			Account destinationAccount) {
		super(date, time, value, description);
		this.originAccount = originAccount;
		this.destinationAccount = destinationAccount;
		Transfer.maxTransferValue = new BigDecimal("1000");
		Transfer.normalTransferTax = new BigDecimal("8");
		Transfer.VipTransferTax = new BigDecimal("0.008");
	}

	// TODO treat concurrency problems
	static Transfer makeTransfer(LocalDate date, LocalTime time, BigDecimal value, String description,
			Account originAccount, Account destinationAccount) {
		Transfer t = new Transfer(date, time, value, description, originAccount, destinationAccount);

		String originProfileType = originAccount.getProfileType();
		BigDecimal originBalance = originAccount.getBalance();
		boolean enoughFounds = Transaction.checkEnoughFounds(originBalance, value);
		boolean valueSmallerThanLimit = t.valueSmallerThanLimit(value);

		if (originProfileType == "Normal") {
			if (enoughFounds) {
				if (valueSmallerThanLimit) {
					BigDecimal realValue = Transfer.applyFixedTax(value, normalTransferTax);
					Transaction.subtractBalance(originAccount, realValue);
					Transaction.addBalance(destinationAccount, realValue);
				} else {
					System.out.println("Error: Limit for transfer exceeded");
				}
			} else {
				System.out.println("Error: not enough founds for the transaction");
			}
		} else if (originProfileType == "VIP") {
			if (enoughFounds) {
				BigDecimal realValue = Transfer.applyPercentageTax(value, VipTransferTax);
				Transaction.subtractBalance(originAccount, realValue);
				Transaction.addBalance(destinationAccount, realValue);
			} else {
				System.out.println("Error: not enough founds for the transaction");
			}
		}
		originAccount.transactions.add(t);
		destinationAccount.transactions.add(t);
		return t;
	}

	private boolean valueSmallerThanLimit(BigDecimal value) {
		return (value.compareTo(Transfer.maxTransferValue) < 0);
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
		Transfer.maxTransferValue = maxTransferValue;
	}

	public BigDecimal getNormalTransferTax() {
		return normalTransferTax;
	}

	public void setNormalTransferTax(BigDecimal normalTransferTax) {
		Transfer.normalTransferTax = normalTransferTax;
	}

	public BigDecimal getVipTransferTax() {
		return VipTransferTax;
	}

	public void setVipTransferTax(BigDecimal vipTransferTax) {
		VipTransferTax = vipTransferTax;
	}

}
