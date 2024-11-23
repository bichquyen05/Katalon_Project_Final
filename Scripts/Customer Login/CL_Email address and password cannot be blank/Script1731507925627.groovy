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

WebUI.click(findTestObject('Customer_Login/a_Login'))

WebUI.setText(findTestObject('Customer_Login/input_Email Address'), email_address)

WebUI.setText(findTestObject('Customer_Login/input_Password'), password)

WebUI.click(findTestObject('Customer_Login/btn_Login_form1'))

if ((email_address == '') || (password == '')) {
    WebUI.verifyElementPresent(findTestObject('Customer_Login/div_Message Present'), 1)

    WebUI.verifyElementText(findTestObject('Customer_Login/div_Message Present'), 'Email and/or Password can not be empty.')

    println('Email and/or Password can not be empty.')
}
WebUI.delay(1)
//WebUI.closeBrowser()

