package pages;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Base class for every page.
 * @author Kedar Saravate
 */
public class BasePage{
	public WebDriver driver;

	public BasePage(WebDriver driver) {
		this.driver = driver;	
	}

	/**
	 * Click any element
	 * @param WebElement
	 */
	public void click(WebElement element) {
		element.click();
	}

	/**
	 * Click any element by using JavascriptExecutor
	 * @param WebElement
	 */
	public void clickByJavaScriptExecutor(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
	}

	/**
	 * Check whether an element is visible/displayed on the browser.
	 * @param WebElement
	 */
	public boolean checkElementIsDisplayed(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOf(element));
		return element.isDisplayed();
	}

	/**
	 * Opens an URL
	 * @param String URL
	 */
	public void openLink(String url) {
		driver.get(url);
	}

	/**
	 * Opens an URL using navigation.
	 * @param String URL
	 */
	public void navigateTo(String url) {
		driver.navigate().to(url);
	}

	/**
	 * Set text in text box
	 * @param WebElement
	 * @param String - Value to be entered.
	 */
	public void setText(WebElement element, String value) {
		element.sendKeys(value);
	}
	
	/**
	 * Takes screenshot
	 * @param String screenShotLocation - The location/path of the screenshot
	 * @param String screenShotName - Name of the screenshot
	 */
	public void takeScreenShot(String screenShotLocation, String screenShotName){
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File desFile = new File(screenShotLocation + screenShotName + ".jpg");
			FileUtils.copyFile(scrFile, desFile);
		} catch(IOException e) {
			e.getMessage();
		}
	}

	/**
	 * Checks whether the value passed is present in the page source.
	 * @param String value
	 * @return boolean true/false
	 */
	public boolean containValue(String value) {
		return driver.getPageSource().contains(value);
	}

	/**
	 * Gives size of the element list.
	 * @param List<WebElement>
	 * @return int value.
	 */
	public int getSizeOfList(List<WebElement> elements) {
		return elements.size();
	}
	
	/**
	 * Highlights the elements in YELLOW color and draw solid RED border around it to highlight an element.
	 * @param WebElement
	 */
	public void drawBorderAndHighlightElement(WebElement element){
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].setAttribute('style', 'border:3px solid red; background:yellow')", element);
       
    }

	/**
	 * Scroll to particular element on browser.
	 * @param WebElement
	 */
	public void scrollForForTheElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOf(element));
		Actions action = new Actions(driver); 	
		action.moveToElement(element).build().perform();
	}

	/**
	 * Gets element's text value.
	 * @param WebElement
	 * @return String text
	 */
	public String getTextValue(WebElement element) {
		return element.getText();
	}

	/**
	 * Checks whether the word is part of current page URL or not.
	 * @param String word
	 * @return boolean true/false
	 */
	public boolean wordContainInURL(String word) {
		return driver.getCurrentUrl().contains(word);
	}
	
	/**
	 * Returns current URL
	 * @return String current URL
	 */
	public String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	/**
	 * Initializes any Page/WebElement for PageFactory
	 * @param Class<T>
	 * @return <T> T.
	 */
	public <T> T getCurrentPageAs(Class<T> T) {
		return PageFactory.initElements(driver, T);
	}

}