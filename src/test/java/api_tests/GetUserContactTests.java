package api_tests;

import config.ContactController;
import dto.ErrorMessageDto;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetUserContactTests extends ContactController {
SoftAssert softAssert = new SoftAssert();
    @Test
    public void getUserContactsPositiveTest()
    {
        Response response = getUserContactResponse(tokenDto.getToken());
       softAssert.assertEquals(response.getStatusCode(),200);
       softAssert.assertAll();
    }
    @Test
    public void getUserContactsNegativeTest_wrongToke401()
    {
        Response response = getUserContactResponse(tokenDto.getToken()+1234);
        ErrorMessageDto errorMessageDto = ErrorMessageDto.builder().build();

        if(response.getStatusCode()==401)
        {
            errorMessageDto = response.as(ErrorMessageDto.class);
        }
        System.out.println(errorMessageDto.toString());
        softAssert.assertTrue(errorMessageDto.getError().equals("Unauthorized"));
        softAssert.assertAll();
    }
}
