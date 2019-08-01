package interaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

import account_operations.CallManager;
import account_operations.Deposit;
import account_operations.Transfer;
import account_operations.Withdrawal;
import bank_management.BusinessRules;
import database.AccountStorage;

public class OperationsMenu {

	private static AccountStorage accStorage = new AccountStorage();

	public static boolean WithdrawalMenu(long accNumber) {
		BigDecimal value = null;
		String description = "";
		Scanner inValue = new Scanner(System.in);
		System.out.println("How much you want to withdraw? ");
		try {
			value = new BigDecimal(inValue.nextLine());
		} catch (Exception e) {
			System.out.println("Not a valid value");
			return false;
		}
		System.out.println("Add an optional description: ");
		try {
			description = inValue.nextLine();
		} catch (Exception e) {
			System.out.println("Not a valid description: ");
			return false;
		}
		try {
			Withdrawal.makeWithdrawal(LocalDateTime.now(), value, description, accNumber);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("Error: couldnt finish operation");
			return false;
		}
		return true;
	}

	public static boolean DespositMenu() {
		BigDecimal value = null;
		long destinationAccountNumber = 0;
		String description = "";
		Scanner inValue = new Scanner(System.in);

		System.out.println("What is the number of the destination account? ");
		try {
			destinationAccountNumber = inValue.nextLong();
		} catch (Exception e) {
			System.out.println("Not a valid value");
			return false;
		}
		if (accStorage.selectAccount(destinationAccountNumber) == null) {
			System.out.println("Error: Account does not exist");
			return false;
		}

		System.out.println("How much you want to deposit? ");
		try {
			value = new BigDecimal(inValue.nextLine());
		} catch (Exception e) {
			System.out.println("Not a valid value");
			return false;
		}
		System.out.println("Add an optional description: ");
		try {
			description = inValue.nextLine();
		} catch (Exception e) {
			System.out.println("Not a valid description: ");
			return false;
		}
		try {
			Deposit.makeDeposit(LocalDateTime.now(), value, description, destinationAccountNumber);
		} catch (Exception e) {
			System.out.println("Error: couldnt finish operation");
			return false;
		}
		return true;
	}

	public static boolean TransferMenu(long accNumber) {
		BigDecimal value = null;
		long destinationAccountNumber = 0;
		String description = "";
		Scanner inValue = new Scanner(System.in);

		System.out.println("What is the number of the destination account? ");
		try {
			destinationAccountNumber = inValue.nextLong();
		} catch (Exception e) {
			System.out.println("Not a valid value");
			return false;
		}
		if (accStorage.selectAccount(destinationAccountNumber) == null) {
			System.out.println("Error: Account does not exist");
			return false;
		}

		System.out.println("How much you want to transfer? ");
		try {
			value = new BigDecimal(inValue.nextLine());
		} catch (Exception e) {
			System.out.println("Not a valid value");
			return false;
		}
		System.out.println("Add an optional description: ");
		try {
			description = inValue.nextLine();
		} catch (Exception e) {
			System.out.println("Not a valid description: ");
			return false;
		}
		try {
			Transfer.makeTransfer(LocalDateTime.now(), value, description, accNumber, destinationAccountNumber);
		} catch (Exception e) {
			System.out.println("Error: couldnt finish operation");
			return false;
		}
		return true;
	}

	public static boolean CallManagerMenu(long accNumber) {

		int option = 0;
		String description = "";
		Scanner inValue = new Scanner(System.in);

		System.out.println("Are you sure you want to call the manager? Please cofirm:\n1-\tYes\n2-\tNo");
		try {
			option = inValue.nextInt();
			switch (option) {
			case 1:
				break;
			case 2:
				System.out.println("Operation was canceled");
				return false;
			default:
				System.out.println("Error, not a valid option");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Not a valid option");
			return false;
		}

		System.out.println("Add an optional description: ");
		try {
			description = inValue.nextLine();
		} catch (Exception e) {
			System.out.println("Not a valid description: ");
			return false;
		}
		try {
			CallManager.makeManagerCall(LocalDateTime.now(), BusinessRules.getCallmanagertax(), description, accNumber);
		} catch (Exception e) {
			System.out.println("Error: couldnt finish operation");
			return false;
		}
		return true;
	}

	public static void ViewBalanceMenu(long accNumber) {
		DisplayData.showAcountBalance(accNumber);
	}

	public static void StatementMenu(long accNumber) {
		DisplayData.showAccountStatement(accNumber);
	}

	public static void ChangeAccountMenu() {

	}

	/**
	 * dont need this right now
	 */
	public static void CreateAccountMenu() {

	}

	/**
	 * dont need this right now
	 */
	public static void CreateAccountHolderMenu() {

	}
}
