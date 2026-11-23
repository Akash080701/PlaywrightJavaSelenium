package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.HomePage;
import Pages.LoginPage;
import base.BaseTest;

public class LoginTest extends BaseTest {

	@Test
	public void loginPage() {
		LoginPage loginpage = new LoginPage(getPage());
		HomePage homepage = new HomePage(getPage());

		getPage().navigate("https://www.saucedemo.com/");
		getTest().info("Successfully Navigated to defined URL");

		System.out.println("Title: " + getPage().title());
		getTest().info("Successfully get a page title");

		loginpage.adduserName("standard_user");
		getTest().info("Successfully entered valid username");

		loginpage.addPassword("secret_sauce");
		getTest().info("Successfully entered valid Password");

		loginpage.clickLogin();
		getTest().info("Successfully Done Login");

		homepage.getTextTitle();
		getTest().info("Verification for homepage title");

		String actualTitle = homepage.getTitle();
		Assert.assertEquals(actualTitle, "Swag Labs", "Page does not match");
	}
}