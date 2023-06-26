package com.skadoosh.automation.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class BaseTest {

	protected WebDriver webDriver;
	EnvironmentConfig envConfig = EnvironmentConfig.getInstance();

	@Parameters({"testType", "platform"})
	@BeforeTest
	public void testSetup(String testType, String platform) {
		envConfig.setProperty("testType", testType);
		envConfig.setProperty("platform", platform);
	}

	@BeforeMethod
	public void methodSetup() {
		if(envConfig.getProperty("testType").equalsIgnoreCase("web")) {
			SeleniumManager seleniumManager = new SeleniumManager(envConfig.getProperty("platform"));
			this.webDriver = seleniumManager.getDriver();
		}
	}

	@AfterMethod()
	public void methodTeardown() {
		webDriver.quit();
	}

}
