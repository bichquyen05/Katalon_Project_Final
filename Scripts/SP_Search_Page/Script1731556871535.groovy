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

String cleanedKeyword = ''

String resultWithKeyword = ''

String resultWithCleanedKeyword = ''

WebUI.setText(findTestObject('Search_Page/input_Keyword'), keyword)

String keyword = WebUI.getAttribute(findTestObject('Search_Page/input_Keyword'), 'value')

WebUI.click(findTestObject('Search_Page/button_Search'))

if ((keyword == null) || (keyword.trim() == '')) {
    WebUI.verifyElementHasAttribute(findTestObject('Search_Page/input_Keyword'), 'required', 10, FailureHandling.STOP_ON_FAILURE)

    WebUI.delay(1)

    WebUI.closeBrowser() 
    
} else {
    boolean hasWhiteSpace = keyword.contains(' ')

    boolean hasSpecialChar = keyword.matches('.*[!@#%^&*(),.?":{}|<>\\[\\]\\\\;\'/`~_+-].*')

    boolean hasUpperCase = keyword.matches('.*[A-Z].*')

    boolean hasLowerCase = keyword.matches('.*[a-z].*')

    if ((((hasWhiteSpace || hasSpecialChar) || hasUpperCase) || hasLowerCase) || (hasUpperCase && hasLowerCase)) {
        if (WebUI.verifyElementPresent(findTestObject('Search_Page/h3_Product_Name'), 1, FailureHandling.OPTIONAL)) {
            resultWithKeyword = WebUI.getText(findTestObject('Search_Page/h3_Product Name'))
        } else {
            resultWithKeyword = WebUI.getText(findTestObject('Search_Page/span_Message Present'))
        }
        
        cleanedKeyword = keyword.trim().replaceAll('[^\\p{L}\\s]', '').replaceAll('\\s+', ' ').toLowerCase()

        WebUI.setText(findTestObject('Search_Page/input_Keyword'), cleanedKeyword)

        WebUI.click(findTestObject('Search_Page/button_Search'))

        WebUI.delay(1)

        if (WebUI.verifyElementPresent(findTestObject('Search_Page/h3_Product_Name'), 1, FailureHandling.OPTIONAL)) {
            resultWithCleanedKeyword = WebUI.getText(findTestObject('Search_Page/h3_Product Name'))
        } else {
            resultWithCleanedKeyword = WebUI.getText(findTestObject('Search_Page/span_Message Present'))
        }
    }
    
    if (resultWithKeyword.toLowerCase().contains(keyword.toLowerCase()) && resultWithCleanedKeyword.toLowerCase().contains(
        keyword.toLowerCase())) {
        WebUI.comment('Sản phẩm hiện có trên hệ thống!')
    } else if (resultWithKeyword.contains('Không có sản phẩm liên quan, vui lòng chọn sản phẩm khác !') && resultWithCleanedKeyword.contains(
        'Không có sản phẩm liên quan, vui lòng chọn sản phẩm khác !')) {
        WebUI.comment('Sản phẩm không có trên hệ thống!')
    } else {
        WebUI.comment('Lỗi xử lý trong chuỗi tìm kiếm!')

        WebUI.verifyEqual(resultWithKeyword, resultWithCleanedKeyword, FailureHandling.STOP_ON_FAILURE)
    }
}

//WebUI.closeBrowser()
