package tests;

import org.testng.annotations.Test;

import base.BaseTest;

public class HomePageTest extends BaseTest {

	@Test
	public void loginPage() {
		getPage().navigate("https://www.saucedemo.com/");
		getTest().info("Navigated to SauceDemo");

		System.out.println("Title: " + getPage().title());
		getPage().getByText("Swag Labs").click();
		getTest().info("Clicked Swag Labs header");
	}
}