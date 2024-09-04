package pages;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import reporting.ExtentReportManager;
import utils.DateUtils;
import utils.ScreenshotUtil;

public class BrightHorizon_CentersPage extends BasePage{

	public BrightHorizon_CentersPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath="//nav[@class='nav-shared txt-nav-hierarchy nav-top js-nav-shared js-nav-top']//li[@class='nav-item displayed-desktop']//a[@class='btn-nav btn btn-large btn-hollow color-nileblue global_header_findcenter track_cta_click'][normalize-space()='Find a Center']")
	WebElement findACenterButton;

	@FindBy(xpath="//button[@id='onetrust-accept-btn-handler']")
	WebElement acceptAllCookiesButton;

	@FindBy(xpath="//input[@id='addressInput']")
	WebElement inputLocationText;

	@FindBy(xpath="//span[@class='resultsNumber']")
	WebElement searchedLocationResultsCount;

	@FindBy(xpath="//div[contains(@class, 'centerResult infoWindow track_center_select covidOpen')] ")
	List<WebElement> allCentersCount;
	
	@FindBy(xpath="//div[contains(@class, 'centerResult infoWindow track_center_select covidOpen')][1]")
	WebElement firstSearchedResult;
	
	@FindBy(xpath="//div[contains(@class, 'centerResult infoWindow track_center_select covidOpen active')]//h3")
	WebElement firstSearchedResultCenterName;
	
	@FindBy(xpath="//div[contains(@class, 'centerResult infoWindow track_center_select covidOpen active')]//span[2]")
	WebElement firstSearchedResultCenterAddress;
	
	@FindBy(xpath="//span[@class='mapTooltip__headline']")
	WebElement centerNameOverPopup;
	
	@FindBy(xpath="//div[@class='mapTooltip__address']")
	WebElement centerAddressOverPopup;

	/**
	 * Clicks on Find A Center Button.
	 */
	public void clickOnFindACenterButton() {
		try {
			checkElementIsDisplayed(findACenterButton);
			clickByJavaScriptExecutor(findACenterButton);
		} catch(Exception e) {
			e.getMessage();
		}
	}

	/**
	 * Validate whether given string is a part of current URL or not.
	 * @param String e.g. child-care-locator
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
	 * @param String e.g. child-care-locator
	 */
	public void verifyURLContents(String urlContents) {
		try {
			openLink(StaticData.homeUrl);
			clickOnFindACenterButton();
			Thread.sleep(4000);
			verifyURLContains(urlContents);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets input location text.
	 * @param String
	 */
	public void searchByTextInput(String locationText) {
		setText(inputLocationText,locationText);
		try {
			Thread.sleep(4000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		inputLocationText.sendKeys(Keys.ENTER);
		checkElementIsDisplayed(searchedLocationResultsCount);
		ScreenshotUtil.takeScreenshot(driver, "Searched_Center_"+DateUtils.getCurrentDate());
	}

	/**
	 * Searches the location by setting location.
	 * @param String e.g. New York
	 */
	public void serachCenterByLocation(String locationText) {
		searchByTextInput(locationText);
	}

	/**
	 * Get the count of centers after search
	 * @return int 
	 */
	public int getSearchedLocationCenterCount() {
		checkElementIsDisplayed(searchedLocationResultsCount);
		String count = searchedLocationResultsCount.getText().trim();
		return Integer.parseInt(count);
	}

	/**
	 * Returns number of centers post search on the page.
	 * @return int
	 */
	public int getAllSerachedCenterCount() {
		return getSizeOfList(allCentersCount);
	}

	/**
	 * Verifies searched center count and actual result count are equal or not.
	 * @param int
	 * @param int
	 */
	public void verifySearchedCenterCountAndActualCount(int serachedCenterCount, int actualResultCount) {
		if(serachedCenterCount == actualResultCount) {
			Assert.assertEquals(actualResultCount, serachedCenterCount, "The number of serached center count and actual result are euqal.");
			ExtentReportManager.logStepInfo("Number of actual centers in the list = " + actualResultCount + " and number of serached centers = " + serachedCenterCount + " are equal.", StaticData.STATUS_PASS);
		}else {
			Assert.assertEquals(actualResultCount, serachedCenterCount, "The number of serached center count and actual result are not euqal.");
			ExtentReportManager.logStepInfo("Number of actual centers in the list = " + actualResultCount + " and number of serached centers = " + serachedCenterCount + " are not equal.", StaticData.STATUS_FAIL);
		}
	}
	
	/**
	 * Verifies the center name on clicked center and the pop-up are equal or not.
	 * @param String
	 * @param String
	 */
	public void verifyCenterName(String serachedCenterName, String centerNameOnPopup) {
		if(serachedCenterName.equals(centerNameOnPopup)) {
			Assert.assertEquals(serachedCenterName, centerNameOnPopup, "The searched center name and name on the popup are euqal.");
			ExtentReportManager.logStepInfo("Name of the center = " + serachedCenterName + " and name on the popup = " + centerNameOnPopup + " are equal.", StaticData.STATUS_PASS);
		}else {
			Assert.assertEquals(serachedCenterName, centerNameOnPopup, "The searched center name and name on the popup are not euqal.");
			ExtentReportManager.logStepInfo("Name of the center = " + serachedCenterName + " and name on the popup = " + centerNameOnPopup + " are not equal.", StaticData.STATUS_PASS);
		}
	}
	
	/**
	 * Verifies the center address on clicked center and the pop-up are equal or not.
	 * @param String
	 * @param String
	 */
	public void verifyCenterAddress(String serachedCenterAddress, String centerAddressOnPopup) {
		if(serachedCenterAddress.equals(centerAddressOnPopup)) {
			Assert.assertEquals(serachedCenterAddress, centerAddressOnPopup, "The searched center address and address on the popup are euqal.");
			ExtentReportManager.logStepInfo("Address of the center = " + serachedCenterAddress + " and address on the popup = " + centerAddressOnPopup + " are equal.", StaticData.STATUS_PASS);
		}else {
			Assert.assertEquals(serachedCenterAddress, centerAddressOnPopup, "The searched center address and address on map are not euqal.");
			ExtentReportManager.logStepInfo("Address of the center = " + serachedCenterAddress + " and address on the popup = " + centerAddressOnPopup + " are not equal.", StaticData.STATUS_PASS);
		}
	}

	/**
	 * To get first center's name.
	 * @return String
	 */
    public String getFirstCenterName() {
    	return firstSearchedResultCenterName.getText().trim();
    }
    
    /**
	 * To get first center's address.
	 * @return String
	 */
    public String getFirstCenterAddress() {
    	return firstSearchedResultCenterAddress.getText().trim();
    }
    
    /**
	 * To get center's name on the pop-up.
	 * @return String
	 */
    public String getCenterNameOnPopup() {
    	return centerNameOverPopup.getText().trim();
    }
    
    /**
  	 * To get center's address on the pop-up. We need a complete address without new line character.
  	 * @return String
  	 */
    public String getCenterAddressOnPopup() {
    	return centerAddressOverPopup.getText().trim().replaceAll("\n", " ");
    }
    
    /**
     * Business method to search the center and verify the details of the center.
     * @param String e.g. New York
     */
	public void searchCenterByLocationAndVerifyCountAndOtherDetails(String locationCenterText) {
		clickOnFindACenterButton();
		serachCenterByLocation(locationCenterText);
		int searchedResultCount = getSearchedLocationCenterCount();
		int resultListCount = getAllSerachedCenterCount();
		verifySearchedCenterCountAndActualCount(searchedResultCount, resultListCount);
		firstSearchedResult.click();
		ScreenshotUtil.takeScreenshot(driver, "First_Searched_Location_"+DateUtils.getCurrentDate());
		String firstCenterName = getFirstCenterName();
		String firstCenteraddress = getFirstCenterAddress();
		String centerNameOnMap = getCenterNameOnPopup();
		String centerAddressOnMap = getCenterAddressOnPopup();
		verifyCenterName(firstCenterName, centerNameOnMap);
		verifyCenterAddress(firstCenteraddress, centerAddressOnMap);
	}



}
