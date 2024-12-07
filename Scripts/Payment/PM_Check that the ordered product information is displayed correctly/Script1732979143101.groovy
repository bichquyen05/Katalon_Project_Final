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

// Gọi hàm với sản phẩm thứ 7 và 8
clickProductByIndex(7)
clickProductByIndex(8)

WebUI.click(findTestObject('Payment/a_Cart'))

List<Map<String, String>> cartItems = []

WebDriver driver = DriverFactory.getWebDriver()

List<WebElement> cartProductRows = driver.findElements(By.xpath('//table/tbody/tr'))

for(int i = 1; i < cartProductRows.size() - 1; i++) {
	WebElement cartProductRow = cartProductRows.get(i)
	Map<String, String> cartProductInfo = new HashMap<>()
	cartProductInfo.put("name", cartProductRow.findElement(By.xpath("//td[3]")).getText())
	cartProductInfo.put("price", cartProductRow.findElement(By.xpath("//td[6]")).getText())
	cartProductInfo.put("quantity", cartProductRow.findElement(By.xpath("//td[7]/input[3]")).getAttribute('value'))
	println(cartProductInfo.get("quantity"))
	cartProductInfo.put("total", cartProductRow.findElement(By.xpath("//td[8]")).getText())
	
	cartItems.add(cartProductInfo)
}
println(cartItems.size())

WebUI.click(findTestObject('Payment/a_Proceed to Checkout'))

List<Map<String, String>> checkoutItems = []

List<WebElement> checkoutProductRows = driver.findElements(By.xpath("(//table)[1]/tbody/tr"))

println(checkoutProductRows.size())

//"(//table)[1]/tbody/tr"

for(int i = 1; i < checkoutProductRows.size() - 2; i++) {
	WebElement checkoutProductRow = checkoutProductRows.get(i)
	Map<String, String> checkoutProductInfo = new HashMap<>()
	checkoutProductInfo.put("name", checkoutProductRow.findElement(By.xpath("//td[3]")).getText())
	checkoutProductInfo.put("price", checkoutProductRow.findElement(By.xpath("//td[6]")).getText())
	checkoutProductInfo.put("quantity", checkoutProductRow.findElement(By.xpath("//td[7]")).getText())	
	checkoutProductInfo.put("total", checkoutProductRow.findElement(By.xpath("//td[8]")).getText())
	
	checkoutItems.add(checkoutProductInfo)
}

println(checkoutItems.size())

assert cartItems.size() != checkoutItems.size() : "Tổng số dòng sản phẩm không khớp!"

for(int i = 0; i < cartItems.size(); i++) {
	Map<String, String> cartProduct = cartItems.get(i)
	Map<String, String> checkoutProduct = checkoutItems.get(i)
	
	assert cartProduct.get("name").equals(checkoutProduct.get("name")) : "Tên sản phẩm không khớp!"
	assert cartProduct.get("price").equals(checkoutProduct.get("price")) : "Giá sản phẩm không khớp!"
	assert cartProduct.get("quantity").equals(checkoutProduct.get("quantity")) : "Số lượng sản phẩm không khớp!"
	assert cartProduct.get("total").equals(checkoutProduct.get("total")) : "Thành tiền sản phẩm không khớp!"
}
