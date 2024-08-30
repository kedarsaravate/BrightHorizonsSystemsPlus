package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import reporting.ExtentReportManager;
import utils.DateUtils;
import utils.ScreenshotUtil;

/**
 * @author Kedar Saravate
 */
public class BrightHorizon_SectionPage extends BasePage{

	@FindBy(xpath="//a[@class='button track_cta_click home_lazy_edassist'][normalize-space()='Learn More']")
	WebElement edAssistlearnMoreButton;

	@FindBy(xpath="//a[@class='button track_cta_click home_lazy_familysolutions'][normalize-space()='Learn More']")
	WebElement familySolutionLearnMoreButton;

	@FindBy(xpath="//section[@id='Client-Results']//child::ul[@class='slick-dots' and @role='tablist']//li[@role='presentation']//button")
	List<WebElement> allDots;

	@FindBy(xpath="//section[@id='Client-Results']//child::div[@class='slick-slide slick-cloned' and (@data-slick-index='-1' or @data-slick-index='5' or @data-slick-index='6' or @data-slick-index='7' or @data-slick-index='8')]")
	List<WebElement> allCommentsAboveDots;

	@FindBy(xpath="//section[@id='Client-Results']//child::ul[@class='slick-dots']//li//button[contains(text(),'1')]")
	WebElement firstDot;

	@FindBy(xpath="//button[@id='onetrust-accept-btn-handler']")
	WebElement acceptAllCookiesButton;
	
	public BrightHorizon_SectionPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Clicks on LEARN MORE button for EdAssist solutions.
	 */
	public void clickOnLearnMoreButton() {
		try {
			clickByJavaScriptExecutor(edAssistlearnMoreButton);
		} catch(Exception e) {
			System.out.print("Error occurred while clicking on EDAssist Solutions LEARN MORE button. Exception details - "+e.getMessage());
		}
	}
	
	/**
	 * Clicks on LEARN MORE button for Family solutions.
	 */
	public void clickOnFamilySolutionsLearnMoreButton() {
		try {
			clickByJavaScriptExecutor(familySolutionLearnMoreButton);
		} catch(Exception e) {
			System.out.print("Error occurred while clicking on Family Solutions LEARN MORE button. Exception details - "+e.getMessage());
		}
	}

	/**
	 * Returns number of dots visible on the page.
	 * @return int
	 */
	public int getDotCount() {
		return getSizeOfList(allDots);
	}
	
	/**
	 * Returns number of comments above the dots visible on the page.
	 * @return int
	 */
	public int getCommentAboveDotsCount() {
		return getSizeOfList(allCommentsAboveDots);
	}

	/**
	 * Scroll till FIRST DOT is visible.
	 */
	public void scrollTillDotIsVisible() {
		scrollForForTheElement(firstDot);
	}

	/**
	 * Validate whether given string is a part of current URL or not.
	 * @param String e.g. edassit
	 */
	public void verifyURLContains(String urlContents) {
		ScreenshotUtil.takeScreenshotWithURLAddressBar(driver, urlContents +"_"+DateUtils.getCurrentDate());
		String currentURL= getCurrentURL();
		if(wordContainInURL(urlContents)) {
			ExtentReportManager.logStepInfo("The URL "+ currentURL +" contains " + urlContents + " text in it.", StaticData.STATUS_PASS);
			Assert.assertTrue(true,  "The URL contains " + urlContents + " text in it.");
		}else {
			ExtentReportManager.logStepInfo("The URL "+ currentURL +" do not contain " + urlContents + " text in it.", StaticData.STATUS_FAIL);
			Assert.assertTrue(false,  "The URL do not contain " + urlContents + " text in it.");
		}
	}

	/**
	 * Business method which validates whether URL contains the string or not.
	 * @param String e.g. Edassit
	 */
	public void verifyURLContents(String urlContents) {
		try {
			openLink(StaticData.homeUrl);
			click(acceptAllCookiesButton);
			clickOnLearnMoreButton();
			verifyURLContains(urlContents);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Verifies dot count and comments are equal or not.
	 * @param int
	 * @param int
	 */
	public void verifyDotsAndCommentsCount(int dotCount, int commentCount) {
		if(dotCount == commentCount) {
			Assert.assertEquals(commentCount, dotCount, "The number of comments and dots are euqal.");
			ExtentReportManager.logStepInfo("Number of comments = " + commentCount + " and number of dots = " + dotCount + " are equal.", StaticData.STATUS_PASS);
		}else {
			Assert.assertEquals(commentCount, dotCount, "The number of comments and dots do not matches.");
			ExtentReportManager.logStepInfo("Number of comments = " + commentCount + " and number of dots = " + dotCount + " are not equal.", StaticData.STATUS_FAIL);
		}
	}

	/**
	 * Business method verifies dot count and comments count.
	 */
	public void verifyDotsCommentsCount() {
		try {
			openLink(StaticData.homeUrl);
			clickOnFamilySolutionsLearnMoreButton();
			scrollTillDotIsVisible();
			int dotCount = getDotCount();
			int commentAboveDotsCount = getCommentAboveDotsCount();
			verifyDotsAndCommentsCount(dotCount, commentAboveDotsCount);
			ScreenshotUtil.takeScreenshot(driver, "FamilySolutions_Dots_Comments_"+DateUtils.getCurrentDate());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}