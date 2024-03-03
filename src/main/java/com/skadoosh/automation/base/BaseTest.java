package com.skadoosh.automation.base;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.skadoosh.automation.exceptions.NotSupportedPlatformException;

public class BaseTest {

	protected WebDriver driver;
	EnvironmentConfig envConfig = EnvironmentConfig.getInstance();
	SessionManager sessionManager = new SessionManager();

	@Parameters({ "platform" })
	@BeforeTest
	public void testSetup(String platform) {
		envConfig.setProperty("platform", platform);
	}

	@BeforeMethod
	public void methodSetup() throws FileNotFoundException, NotSupportedPlatformException, IOException, ParseException {
		driver = sessionManager.createSession(envConfig.getProperty("platform"));
	}

	@AfterMethod()
	public void methodTeardown() {
		driver.quit();
	}

}
