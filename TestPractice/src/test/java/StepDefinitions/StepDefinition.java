package StepDefinitions;


import java.util.Map;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;
import utilities.Hooks;


public class StepDefinition{

	
	WebDriver driver = Hooks.driver;
	public WebDriverWait wait;
	 

	@Given("^User should be navigated to elearning site \"([^\"]*)\"$")
	public void navigatetoSite(String url) throws Throwable {
		driver.get(url);
		wait = new WebDriverWait(driver,20);
		wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),' Sign up! ')]")));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}
	@When("^User clicks on Sign up link$")
	public void clicksSignUpLink() throws Throwable {
		driver.findElement(By.xpath("//a[contains(text(),' Sign up! ')]")).click();
	    String actualTitle=driver.findElement(By.xpath("//h2[@class='page-header' and text()='Registration']")).getText();
	    Assert.assertEquals("Registration", actualTitle);
	}
	@And("^User enter below registration details$")
	public void enterRegistrationDetails(DataTable dt) throws Throwable {
		Map<String,String> list=dt.asMap(String.class,String.class);
		String fName=list.get("FirstName");
		String lName=list.get("LastName");
		String mail=list.get("EMail");
		driver.findElement(By.id("registration_firstname")).sendKeys(fName);
		driver.findElement(By.id("registration_lastname")).sendKeys(lName);
		driver.findElement(By.id("registration_email")).sendKeys(mail);
		driver.findElement(By.id("username")).sendKeys(list.get("UserName"));
		driver.findElement(By.id("pass1")).sendKeys(list.get("Pass"));
		driver.findElement(By.id("pass2")).sendKeys(list.get("Pass"));
		driver.findElement(By.xpath("//*[contains(text(),'Register')]")).click();
	}
	@Then("^User validates Registration details$")
	public void validatesRegistrationDetails(DataTable dt) throws Throwable {
		Map<String,String> list=dt.asMap(String.class,String.class);
		wait = new WebDriverWait(driver,20);
		wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//li[contains(text(),'Registration')]")));
		String actual2 = driver.findElement(By.xpath("//*[contains(text(),'Dear')]")).getText();
		Assert.assertTrue("Validate message is present", actual2.contains("Dear "+list.get("FirstName")+" "+list.get("LastName")));
		String actual = driver.findElement(By.xpath("//*[text()='Your personal settings have been registered.']")).getText();
		Assert.assertTrue("Validate message is present", actual.contains("Your personal settings have been registered."));
		String actual1 = driver.findElement(By.xpath("//*[text()='An e-mail has been sent to remind you of your login and password.']")).getText();
		Assert.assertEquals("An e-mail has been sent to remind you of your login and password.", actual1);
		driver.findElement(By.xpath("//a[@href='#']/img")).click();
		String actualMail = driver.findElement(By.xpath("//li[@class='user-header']//a/following-sibling::p")).getText();
		Assert.assertEquals(list.get("EMail"),actualMail.trim());
	}
	
	@And("^User sends an email \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
	public void user_sends_an_email(String toMailId, String subject, String message) throws Throwable {
		driver.findElement(By.xpath("//a[@title='Inbox']")).click();
		wait = new WebDriverWait(driver,10);
		wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Profile')]")));
		driver.findElement(By.xpath("//img[@title='Compose message']")).click();
		String firstThreeLetters=toMailId.substring(0, 3);
		driver.findElement(By.xpath("//input[@type='search']")).sendKeys(firstThreeLetters);
		driver.findElement(By.xpath("//ul[@id='select2-compose_message_users-results']/li[text()='"+toMailId+"']")).click();
		
		driver.findElement(By.cssSelector("input#compose_message_title")).sendKeys(subject);
		WebElement frameElement = driver.findElement(By.xpath("//iframe[@class='cke_wysiwyg_frame cke_reset']"));
		driver.switchTo().frame(frameElement);
		
		driver.findElement(By.xpath("//body[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']")).click();
		driver.findElement(By.xpath("//body[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']")).sendKeys(message);
		driver.switchTo().defaultContent();
		driver.findElement(By.cssSelector("button#compose_message_compose")).click();
	}
	
	@And("^User validates success message \"([^\"]*)\"$")
	public void validatesSuccessMessage(String toMailId) throws Throwable {
		String actualSuccessMessage=driver.findElement(By.xpath("//div[@class='alert alert-success' and contains(text(),'The message has been sent')]")).getText();
	    Assert.assertEquals("The message has been sent to "+toMailId, actualSuccessMessage);
	}
	
	@Then("^User logouts from the application$")
	public void logoutApplication() throws Throwable {
		driver.findElement(By.xpath("//a[@href='#']/img")).click();
		driver.findElement(By.cssSelector("a#logout_button")).click();
		
	}
}
