package account_operations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import account.Account;
import bank_management.BusinessRules;
import bank_management.BusinessRules.profileTypes;

public class CallManager extends Transaction {

	/**
	 * Since the Call Manager operation simply removes 50R$ from the account, it's
	 * been treated as a normal transaction, showing on the Account Statement, etc
	 */
	// TODO treat concurrency problems

	private Account account;

	/**
	 * Used a private constructor, instantiating the class with the static method
	 * makeManagerCall() for encapsulation
	 */
	private CallManager(LocalDate date, LocalTime time, BigDecimal value, String description, Account account) {
		super(date, time, value, description);
		this.account = account;
	}

	// TODO treat errors better
	static CallManager makeManagerCall(LocalDate date, LocalTime time, BigDecimal value, String description,
			Account account) {

		profileTypes profileType = account.getAccountHolder().getProfileType();
		BigDecimal balance = account.getBalance();
		BigDecimal managerTax = BusinessRules.getCallmanagertax();
		boolean enoughFounds = Account.checkEnoughFounds(balance, managerTax);

		if (profileType == profileTypes.NORMAL) {
			System.out.println(BusinessRules.Error.UNAUTHORIZED.toString());
		} else if (profileType == profileTypes.VIP) {
			// TODO ask for confirmation
			if (enoughFounds) {
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
		CallManager cm = new CallManager(date, time, value, description, account);
		cm.getAccount().transactions.add(cm);
		return cm;
	}

	@Override
	public String toString() {
		BigDecimal value = getValue();
		Account account = getAccount();
		BigDecimal balance = account.getBalance();

		return String.format(
				"Transaction Type: Call Manager\nDate: %o\nHour: %o\nValue: %oR$\nDescription: %o\nAccount number: %o\nBalance: %oR$ - %oR$ = %oR$\n",
				getDate(), getTime(), value, getDescription(), account.getAccountNumber(), balance, value,
				balance.subtract(value));
	}

	/**
	 * Getters and Setters
	 */

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
