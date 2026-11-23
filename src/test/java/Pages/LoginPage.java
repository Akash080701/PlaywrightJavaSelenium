package Pages;

import com.microsoft.playwright.Page;

public class LoginPage {

	private final Page page;

	private final String usernameInput = "[data-test='username']";
	private final String passwordInput = "[data-test='password']";
	private final String loginButton = "[data-test='login-button']";

	public LoginPage(Page page) {
		this.page = page;
	}

	public String getTitle() {
		return page.title();
	}

	public void navigate() {
		page.navigate("https://www.saucedemo.com/");
	}

	public void adduserName(String username) {
		page.fill(usernameInput, username);
	}

	public void addPassword(String password) {
		page.fill(passwordInput, password);
	}

	public void clickLogin() {
		page.click(loginButton);
	}
}