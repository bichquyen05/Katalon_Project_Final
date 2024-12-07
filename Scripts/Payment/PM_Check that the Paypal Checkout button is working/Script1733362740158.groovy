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

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement

WebUI.openBrowser(url)

WebUI.click(findTestObject('Payment/a_Login'))

WebUI.setText(findTestObject('Payment/input_Email Address'), email_address)

WebUI.setText(findTestObject('Payment/input_Password'), password)

WebUI.click(findTestObject('Payment/btn_Login'))

void clickProductByIndex(int index) {
	// Tạo một TestObject động
	TestObject dynamicProduct = new TestObject()

	// Thêm thuộc tính XPath với vị trí sản phẩm
	String xpath = "(//a[contains(text(),'Product Details')])[" + index + "]"
	dynamicProduct.addProperty("xpath", ConditionType.EQUALS, xpath)

	// Click vào sản phẩm
	WebUI.click(dynamicProduct)
	
	WebUI.click(findTestObject('Payment/btn_Add to cart'))
	
	WebUI.click(findTestObject('Payment/a_Home'))
	
}

clickProductByIndex(8)

WebUI.click(findTestObject('Payment/a_Cart'))

WebUI.click(findTestObject('Payment/a_Proceed to Checkout'))

WebUI.click(findTestObject('Payment/btn_Payment Section_Paypal'))

// Đợi trình duyệt mở tab hoặc cửa sổ mới
WebUI.delay(5)

// Lấy danh sách các cửa sổ/tab đang mở
WebDriver driver = DriverFactory.getWebDriver()

Set<String> windowHandles = driver.getWindowHandles()

// Chuyển sang cửa sổ/tab thứ hai

driver.switchTo().window(windowHandles.toArray()[1])

// Lấy URL của tab mới
String currentUrl = WebUI.getUrl()
println("URL của PayPal: " + currentUrl)

// Kiểm tra URL có chứa thông tin PayPal chính xác không
WebUI.verifyMatch(currentUrl, '.*sandbox.paypal.com.*', true) // Kiểm tra trang PayPal
WebUI.verifyMatch(currentUrl, '.*return=payment_success.php.*', true) // Trang thành công
