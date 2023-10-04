package com.facebook.automation.test;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SignUp {

	static WebDriver webdriver;

	final String fnameErrorIconXpath = "/html/body/div[3]/div[2]/div/div/div[2]/div/div/div[1]/form/div[1]/div[1]/div[1]/div[1]/div/i[1]";
	final String lnameErrorIconXpath = "/html/body/div[3]/div[2]/div/div/div[2]/div/div/div[1]/form/div[1]/div[1]/div[1]/div[2]/div/i[1]";
	final String emailErrorIconXpath = "/html/body/div[3]/div[2]/div/div/div[2]/div/div/div[1]/form/div[1]/div[2]/div/i[1]";
	final String passwordErrorIconXpath = "html/body/div[3]/div[2]/div/div/div[2]/div/div/div[1]/form/div[1]/div[4]/div/i[1]";
	final String birthdayErrorIconXpath = "/html/body/div[3]/div[2]/div/div/div[2]/div/div/div[1]/form/div[1]/div[7]/i[1]";
	final String genderErrorIconXpath = "/html/body/div[3]/div[2]/div/div/div[2]/div/div/div[1]/form/div[1]/div[7]/i[1]";

	@BeforeClass
	public static void open_browser() {
		webdriver = new ChromeDriver();
		webdriver.get("https://www.facebook.com/");
		webdriver.manage().window().maximize();
	}

	@Test
	public void signup_success_form() {
		try {
			webdriver.findElement(By.linkText("Create new account")).click();

			// explicit wait until popup is openned
			WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOfElementLocated(By.className("_n3"))));

			// 1. set first name input field 
			webdriver.findElement(By.name("firstname")).sendKeys("Fatma");
			// 2. set last name input field 
			webdriver.findElement(By.name("lastname")).sendKeys("Tarek");
			// 3. set email input field 
			webdriver.findElement(By.name("reg_email__")).sendKeys("fatma.tarek.cs@gmail.com");
			webdriver.findElement(By.name("reg_email_confirmation__")).sendKeys("fatma.tarek.cs@gmail.com");
			// 4. set password input field 
			webdriver.findElement(By.name("reg_passwd__")).sendKeys("10111995F@tma");
			// 5. set gender input field 
			webdriver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div/div/div[1]/form/div[1]/div[7]/span/span[1]/label")).click();
		   
			// 6. set birthday input field 
			Select drpMonth = new Select(webdriver.findElement(By.name("birthday_month")));
			drpMonth.selectByValue("11");
			Select drpDay = new Select(webdriver.findElement(By.name("birthday_day")));
			drpDay.selectByValue("10");
			Select drpYear = new Select(webdriver.findElement(By.name("birthday_year")));
			drpYear.selectByValue("1995");
			
			// 7. click sign up button
			webdriver.findElement(By.name("websubmit")).click();
			
			// 8. Assert success (signup success and profile name appeared)
			WebElement profileName = webdriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[3]/div/div/div/div[1]/div[1]/div/div[1]/div/div/div[1]/div/div/div[1]/ul/li/div/a/div[1]/div[2]/div/div/div/div/span/span"));
			String actual = profileName.getText();
			String expected = "Fatma Tarek";
			Assert.assertEquals(expected, actual);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void signup_failed_empty_form() {
		try {
			webdriver.findElement(By.linkText("Create new account")).click();

			// explicit wait until popup is openned
			WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOfElementLocated(By.className("_n3"))));

			// 1. validate first name empty field
			validate_fname_empty_field();

			// 2. validate last name empty field
			validate_lname_empty_field();

			// 3. validate email empty field
			validate_email_empty_field();

			// 4. validate password empty field
			validate_password_empty_field();

			// 5. validate birthday empty field
			validate_birthday_not_selected_field();

			// 6. validate birthday empty field
			validate_gender_not_selected_field();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void validate_fname_empty_field() {
		// 1. set first name input field as empty
		webdriver.findElement(By.name("firstname")).sendKeys("");
		// 2. click sign up button
		webdriver.findElement(By.name("websubmit")).click();
		// 3. move cursor to last name input field
		new Actions(webdriver).click(webdriver.findElement(By.name("lastname"))).perform();
		// 4. find first name field warning icon by xpath
		WebElement fnameWarningIcon = webdriver.findElement(By.xpath(fnameErrorIconXpath));
		// 5. first name field warning icon should be displayed
		AssertJUnit.assertEquals(fnameWarningIcon.isDisplayed(), true);
		System.out.println("first name warning icon displayed :[" + fnameWarningIcon.isDisplayed() + "]");
	}

	private void validate_lname_empty_field() {
		// 1. set last name input field as empty
		webdriver.findElement(By.name("lastname")).sendKeys("");
		// 2. click sign up button
		webdriver.findElement(By.name("websubmit")).click();
		// 3. move cursor to email input field
		new Actions(webdriver).click(webdriver.findElement(By.name("reg_email__"))).perform();
		// 4. find last name field warning icon by xpath
		WebElement lnameWarningIcon = webdriver.findElement(By.xpath(lnameErrorIconXpath));
		// 5. last name field warning icon should be displayed
		AssertJUnit.assertEquals(lnameWarningIcon.isDisplayed(), true);
		System.out.println("last name warning icon displayed :[" + lnameWarningIcon.isDisplayed() + "]");
	}

	private void validate_email_empty_field() {
		// 1. set email input field as empty
		webdriver.findElement(By.name("reg_email__")).sendKeys("");
		// 2. click sign up button
		webdriver.findElement(By.name("websubmit")).click();
		// 3. move cursor to password input field
		new Actions(webdriver).click(webdriver.findElement(By.name("reg_passwd__"))).perform();
		// 4. find email field warning icon by xpath
		WebElement emailWarningIcon = webdriver.findElement(By.xpath(emailErrorIconXpath));
		// 5. email field warning icon should be displayed
		AssertJUnit.assertEquals(emailWarningIcon.isDisplayed(), true);
		System.out.println("email warning icon displayed :[" + emailWarningIcon.isDisplayed() + "]");
	}

	private void validate_password_empty_field() {
		// 1. set password input field as empty
		webdriver.findElement(By.name("reg_passwd__")).sendKeys("");
		// 2. click sign up button
		webdriver.findElement(By.name("websubmit")).click();
		// 3. find password field warning icon by xpath
		WebElement passwordWarningIcon = webdriver.findElement(By.xpath(passwordErrorIconXpath));
		// 4. password field warning icon should be displayed
		AssertJUnit.assertEquals(passwordWarningIcon.isDisplayed(), true);
		System.out.println("password warning icon displayed :[" + passwordWarningIcon.isDisplayed() + "]");
	}

	private void validate_birthday_not_selected_field() {
		// 2. find birthday field warning icon by xpath
		WebElement birthdayWarningIcon = webdriver.findElement(By.xpath(birthdayErrorIconXpath));
		// 3. birthday field warning icon should be displayed
		AssertJUnit.assertEquals(birthdayWarningIcon.isDisplayed(), true);
		System.out.println("birthday warning icon displayed :[" + birthdayWarningIcon.isDisplayed() + "]");
	}

	private void validate_gender_not_selected_field() {
		// 2. find gender field warning icon by xpath
		WebElement genderWarningIcon = webdriver.findElement(By.xpath(genderErrorIconXpath));
		// 3. gender field warning icon should be displayed
		AssertJUnit.assertEquals(genderWarningIcon.isDisplayed(), true);
		System.out.println("gender warning icon displayed :[" + genderWarningIcon.isDisplayed() + "]");
	}
}
