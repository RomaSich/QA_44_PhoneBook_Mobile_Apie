package mobile_tests;

import config.AppiumConfig;
import dto.ContactDtoLombok;
import dto.UserDto;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.AuthenticationScreen;
import screens.ContactsScreen;
import screens.EditeContactScreen;
import screens.SplashScreen;

import static helper.PropertiesReader.getProperty;
import static helper.RandomUtils.*;
import static helper.RandomUtils.generateString;

public class EditContactTests extends AppiumConfig {
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
    public void updateContactPositiveTest()
    {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5)).
                lastName(generateString(10)).
                email(generateEmail(12)).
                phone(generatePhone(10)).
                address(generateString(8)+" app."+generatePhone(2)).
                description(generateString(10))
                .build();
        contactsScreen.updateContact();
       EditeContactScreen editeContact = new EditeContactScreen(driver);
       editeContact.typeEditeForm(contact);
       editeContact.clickBtnUpdateContact();
        Assert.assertTrue(new ContactsScreen(driver).validatePopMessage("Contact was updated!"));

    }

}
