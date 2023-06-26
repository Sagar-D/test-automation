package com.skadoosh.automation.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvironmentConfig {

	Properties prop = new Properties();
	private static EnvironmentConfig config;

	private EnvironmentConfig() {

	}

	public static synchronized EnvironmentConfig getInstance() {
		if (config == null)
			config = new EnvironmentConfig();
		return config;
	}

	public void loadProperties(File propertiesFile) {
		try {
			prop.load(new FileInputStream(propertiesFile));
		} catch (IOException e) {
			System.out.println("Failed to load properties");
			e.printStackTrace();
		}
	}

	public String getProperty(String key) {
		return prop.getProperty(key);
	}

	public void setProperty(String key, String value) {
		prop.setProperty(key, value);
	}

}
