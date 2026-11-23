package Pages;

import com.microsoft.playwright.Page;

public class HomePage {

	private final Page page;
	private final String HomePageTitle = "[data-test='product-sort-container']";

	public HomePage(Page page) {
		this.page = page;
	}

	public void getTextTitle() {
		page.click(HomePageTitle);
	}

	public String getTitle() {
		return page.title();
	}
}