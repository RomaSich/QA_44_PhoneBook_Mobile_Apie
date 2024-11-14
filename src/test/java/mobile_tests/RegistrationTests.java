package mobile_tests;

import config.AppiumConfig;
import dto.UserDto;
import org.testng.Assert;
import org.testng.annotations.Test;
import screens.AuthenticationScreen;
import screens.ContactsScreen;
import screens.ErrorScreen;
import screens.SplashScreen;
import static helper.RandomUtils.*;
import static helper.PropertiesReader.getProperty;
public class RegistrationTests extends AppiumConfig {


    @Test
    public void registrationPositiveTest() {
        UserDto user = UserDto.builder()
                .username(generateEmail(10))
                .password("7206@Rom")
                .build();
        new SplashScreen(driver).goToAuthScreen();
        AuthenticationScreen authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.typeAuthenticationForm(user);
        authenticationScreen.clickBtnRegistration();
        Assert.assertTrue( new ContactsScreen(driver).validateHeader());
    }

    @Test
    public void registrationNegativeTest_wrongEmail() {
        UserDto user = UserDto.builder()
                .username(generateString(10))
                .password("7206@Rom")
                .build();
        new SplashScreen(driver).goToAuthScreen();
        AuthenticationScreen authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.typeAuthenticationForm(user);
        authenticationScreen.clickBtnRegistration();
      Assert.assertTrue( new ErrorScreen(driver)
              .validateErrorMessage("must be a well-formed email address",5));
    }

    @Test
    public void registrationNegativeTest_wrongPassword() {
        UserDto user = UserDto.builder()
                .username(generateEmail(10))
                .password("7206Rom")
                .build();
        new SplashScreen(driver).goToAuthScreen();
        AuthenticationScreen authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.typeAuthenticationForm(user);
        authenticationScreen.clickBtnRegistration();
        Assert.assertTrue( new ErrorScreen(driver)
                .validateErrorMessage("Must contain at least",5));
    }

    @Test
    public void registrationNegativeTest_duplicateUser() {
        UserDto user = UserDto.builder()
                .username(getProperty("data.properties","email"))
                .password(getProperty("data.properties","password"))
                .build();
        new SplashScreen(driver).goToAuthScreen();
        AuthenticationScreen authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.typeAuthenticationForm(user);
        authenticationScreen.clickBtnRegistration();
        Assert.assertTrue( new ErrorScreen(driver)
                .validateErrorMessage("User already exists",5));
    }
}