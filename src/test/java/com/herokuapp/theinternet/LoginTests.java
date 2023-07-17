package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTests {

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

	@Test(priority = 1, groups = { "positiveTests", "smokeTests" })
	public void positiveLoginTest() {
		System.out.println("Starting loginTest");

//		open test page
		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page is opened.");

//		enter username
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys("tomsmith");

//		enter password
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("SuperSecretPassword!");

//		click on login button 
		WebElement logInButton = driver.findElement(By.tagName("button"));
		logInButton.click();

//		verification: 
//			new url

		String expectedUrl = "http://the-internet.herokuapp.com/secure";
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl, "The actual url is not the same as the expected url");

//			logout button is visible
		WebElement logOutButton = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
		Assert.assertTrue(logOutButton.isDisplayed(), "The logOut button is not displayed");

//			succsesful login message
		WebElement successfulLoginMessage = driver.findElement(By.xpath("//div[@id='flash']")); // this locator was foud
		// by the using Ranorex
		// Selocity chrome
		// extension

		String expectedMessage = "You logged into a secure area!";
		String actualMessage = successfulLoginMessage.getText();
		Assert.assertTrue(actualMessage.contains(expectedMessage), "The successful login message is not displayed");

	}

	@Parameters({ "username", "password", "expectedMessage" })
	@Test(priority = 2, groups = { "negativeTests", "smokeTests" })
	public void negativeLoginTests(String username, String password, String expectedMessage) {

		System.out.println("Starting negativeLoginTests with " + username + " and " + password);

//		open test page
		String url = "https://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("page is opened");

//		enter username
		WebElement usernameField = driver.findElement(By.id("username"));

		usernameField.sendKeys(username);

//		enter password
		WebElement passwordField = driver.findElement(By.name("password"));
		passwordField.sendKeys(password);

//		click on login button 
		WebElement logInButton = driver.findElement(By.tagName("button"));
		logInButton.click();

//		login button is still displayed
		WebElement login2Button = driver.findElement(By.xpath("//i[@class='fa fa-2x fa-sign-in']"));
		Assert.assertTrue(login2Button.isDisplayed(), "The login button is not displayed");

//		invalid password message

		WebElement invalidPasswordMessage = driver.findElement(By.cssSelector("div#flash.flash.error"));

		String actualMessage = invalidPasswordMessage.getText();
		Assert.assertTrue(actualMessage.contains(expectedMessage),
				"Actual message does not contain the expected message");

	}

	@AfterMethod(alwaysRun = true)
	private void tearDown() {
		// Close browser
		driver.quit();
	}

}
