package rough;

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

import io.github.bonigarcia.wdm.WebDriverManager;

public class GetExcelData {

	public static void main(String[] args) throws IOException, InterruptedException {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://dev1-dcweb.oneflotest.in/capitallever/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.className("btn1")).click();

		FileInputStream fis = new FileInputStream("D:\\OneInfinity\\OneInfinity-1\\TestData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("OneInfinitySheet");
		int rowCount = sheet.getLastRowNum();
		int colcount = sheet.getRow(1).getLastCellNum();
		System.out.println("rowCount : " + rowCount + "   colCount : " + colcount);
 
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		for (int i = 1; i <= rowCount; i++) {

			XSSFRow cellData = sheet.getRow(i);
			String fullName = cellData.getCell(0).getStringCellValue();

			String gender = cellData.getCell(1).getStringCellValue();

			String pinCode = "";
			if (cellData.getCell(2).getCellType() == CellType.NUMERIC) {

				pinCode = String.valueOf((long) cellData.getCell(2).getNumericCellValue());
			} else {
				pinCode = cellData.getCell(2).getStringCellValue();
			}

			String phone = "";
			if (cellData.getCell(3).getCellType() == CellType.NUMERIC) {
				phone = String.valueOf((long) cellData.getCell(3).getNumericCellValue());
			} else {
				phone = cellData.getCell(3).getStringCellValue();
			}

			String email = "";
			if (cellData.getCell(4).getCellType() == CellType.NUMERIC) {
				email = String.valueOf(cellData.getCell(4).getNumericCellValue());
			} else {
				email = cellData.getCell(4).getStringCellValue();
			}

			driver.findElement(By.id("full_name")).clear();
			driver.findElement(By.id("full_name")).sendKeys(fullName);

			// driver.findElement(By.name("address.gender")).clear();

			if (gender.equalsIgnoreCase("male")) {
				driver.findElement(By.xpath("//input[@value=\"M\"]")).click();
			} else if (gender.equalsIgnoreCase("female")) {
				driver.findElement(By.xpath("//input[@value=\"F\"]")).click();
			} else if (gender.equalsIgnoreCase("Other")) {
				WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
						By.xpath("//*[@id=\"agreeStep1\"]/div/div/div[3]/div/div/div/div/input[3]")));
				element.click();
				//driver.findElement(By.xpath("//input[@value=\"0\"]")).click();
			} else {
				System.out.println("Unexpeted gender " + gender);
			}

			driver.findElement(By.name("pin_code")).clear();
			driver.findElement(By.name("pin_code")).sendKeys(pinCode);
			driver.findElement(By.id("sign_phone_number")).clear();
			driver.findElement(By.id("sign_phone_number")).sendKeys(phone);
			driver.findElement(By.id("sign_email")).clear();
			driver.findElement(By.id("sign_email")).sendKeys(email);
			// driver.findElement(By.className("btn btn-primary login-button")).submit();

			System.out.println(fullName + " || " + gender + " || " + pinCode + " || " + phone + " || " + email);

		}

	}

}
