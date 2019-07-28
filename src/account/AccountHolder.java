package account;

public class AccountHolder {

	private String profileType;
	private Account account;

	/**
	 * For security, we need to protect the password. We could use encryption or
	 * hashing. Made the option for hashing, since it gives no way back and it's
	 * more secure.
	 */
	private String password;

	public AccountHolder(String profileType, Account account, String password) {
		this.profileType = profileType;
		this.account = account;
		this.password = password;
	}

	public String getProfileType() {
		return profileType;
	}

	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
