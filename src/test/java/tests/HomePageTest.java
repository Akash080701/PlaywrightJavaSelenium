package tests;

import com.microsoft.playwright.Download;

import base.BaseTest;

public class HomePageTest extends BaseTest {
	
	public void loginPage() {
		page.navigate("https://www.saucedemo.com/");
		System.out.println("Title: " + page.title());
		
		page.getByText("Swag Labs").click();
//		page.locator("[data-test=\"product-sort-container\"]").selectOption("za");
//		page.locator("[data-test=\"product-sort-container\"]").selectOption("lohi");
//		page.locator("[data-test=\"product-sort-container\"]").selectOption("az");
//		page.locator("[data-test=\"shopping-cart-link\"]").click();
//		page.locator("[data-test=\"continue-shopping\"]").click();
//		page.locator("[data-test=\"title\"]").click();
//		page.locator("[data-test=\"add-to-cart-sauce-labs-backpack\"]").click();
//		page.locator("[data-test=\"shopping-cart-link\"]").click();
//		page.locator("[data-test=\"checkout\"]").click();
//		page.locator("[data-test=\"firstName\"]").click();
//		page.locator("[data-test=\"firstName\"]").fill("Akash");
//		page.locator("[data-test=\"firstName\"]").press("Tab");
//		page.locator("[data-test=\"lastName\"]").fill("Ganesh");
//		page.locator("[data-test=\"lastName\"]").press("Tab");
//		page.locator("[data-test=\"postalCode\"]").fill("625009");
//		page.locator("[data-test=\"continue\"]").click();
//		page.locator("[data-test=\"finish\"]").click();
//		Download download = page.waitForDownload(() -> {
//			page.locator("[data-test=\"generate-pdf-order\"]").click();
//		});
//		page.locator("[data-test=\"back-to-products\"]").click();
		
	}
}
