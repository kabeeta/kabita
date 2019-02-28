package test;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.base.BaseClass;

public class DropdownTest extends BaseClass {
	
	
	@Test
	public void testDropdown() {
		try {
		
		launchUrl("https://the-internet.herokuapp.com/");
		//driver is loading from base class
		driver.findElement(By.xpath("//a[@href='/dropdown']")).click();
		((ExtentTest) test.get()).pass("Clicked on dropdown link");
		Thread.sleep(3000);
		
		Select sel = new Select(driver.findElement(By.xpath("//select[@id='dropdown']")));
		sel.selectByVisibleText("Option 2");
		Assert.assertEquals(sel.getFirstSelectedOption().getText(), "Option 2", "Invalid value selected from dropdown");
		((ExtentTest) test.get()).pass("Option 2 selected from dropdown");
		
		
		
		
		
		} catch (AssertionError e) {
			e.printStackTrace();
			//etest.log(Status.ERROR, e.getMessage());
			Assert.fail();
		} catch (Exception e) {
			e.printStackTrace();
		//	etest.log(Status.ERROR, e.getMessage());
			Assert.fail();
		}
		
	}
	

}
