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

// Hàm kiểm tra bất kỳ ô nào trong bảng có giá trị rỗng
public checkEmptyFields(List<String> cellPaths, String sectionName) {
    boolean isEmpty = false

    for (String cellPath : cellPaths) {
        String value = WebUI.getText(findTestObject(cellPath)).trim()
        if (value == '') {
            println "Missing value in ${sectionName}, field: ${cellPath}"
            isEmpty = true
            break
        }
    }

    return isEmpty
}

// Danh sách các Test Object tương ứng với từng ô của bảng Billing Address
List<String> billingCells = [
    'Payment/td_BA_Full Name',
    'Payment/td_BA_Company Name',
    'Payment/td_BA_Phone Number',
    'Payment/td_BA_Country',
    'Payment/td_BA_Address',
    'Payment/td_BA_City',
    'Payment/td_BA_State',
    'Payment/td_BA_Zip Code'
]

// Danh sách các Test Object tương ứng với từng ô của bảng Shipping Address
List<String> shippingCells = [
    'Payment/td_SA_Full Name',
    'Payment/td_SA_Company Name',
    'Payment/td_SA_Phone Number',
    'Payment/td_SA_Country',
    'Payment/td_SA_Address',
    'Payment/td_SA_City',
    'Payment/td_SA_State',
    'Payment/td_SA_Zip Code'
]

// Kiểm tra Billing Address
boolean isBillingMissing = checkEmptyFields(billingCells, 'Billing Address')

// Kiểm tra Shipping Address
boolean isShippingMissing = checkEmptyFields(shippingCells, 'Shipping Address')

// Hiển thị thông báo nếu thiếu thông tin
if (isBillingMissing || isShippingMissing) {
	WebUI.verifyElementPresent(findTestObject('Payment/div_Message'), 1)  
	
	WebUI.verifyElementText(findTestObject('Payment/div_Message'), 'You must have to fill up all the billing and shipping information from your dashboard panel in order to checkout the order. Please fill up the information going to this link.')
	
	WebUI.comment("<span style='color:red;'>You must fill up all the billing and shipping information. <a href='update-info-link'>Click here</a> to update.</span>")
	
	WebUI.click(findTestObject('Payment/a_this link'))
	
	String currentUrl = WebUI.getUrl()
	
	String expectedUrl = 'http://shopbachhoaweb.wuaze.com/customer-billing-shipping-update.php'
	
	WebUI.verifyMatch(currentUrl, expectedUrl, false)
	
} else {
    WebUI.comment("All information is filled.")
}

