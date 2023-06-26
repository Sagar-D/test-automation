package com.skadoosh.automation.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumManager {

	private WebDriver driver;

	public SeleniumManager(String browser) {
		switch (browser.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		default:
			driver = new ChromeDriver();
			break;
		}
	}

	public void finalize() {
		this.driver.quit();
	}

	public WebDriver getDriver() {
		return this.driver;
	}

}
