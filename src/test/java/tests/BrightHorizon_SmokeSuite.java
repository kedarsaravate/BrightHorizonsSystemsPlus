package tests;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import pages.BrightHorizon_CentersPage;
import pages.BrightHorizon_SearchPage;
import pages.BrightHorizon_SectionPage;

public class BrightHorizon_SmokeSuite extends BaseTest {
	
	Logger log = Logger.getLogger(BrightHorizon_SmokeSuite.class);

	@Test (priority = 1, groups = {"smoke"} , description ="1)Validation of 'edssist' as a part of URL for EdAssist Solutions page. "
			+ " 2)Validation of number of dots and comments are equal on Family Solutions page.") 
	public void verifyURLContentsAndMatchingNumberOfDotsAndComments() { 
		
		log.info("Create object of the BrightHorizon_SectionPage page.");
		BrightHorizon_SectionPage bhSectionPage = brightHorizon_SectionPage() ; 
		log.info("Verifying the content of URL for EdAssist Solutions");
		String urlContents=searchConfigPropFile.getProperty("url_contains");
		bhSectionPage.verifyURLContents(urlContents);
		log.info("Verifying the dots and comments inside Family Solutions");
		bhSectionPage.verifyDotsCommentsCount();
	}
	
	@Test (priority = 2, groups = {"smoke"} , description = "Validation of valid search criteria is the first searched result after searching.")
	public void verifyValidSearchCriteriaIsFirstResult() {
		log.info("Create object of the BrightHorizon_SearchPage page.");
		BrightHorizon_SearchPage bhSearchPage = brightHorizon_SearchPage();
		log.info("Getting value of the search text from properties file.");
		String searchCriteria=searchConfigPropFile.getProperty("search_education");
		log.info("Validate the actual search now for search text as: " + searchCriteria);
		bhSearchPage.verifySearchFunctionality(searchCriteria); 
	}

	@Test (priority = 3, groups = {"smoke"} , description = "Validation of invalid search criteria is not the first searched result after searching.")
	public void verifyInvalidSearchCriteriaIsNotFirstResult() {
		log.info("Create object of the BrightHorizon_SearchPage page.");
		BrightHorizon_SearchPage bhSearchPage = brightHorizon_SearchPage();
		log.info("Getting value of the search text from properties file.");
		String searchCriteria=searchConfigPropFile.getProperty("search_education_not_exist");
		log.info("Validate the actual search now for search text as: " + searchCriteria);
		bhSearchPage.verifySearchFunctionality(searchCriteria); 
	}
	
	@Test (priority = 4, groups = {"smoke"} , description ="1)Validation of 'child-care-locator' as a part of URL for Find A Center page. "
			+ " 2)Validation of number of centers, center name and address after search by city name on Find A Center page.") 
	public void verifyURLContentsAndMatchingNumberOfCenters() { 
		
		log.info("Create object of the BrightHorizon_CentersPage page.");
		BrightHorizon_CentersPage bhCentersPage = brightHorizon_CentersPage() ; 
		log.info("Verifying the content of URL for Find A Center Page");
		String urlContents=searchConfigPropFile.getProperty("url_contains_child_care_locator");
		bhCentersPage.verifyURLContents(urlContents);
		String locationText=searchConfigPropFile.getProperty("location");
		log.info("Verifying the center name, address by City search for Find A Center Page");
		bhCentersPage.searchCenterByLocationAndVerifyCountAndOtherDetails(locationText);		
	}
}