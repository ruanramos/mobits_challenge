package interaction;

import account_operations.Transaction;
import database.AccountStorage;
import database.TransactionStorage;

public class DisplayData {

	private static AccountStorage accStorage = new AccountStorage();
	private static TransactionStorage transactionStorage = new TransactionStorage();

	public static void showAcountBalance(long accNumber) {
		System.out.println(accStorage.selectAccountBalance(accNumber));
	}

	public static void showAccountStatement(long accNumber) {
		for (Transaction t : transactionStorage.selectTransactionsFromAccount(accNumber)) {
			System.out.println(t.toString());
		}
	}
}
