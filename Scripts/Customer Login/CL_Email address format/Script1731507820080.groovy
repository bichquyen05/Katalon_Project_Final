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

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil

WebUI.openBrowser(url)

WebUI.click(findTestObject('Customer_Login/a_Login'))

WebUI.setText(findTestObject('Customer_Login/input_Email Address'), email_address)

WebUI.setText(findTestObject('Customer_Login/input_Password'), password)

String email = WebUI.getAttribute(findTestObject('Customer_Login/input_Email Address'), 'value')

WebUI.click(findTestObject('Customer_Login/btn_Login_form1'))

boolean isValidEmail(String e) {
	
		//email must contain @
		if(!e.contains("@")) {
			return false
		}
		
		//tách email thành 2 phần trc @ và sau @
		String[] parts = e.split('@')
		if(parts.length != 2) {
			return false
		}
		
		String user = parts[0]
		String domain = parts[1]
		
		//Kiểm tra user hợp lệ 
		if (user.isEmpty() || user.startsWith('.') || user.endsWith('.')) {
			return false
		}
		
		for(char c : user.toCharArray()) {
			if(!Character.isLetterOrDigit(c) && c != "." && c != "_" && c != "-") {
				return false
			}
		}
		
		//kiểm tra domain hợp lệ
		if(domain.isEmpty() || domain.startsWith('.') || domain.endsWith('.')) {
			return false
		}
		
		if(!domain.contains('.')) {
			return false
		}
		
		String[] domainParts = domain.split("\\.") //gmail.com
		String topLevelDomain = domainParts[domain.length()-1]
		if(topLevelDomain < 2) {
			return false
		}	
		for(char c : domain.toCharArray()) {
			if(!Character.isLetterOrDigit(c) && c != '.' && c != '_' && c != '-') {
				return false
			}
		}
		return true
}
if(isValidEmail(email)) {
	
	WebUI.comment('Valid email!')
	
}else {	
	boolean messagePresent = WebUI.verifyElementPresent(findTestObject('Customer_Login/div_Message Present'), 1)	
	if(messagePresent) {		
		String message = WebUI.getText(findTestObject('Customer_Login/div_Message Present'))		
		WebUI.verifyMatch(message, 'Email address must be valid.', false)		
	}else {
		KeywordUtil.markFailed("Error: No error message present for invalid input.")
	}
}

WebUI.delay(1)
//WebUI.closeBrowser()