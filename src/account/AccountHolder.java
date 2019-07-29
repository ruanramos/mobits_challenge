package account;

import account.AccountsManager.profileTypes;

public class AccountHolder {

	private profileTypes profileType;
	private Account account;

	/**
	 * For security, we need to protect the password. We could use encryption or
	 * hashing. Made the option for hashing, since it gives no way back and it's
	 * more secure.
	 */
	private String password;

	public AccountHolder(profileTypes profileType, String password) {
		this.profileType = profileType; // made profileType binded to the holder, not the account
		this.password = password;
	}

	public profileTypes getProfileType() {
		return profileType;
	}

	public void setProfileType(profileTypes profileType) {
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
