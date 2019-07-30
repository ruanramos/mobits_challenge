package bank_management;

import java.math.BigDecimal;

public class BusinessRules {

	/**
	 * Account related
	 */
	private static final int lenAccountNumber = 8;
	private static final int lenPassword = 6;

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
	public static BigDecimal getNormaltransfertax() {
		return normalTransferTax;
	}

	public static BigDecimal getViptransfertax() {
		return VipTransferTax;
	}

	public static BigDecimal getNormalMaxtransfervalue() {
		return normalMaxTransferValue;
	}

	public static int getLenaccountnumber() {
		return lenAccountNumber;
	}

	public static int getLenpassword() {
		return lenPassword;
	}

	public static BigDecimal getNegativebalancetax() {
		return negativeBalanceTax;
	}

	public static int getTaxapplicationinterval() {
		return taxApplicationInterval;
	}

}
