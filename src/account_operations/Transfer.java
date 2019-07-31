package account_operations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import account.Account;
import bank_management.BusinessRules.profileTypes;
import bank_management.BusinessRules;

public class Transfer extends Transaction {

	// TODO treat concurrency problems

	private Account originAccount;
	private Account destinationAccount;
	private BigDecimal feeCharged;

	/**
	 * Used a private constructor, instantiating the class with the static method
	 * makeTransfer() for encapsulation
	 */
	private Transfer(LocalDate date, LocalTime time, BigDecimal value, String description, Account originAccount,
			Account destinationAccount) {
		super(date, time, value, description);
		this.originAccount = originAccount;
		this.destinationAccount = destinationAccount;
	}

	// TODO treat errors better
	static Transfer makeTransfer(LocalDate date, LocalTime time, BigDecimal value, String description,
			Account originAccount, Account destinationAccount) {

		Transfer t = new Transfer(date, time, value, description, originAccount, destinationAccount);

		profileTypes originProfileType = originAccount.getAccountHolder().getProfileType();
		BigDecimal originBalance = originAccount.getBalance();
		boolean enoughFounds = Account.checkEnoughFounds(originBalance, value);
		boolean valueSmallerThanLimit = Transfer.valueSmallerThanLimit(value);

		if (originProfileType == profileTypes.NORMAL) {
			if (enoughFounds) {
				if (valueSmallerThanLimit) {
					t.setFeeCharged(BusinessRules.getNormalTransferTax());
					BigDecimal realValue = Transfer.applyFixedTax(value, t.getFeeCharged());
					Account.subtractBalance(originAccount, realValue);
					Account.addBalance(destinationAccount, realValue);
					System.out.println(String.format(
							"Transfer of %oR$ from account number %o to account number %o finished successfully.\nNew balance on origin account: %oR$.",
							value.toString(), originAccount.getAccountNumber(), destinationAccount.getAccountNumber(),
							originAccount.getBalance()));
				} else {
					System.out.println("Error: Limit for transfer exceeded");
				}
			} else {
				System.out.println("Error: not enough founds for the transaction");
			}
		} else if (originProfileType == profileTypes.VIP) {
			if (enoughFounds) {
				t.setFeeCharged(Transfer.calculatePercentageFee(value, BusinessRules.getVipTransferTax()));
				BigDecimal realValue = value.add(t.getFeeCharged());
				Account.subtractBalance(originAccount, realValue);
				Account.addBalance(destinationAccount, realValue);
				System.out.println(String.format(
						"Transfer of %oR$ from account number %o to account number %o finished successfully.\nNew balance on origin account: %oR$.",
						value.toString(), originAccount.getAccountNumber(), destinationAccount.getAccountNumber(),
						originAccount.getBalance()));
			} else {
				/**
				 * Negative balance treatment for VIP account holder
				 */
				t.setFeeCharged(Transfer.calculatePercentageFee(value, BusinessRules.getVipTransferTax()));
				BigDecimal realValue = value.add(t.getFeeCharged());
				Account.subtractBalance(originAccount, realValue);
				Account.addBalance(destinationAccount, realValue);
				System.out.println(String.format(
						"Transfer of %oR$ from account number %o to account number %o finished successfully.\nNew balance on origin account: %oR$. You are beeing taxed, negative balance!",
						value.toString(), originAccount.getAccountNumber(), destinationAccount.getAccountNumber(),
						originAccount.getBalance()));
				BigDecimal currentBalance;
				while (originAccount.getBalance().compareTo(BigDecimal.ZERO) < 0) {
					currentBalance = originAccount.getBalance();
					Transaction.waitAndApplyTax(originAccount, BusinessRules.getTaxapplicationInterval(),
							currentBalance, BusinessRules.getNegativebalanceTax());
				}
			}
		}
		t.getOriginAccount().transactions.add(t);
		t.getDestinationAccount().transactions.add(t);
		return t;
	}

	private static boolean valueSmallerThanLimit(BigDecimal value) {
		return (value.compareTo(BusinessRules.getNormalMaxTransferValue()) < 0);
	}

	@Override
	public String toString() {
		BigDecimal value = getValue();
		Account origAccount = getOriginAccount();
		Account destAccount = getDestinationAccount();
		BigDecimal origBalance = origAccount.getBalance();
		BigDecimal destBalance = destAccount.getBalance();

		return String.format(
				"Transaction Type: Transfer\nDate: %o\nHour: %o\nValue: %o\nDescription: %o\nOrigin Account number: %o\nDestination Account number: %o\nOrigin Account Balance: %o - %o = %o\nDestination Account Balance: %o + %o = %o\n",
				getDate(), getTime(), value, getDescription(), origAccount.getAccountNumber(),
				destAccount.getAccountNumber(), origBalance, value, origBalance.subtract(value), destBalance, value,
				destBalance.add(value));
	}

	/**
	 * Getters and Setters
	 */
	public BigDecimal getFeeCharged() {
		return feeCharged;
	}

	public void setFeeCharged(BigDecimal feeCharged) {
		this.feeCharged = feeCharged;
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
}
