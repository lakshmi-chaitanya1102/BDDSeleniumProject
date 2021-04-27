package utilities;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {
	
	public static WebDriver driver;
	
	@Before
	public static void invokeBrowser(){
		String path= System.getProperty("user.dir");  	
		System.setProperty("webdriver.chrome.driver", path+"/src/test/java/browsers/chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@After
	public static void tearDown(){
		driver.quit();
	}

}
