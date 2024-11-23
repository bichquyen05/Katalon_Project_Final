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
import groovy.sql.Sql as Sql
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.annotation.AfterTestCase as AfterTestCase
import com.kms.katalon.core.context.TestCaseContext as TestCaseContext

WebUI.openBrowser(url)

WebUI.click(findTestObject('Customer_Register/a_Register'))

WebUI.setText(findTestObject('Customer_Register/input_Full Name'), fullname)

WebUI.setText(findTestObject('Customer_Register/input_Company Name'), company_name)

WebUI.setText(findTestObject('Customer_Register/input_Email Address'), email_address)

WebUI.setText(findTestObject('Customer_Register/input_Phone Number'), phone_number)

WebUI.setText(findTestObject('Customer_Register/textarea_Address'), address)

WebUI.setText(findTestObject('Customer_Register/input_Country'), country)

WebUI.setText(findTestObject('Customer_Register/input_City'), city)

WebUI.setText(findTestObject('Customer_Register/input_State'), state)

WebUI.setText(findTestObject('Customer_Register/input_Zip Code'), zip_code)

WebUI.setText(findTestObject('Customer_Register/input_Password'), password)

WebUI.setText(findTestObject('Customer_Register/input_Retype Password'), repassword)

String fullName = WebUI.getAttribute(findTestObject('Customer_Register/input_Full Name'), 'value')
String emailAddress = WebUI.getAttribute(findTestObject('Customer_Register/input_Email Address'), 'value')
String phoneNumber = WebUI.getAttribute(findTestObject('Customer_Register/input_Phone Number'), 'value')
String address = WebUI.getText(findTestObject('Customer_Register/textarea_Address'))
String country = WebUI.getAttribute(findTestObject('Customer_Register/input_Country'), 'value')
String city = WebUI.getAttribute(findTestObject('Customer_Register/input_City'), 'value')
String state = WebUI.getAttribute(findTestObject('Customer_Register/input_State'), 'value')
String zipCode = WebUI.getAttribute(findTestObject('Customer_Register/input_Zip Code'), 'value')
String password = WebUI.getAttribute(findTestObject('Customer_Register/input_Password'), 'value')
String rePassword = WebUI.getAttribute(findTestObject('Customer_Register/input_Retype Password'), 'value')

WebUI.click(findTestObject('Customer_Register/btn_Register_form1'))

List<String> expectedMessages = []

if (fullName == null || fullName.trim().isEmpty()) {
	expectedMessages.add("Customer Name can not be empty.")
}
if (emailAddress == null || emailAddress.trim().isEmpty()) {
	expectedMessages.add("Email Address can not be empty.")
}
if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
	expectedMessages.add("Phone Number can not be empty.")
}
if (address == null || address.trim().isEmpty()) {
	expectedMessages.add("Address can not be empty.")
}
if (country == null || country.trim().isEmpty()) {
	expectedMessages.add("Country can not be empty.")
}
if (city == null || city.trim().isEmpty()) {
	expectedMessages.add("City can not be empty.")
}
if (state == null || state.trim().isEmpty()) {
	expectedMessages.add("State can not be empty.")
}
if (zipCode == null || zipCode.trim().isEmpty()) {
	expectedMessages.add("Zip Code can not be empty.")
}
if (password == null || password.trim().isEmpty()) {
	expectedMessages.add("Password can not be empty.")
}
if (rePassword == null || rePassword.trim().isEmpty()) {
	expectedMessages.add("Retype Password can not be empty.")
}

String actualMessage = WebUI.getText(findTestObject('Object Repository/Customer_Register/div_Message Present'))

List<String> actualMessages = actualMessage.split("(?<=\\.)\\s*").toList().findAll { it.trim() }


if (actualMessages.size() != expectedMessages.size()) {
	KeywordUtil.markFailed("Actual number of notifications (${actualMessages.size()}) does not match expectations (${expectedMessages.size()})")
} else {
	// So sánh từng thông báo
	for (int i = 0; i < actualMessages.size(); i++) {
		if (!actualMessages[i].equals(expectedMessages[i])) {
			KeywordUtil.markFailed("Notification at location ${i + 1} no match! Expected: '${expectedMessages[i]}', Actual: '${actualMessages[i]}'")
		}
	}
}

KeywordUtil.logInfo("All actual notifications match expectations.")

//WebUI.closeBrowser()