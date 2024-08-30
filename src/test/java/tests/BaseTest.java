package tests;

import java.time.Duration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.BrightHorizon_SearchPage;
import pages.BrightHorizon_SectionPage;
import reporting.ExtentManager;
import utils.ReadPropertiesFile;

@Listeners(reporting.ExtentReportManager.class)
public class BaseTest{
	public WebDriver driver;
	Properties searchConfigPropFile;
	Properties configPropFile;
	Logger log = Logger.getLogger(BaseTest.class);
	int implicit_wait;
	String browser;
	
	@BeforeSuite
	public void beforeSuite() {
		log.info("@BeforeSuite method execution");
		ExtentManager.setExtentReport();
		//Load the configuration file.
		configPropFile = ReadPropertiesFile.getConfigFile("config.properties");
		//Load search data file.
		String searchConfigFileName = configPropFile.getProperty("search_data_file_name");
		searchConfigPropFile = ReadPropertiesFile.getConfigFile(searchConfigFileName);
		//Load implicit wait time in seconds.
		implicit_wait = Integer.parseInt(configPropFile.getProperty("implicit_wait"));

	}

	@BeforeTest
	public void setUp() {
		log.info("@BeforeTest method execution");
		browser = configPropFile.getProperty("browser");
		
		if (browser.equals("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions firefoxOption = new FirefoxOptions();
			firefoxOption.addPreference("dom.disable_open_during_load", true); // Disable pop-up windows
			firefoxOption.addPreference("privacy.popups.policy", 2); // Block all pop-ups
			firefoxOption.addPreference("dom.webnotifications.enabled", false); // Disable web notifications
			firefoxOption.addPreference("geo.enabled", false); // Disable geo-location
			driver = new FirefoxDriver(firefoxOption);
		}
		else if (browser.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver.exe"); 
			ChromeOptions options = new ChromeOptions(); 
			options.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(options);         
		}
		else if (browser.equals("Edge")) {
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--remote-allow-origins=*");
			driver = new EdgeDriver(options);
		}
		else {
			throw new RuntimeException("Browser type unsupported");
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicit_wait));
		driver.manage().window().maximize(); 
	}
	
	@AfterTest
	public void setUpDown() {
		log.info("@AfterTest method execution");
		driver.quit();
	}
	
	@AfterSuite
	public void afterSuite() {
		log.info("@AfterSuite method execution");
		ExtentManager.endExtentReport();
	}
	
	public <T> T getCurrentPageAs(Class<T> T) {
		return PageFactory.initElements(driver, T);
	}

	public BrightHorizon_SectionPage brightHorizon_SectionPage() { 
		return (BrightHorizon_SectionPage) getCurrentPageAs(BrightHorizon_SectionPage.class);
	}

	public BrightHorizon_SearchPage brightHorizon_SearchPage() { 
		return (BrightHorizon_SearchPage) getCurrentPageAs(BrightHorizon_SearchPage.class);
	}
	
	
}
