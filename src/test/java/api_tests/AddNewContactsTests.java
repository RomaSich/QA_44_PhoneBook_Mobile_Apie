package api_tests;

import config.ContactController;
import dto.ContactDtoLombok;
import dto.ContactsDto;
import dto.ErrorMessageDto;
import org.testng.Assert;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static helper.RandomUtils.*;

public class AddNewContactsTests extends ContactController {
    ContactsDto contacts;
    SoftAssert softAssert = new SoftAssert();
    @BeforeMethod
    public void getContact()
    {
        Response response = getUserContactResponse(tokenDto.getToken());
        if(response.getStatusCode()==200)
        {
            contacts = response.as(ContactsDto.class);
        }else {
            System.out.println("Something wrong, status code"+response.getStatusCode());
        }
    }
    @Test
    public void addNewContactPositiveTest()
    {
        String idUpdateContact = contacts.getContacts()[0].getId();
        ContactDtoLombok newContact = ContactDtoLombok.builder()
                .id(idUpdateContact)
                .name(generateString(5)).
                lastName(generateString(10)).
                phone(generatePhone(10)).
                email(generateEmail(12)).
                address(generateString(10)).
                description(generateString(10))
                .build();
        Assert.assertEquals(getAddNewContactsRequest(newContact,tokenDto.getToken()).getStatusCode(),200);
    }
    @Test
    public void addNewContactNegativeTest_unauthorized401()
    {
        String idUpdateContact = contacts.getContacts()[0].getId();
        ContactDtoLombok newContact = ContactDtoLombok.builder()
                .id(idUpdateContact)
                .name(generateString(5)).
                lastName(generateString(10)).
                phone(generatePhone(10)).
                email(generateString(12)).
                address(generateString(10)).
                description(generateString(10))
                .build();
        Response response = getAddNewContactsRequest(newContact,GET_ALL_CONTACTS_PATH);
        ErrorMessageDto errorMessageDto = response.getBody().as(ErrorMessageDto.class);
        System.out.println(errorMessageDto);
        softAssert.assertTrue(errorMessageDto.getError().equals("Unauthorized"));
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("JWT strings must contain exactly 2 period characters"));
        softAssert.assertEquals(response.getStatusCode(), 401);
        softAssert.assertAll();
    }
}
