package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import com.microsoft.playwright.Page;

public class ScreenshotUtils {

    // Saves the screenshot to disk AND returns it as base64
    public static String takeScreenshotBase64(Page page, String testName) {
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            Path folder = Paths.get("test-output", "FailureScreenshots");
            Files.createDirectories(folder);

            Path file = folder.resolve(testName + "_" + timestamp + ".png");

            byte[] bytes = page.screenshot(new Page.ScreenshotOptions()
                    .setPath(file)
                    .setFullPage(true));

            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}