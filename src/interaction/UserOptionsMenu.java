package interaction;

import java.util.Scanner;

import bank_management.BusinessRules.TransactionTypes;
import bank_management.BusinessRules.profileTypes;

public class UserOptionsMenu {

	static private long accNumber;

	/**
	 * Just to be clear, tried to make an MVP with the really important things
	 * needed to do what was asked on the description of the challenge. That's the
	 * reason there is no option for deleting, updating, changing password and all
	 * unnecessary functionalities for the first delivery
	 */

	static final int normalPermissionCode = profileTypes.NORMAL.ordinal();
	static final int VIPPermissionCode = profileTypes.VIP.ordinal();
	static final int adminPermissionCode = profileTypes.ADMIN.ordinal();

	static final int withdrawalCode = TransactionTypes.WITHDRAWAL.ordinal();
	static final int viewBalanceCode = TransactionTypes.VIEW_BALANCE.ordinal();
	static final int depositCode = TransactionTypes.DEPOSIT.ordinal();
	static final int transferCode = TransactionTypes.TRANSFER.ordinal();
	static final int statementCode = TransactionTypes.STATEMENT.ordinal();
	static final int callManagerCode = TransactionTypes.CALL_MANAGER.ordinal();

	public enum UserOptions {
		LOGIN(0, "Login to an existing account", normalPermissionCode),
		CHANGE_ACCOUNT(1, "Login to another account", normalPermissionCode),
		MAKE_WITHDRAWAL(withdrawalCode, "Make a Withdrawal of an account", normalPermissionCode),
		VIEW_BALANCE(viewBalanceCode, "View balance of an account", normalPermissionCode),
		MAKE_DEPOSIT(depositCode, "Make a deposit on an account", normalPermissionCode),
		MAKE_TRANSFER(transferCode, "Make a transfer between two accounts", normalPermissionCode),
		GENERATE_STATEMENT(statementCode, "Generate a statement with all account transactions", normalPermissionCode),
		CALL_MANAGER(callManagerCode, "Request a manager visit, VIP only", VIPPermissionCode),
		CREATE_ACCOUNT(800, "Create a new account on the system, ADMIN only", adminPermissionCode),
		CREATE_ACCOUNT_HOLDER(801, "Create a new account holder on the system, ADMIN only", adminPermissionCode);

		private final int code;
		private final String description;
		private final int permission;

		private UserOptions(int code, String description, int permission) {
			this.code = code;
			this.description = description;
			this.permission = permission;
		}

		public String getDescription() {
			return description;
		}

		public int getCode() {
			return code;
		}

		public int getPermission() {
			return permission;
		}

		@Override
		public String toString() {
			return code + ": " + description;
		}
	}

	private static int displayMenu(int permissionCode) {
		int chosenOption = 0;
		System.out.println(
				String.format("Logged in as account number %o.\n\nChoose what operation you want to do:", accNumber));

		if (permissionCode == normalPermissionCode) {
			displayNormalMenu();
		} else if (permissionCode == VIPPermissionCode) {
			displayVIPMenu();
		} else if (permissionCode == adminPermissionCode) {
			displayAdminMenu();
		}
		return chosenOption;
	}

	private static void displayNormalMenu() {
		Scanner inOption = new Scanner(System.in);
		// Display the menu
		System.out.println(String.format("1\t %o", UserOptions.MAKE_WITHDRAWAL));
		System.out.println(String.format("2\t %o", UserOptions.VIEW_BALANCE));
		System.out.println(String.format("3\t %o", UserOptions.MAKE_DEPOSIT));
		System.out.println(String.format("4\t %o", UserOptions.MAKE_TRANSFER));
		System.out.println(String.format("5\t %o", UserOptions.GENERATE_STATEMENT));
		System.out.println(String.format("6\t %o", UserOptions.CHANGE_ACCOUNT));

		System.out.println("Option: ");

		int choice = inOption.nextInt();

		switch (choice) {
		case 1:

			break;
		case 2:

			break;
		case 3:

			break;
		case 4:

			break;
		case 5:

			break;
		case 6:

			break;
		default:
			System.out.println("Invalid choice");
		}
	}

	private static void displayVIPMenu() {
		Scanner inOption = new Scanner(System.in);
		// Display the menu
		System.out.println(String.format("1\t %o", UserOptions.MAKE_WITHDRAWAL));
		System.out.println(String.format("2\t %o", UserOptions.VIEW_BALANCE));
		System.out.println(String.format("3\t %o", UserOptions.MAKE_DEPOSIT));
		System.out.println(String.format("4\t %o", UserOptions.MAKE_TRANSFER));
		System.out.println(String.format("5\t %o", UserOptions.GENERATE_STATEMENT));
		System.out.println(String.format("6\t %o", UserOptions.CALL_MANAGER));
		System.out.println(String.format("7\t %o", UserOptions.CHANGE_ACCOUNT));

		System.out.println("Option: ");

		int choice = inOption.nextInt();

		switch (choice) {
		case 1:

			break;
		case 2:

			break;
		case 3:

			break;
		case 4:

			break;
		case 5:

			break;
		case 6:

			break;
		case 7:

			break;
		default:
			System.out.println("Invalid choice");
		}
	}

	private static void displayAdminMenu() {
		Scanner inOption = new Scanner(System.in);
		// Display the menu
		System.out.println(String.format("1\t %o", UserOptions.CREATE_ACCOUNT_HOLDER));
		System.out.println(String.format("2\t %o", UserOptions.CREATE_ACCOUNT));
		System.out.println(String.format("3\t %o", UserOptions.CHANGE_ACCOUNT));

		System.out.println("Option: ");

		int choice = inOption.nextInt();

		switch (choice) {
		case 1:

			break;
		case 2:

			break;
		case 3:

			break;
		default:
			System.out.println("Invalid choice");
		}
	}
}
