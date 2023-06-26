package com.skadoosh.automation.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

	protected WebDriver webDriver;

	@BeforeSuite
	public void suiteSetup() {
		SeleniumManager seleniumManager = new SeleniumManager("chrome");
		this.webDriver = seleniumManager.getDriver();
	}

	@BeforeMethod
	public void testSetup() {

	}

	@AfterMethod()
	public void testTeardown() {
		webDriver.quit();
	}

}
