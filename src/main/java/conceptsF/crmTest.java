package conceptsF;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class crmTest {
	String browser;
	String url;
	WebDriver driver;


	// ELEMENT LIST
	By USERNAME_LOCATOR = By.xpath("//input[@id='username']");
	By PASSWORD_LOCATOR = By.xpath("//*[@id=\"password\"]");
	By SIGNIN_BUTTON_LOCATOR = By.xpath("/html/body/div/div/div/form/div[3]/button");
	By DASHBOARD_HEADER_LOCATOR = By.xpath("//h2[contains(text(), 'Dashboard')]");

	By CUSTOMER_MENU_LOCATOR = By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]");
	By ADD_CUSTOMER_MENU_LOCATOR = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
	By ADD_CONTACT_HEADER_LOCATOR = By.xpath("//*[@id=\"page-wrapper\"]/div[3]/div[1]/div/div/div/div[1]/h5");
	By FULL_NAME_LOCATOR = By.xpath("//*[@id=\"account\"]");
	By COMPANY_DROPDOWN_LOCATOR = By.xpath("//select[@id=\"cid\"]");
	By EMAIL_LOCATOR = By.xpath("//*[@id=\"email\"]");
	By PHONE_LOCATOR = By.xpath("//input[@id='phone']");
	By ADDRESS_LOCATOR = By.xpath("//input[@id='address']");
	By CITY_LOCATOR = By.xpath("//input[@id='city']");
	By STATE_LOCATOR = By.xpath("//input[@id='state']");
	By ZIPCODE_LOCATOR = By.xpath("//input[@id='zip']");
	By COUNTRY_LOCATOR = By.xpath("//select[@id=\"country\"]");
	By CURRENCY_LOCATOR = By.xpath("//select[@id= 'currency']");
	By GROUP_LOCATOR = By.xpath("//select[@id= 'group']");
	By PASSWORD2_LOCATOR = By.xpath("//input[@id= 'password']");
	By CPASSWORD_LOCATOR = By.xpath("//input[@id= 'cpassword']");
	By WELCOME_EMAIL_LOCATOR = By.xpath("//label[@class='btn btn-primary btn-sm toggle-on']");
	By SUBMIT_BUTTON_LOCATOR = By.xpath("//button[@id='submit']");
	By SUMMARY_LOCATOR = By.xpath("//a[@id='summary']");
	String fullName= "TestNG" + generateRandom(9999);

	By LIST_CUSTOMER_LOCATOR = By.xpath("//ul[@id='side-menu']/li[3]/ul/li[2]/a");
	By SEARCH_BOX_LOCATOR = By.xpath("//input[@id='foo_filter']");
	By SEARCH_ICON_LOCATOR = By.xpath("//div[@id='page-wrapper']/div[3]/div[1]/div/div/div[2]/table/tbody/tr[1]/td[7]/a[1]");
	
	By CONTACT_HEADER_LOCATOR = By.xpath("//h2[text()=' Contacts ']");

	@BeforeTest
	public void readConfig() {
		// FileReader //Scanner //InputStream //BufferedReader

		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser Used = " + browser);
			url = prop.getProperty("url");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "driver\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	// @Test(priority = 1)
	public void loginTest() {
		driver.findElement(USERNAME_LOCATOR).sendKeys("demo@techfios.com");
		driver.findElement(PASSWORD_LOCATOR).sendKeys("abc123");
		driver.findElement(SIGNIN_BUTTON_LOCATOR).click();

		String dashboardHearderText = driver.findElement(DASHBOARD_HEADER_LOCATOR).getText();
		Assert.assertEquals(dashboardHearderText, "Dashboard", "Wrong Page!!");
	}

	@Test(priority = 2)
	public void addCustomerTest() throws InterruptedException {
		loginTest();
		driver.findElement(CUSTOMER_MENU_LOCATOR).click();
		driver.findElement(ADD_CUSTOMER_MENU_LOCATOR).click();

		waitElement(driver, ADD_CONTACT_HEADER_LOCATOR, 5);
		Assert.assertEquals(driver.findElement(ADD_CONTACT_HEADER_LOCATOR).getText(), "Add Contact", "Wrong page!!");

		driver.findElement(FULL_NAME_LOCATOR).sendKeys(fullName);
		selectFromDropdown(COMPANY_DROPDOWN_LOCATOR, "ATT");
		driver.findElement(EMAIL_LOCATOR).sendKeys(generateRandom(9999) + "demo@techfios.com");
		driver.findElement(PHONE_LOCATOR).sendKeys(generateRandom(999) + "8576934568");
		driver.findElement(ADDRESS_LOCATOR).sendKeys("584 connecticut dr.");
		driver.findElement(CITY_LOCATOR).sendKeys("Malden");
		driver.findElement(STATE_LOCATOR).sendKeys("MA");
		driver.findElement(ZIPCODE_LOCATOR).sendKeys("02148");
		selectFromDropdown(COUNTRY_LOCATOR, "Afghanistan");
		selectFromDropdown(CURRENCY_LOCATOR, "USD");
		selectFromDropdown(GROUP_LOCATOR, "None");
		driver.findElement(PASSWORD2_LOCATOR).sendKeys("abc123");
		driver.findElement(CPASSWORD_LOCATOR).sendKeys("abc123");
		driver.findElement(WELCOME_EMAIL_LOCATOR).click();
		driver.findElement(SUBMIT_BUTTON_LOCATOR).click();
		
		waitElement(driver, SUMMARY_LOCATOR, 10);
		Assert.assertEquals(driver.findElement(SUMMARY_LOCATOR).getText(), "Summary", "Wrong page!!");
		System.out.println(driver.findElement(SUMMARY_LOCATOR).getText());
		
		
		//locate unique element in profile page
		waitElement(driver, LIST_CUSTOMER_LOCATOR, 20);
		driver.findElement(LIST_CUSTOMER_LOCATOR).click();
		driver.findElement(SEARCH_BOX_LOCATOR).sendKeys(fullName);
		driver.findElement(SEARCH_ICON_LOCATOR).click();

		waitElement(driver, CONTACT_HEADER_LOCATOR, 5);
		Assert.assertEquals(driver.findElement(CONTACT_HEADER_LOCATOR).getText(), "Contacts", "Page not found!!");
		System.out.println(driver.findElement(CONTACT_HEADER_LOCATOR).getText());
		
	}

	public void waitElement(WebDriver driver, By locator1, int timeUnitSecond) {
		WebDriverWait wait = new WebDriverWait(driver, timeUnitSecond);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator1));

	}

	public void selectFromDropdown(By locator, String visibleText) {
		Select sel = new Select(driver.findElement(locator));
		sel.selectByVisibleText(visibleText);
	}

	public int generateRandom(int bounderyNum) {
		Random rnd = new Random();
		int generatedNum = rnd.nextInt(bounderyNum);
		return generatedNum;

	}

	 @AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
