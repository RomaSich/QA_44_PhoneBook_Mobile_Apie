package mobile_tests;

import config.AppiumConfig;
import dto.UserDto;
import helper.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.AuthenticationScreen;
import screens.ContactsScreen;
import screens.ErrorScreen;
import screens.SplashScreen;
import static helper.RandomUtils.*;
import static helper.PropertiesReader.getProperty;

public class LoginTests extends AppiumConfig {

    AuthenticationScreen authenticationScreen;
    @BeforeMethod
    public void openLoginForm()
    {
        new SplashScreen(driver).goToAuthScreen(5);
        authenticationScreen = new AuthenticationScreen(driver);
    }

    @Test
    public void loginPositiveTest()
    {
        UserDto user = UserDto.builder()
                .username(getProperty("data.properties", "email"))
                .password(getProperty("data.properties", "password"))
                .build();
        authenticationScreen.typeAuthenticationForm(user);
        authenticationScreen.clickBtnLogin();
        Assert.assertTrue( new ContactsScreen(driver).validateHeader());
    }
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void loginNegativeTest_unRegEmail()
    {
        UserDto user = UserDto.builder()
                .username(generateEmail(10))
                .password("7206!Rom")
                .build();
        authenticationScreen.typeAuthenticationForm(user);
        authenticationScreen.clickBtnLogin();
        Assert.assertTrue(new ErrorScreen(driver).validateErrorMessage("Login or Password incorrect", 5));
    }
    @Test
    public void loginNegativeTest_regEmailWrongPassword(){
        UserDto user = UserDto.builder()
                .username(getProperty("data.properties", "email"))
                .password("7206!Rom")
                .build();
        authenticationScreen.typeAuthenticationForm(user);
        authenticationScreen.clickBtnLogin();
        Assert.assertTrue(new ErrorScreen(driver).validateErrorMessage("Login or Password incorrect", 5));
    }

}
