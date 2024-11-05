package api_tests;

import config.ContactController;
import dto.ContactDtoLombok;
import dto.ErrorMessageDto;
import org.testng.Assert;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static helper.RandomUtils.*;

public class addNewContactsTests extends ContactController {
    ContactDtoLombok contact = new ContactDtoLombok();
    SoftAssert softAssert = new SoftAssert();
    @Test
    public void addNewContactPositiveTest()
    {
        ContactDtoLombok newContact = ContactDtoLombok.builder()
                .id(contact.getId())
                .name(generateString(5)).
                lastName(generateString(10)).
                phone(generatePhone(10)).
                email(generateEmail(12)).
                address(generateString(10)).
                description(generateString(10))
                .build();
        Assert.assertEquals(requestContacts(newContact,tokenDto.getToken()).getStatusCode(),200);
    }
    @Test
    public void addNewContactNegativeTest_unauthorized401()
    {
        ContactDtoLombok newContact = ContactDtoLombok.builder()
                .id(contact.getId())
                .name(generateString(5)).
                lastName(generateString(10)).
                phone(generatePhone(10)).
                email(generateEmail(12)).
                address(generateString(10)).
                description(generateString(10))
                .build();
        Response response = requestContacts(newContact,GET_ALL_CONTACTS_PATH);
        ErrorMessageDto errorMessageDto = response.getBody().as(ErrorMessageDto.class);
        System.out.println(errorMessageDto);
        softAssert.assertTrue(errorMessageDto.getError().equals("Unauthorized"));
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("JWT strings must contain exactly 2 period characters"));
        softAssert.assertEquals(response.getStatusCode(), 401);
        softAssert.assertAll();
    }
}
