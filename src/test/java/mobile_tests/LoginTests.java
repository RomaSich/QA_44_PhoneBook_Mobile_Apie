package mobile_tests;
import static helper.PropertiesReader.getProperty;
import config.AppiumConfig;
import dto.UserDto;
import org.testng.Assert;
import org.testng.annotations.Test;
import screens.AuthenticationScreen;
import screens.SplashScreen;

public class LoginTests extends AppiumConfig {

    @Test
    public void loginPositiveTests()
    {
        UserDto user = new UserDto(getProperty("data.properties","email"),
                getProperty("data.properties","password"));
        new SplashScreen(driver).goToAuthScreen();
        AuthenticationScreen authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.typeAuthenticationForm(user);
        authenticationScreen.clickBtnLogin();
        Assert.assertTrue( new AuthenticationScreen(driver).elementPresent());
    }
}
