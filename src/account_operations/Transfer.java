package account_operations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import account.Account;
import bank_management.BusinessRules;
import bank_management.BusinessRules.TransactionTypes;
import bank_management.BusinessRules.profileTypes;
import database.AccountStorage;
import database.TransactionStorage;

public class Transfer extends Transaction {

	// TODO treat concurrency problems

	private long originAccountNumber;
	private long destinationAccountNumber;
	private BigDecimal feeCharged;

	public Transfer(LocalDateTime time, BigDecimal value, String description, long originAccountNumber,
			long destinationAccountNumber) {
		super(time, value, description);
		this.type = BusinessRules.TransactionTypes.TRANSFER.ordinal();
		this.originAccountNumber = originAccountNumber;
		this.destinationAccountNumber = destinationAccountNumber;
	}

	/**
	 * Used the static method makeTransfer() because the class will only be
	 * instantiated when the operation is completed,
	 */
	// TODO treat errors better
	public static Transfer makeTransfer(LocalDateTime time, BigDecimal value, String description,
			long originAccountNumber, long destinationAccountNumber) {

		Transfer t = new Transfer(time, value, description, originAccountNumber, destinationAccountNumber);

		Account destinationAccount = t.getDestinationAccount();
		Account originAccount = t.getOriginAccount();

		int originProfileType = originAccount.getAccountHolder().getProfileType();
		BigDecimal originBalance = originAccount.getBalance();
		boolean enoughFunds = Account.checkEnoughFunds(originBalance, value);
		boolean valueSmallerThanLimit = Transfer.valueSmallerThanLimit(value);

		if (originProfileType == profileTypes.NORMAL.ordinal()) {
			if (enoughFunds) {
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
				System.out.println("Error: not enough funds for the transaction");
			}
		} else if (originProfileType == profileTypes.VIP.ordinal()) {
			if (enoughFunds) {
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

		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		String formattedDateTime = time.format(formatter);

		TransactionStorage.insertTransaction(t.getId(), destinationAccountNumber, formattedDateTime, value, description,
				TransactionTypes.TRANSFER.toString());
		TransactionStorage.insertTransaction(t.getId(), originAccountNumber, formattedDateTime, value, description,
				TransactionTypes.TRANSFER.toString());

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
				"Transaction Type: Transfer\nDate: %o\nValue: %oR$\nDescription: %o\nOrigin Account number: %o\nDestination Account number: %o\nOrigin Account Balance: %oR$ - %oR$ = %oR$\nDestination Account Balance: %oR$ + %oR$ = %oR$\n",
				getTime().toString(), value, getDescription(), origAccount.getAccountNumber(),
				destAccount.getAccountNumber(), origBalance, value, origBalance.subtract(value), destBalance, value,
				destBalance.add(value));
	}

	/**
	 * Getters and Setters
	 */
	public long getOriginAccountNumber() {
		return originAccountNumber;
	}

	public void setOriginAccountNumber(long originAccountNumber) {
		this.originAccountNumber = originAccountNumber;
	}

	public long getDestinationAccountNumber() {
		return destinationAccountNumber;
	}

	public void setDestinationAccountNumber(long destinationAccountNumber) {
		this.destinationAccountNumber = destinationAccountNumber;
	}

	public BigDecimal getFeeCharged() {
		return feeCharged;
	}

	public void setFeeCharged(BigDecimal feeCharged) {
		this.feeCharged = feeCharged;
	}

	public Account getDestinationAccount() {
		AccountStorage accStorage = new AccountStorage();
		return accStorage.selectAccount(getDestinationAccountNumber());
	}

	public Account getOriginAccount() {
		AccountStorage accStorage = new AccountStorage();
		return accStorage.selectAccount(getOriginAccountNumber());
	}
}
