package mobile_tests;

import config.AppiumConfig;
import dto.UserDto;
import helper.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.*;

import static helper.PropertiesReader.getProperty;

public class DatePikerTests extends AppiumConfig {

    UserDto user = UserDto.builder()
            .username(getProperty("data.properties", "email"))
            .password(getProperty("data.properties", "password"))
            .build();
    ContactsScreen contactsScreen;

    @BeforeMethod
    public void loginAndGoToAddNewContactScreen() {
        new SplashScreen(driver).goToAuthScreen(5);
        AuthenticationScreen authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.typeAuthenticationForm(user);
        authenticationScreen.clickBtnLogin();
        contactsScreen = new ContactsScreen(driver);
        contactsScreen.clickToDatePiker();
    }
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void datePikerPositiveTest() {
        DatePikerScreen datePikerScreen = new DatePikerScreen(driver);
        datePikerScreen.typeDate("30 dec 2028");
        Assert.assertTrue(datePikerScreen.validatePikerDate("30/11/2028"));
    }
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void datePikerNegativeTest_noSeeTheMonth() {
        DatePikerScreen datePikerScreen = new DatePikerScreen(driver);
        datePikerScreen.typeDate("30 mart 2028");
        Assert.assertTrue(datePikerScreen.validatePikerDate("30/02/2028"));
    }
}
