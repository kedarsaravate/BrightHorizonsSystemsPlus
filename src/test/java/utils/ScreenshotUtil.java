package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ScreenshotUtil {

	public static void takeScreenshot(WebDriver driver, String fileName) {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File("./screenshots/" + fileName + ".png"));
			System.out.println("Screenshot taken: " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String takeScreenshotWithAbsolutePath(WebDriver driver, String fileName) {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File destFile = new File("./screenshots/" + fileName + ".png");
		try {
			FileUtils.copyFile(srcFile, destFile);
			System.out.println("Screenshot taken: " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile.getAbsolutePath();
	}

	public static void takeFullPageScreenshot(WebDriver driver, String fileName) {
		Screenshot screenshoot=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		try {
			ImageIO.write(screenshoot.getImage(),"PNG",new File("./screenshots/" + fileName + ".png"));
			System.out.println("Full Page Screenshot taken: " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void takeScreenshotWithURLAddressBar(WebDriver driver, String fileName) {    	
		try {
			Robot robot = new Robot();
			BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(screenShot, "JPG", new File("./screenshots/" + fileName + ".png"));
			System.out.println("Screenshot with URL Address Bar taken: " + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




}
