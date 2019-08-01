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

public class CallManager extends Transaction {

	/**
	 * Since the Call Manager operation simply removes 50R$ from the account, it's
	 * been treated as a normal transaction, showing on the Account Statement, etc
	 */
	// TODO treat concurrency problems

	private long accountNumber;

	public CallManager(LocalDateTime time, BigDecimal value, String description, long accountNumber) {
		super(time, value, description);
		this.type = BusinessRules.TransactionTypes.CALL_MANAGER.ordinal();
		this.setValue(BusinessRules.getCallmanagertax());
		this.setAccountNumber(accountNumber);
	}

	/**
	 * Used the static method makeManagerCall() because the class will only be
	 * instantiated when the operation is completed,
	 */
	// TODO treat errors better
	public static CallManager makeManagerCall(LocalDateTime time, BigDecimal value, String description,
			long accountNumber) {
		CallManager cm = new CallManager(time, value, description, accountNumber);

		Account account = cm.getAccount();
		int profileType = account.getAccountHolder().getProfileType();
		BigDecimal balance = account.getBalance();
		BigDecimal managerTax = BusinessRules.getCallmanagertax();
		boolean enoughFunds = Account.checkEnoughFunds(balance, managerTax);

		if (profileType == profileTypes.NORMAL.ordinal()) {
			System.out.println(BusinessRules.Error.UNAUTHORIZED.toString());
		} else if (profileType == profileTypes.VIP.ordinal()) {
			// TODO ask for confirmation
			if (enoughFunds) {
				Account.subtractBalance(account, managerTax);
				System.out.println(
						String.format("Manager called. %oR$ debited from account number %o.\nNew balance: %oR$.",
								managerTax.toString(), account.getAccountNumber(), account.getBalance()));
			} else {
				/**
				 * Negative balance treatment for VIP account holder
				 */
				Account.subtractBalance(account, value);
				System.out.println(String.format(
						"Manager called. %oR$ debited from account number %o.\nNew balance: %oR$. You are beeing taxed, negative balance!",
						managerTax.toString(), account.getAccountNumber(), account.getBalance()));
				BigDecimal currentBalance;
				while (account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
					currentBalance = account.getBalance();
					Transaction.waitAndApplyTax(account, BusinessRules.getTaxapplicationInterval(), currentBalance,
							BusinessRules.getNegativebalanceTax());
				}
			}
		}
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		String formattedDateTime = time.format(formatter);

		TransactionStorage.insertTransaction(cm.getId(), accountNumber, formattedDateTime, value, description,
				TransactionTypes.CALL_MANAGER.toString());

		cm.getAccount().transactions.add(cm);
		return cm;
	}

	@Override
	public String toString() {
		BigDecimal value = getValue();
		Account account = getAccount();
		BigDecimal balance = account.getBalance();

		return String.format(
				"Transaction Type: Call Manager\nDate: %o\nValue: %oR$\nDescription: %o\nAccount number: %o\nBalance: %oR$ - %oR$ = %oR$\n",
				getTime().toString(), value, getDescription(), account.getAccountNumber(), balance, value,
				balance.subtract(value));
	}

	/**
	 * Getters and Setters
	 */

	public Account getAccount() {
		AccountStorage accStorage = new AccountStorage();
		return accStorage.selectAccount(getAccountNumber());
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
}
