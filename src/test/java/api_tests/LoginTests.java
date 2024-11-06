package api_tests;

import config.AuthenticationController;
import dto.ErrorMessageDto;
import dto.UserDto;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;

import static helper.PropertiesReader.getProperty;
import static helper.RandomUtils.*;

public class LoginTests extends AuthenticationController {
    SoftAssert softAssert = new SoftAssert();
    @Test
    public void loginPositiveTest()
    {
        UserDto user = new UserDto(getProperty("data.properties","email")
                ,getProperty("data.properties","password"));

        Assert.assertEquals(requestRegLogin(user,LOGIN_PATH).getStatusCode(),200);
    }
    @Test
    public void loginNegativeTest_emailIsIncorrect_401()
    {
        UserDto user = new UserDto(generateString(12),"7206@Rom");
        Response response = requestRegLogin(user,LOGIN_PATH);
        ErrorMessageDto errorMessageDto = response.getBody().as(ErrorMessageDto.class);
        System.out.println(errorMessageDto);
        softAssert.assertTrue(errorMessageDto.getError().equals("Unauthorized"));
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("Login or Password incorrect"));
        softAssert.assertEquals(response.getStatusCode(),401);
        //System.out.println(errorMessageDto.getTimestamp());
        LocalDate localDate = LocalDate.now();
        //System.out.println(localDate.toString());
        softAssert.assertEquals(errorMessageDto.getTimestamp().split("T")[0], localDate.toString());
        softAssert.assertAll();
    }

}
