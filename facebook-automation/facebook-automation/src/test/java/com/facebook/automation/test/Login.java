package com.facebook.automation.test;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeTest;

public class Login {

	static WebDriver webdriver;
	static HSSFWorkbook workbook;
	static HSSFSheet sheet;
	static HSSFCell cell;
	    
	@BeforeTest
	public static void open_browser() {
		webdriver = new ChromeDriver();
		webdriver.get("https://www.facebook.com/");
		webdriver.manage().window().maximize();
		
	}

	public void login_passed() {
		try {
			webdriver.findElement(By.name("email")).sendKeys("fatma.tarek.cs@gmail.com");
			webdriver.findElement(By.name("pass")).sendKeys("10111995F@tma");
			webdriver.findElement(By.name("login")).click();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void login_failed_empty_form() {
		try {
			webdriver.findElement(By.name("email")).sendKeys("");
			webdriver.findElement(By.name("pass")).sendKeys("");
			webdriver.findElement(By.name("login")).click();

			WebElement errorMessageElement = webdriver
					.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/div/div[2]/div[2]/form/div/div[1]/div[2]"));
			String errorMessage = errorMessageElement.getText();

			AssertJUnit.assertEquals(errorMessageElement.isDisplayed(), true);
			AssertJUnit.assertEquals(errorMessage,
					"The email or mobile number you entered isn’t connected to an account. Find your account and log in.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void login_failed_empty_email() {
		try {
			webdriver.findElement(By.name("email")).sendKeys("");
			webdriver.findElement(By.name("pass")).sendKeys("10111995F@tma");
			webdriver.findElement(By.name("login")).click();

			WebElement errorMessageElement = webdriver
					.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/div/div[2]/div[2]/form/div/div[1]/div[2]"));
			String errorMessage = errorMessageElement.getText();

			AssertJUnit.assertEquals(errorMessageElement.isDisplayed(), true);
			AssertJUnit.assertEquals(errorMessage,
					"The email or mobile number you entered isn’t connected to an account. Find your account and log in.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void login_failed_empty_password() {
		try {
			webdriver.findElement(By.name("email")).sendKeys("fatma.tarek.cs@gmail.com");
			webdriver.findElement(By.name("pass")).sendKeys("");
			webdriver.findElement(By.name("login")).click();

			String errorMessage = webdriver.findElement(By.className("_akzt")).getText();
			AssertJUnit.assertEquals(errorMessage, "The password you entered is incorrect.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void login_failed_invalid_email_format() {

		try {
			webdriver.findElement(By.name("email")).sendKeys("fatma.tarek.cs");
			webdriver.findElement(By.name("pass")).sendKeys("10111995F@tma");
			webdriver.findElement(By.name("login")).click();

			WebElement errorMessageElement = webdriver
					.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/div/div[2]/div[2]/form/div/div[1]/div[2]"));
			String errorMessage = errorMessageElement.getText();

			AssertJUnit.assertEquals(errorMessageElement.isDisplayed(), true);
			AssertJUnit.assertEquals(errorMessage,
					"The email or mobile number you entered isn’t connected to an account. Find your account and log in.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void login_failed_email_login_invalid_password() {
		try {
			webdriver.findElement(By.name("email")).sendKeys("fatma.tarek.cs@gmail.com");
			webdriver.findElement(By.name("pass")).sendKeys("10111995");
			webdriver.findElement(By.name("login")).click();

			WebElement errorMessageElement = webdriver
					.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/div/div/form/div/div[1]/div/div[2]/h2"));
			String errorMessage = errorMessageElement.getText();

			AssertJUnit.assertEquals(errorMessageElement.isDisplayed(), true);
			AssertJUnit.assertEquals(errorMessage, "We'll send you a code to your email");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void login_failed_mobile_login_invalid_password() {
		try {
			webdriver.findElement(By.name("email")).sendKeys("+971505692280");
			webdriver.findElement(By.name("pass")).sendKeys("10111995");
			webdriver.findElement(By.name("login")).click();

			WebElement errorMessageElement = webdriver
					.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/div/div/form/div/div[1]/div/div[2]/h2"));
			String errorMessage = errorMessageElement.getText();

			AssertJUnit.assertEquals(errorMessageElement.isDisplayed(), true);
			AssertJUnit.assertEquals(errorMessage, "We'll send you a code to your mobile number");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
