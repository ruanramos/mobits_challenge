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

	/**
	 * Transaction related
	 */
	private static final BigDecimal normalMaxTransferValue = new BigDecimal("1000");

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

}
