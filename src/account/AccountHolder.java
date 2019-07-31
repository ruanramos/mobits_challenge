package account;

public class AccountHolder {

	private int id;
	private int profileType;
	private Account account;
	private long accountNumber;
	/**
	 * For security, we need to protect the password. We could use encryption or
	 * hashing. Made the option for hashing, since it gives no way back and it's
	 * more secure.
	 */
	private String password;

	/**
	 * Constructor without applying an account
	 */
	public AccountHolder(int profileType, String password, int id) {
		this.profileType = profileType; // made profileType binded to the holder, not the account
		this.password = password;
		this.id = id;
	}

	/**
	 * Constructor applying an account
	 */
	public AccountHolder(int profileType, String password, int id, long accountNumber) {
		this.profileType = profileType;
		this.password = password;
		this.id = id;
		this.setAccountNumber(accountNumber);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Account getAccount() {
		return account; // TODO get from bd using account id
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getProfileType() {
		return profileType;
	}

	public void setProfileType(int profileType) {
		this.profileType = profileType;
	}
}
