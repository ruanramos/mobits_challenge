package interaction;

import java.util.Scanner;

import bank_management.BusinessRules;
import bank_management.BusinessRules.TransactionTypes;
import bank_management.BusinessRules.profileTypes;
import validation.Validation;

public class LoginMenu {

	static public void createloginMenu() {
		System.out.println("Welcome, please login to your account!\n\n");
		long accNumber = getUserAccountNumber();
		String hashedPassword = getUserPassword(accNumber);

		// LogIn();
	}

	private static long getUserAccountNumber() {
		long accNumber = 0;
		int numberOfAttempts = 0;
		int maxAttempts = BusinessRules.loginAttemptsPermited;
		do {
			numberOfAttempts++;
			System.out.println(String.format("(Attempt %o of %o) --> Account Number: ", numberOfAttempts, maxAttempts));
			Scanner inAcc = new Scanner(System.in);
			try {
				accNumber = inAcc.nextLong();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} while ((String.valueOf(accNumber).length() != BusinessRules.getLenAccountnumber()
				|| !Validation.validateAccountNumber(accNumber)) && numberOfAttempts < maxAttempts);
		if (numberOfAttempts >= maxAttempts) {
			System.out.println("Max attemps reached. Terminating the program");
			System.exit(0);
		}
		return accNumber;
	}

	private static String getUserPassword(long accNumber) {
		String password = "";
		int numberOfAttempts = 0;
		int maxAttempts = BusinessRules.loginAttemptsPermited;
		do {
			numberOfAttempts++;
			System.out.println(String.format("(Attempt %o of %o) --> Password: ", numberOfAttempts, maxAttempts));
			Scanner inPassword = new Scanner(System.in);
			try {
				password = inPassword.nextLine();
				// TODO hash the password here
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} while ((password.length() != BusinessRules.getLenPassword()
				|| !Validation.validatePassword(accNumber, password)) && numberOfAttempts < maxAttempts);
		if (numberOfAttempts >= maxAttempts) {
			System.out.println("Max attemps reached. Terminating the program");
			System.exit(0);
		}
		return password;
	}

//	public static void main(String[] args) {
//		LoginMenu();
//	}
}
