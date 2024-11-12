package mobile_tests;
import static helper.PropertiesReader.getProperty;
import config.AppiumConfig;
import dto.UserDto;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.AuthenticationScreen;
import screens.ContactsScreen;
import screens.SplashScreen;
import static helper.RandomUtils.*;

public class LoginTests extends AppiumConfig {

    AuthenticationScreen authenticationScreen;
    @BeforeMethod
    public void openLoginForm()
    {
        new SplashScreen(driver).goToAuthScreen(5);
        authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.typeAuthenticationForm(user);
    }

    @Test
    public void loginPositiveTest()
    {
        UserDto user = UserDto.builder().build();
        authenticationScreen.typeAuthenticationForm(user);
        authenticationScreen.clickBtnLogin();
        Assert.assertTrue( new ContactsScreen(driver).validateHeader());
    }
    @Test
    public void loginNegativeTest_unregEmail()
    {
        UserDto user = new UserDto(getProperty("data.properties","email"),
                getProperty("data.properties","password"));
        authenticationScreen.typeAuthenticationForm(user);
        authenticationScreen.clickBtnLogin();
        Assert.assertTrue( new ContactsScreen(driver).validateHeader());
    }
}
