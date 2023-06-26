package com.skadoosh.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.skadoosh.automation.base.BaseTest;

public class GoogleTests extends BaseTest {

	@Test(enabled = true, description = "Launch google in browser")
	public void launchGoogleTest() throws InterruptedException {
		this.webDriver.get("http://www.google.com");
		System.out.println("launched google.com on browser........");
		Thread.sleep(5000);
	}
}
