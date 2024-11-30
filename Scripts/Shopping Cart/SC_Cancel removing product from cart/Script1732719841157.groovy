import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.JavascriptExecutor

WebUI.openBrowser('http://shopbachhoaweb.wuaze.com/')
//WebUI.maximizeWindow()

WebDriver driver = DriverFactory.getWebDriver()

WebUI.navigateToUrl('http://shopbachhoaweb.wuaze.com/')

WebUI.click(findTestObject('Shopping Cart/a_Product Details'))

WebUI.click(findTestObject('Shopping Cart/btn_PD_Add To Cart'))

WebUI.click(findTestObject('Shopping Cart/a_Cart'))

List<WebElement> productRows = driver.findElements(By.xpath('//table/tbody/tr'))

assert productRows.size() > 0 : "Cart is still empty after adding products!"

WebElement lastRow = productRows.get(productRows.size() - 1)

String sNameProductDelete = lastRow.findElement(By.xpath('/html/body/div[8]/div/div/div/form/div[1]/table/tbody/tr[2]/td[3]')).getText()

WebElement btnRemove = lastRow.findElement(By.xpath("//td[9]/a/i"))
((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btnRemove)
btnRemove.click()


driver.switchTo().alert().dismiss()

List<WebElement> updatedProductRows = driver.findElements(By.xpath('//table/tbody/tr'))

boolean isProductStillExist = false;

for (WebElement row : productRows) {
	WebElement productName = row.findElement(By.xpath('/html/body/div[8]/div/div/div/form/div[1]/table/tbody/tr[2]/td[3]'));
	if (productName.getText().equals(sNameProductDelete)) {
		isProductStillExist = true;
		break;
	}
}

if(!isProductStillExist) {
	assert false : "Product has been removed from cart!"
}