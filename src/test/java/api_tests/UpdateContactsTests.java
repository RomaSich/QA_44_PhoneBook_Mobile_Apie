package api_tests;

import config.ContactController;
import dto.ContactDtoLombok;
import dto.ContactsDto;
import dto.ErrorMessageDto;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static helper.RandomUtils.*;
public class UpdateContactsTests extends ContactController {
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
    public void updateContactPositiveTests()
    {
        String idUpdateContact = contacts.getContacts()[0].getId();
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .id(idUpdateContact)
                .name(generateString(5))
                .lastName(generateString(10))
                .address(generateString(20))
                .build();
        Response response = updateContactResponse(contact,tokenDto.getToken());
        softAssert.assertEquals(response.getStatusCode(),200);
        softAssert.assertAll();
    }
    @Test
    public void updateContactNegativeTests_wrongToken401()
    {
        String idUpdateContact = contacts.getContacts()[0].getId();
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .id(idUpdateContact)
                .name(generateString(5))
                .lastName(generateString(10))
                .address(generateString(20))
                .build();
        Response response = updateContactResponse(contact,tokenDto.getToken()+1234);
        softAssert.assertEquals(response.getStatusCode(),401);
        ErrorMessageDto errorMessageDto = ErrorMessageDto.builder().build();
        if(response.getStatusCode()==401)
        {
            errorMessageDto = response.getBody().as(ErrorMessageDto.class);
        }System.out.println(errorMessageDto.getError());
        softAssert.assertTrue(errorMessageDto.getError().equals("Unauthorized"));
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("JWT signature does not match locally computed signature"));
        softAssert.assertAll();
    }
    @Test
    public void updateContactNegativeTests_wrongIdNull400()
    {
        String idUpdateContact = contacts.getContacts()[0].getId();
        ContactDtoLombok contact = ContactDtoLombok.builder()
                .id("null")
                .name(generateString(5))
                .lastName(generateString(10))
                .address(generateString(20))
                .build();
        Response response = updateContactResponse(contact,tokenDto.getToken());
        softAssert.assertEquals(response.getStatusCode(),400);
        ErrorMessageDto errorMessageDto = response.getBody().as(ErrorMessageDto.class);
        System.out.println(errorMessageDto);
        if(response.getStatusCode()==400)
        {
            errorMessageDto = response.getBody().as(ErrorMessageDto.class);
        }System.out.println(errorMessageDto.getError());
        softAssert.assertTrue(errorMessageDto.getError().equals("Bad Request"));
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("null not found in your contacts!"));
        softAssert.assertAll();
    }
//    @Test
//    public void updateContactNegativeTests_contactNoFound404()
//    {
//        String idUpdateContact = contacts.getContacts()[0].getId();
//        ContactDtoLombok contact = ContactDtoLombok.builder()
//                .id(idUpdateContact)
//                .name(generateString(5))
//                .lastName(generateString(10))
//                .address(generateString(20))
//                .build();
//        Response response = updateContactResponse(contact,tokenDto.getToken());
//        softAssert.assertEquals(response.getStatusCode(),404);
//        ErrorMessageDto errorMessageDto = ErrorMessageDto.builder().build();
//        if(response.getStatusCode()==404)
//        {
//            errorMessageDto = response.getBody().as(ErrorMessageDto.class);
//        }System.out.println(errorMessageDto.getError());
//       softAssert.assertAll();
//    }
}
