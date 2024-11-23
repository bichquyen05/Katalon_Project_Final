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

WebUI.click(findTestObject('Customer_Register/btn_Register_form1'))

boolean messagePresent = WebUI.waitForElementPresent(findTestObject('Customer_Register/div_Message Present'), 1)

if(messagePresent) {
	
	String message = WebUI.getText(findTestObject('Customer_Register/div_Message Present'))
	
	WebUI.verifyMatch(message,'Your registration is completed. Please check your email address to follow the process to confirm your registration.', false)
}
	
WebUI.delay(1)

WebUI.closeBrowser()








