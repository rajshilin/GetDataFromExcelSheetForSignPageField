package oneInfinity.SignUpPage;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestNG;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class GetExcelDataTest {

	public WebDriver driver;
	public WebDriverWait wait;

	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.get("https://dev1-dcweb.oneflotest.in/capitallever/");
		driver.findElement(By.className("btn1")).click();
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testSignUpWithExcelData() throws IOException {
		FileInputStream fis = new FileInputStream("D:\\OneInfinity\\OneInfinity-1\\TestData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("OneInfinitySheet");
		int rowCount = sheet.getLastRowNum();
		int colcount = sheet.getRow(1).getLastCellNum();
		System.out.println("rowCount : " + rowCount + "   colCount : " + colcount);

		for (int i = 1; i <= rowCount; i++) {
			XSSFRow cellData = sheet.getRow(i);
			String fullName = cellData.getCell(0).getStringCellValue();
			String gender = cellData.getCell(1).getStringCellValue();
			String pinCode = getCellValueAsString(cellData.getCell(2));
			String phone = getCellValueAsString(cellData.getCell(3));
			String email = getCellValueAsString(cellData.getCell(4));

			driver.findElement(By.id("full_name")).clear();
			driver.findElement(By.id("full_name")).sendKeys(fullName);

			if (gender.equalsIgnoreCase("male")) {
				driver.findElement(By.xpath("//input[@value=\"M\"]")).click();
			} else if (gender.equalsIgnoreCase("female")) {
				driver.findElement(By.xpath("//input[@value=\"F\"]")).click();
			} else if (gender.equalsIgnoreCase("Other")) {
				WebElement element = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value=\"O\"]")));
				element.click();
			} else {
				System.out.println("Unexpected gender " + gender);
			}

			driver.findElement(By.name("pin_code")).clear();
			driver.findElement(By.name("pin_code")).sendKeys(pinCode);
			driver.findElement(By.id("sign_phone_number")).clear();
			driver.findElement(By.id("sign_phone_number")).sendKeys(phone);
			driver.findElement(By.id("sign_email")).clear();
			driver.findElement(By.id("sign_email")).sendKeys(email);

			System.out.println(fullName + " || " + gender + " || " + pinCode + " || " + phone + " || " + email);
		}
	}

	public String getCellValueAsString(org.apache.poi.ss.usermodel.Cell cell) {
		if (cell.getCellType() == CellType.NUMERIC) {
			return String.valueOf((long) cell.getNumericCellValue());
		} else {
			return cell.getStringCellValue();
		}
	}
}
