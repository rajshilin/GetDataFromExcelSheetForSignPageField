package rough;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestSignUp {

	public static void main(String[] args) {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://dev1-dcweb.oneflotest.in/capitallever/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3000));

		driver.findElement(By.className("btn1")).click();
		driver.findElement(By.id("full_name")).sendKeys("Raj Shilin");
		driver.findElement(By.xpath("//*[@id=\"agreeStep1\"]/div/div/div[3]/div/div/div/div/input[3]")).click();
		driver.findElement(By.name("pin_code")).sendKeys("411011");
		driver.findElement(By.id("sign_phone_number")).sendKeys("9765976476");
		driver.findElement(By.id("sign_email")).sendKeys("abc1@gmail.com");
		driver.findElement(By.className("btn btn-primary login-button")).submit();

		driver.quit();
	}

}
