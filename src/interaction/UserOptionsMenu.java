package interaction;

import java.util.Scanner;

import bank_management.BusinessRules.TransactionTypes;
import bank_management.BusinessRules.profileTypes;

public class UserOptionsMenu {

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
		LOGIN(8, "Login to an existing account", normalPermissionCode),
		CHANGE_ACCOUNT(9, "Login to another account", normalPermissionCode),
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

	public static int displayMenu(int permissionCode, long accNumber) {
		int chosenOption = 0;
		System.out.println(
				String.format("Logged in as account number %d.\n\nChoose what operation you want to do:", accNumber));

		if (permissionCode == normalPermissionCode) {
			chosenOption = displayNormalMenu(accNumber);
		} else if (permissionCode == VIPPermissionCode) {
			chosenOption = displayVIPMenu(accNumber);
		} else if (permissionCode == adminPermissionCode) {
			chosenOption = displayAdminMenu(accNumber);
		}
		return chosenOption;
	}

	private static int displayNormalMenu(long accNumber) {
		Scanner inOption = new Scanner(System.in);
		int choice = 0;

		do {
			System.out.println("1\t" + UserOptions.MAKE_WITHDRAWAL.description);
			System.out.println("2\t" + UserOptions.VIEW_BALANCE.description);
			System.out.println("3\t" + UserOptions.MAKE_DEPOSIT.description);
			System.out.println("4\t" + UserOptions.MAKE_TRANSFER.description);
			System.out.println("5\t" + UserOptions.GENERATE_STATEMENT.description);
			System.out.println("6\t" + UserOptions.CHANGE_ACCOUNT.description);

			System.out.println("Option: ");
			try {
				choice = inOption.nextInt();
				if (choice < 1 || choice > 6) {
					System.out.println("not a valid option!");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} while (choice < 1 || choice > 6);

		switch (choice) {
		case 1:
			OperationsMenu.WithdrawalMenu(accNumber);
			break;
		case 2:
			OperationsMenu.ViewBalanceMenu(accNumber);
			break;
		case 3:
			OperationsMenu.DespositMenu();
			break;
		case 4:
			OperationsMenu.TransferMenu(accNumber);
			break;
		case 5:
			OperationsMenu.StatementMenu(accNumber);
			break;
		case 6:
			OperationsMenu.ChangeAccountMenu();
			break;
		default:
			System.out.println("Invalid choice");
		}
		return choice;
	}

	private static int displayVIPMenu(long accNumber) {
		Scanner inOption = new Scanner(System.in);
		int choice = 0;

		do {
			System.out.println("1\t" + UserOptions.MAKE_WITHDRAWAL.description);
			System.out.println("2\t" + UserOptions.VIEW_BALANCE.description);
			System.out.println("3\t" + UserOptions.MAKE_DEPOSIT.description);
			System.out.println("4\t" + UserOptions.MAKE_TRANSFER.description);
			System.out.println("5\t" + UserOptions.GENERATE_STATEMENT.description);
			System.out.println("6\t" + UserOptions.CALL_MANAGER.description);
			System.out.println("7\t" + UserOptions.CHANGE_ACCOUNT.description);

			System.out.println("Option: ");
			try {
				choice = inOption.nextInt();
				if (choice < 1 || choice > 7) {
					System.out.println("not a valid option!");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} while (choice < 1 || choice > 7);

		switch (choice) {
		case 1:
			OperationsMenu.WithdrawalMenu(accNumber);
			break;
		case 2:
			OperationsMenu.ViewBalanceMenu(accNumber);
			break;
		case 3:
			OperationsMenu.DespositMenu();
			break;
		case 4:
			OperationsMenu.TransferMenu(accNumber);
			break;
		case 5:
			OperationsMenu.StatementMenu(accNumber);
			break;
		case 6:
			OperationsMenu.CallManagerMenu(accNumber);
			break;
		case 7:
			OperationsMenu.ChangeAccountMenu();
			break;
		default:
			System.out.println("Invalid choice");
		}
		return choice;
	}

	private static int displayAdminMenu(long accNumber) {
		Scanner inOption = new Scanner(System.in);
		int choice = 0;
		do {
			System.out.println("1\t" + UserOptions.CREATE_ACCOUNT_HOLDER.description);
			System.out.println("2\t" + UserOptions.CREATE_ACCOUNT.description);
			System.out.println("3\t" + UserOptions.CHANGE_ACCOUNT.description);

			System.out.println("Option: ");
			try {
				choice = inOption.nextInt();
				if (choice < 1 || choice > 3) {
					System.out.println("not a valid option!");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} while (choice < 1 || choice > 3);

		switch (choice) {
		case 1:
			OperationsMenu.CreateAccountHolderMenu();
			break;
		case 2:
			OperationsMenu.CreateAccountMenu();
			break;
		case 3:
			OperationsMenu.ChangeAccountMenu();
			break;
		default:
			System.out.println("Invalid choice");
		}
		return choice;
	}
}
