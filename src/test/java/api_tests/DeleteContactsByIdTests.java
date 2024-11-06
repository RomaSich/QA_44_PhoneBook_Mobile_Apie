package api_tests;

import config.ContactController;
import dto.ContactsDto;
import dto.ErrorMessageDto;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class DeleteContactsByIdTests extends ContactController {

    ContactsDto contacts;
    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void getContact() {
        Response response = getUserContactResponse(tokenDto.getToken());
        if (response.getStatusCode() == 200) {
            contacts = response.as(ContactsDto.class);
        } else {
            System.out.println("Something wrong, status code" + response.getStatusCode());
        }
    }

    @Test
    public void deleteContactByIdPositiveTest() {
        String idUpdateContact = contacts.getContacts()[0].getId();
        Response response = deleteContactByIdResponse(idUpdateContact, tokenDto.getToken());
        softAssert.assertEquals(response.getStatusCode(), 200);
        ErrorMessageDto message = ErrorMessageDto.builder().build();
        if (response.getStatusCode() == 200) {
            System.out.println("<<Contact deleted>>");
        } else
            System.out.println(message.getError());
        softAssert.assertAll();
    }

    @Test
    public void deleteContactByIdNegativeTest_wrongId_400() {
        String idUpdateContact = contacts.getContacts()[0].toString();
        Response response = deleteContactByIdResponse(idUpdateContact, tokenDto.getToken());
        softAssert.assertEquals(response.getStatusCode(), 400);
        ErrorMessageDto errorMessage = ErrorMessageDto.builder().build();
        if (response.getStatusCode() == 400) {
            errorMessage = response.getBody().as(ErrorMessageDto.class);
            System.out.println("<<Contact no deleted>>" + System.lineSeparator() + errorMessage);
        } else
            System.out.println(errorMessage.getError());
        softAssert.assertTrue(errorMessage.getError().equals("Bad Request"));
        softAssert.assertAll();
    }

    @Test
    public void deleteContactByIdNegativeTest_wrongToken_401() {
        String idUpdateContact = contacts.getContacts()[0].getId();
        Response response = deleteContactByIdResponse(idUpdateContact, tokenDto.getToken() + 1234);
        softAssert.assertEquals(response.getStatusCode(), 401);
        ErrorMessageDto errorMessage = ErrorMessageDto.builder().build();
        if (response.getStatusCode() == 401) {
            errorMessage = response.getBody().as(ErrorMessageDto.class);
            System.out.println("<<Contact no deleted>>" + System.lineSeparator() + errorMessage);
        } else
            System.out.println(errorMessage.getError());
        softAssert.assertTrue(errorMessage.getError().equals("Unauthorized"));
        softAssert.assertTrue(errorMessage.getMessage().toString().contains("JWT signature does not match locally computed signature"));
        softAssert.assertAll();
    }
}
