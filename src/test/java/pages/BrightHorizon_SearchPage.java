package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import reporting.ExtentReportManager;
import utils.DateUtils;
import utils.ScreenshotUtil;

public class BrightHorizon_SearchPage extends BasePage{

	@FindBy(xpath="//div[@class='results container']//a[1]//h3")
	WebElement searchFirstResult;

	@FindBy(xpath="//a[@href='#subnav-search-desktop-top']//span[@class='icon-search bhc-icon-search-rounded']")
	WebElement searchIcon;

	@FindBy(xpath="(//button[@class='btn btn-large btn-solid color-buttercup btn-search']/..//input)[2]")
	WebElement inputSearchText;

	@FindBy(xpath="//nav[@id='subnav-search-desktop-top']//button[@type='submit'][normalize-space()='Search']")
	WebElement searchButton;
	
	@FindBy(xpath="//button[@id='onetrust-accept-btn-handler']")
	WebElement acceptAllCookiesButton;

	public BrightHorizon_SearchPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Validates the searched criteria value present as very first result in the list on search page or not.
	 * @param String
	 */
	public void verifyTheResultForSearchValue(String searchCriteriaText) {
		
		try {
			if(getTextValue(searchFirstResult).trim().equals(searchCriteriaText)) {
				ExtentReportManager.logStepInfo("The search criteria " + searchCriteriaText + " is the first result in search list.", StaticData.STATUS_PASS);
				drawBorderAndHighlightElement(searchFirstResult);
				ScreenshotUtil.takeScreenshot(driver, "Search_Result_Passed_"+DateUtils.getCurrentDate());
				Assert.assertTrue(true, "The search criteria " + searchCriteriaText + " is the first result in search list.");
			}else {
				ExtentReportManager.logStepInfo("The search criteria " + searchCriteriaText + " is not the first result in search list.", StaticData.STATUS_FAIL);
				drawBorderAndHighlightElement(searchFirstResult);
				ScreenshotUtil.takeScreenshot(driver, "Search_Result_Failed_"+DateUtils.getCurrentDate());
				Assert.assertTrue(false, "The search criteria " + searchCriteriaText + " is not the first result in search list.");	
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks whether input text box is visible or not.
	 * @return boolean true/false
	 */
	public boolean verifySearchTextBoxIsDisplaying() {
		return checkElementIsDisplayed(inputSearchText);

	}

	/**
	 * Sets input search text.
	 * @param String
	 */
	public void searchByTextInput(String inputText) {
		setText(inputSearchText,inputText);
	}

	/**
	 * Clicks on Search Button.
	 */
	public void clickOnSearchButton() {
		try {
			clickByJavaScriptExecutor(searchButton);
		} catch(Exception e) {
			e.getMessage();
		}
	}
	
	/**
	 * Clicks on Search Icon on top right hand corner.
	 */
	public void clickOnSearchIcon() {
		try {
			clickByJavaScriptExecutor(searchIcon);
		} catch(Exception e) {
			e.getMessage();
		}
	}
	
	/**
	 * Business method to search a given text and verify whether it is a first searched result or not.
	 * @param String
	 */
	public void verifySearchFunctionality(String searchCriteriaText) {
			openLink(StaticData.homeUrl);
			//click(acceptAllCookiesButton);
			clickOnSearchIcon();
			verifySearchTextBoxIsDisplaying();
			searchByTextInput(searchCriteriaText);
			clickOnSearchButton();
			verifyTheResultForSearchValue(searchCriteriaText);
	}
}