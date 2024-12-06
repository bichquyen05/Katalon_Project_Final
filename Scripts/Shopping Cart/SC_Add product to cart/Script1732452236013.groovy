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
import org.openqa.selenium.WebElement


WebUI.openBrowser('http://shopbachhoaweb.wuaze.com/')

WebUI.click(findTestObject('Shopping Cart/a_Cart'))

WebDriver driver = DriverFactory.getWebDriver()

String emptyCartMessage = driver.findElement(By.xpath('//h2')).getText()

assert emptyCartMessage.contains('Your cart is empty!') : 'Cart does not show empty status!'

WebUI.navigateToUrl('http://shopbachhoaweb.wuaze.com/')

WebUI.click(findTestObject('Shopping Cart/a_Product Details'))

String name = WebUI.getText(findTestObject('Shopping Cart/h2_PD_Product Name'))

String fullPrice = WebUI.getText(findTestObject('Shopping Cart/span_PD_Price')).split(' ')[0]

double price = Double.parseDouble(fullPrice.replace(",", ""))

int quantity = Integer.parseInt(WebUI.getAttribute(findTestObject('Shopping Cart/input_PD_Product Quantity'), 'value'))

WebUI.click(findTestObject('Shopping Cart/btn_PD_Add To Cart'))


WebUI.click(findTestObject('Shopping Cart/a_Cart'))

List<WebElement> productRows = driver.findElements(By.xpath('//table/tbody/tr'))
assert productRows.size() > 0 : "Cart is still empty after adding products!"

WebElement lastRow = productRows.get(productRows.size() - 1)

String sName = lastRow.findElement(By.xpath('//td[3]')).getText()

String sPrice = lastRow.findElement(By.xpath('//td[6]')).getText().split(' ')[0]
double bPrice = Double.parseDouble(sPrice.replace(",", ""))

String sQuantity = lastRow.findElement(By.xpath('//td[7]/input[3]')).getAttribute('value')
int iQuantity = Integer.parseInt(sQuantity)

String sTotal = lastRow.findElement(By.xpath('//td[8]')).getText().split(' ')[0]
double bTotal = Double.parseDouble(sTotal.replace(",", ""))

assert sName == name : "Product name does not match!"
assert bPrice == price : "Product prices do not match!"
assert iQuantity == quantity : "Product quantity does not match!"

double bexpectedTotal = bPrice * iQuantity
assert Math.abs(bTotal -  bexpectedTotal) < 0.01 : "Total product price does not match! Expected: ${ bexpectedTotal}, Actual: ${bTotal}"

 
 //String productName = lastRow.findElement(By.xpath('/html/body/div[8]/div/div/div/form/div[1]/table/tbody/tr[2]/td[3]')).getText() // Tên sản phẩm
 //String productPrice = lastRow.findElement(By.xpath('/html/body/div[8]/div/div/div/form/div[1]/table/tbody/tr[2]/td[6]')).getText().split(' ')[0] // Giá sản phẩm
 //String productQuantity = lastRow.findElement(By.xpath('/html/body/div[8]/div/div/div/form/div[1]/table/tbody/tr[2]/td[7]/input[3]')).getAttribute('value') // Số lượng
 //String productTotal = lastRow.findElement(By.xpath('/html/body/div[8]/div/div/div/form/div[1]/table/tbody/tr[2]/td[8]')).getText() // Tổng giá sản phẩm
 