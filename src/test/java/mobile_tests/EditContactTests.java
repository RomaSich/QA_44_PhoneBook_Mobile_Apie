package mobile_tests;

import config.AppiumConfig;
import dto.ContactDtoLombok;
import dto.ContactsDto;
import dto.UserDto;
import helper.HelperApiMobile;
import io.restassured.response.Response;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.*;

import java.util.Arrays;
import java.util.List;

import static helper.PropertiesReader.getProperty;
import static helper.RandomUtils.*;
import static helper.RandomUtils.generateString;

public class EditContactTests extends AppiumConfig {
    ContactsScreen contactsScreen;
    EditeContactScreen editeContact;
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
       new EditeContactScreen(driver);
       editeContact.typeEditeForm(contact);
        Assert.assertTrue(new ContactsScreen(driver).validatePopMessage("Contact was updated!"));

    }

    @Test
    public void updateContactPositiveTest_Api()
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
        new EditeContactScreen(driver).typeEditeForm(contact);
        HelperApiMobile helperApiMobile = new HelperApiMobile();
        helperApiMobile.login(user.getUsername(),user.getPassword());
        Response responseGet = helperApiMobile.getUserContactsResponse();
        ContactsDto contactsDto = responseGet.as(ContactsDto.class);
        List<ContactDtoLombok> contactDtoLombokList = Arrays.asList(contactsDto.getContacts());
        Assert.assertTrue(contactDtoLombokList.contains(contact));

    }
    @Test
    public void updateContactNegativeTest_emptyName()
    {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(" ").
                lastName(generateString(10)).
                email(generateEmail(12)).
                phone(generatePhone(10)).
                address(generateString(8)+" app."+generatePhone(2)).
                description(generateString(10))
                .build();
        contactsScreen.updateContact();
        EditeContactScreen editeContact = new EditeContactScreen(driver);
        editeContact.typeEditeForm(contact);
        Assert.assertTrue(new ErrorScreen(driver).validateErrorMessage("must not be blank",5));

    }

}
