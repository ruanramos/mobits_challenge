package bank_management;

import java.math.BigDecimal;

public class BusinessRules {

	/**
	 * Account related
	 */
	private static final int lenAccountNumber = 5;
	private static final int lenPassword = 4;

	public static enum profileTypes {
		NORMAL, VIP;
	}

	/**
	 * Tax related
	 */
	private static final BigDecimal negativeBalanceTax = new BigDecimal("0.1");
	private static final int taxApplicationInterval = 1;
	private static final BigDecimal normalTransferTax = new BigDecimal("8");
	private static final BigDecimal VipTransferTax = new BigDecimal("0.008");
	private static final BigDecimal CallManagerTax = new BigDecimal("50");

	/**
	 * Transaction related
	 */
	private static final BigDecimal normalMaxTransferValue = new BigDecimal("1000");

	public enum Error {
		NO_FOUNDS(0, "Not enough fund for operation"), LIMIT_EXCEEDED(1, "Limit for operation exceeded"),
		UNEXPECTED_ERROR(2, "An unexpected "), ACCOUNT_NOT_FOUND(3, "Account number was not fund"),
		ACCOUNT_HOLDER_NOT_FOUND(4, "Account holder was not fund"),
		PROFILE_TYPE_NOT_FOUND(5, "Profile type was not fund"),
		UNAUTHORIZED(6, "You have no authorization to perform this action");

		private final int code;
		private final String description;

		private Error(int code, String description) {
			this.code = code;
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public int getCode() {
			return code;
		}

		@Override
		public String toString() {
			return code + ": " + description;
		}
	}

	public enum TransactionTypes {
		WITHDRAWAL(0), DEPOSIT(1), TRANSFER(2), CALL_MANAGER(3);

		private final int code;

		private TransactionTypes(int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		@Override
		public String toString() {
			String res = "";
			switch (code) {
			case 0:
				res = "Withdrawal";
				break;
			case 1:
				res = "Deposit";
				break;
			case 2:
				res = "Transfer";
				break;
			case 3:
				res = "Call Manager";
				break;
			default:
				return Error.UNEXPECTED_ERROR.toString();
			}
			return res;
		}
	}

	/**
	 * Getters and Setters
	 */
	public static BigDecimal getNormalTransferTax() {
		return normalTransferTax;
	}

	public static BigDecimal getVipTransferTax() {
		return VipTransferTax;
	}

	public static BigDecimal getNormalMaxTransferValue() {
		return normalMaxTransferValue;
	}

	public static int getLenAccountnumber() {
		return lenAccountNumber;
	}

	public static int getLenPassword() {
		return lenPassword;
	}

	public static BigDecimal getNegativebalanceTax() {
		return negativeBalanceTax;
	}

	public static int getTaxapplicationInterval() {
		return taxApplicationInterval;
	}

	public static BigDecimal getCallmanagertax() {
		return CallManagerTax;
	}

}
