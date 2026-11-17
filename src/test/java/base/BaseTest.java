package base;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.naming.Context;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.internal.annotations.ITest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import utils.ExtentManager;
import utils.ScreenshotUtils;

public class BaseTest {

	protected Playwright playwright;
	protected Browser browser;
	protected Page page;
	protected BrowserContext context;
	protected Download download;
	protected ExtentReports extent;
	protected ExtentTest test;

	@BeforeMethod
	public void setUp(Method method) {

		extent = ExtentManager.getInstance();
		test = extent.createTest(method.getName());
		
		//Added CI based logic for browser launch
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        playwright = Playwright.create();

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();

        if (headless) {
            contextOptions.setViewportSize(1920, 1080);
        } else {
            options.setArgs(Arrays.asList("--start-maximized"));
            contextOptions.setViewportSize(null);
        }

        browser = playwright.chromium().launch(options);
        context = browser.newContext(contextOptions);
        page = context.newPage();
//		page.setDefaultTimeout(4000);
	}

	@AfterMethod
	public void tearDown(ITestResult result) {

		if (result.getStatus() == ITestResult.FAILURE) {
			test.fail(result.getThrowable());

			String screenshotPath = ScreenshotUtils.takeScreenshotBase64(page, result.getName());
			test.addScreenCaptureFromBase64String(screenshotPath);

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.pass("Test Passed Successfully");
		} else {
			test.skip("Test Skipped");
		}

		extent.flush();

		if (browser != null)
			browser.close();
		if (playwright != null)
			playwright.close();
	}

}
