package com.skadoosh.automation.base;

import org.openqa.selenium.WebDriver;

public class SeleniumManager {

	private WebDriver driver;

	public SeleniumManager(String browser) {
//		switch (browser.toLowerCase()) {
//		case "chrome":
//			driver = new ChromeDriver();
//			break;
//		case "firefox":
//			driver = new FirefoxDriver();
//			break;
//		default:
//			driver = new ChromeDriver();
//			break;
//		}
	}

	public void finalize() {
		this.driver.quit();
	}

	public WebDriver getDriver() {
		return this.driver;
	}

}
