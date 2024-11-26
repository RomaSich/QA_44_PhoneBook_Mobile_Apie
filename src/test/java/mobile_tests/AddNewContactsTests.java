package mobile_tests;

import config.AppiumConfig;
import data_provider.ContactDP;
import dto.ContactDtoLombok;
import dto.ContactsDto;
import dto.ErrorMessageDto;
import dto.UserDto;
import helper.HelperApiMobile;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import screens.*;

import java.util.Arrays;

import static helper.PropertiesReader.getProperty;
import static helper.RandomUtils.*;
import static helper.RandomUtils.generateString;

public class AddNewContactsTests extends AppiumConfig {
    SoftAssert softAssert = new SoftAssert();
AddNewContactScreen addNewContactScreen;
ContactsScreen contactsScreen;
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
        contactsScreen= new  ContactsScreen(driver);
        contactsScreen.clickBtnAddNewContact();
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
        Assert.assertTrue(new ContactsScreen(driver).validatePopMessage("Contact was added!"));
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
        System.out.println("--> " + flag);
        Assert.assertTrue(flag);
    }
    @Test
    public void addNewContactNegativeTest_wrongEmail()
    {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(5)).
                lastName(generateString(10)).
                email("wrong email").
                phone(generatePhone(10)).
                address(generateString(8)+" app."+generatePhone(2)).
                description(generateString(10))
                .build();
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreateContact();
        HelperApiMobile helperApiMobile = new HelperApiMobile();
        helperApiMobile.login(user.getUsername(), user.getPassword());
        Response responseGet = helperApiMobile.getUserContactsResponse();
        ContactsDto contacts = responseGet.as(ContactsDto.class);
        boolean flag = true;
        for (ContactDtoLombok c:contacts.getContacts())
        {
            if (c.equals(contact)) {
                flag=false;
                break;
            }
        } System.out.println("--> Contact exists: " + flag);
Assert.assertTrue(flag);
    }
    @Test(dataProvider = "addNewContactDPFile", dataProviderClass = ContactDP.class)
    public void addNewContactNegativeTest_wrongPhone()
    {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name(generateString(6)).
                lastName(generateString(10)).
                email(generateEmail(12)).
                phone("wrong phone").
                address(generateString(8)+" app."+generatePhone(2)).
                description(generateString(10))
                .build();
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreateContact();
        HelperApiMobile helperApiMobile = new HelperApiMobile();
        helperApiMobile.login(user.getUsername(), user.getPassword());
        Response responseGet = helperApiMobile.getUserContactsResponse();
        ContactsDto contacts = responseGet.as(ContactsDto.class);
//        boolean flag = true;
//        for (ContactDtoLombok c:contacts.getContacts())
//        {
//            if (c.equals(contact)) {
//                flag=false;
//                break;
//            }
//        } System.out.println("--> Contact exists: " + flag);
        int numContacts = Arrays.asList(contacts.getContacts()).indexOf(contact);
        System.out.println(numContacts);
        Assert.assertTrue(numContacts!=-1);

    }
    @Test(dataProvider = "addNewContactDPFile", dataProviderClass = ContactDP.class)
    public void addNewContactNegativeTest_emptyFields(ContactDtoLombok contact)
    {
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreateContact();
        Assert.assertTrue(new ErrorScreen(driver)
                .validateErrorMessage("must not be blank",5)
        || new ErrorScreen(driver)
                .validateErrorMessage("well-formed email address",5)
        ||new ErrorScreen(driver)
                .validateErrorMessage("phone number must contain",5));
    }
    @Test
    public void addNewContactNegativeTest_duplicateContact()
    {
        HelperApiMobile helperApiMobile = new HelperApiMobile();
        helperApiMobile.login(user.getUsername(),user.getPassword());
        Response responseGet = helperApiMobile.getUserContactsResponse();
        if (responseGet.getStatusCode()==200) {
            ContactsDto contactsDto = responseGet.as(ContactsDto.class);
            ContactDtoLombok contactApi = contactsDto.getContacts()[0];
            ContactDtoLombok contact = ContactDtoLombok.builder()
                    .name(contactApi.getName()).
                    lastName(contactApi.getLastName()).
                    email(contactApi.getEmail()).
                    phone(contactApi.getPhone()).
                    address(contactApi.getAddress()).
                    description(contactApi.getDescription())
                    .build();
            addNewContactScreen.typeContactForm(contact);
            addNewContactScreen.clickBtnCreateContact();
        }
    }
    @Test
    public void addNewContactPositiveTest_validateUIListContact()
    {
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .name("!!!"+generateString(5)).
                lastName(generateString(10)).
                email(generateEmail(12)).
                phone(generatePhone(10)).
                address(generateString(8)+" app."+generatePhone(2)).
                description(generateString(10))
                .build();
        addNewContactScreen.typeContactForm(contact);
        addNewContactScreen.clickBtnCreateContact();
        softAssert.assertTrue(new ContactsScreen(driver).validatePopMessage("Contact was added!"));
        softAssert.assertTrue(contactsScreen.validateUIListContact(contact));
        softAssert.assertAll();
    }

}
