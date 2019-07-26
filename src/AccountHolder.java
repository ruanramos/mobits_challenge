
public class AccountHolder {

	private String profileType;
	private Account account;
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
