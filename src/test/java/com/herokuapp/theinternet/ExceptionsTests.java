package com.herokuapp.theinternet;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ExceptionsTests {

	private WebDriver driver;

	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun = true)
	private void setUp(String browser) {
//		Create driver

		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			driver = new ChromeDriver();
			break;

		case "firefox":
			System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
			driver = new FirefoxDriver();
			break;

		default:
			System.out.println("Do not know how to start " + browser + ", starting chrome instead");
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}

		driver.manage().window().maximize();
	}

	@Test
	public void noSuchElementExceptionTest() {
		System.out.println("Starting noSuchElementException");

//		open test page
		String url = "https://practicetestautomation.com/practice-test-exceptions/";
		driver.get(url);
		System.out.println("Page is opened.");

//	click on Add button 

		WebElement addButtonElement = driver.findElement(By.id("add_btn"));
		addButtonElement.click();

//		implicit wait
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

//		explicit wait for row 2 to be visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));

//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}

//		Row 2 does not appear instantly sa a WAIT is needed

//		verification: 
//		verifiy row 2
		WebElement row2Input = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
		Assert.assertTrue(row2Input.isDisplayed(), "The Row2 element is not displayed");

	}

	@AfterMethod(alwaysRun = true)
	private void tearDown() {
		// Close browser
		driver.quit();
	}

	@Test
	public void elementNotInteractableTest() {
		System.out.println("Starting noSuchElementException");

//		open test page
		String url = "https://practicetestautomation.com/practice-test-exceptions/";
		driver.get(url);
		System.out.println("Page is opened.");

//	click on Add button 

		WebElement addButtonElement = driver.findElement(By.id("add_btn"));
		addButtonElement.click();

//		implicit wait
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

//		explicit wait 
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));

//		type text into second input field

		WebElement row2Input = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
		row2Input.sendKeys("text");

		// click on the save button
		WebElement saveButton = driver.findElement(By.xpath("//div[@id='row2']/button[@name= 'Save']"));
		saveButton.click();

//		Verification"
//		Verifiy the text saved
		WebElement confirmationMessage = driver.findElement(By.id("confirmation"));
		String messageText = confirmationMessage.getText();
		Assert.assertEquals(messageText, "Row 2 was saved", "Confirmation message text is not expected");

	}

	@Test
	public void invalidElementStateExceptionTest() {
		System.out.println("Starting invalidElementStateExceptionTest");

//		open test page
		String url = "https://practicetestautomation.com/practice-test-exceptions/";
		driver.get(url);
		System.out.println("Page is opened.");

//		Enable the input field to resolve the exception
		WebElement editButton = driver.findElement(By.xpath("//button[@id='edit_btn']"));
		editButton.click();

//		Clear the input field

		WebElement row1Input = driver.findElement(By.xpath("//div[@id='row1']/input"));
		row1Input.clear();

//		Type text into the field
		row1Input.sendKeys("new text");

//		Click on the save button
		WebElement saveButton = driver.findElement(By.id("save_btn"));
		saveButton.click();

//		Verification

//		Verify text changed
		// Verify text changed
		String value = row1Input.getAttribute("value");
		Assert.assertEquals(value, "new text", "Input 1 field value is not expected");

//		Verify text saved
		WebElement confirmationMessage = driver.findElement(By.id("confirmation"));
		String messageText = confirmationMessage.getText();
		Assert.assertEquals(messageText, "Row 1 was saved", "Confirmation message text is not expected");

	}

	@Test
	public void staleElementReferenceExceptionTest() {

		System.out.println("Starting staleElementReferenceExceptionTest");

//		open test page
		String url = "https://practicetestautomation.com/practice-test-exceptions/";
		driver.get(url);
		System.out.println("Page is opened.");

//		click on Add button 
		WebElement addButtonElement = driver.findElement(By.id("add_btn"));
		addButtonElement.click();

//		Verification
//		Verify that instructions is no longer displayed
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("instructions"))),
				"The text is still displayed");
	}

	@Test
	public void timeoutExceptionTest() {

		System.out.println("Starting timeoutExceptionTest");

//		open test page
		String url = "https://practicetestautomation.com/practice-test-exceptions/";
		driver.get(url);
		System.out.println("Page is opened.");

//		click on Add button 

		WebElement addButtonElement = driver.findElement(By.id("add_btn"));
		addButtonElement.click();

//		explicit wait for row 2 to be visible

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
		WebElement row2Input = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));

//		Verification
//		Verify that row2 is displayed
		Assert.assertTrue(row2Input.isDisplayed(), "The Row2 element is not displayed");
	}
}
