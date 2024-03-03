package com.skadoosh.automation.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;

import com.skadoosh.automation.exceptions.NotSupportedPlatformException;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class SessionManager {

	private AndroidDriver androidDriver;
	private IOSDriver iosDriver;
	private BaseOptions capabilities;
	private AppiumDriverLocalService apppiumService;

	public enum PLATFORM {
		ANDROID, IOS, WEB
	}

	public WebDriver createSession(final String platform)
			throws NotSupportedPlatformException, FileNotFoundException, IOException, ParseException {

		PLATFORM platformValue;
		try {
			platformValue = PLATFORM.valueOf(platform.trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new NotSupportedPlatformException("Platform " + platform
					+ " is not supported. Please pass on of the below supported values : " + PLATFORM.values());
		}

		switch (platformValue) {

		case ANDROID:
			return createAndroidSession();

		case IOS:
			return createIosSession();

		case WEB:
			return createWebSession();

		default:
			throw new NotSupportedPlatformException();
		}

	}

	private AndroidDriver createAndroidSession() throws FileNotFoundException, IOException, ParseException {

		JSONObject capabilityJson = readCapabilities("android");

		UiAutomator2Options androidOptions = new UiAutomator2Options();
		androidOptions.setPlatformName("android");
		androidOptions.setAutomationName("UIAutomator2");

		if (capabilityJson.containsKey("appium:app")) {
			androidOptions.setApp(capabilityJson.get("appium:app").toString());
			capabilityJson.remove("appium:app");
		} else if (capabilityJson.containsKey("app")) {
			androidOptions.setApp(capabilityJson.get("app").toString());
			capabilityJson.remove("app");
		} else {
			throw new IllegalArgumentException("Required capability - appium:app not found in the capabilities file");
		}

		if (capabilityJson.containsKey("appium:appPackage")) {
			androidOptions.setAppPackage(capabilityJson.get("appium:appPackage").toString());
			capabilityJson.remove("appium:appPackage");
		} else if (capabilityJson.containsKey("appPackage")) {
			androidOptions.setAppPackage(capabilityJson.get("appPackage").toString());
			capabilityJson.remove("appPackage");
		}

		if (capabilityJson.containsKey("appium:appActivity")) {
			androidOptions.setAppActivity(capabilityJson.get("appium:appActivity").toString());
			capabilityJson.remove("appium:appActivity");
		} else if (capabilityJson.containsKey("appActivity")) {
			androidOptions.setAppActivity(capabilityJson.get("appActivity").toString());
			capabilityJson.remove("appActivity");
		}

		if (capabilityJson.containsKey("appium:platformVersion")) {
			androidOptions.setPlatformVersion(capabilityJson.get("appium:platformVersion").toString());
			capabilityJson.remove("appium:platformVersion");
		} else if (capabilityJson.containsKey("platformVersion")) {
			androidOptions.setPlatformVersion(capabilityJson.get("platformVersion").toString());
			capabilityJson.remove("platformVersion");
		}

		if (capabilityJson.containsKey("appium:deviceName")) {
			androidOptions.setDeviceName(capabilityJson.get("appium:deviceName").toString());
			capabilityJson.remove("appium:deviceName");
		} else if (capabilityJson.containsKey("deviceName")) {
			androidOptions.setDeviceName(capabilityJson.get("deviceName").toString());
			capabilityJson.remove("deviceName");
		}

		this.apppiumService = startAppiumService();
		this.capabilities = androidOptions;
		this.androidDriver = new AndroidDriver(this.apppiumService.getUrl(), androidOptions);
		return this.androidDriver;
	}

	private IOSDriver createIosSession() throws FileNotFoundException, IOException, ParseException {

		JSONObject capabilityJson = readCapabilities("ios");

		XCUITestOptions iosOptions = new XCUITestOptions();
		iosOptions.setPlatformName("android");
		iosOptions.setAutomationName("UIAutomator2");

		if (capabilityJson.containsKey("appium:app")) {
			iosOptions.setApp(capabilityJson.get("appium:app").toString());
			capabilityJson.remove("appium:app");
		} else if (capabilityJson.containsKey("app")) {
			iosOptions.setApp(capabilityJson.get("app").toString());
			capabilityJson.remove("app");
		} else {
			throw new IllegalArgumentException("Required capability - appium:app not found in the capabilities file");
		}

		if (capabilityJson.containsKey("appium:platformVersion")) {
			iosOptions.setPlatformVersion(capabilityJson.get("appium:platformVersion").toString());
			capabilityJson.remove("appium:platformVersion");
		} else if (capabilityJson.containsKey("platformVersion")) {
			iosOptions.setPlatformVersion(capabilityJson.get("platformVersion").toString());
			capabilityJson.remove("platformVersion");
		}

		if (capabilityJson.containsKey("appium:deviceName")) {
			iosOptions.setDeviceName(capabilityJson.get("appium:deviceName").toString());
			capabilityJson.remove("appium:deviceName");
		} else if (capabilityJson.containsKey("deviceName")) {
			iosOptions.setDeviceName(capabilityJson.get("deviceName").toString());
			capabilityJson.remove("deviceName");
		}

		if (capabilityJson.containsKey("appium:udid")) {
			iosOptions.setUdid(capabilityJson.get("appium:udid").toString());
			capabilityJson.remove("appium:udid");
		} else if (capabilityJson.containsKey("udid")) {
			iosOptions.setDeviceName(capabilityJson.get("udid").toString());
			capabilityJson.remove("udid");
		}

		this.apppiumService = startAppiumService();
		this.capabilities = iosOptions;
		this.iosDriver = new IOSDriver(this.apppiumService.getUrl(), iosOptions);
		return this.iosDriver;
	}

	private WebDriver createWebSession() {
		// TODO Auto-generated method stub
		return null;
	}

	private JSONObject readCapabilities(String platform) throws FileNotFoundException, IOException, ParseException {

		JSONParser parser = new JSONParser();
		JSONObject capabilities = (JSONObject) parser
				.parse(new FileReader("src/main/resources/staging/capabilities.json"));
		return (JSONObject) capabilities.get(platform);
	}

	public BaseOptions getSessionCapabilities() {
		return this.capabilities;
	}

	private AppiumDriverLocalService startAppiumService() {

		if (this.apppiumService != null && this.apppiumService.isRunning())
			return this.apppiumService;

		AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
		this.apppiumService = serviceBuilder.usingAnyFreePort().build();
		this.apppiumService.start();

		return this.apppiumService;
	}

	public void stopAppiumService() {
		if (this.apppiumService != null && this.apppiumService.isRunning())
			this.apppiumService.stop();
	}

}
