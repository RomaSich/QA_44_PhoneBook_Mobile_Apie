package mobile_tests;

import config.AppiumConfig;
import dto.ContactDtoLombok;
import dto.ContactsDto;
import dto.UserDto;
import helper.HelperApiMobile;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.*;

import static helper.PropertiesReader.getProperty;
import static helper.RandomUtils.*;
import static helper.RandomUtils.generateString;

public class AddNewContactsTests extends AppiumConfig {

AddNewContactScreen addNewContactScreen;
    UserDto user = UserDto.builder()
            .username(getProperty("data.properties", "email"))
            .password(getProperty("data.properties", "password"))
            .build();
    @BeforeMethod
    public void loginAndGoToContactScreen()
    {
        new SplashScreen(driver).goToAuthScreen(5);
        AuthenticationScreen authenticationScreen = new AuthenticationScreen(driver);
        authenticationScreen.typeAuthenticationForm(user);
        authenticationScreen.clickBtnLogin();
        new ContactsScreen(driver).clickBtnAddNewContact();
        addNewContactScreen=new AddNewContactScreen(driver);
    }
    @Test
    public void addNewContactPositiveTest()
    {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5)).
                lastName(generateString(10)).
                email(generateEmail(12)).
                phone(generatePhone(10)).
                address(generateString(8)+" app."+generatePhone(2)).
                description(generateString(10))
                .build();
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreateContact();
        Assert.assertTrue(new ContactsScreen(driver).validatePopMessage());
    }

    @Test
    public void addNewContactPositiveTestValidateContactApi()
    {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5)).
                lastName(generateString(10)).
                email(generateEmail(12)).
                phone(generatePhone(10)).
                address(generateString(8)+" app."+generatePhone(2)).
                description(generateString(10))
                .build();
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreateContact();

        HelperApiMobile helperApiMobile = new HelperApiMobile();
        helperApiMobile.login(user.getUsername(),user.getPassword());
        Response responseGet = helperApiMobile.getUserContactsResponse();
        ContactsDto contactsDto = responseGet.as(ContactsDto.class);
        boolean flag = false;
        for (ContactDtoLombok c:contactsDto.getContacts())
        {
            if (c.equals(contact)) {
                flag=true;
                break;
            }
        }
        System.out.println("--. " + flag);
        Assert.assertTrue(flag);
    }
    @Test
    public void addNewContactNegativeTest_wrongEmail()
    {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5)).
                lastName(generateString(10)).
                email(generateString(12)).
                phone(generatePhone(10)).
                address(generateString(8)+" app."+generatePhone(2)).
                description(generateString(10))
                .build();
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreateContact();
        Assert.assertTrue(new ErrorScreen(driver).validateErrorMessage("email=must be a well",5));
    }
    @Test
    public void addNewContactNegativeTest_wrongPhone()
    {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(6)).
                lastName(generateString(10)).
                email(generateEmail(12)).
                phone(generateString(10)).
                address(generateString(8)+" app."+generatePhone(2)).
                description(generateString(10))
                .build();
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreateContact();
        Assert.assertTrue(new ErrorScreen(driver)
         .validateErrorMessage("Phone number must contain only digits! ",5));
    }
}
