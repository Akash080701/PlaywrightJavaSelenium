package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.HomePage;
import Pages.LoginPage;
import base.BaseTest;

public class LoginTest extends BaseTest {

	@Test
	public void loginPage() {
		LoginPage loginpage = new LoginPage(page);
		HomePage homepage = new HomePage(page);
		
		page.navigate("https://www.saucedemo.com/");
		test.info("Successfully Navigated to defined URL");
		
		System.out.println("Title: " + page.title());
		test.info("Successfully get a page title");
		
		loginpage.adduserName("standard_user");
		test.info("Successfully entered valid username");
		
		loginpage.addPassword("secret_sauce");
		test.info("Successfully entered valid Password");
		
		loginpage.clickLogin();
		test.info("Successfully Done Login");
		
		homepage.getTextTitle();
		test.info("Verification for homepage title");
		
		String actualTitle = homepage.getTitle();
		Assert.assertEquals(actualTitle, "Swag Labs", "Page does not match");
		
		
		
//		page.locator("[data-test=\"username\"]").click();
//		page.locator("[data-test=\"username\"]").fill("standard_user");
//		page.locator("[data-test=\"password\"]").click();
//		page.locator("[data-test=\"password\"]").fill("secret_sauce");
//		page.locator("[data-test=\"login-button\"]").click();
	}
}
