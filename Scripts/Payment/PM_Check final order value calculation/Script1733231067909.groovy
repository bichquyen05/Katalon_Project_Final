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

clickProductByIndex(7)
clickProductByIndex(8)

WebUI.click(findTestObject('Payment/a_Cart'))

WebUI.click(findTestObject('Payment/a_Proceed to Checkout'))

WebDriver driver = DriverFactory.getWebDriver()

List<WebElement> products = driver.findElements(By.xpath("(//table)[1]/tbody/tr"))

//println(products.size())
//for (int i = 0; i < products.size(); i++) {
//	WebElement product = products.get(i);
//	List<WebElement> columns = product.findElements(By.tagName("td")); // Lấy danh sách các cột trong hàng
//	System.out.print("Row " + (i + 1) + ": ");
//	for (WebElement column : columns) {
//		System.out.print(column.getText() + " | "); // In từng cột, ngăn cách bởi "|"
//	}
//	System.out.println(); // Xuống dòng sau khi in xong mỗi hàng
//}

double totalPrice = 0.0

for (int i = 1; i < products.size() - 3; i++) {
    WebElement product = products.get(i);

    String productName = product.findElement(By.xpath("td[3]")).getText();  // Cột 3: Tên sản phẩm
    String price = product.findElement(By.xpath("td[6]")).getText();     // Cột 6: Giá tiền
    String quantity = product.findElement(By.xpath("td[7]")).getText();        // Cột 7: số lượng
    String total = product.findElement(By.xpath("td[8]")).getText();        // Cột 8: Thành tiền

    println("Product Row: " + i);
    println("Name: " + productName);
	println("Price: " + price);
    println("Quantity: " + quantity);
    println("Total: " + total);
	
	totalPrice += Double.parseDouble(total.split(" ")[0].replace(",", ""))
}

println("Total Price of products: " + totalPrice);

String sSubTotal = WebUI.getText(findTestObject('Payment/th_Sub Total')).split(" ")[0]
double subTotal = Double.parseDouble(sSubTotal.replace(",", ""))
println(subTotal)

String sShippingCost = WebUI.getText(findTestObject('Payment/td_Shipping Cost')).split(" ")[0]
double shippingCost = Double.parseDouble(sShippingCost.replace(",", ""))
println(shippingCost)

String sTotalOrder = WebUI.getText(findTestObject('Payment/th_Total')).split(" ")[0]
double totalOrder = Double.parseDouble(sTotalOrder.replace(",", ""))
println(totalOrder)

final double EPSILON = 0.0001;

assert Math.abs(totalPrice -  subTotal) < EPSILON : "Total amount does not match!"

double shipping = 0.00
if(subTotal < 300000 - EPSILON) {
	shipping = 20000
}else {
	shipping = 10000
}

assert Math.abs(shippingCost - shipping) < EPSILON : "Shipping charges are incorrect!"

double totalPriceAndShipping = subTotal + shippingCost

assert Math.abs(totalOrder - totalPriceAndShipping) < EPSILON : "Total order value is incorrect!"

///html/body/div[8]/div/div/div/div[2]/div/div[1]/table/tbody/tr[3]/td[2]