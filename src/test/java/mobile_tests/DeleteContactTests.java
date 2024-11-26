package mobile_tests;

import config.AppiumConfig;
import dto.ContactsDto;
import dto.UserDto;
import helper.HelperApiMobile;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.AddNewContactScreen;
import screens.AuthenticationScreen;
import screens.ContactsScreen;
import screens.SplashScreen;

import static helper.PropertiesReader.getProperty;

public class DeleteContactTests extends AppiumConfig {

    ContactsScreen contactsScreen;
    UserDto user = UserDto.builder()
            .username(getProperty("data.properties", "email"))
            .password(getProperty("data.properties", "password"))
            .build();

    @BeforeMethod
    public void loginAndGoToContactScreen() {
        new SplashScreen(driver).goToAuthScreen(5);
        AuthenticationScreen authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.typeAuthenticationForm(user);
        authenticationScreen.clickBtnLogin();
        contactsScreen = new ContactsScreen(driver);
    }

    @Test
    public void deleteContactPositiveTest()
    {
        HelperApiMobile helperApiMobile = new HelperApiMobile();
        helperApiMobile.login(user.getUsername(),user.getPassword());
        Response responseGetBefore = helperApiMobile.getUserContactsResponse();
        ContactsDto contactsDtoBefore = responseGetBefore.as(ContactsDto.class);
        int quantityContactsBefore = contactsDtoBefore.getContacts().length;
        contactsScreen.deleteContact();
        contactsScreen.clickBtnYes();
        Response responseGetAfter = helperApiMobile.getUserContactsResponse();
        ContactsDto contactsDtoAfter = responseGetAfter.as(ContactsDto.class);
        int quantityContactsAfter = contactsDtoAfter.getContacts().length;
        System.out.println(quantityContactsBefore+" X "+quantityContactsAfter);
        Assert.assertEquals(quantityContactsBefore-1,quantityContactsAfter);
    }
}
