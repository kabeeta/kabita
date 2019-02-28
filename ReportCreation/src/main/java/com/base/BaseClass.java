package com.base;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import reportManager.ExtentTestNGReportBuilder;

public class BaseClass extends ExtentTestNGReportBuilder {
	public  WebDriver driver;
	// public ExtentReports extentReports;
	// public ExtentTest test;
	String screenshotPath;

	@BeforeTest
	@Parameters("browser")
	public void openBrowser(String browser) throws Exception {
//abc
		if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.firefox.marionette", ".\\Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		else if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", ".\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}

		else if (browser.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.edge.driver", ".\\Drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		} else {

			throw new Exception("Invalid browser Name");
		}

		driver.manage().window().maximize();
	}

	public void launchUrl(String URL) {
		driver.get(URL);
	}

	public String captureScreenshot(String screenshotName) throws Exception {

		String str_todaysDateTimeStamp = new SimpleDateFormat("dd-MMM-YYYY HH-mm-ss").format(new Date());

		TakesScreenshot ts = (TakesScreenshot) driver;
		File sourceFile = ts.getScreenshotAs(OutputType.FILE);
		String ImageName = screenshotName.replaceAll("\\s+", "").trim() + "_" + str_todaysDateTimeStamp + ".jpeg";

		String destinationFile = ".\\ExtentReport\\" + ImageName;
		FileUtils.copyFile(sourceFile, new File(destinationFile));
		return ImageName;
	}

	
	
	@AfterMethod
	public synchronized void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			try {
				screenshotPath = captureScreenshot(result.getName());
				((ExtentTest) test.get()).fail("Test  Failed is : " + result.getName(),
				MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			((ExtentTest) test.get()).fail(result.getThrowable());
			
		} else if (result.getStatus() == ITestResult.SKIP) {
			try {
				screenshotPath = captureScreenshot(result.getName());
				((ExtentTest) test.get()).skip("Test  Skip is : " + result.getName(),
				MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			((ExtentTest) test.get()).skip(result.getThrowable());

		} else {
			
			try {
				screenshotPath = captureScreenshot(result.getName());
				((ExtentTest) test.get()).pass("Test  Passed is : " + result.getName(),
				MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		
			} catch (Exception e) {
				e.printStackTrace();
			}

			((ExtentTest) test.get()).pass("Test passed");

		}
		extent.flush();
	}

}
