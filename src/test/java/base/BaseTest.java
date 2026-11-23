package base;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

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

	private static final ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
	private static final ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
	private static final ThreadLocal<BrowserContext> tlContext = new ThreadLocal<>();
	private static final ThreadLocal<Page> tlPage = new ThreadLocal<>();
	private static final ThreadLocal<Download> tlDownload = new ThreadLocal<>();
	private static final ThreadLocal<ExtentTest> tlTest = new ThreadLocal<>();

	public static Playwright getPlaywright() {
		return tlPlaywright.get();
	}

	public static Browser getBrowser() {
		return tlBrowser.get();
	}

	public static BrowserContext getContext() {
		return tlContext.get();
	}

	public static Page getPage() {
		return tlPage.get();
	}

	public static Download getDownload() {
		return tlDownload.get();
	}

	public static void setDownload(Download download) {
		tlDownload.set(download);
	}

	public static ExtentTest getTest() {
		return tlTest.get();
	}

	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method) {

		ExtentReports extent = ExtentManager.getInstance();
		ExtentTest extentTest = extent.createTest(method.getDeclaringClass().getSimpleName() + " - " + method.getName());
		tlTest.set(extentTest);

		// Added CI based logic for browser launch
		boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

		Playwright playwright = Playwright.create();
		tlPlaywright.set(playwright);

		BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);
		Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();

		if (headless) {
			contextOptions.setViewportSize(1920, 1080);
		} else {
			options.setArgs(Arrays.asList("--start-maximized"));
			contextOptions.setViewportSize(null);
		}

		Browser browser = playwright.chromium().launch(options);
		tlBrowser.set(browser);

		BrowserContext context = browser.newContext(contextOptions);
		tlContext.set(context);

		Page page = context.newPage();
		tlPage.set(page);
//		page.setDefaultTimeout(4000);
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) {

		ExtentTest currentTest = getTest();
		Page currentPage = getPage();

		if (currentTest != null) {
			if (result.getStatus() == ITestResult.FAILURE) {
				currentTest.fail(result.getThrowable());

				if (currentPage != null) {
					String screenshotPath = ScreenshotUtils.takeScreenshotBase64(currentPage, result.getName());
					if (screenshotPath != null) {
						currentTest.addScreenCaptureFromBase64String(screenshotPath);
					}
				}

			} else if (result.getStatus() == ITestResult.SUCCESS) {
				currentTest.pass("Test Passed Successfully");
			} else {
				currentTest.skip("Test Skipped");
			}
		}

		if (currentPage != null) {
			currentPage.close();
			tlPage.remove();
		}
		if (getContext() != null) {
			getContext().close();
			tlContext.remove();
		}
		if (getBrowser() != null) {
			getBrowser().close();
			tlBrowser.remove();
		}
		if (getPlaywright() != null) {
			getPlaywright().close();
			tlPlaywright.remove();
		}

		tlDownload.remove();
		tlTest.remove();
	}

	@AfterSuite(alwaysRun = true)
	public void flushReport() {
		ExtentReports extent = ExtentManager.getInstance();
		if (extent != null) {
			extent.flush();
		}
	}
}