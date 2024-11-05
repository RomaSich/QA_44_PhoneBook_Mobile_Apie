package config;

import dto.ContactDtoLombok;
import dto.TokenDto;
import org.testng.annotations.BeforeSuite;
import interfaces.BaseApi;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ContactController implements BaseApi {
public TokenDto tokenDto;
    @BeforeSuite
    public void login() {
        AuthenticationController authenticationController = new AuthenticationController();
       tokenDto = authenticationController.requestRegLogin(USER_LOGIN,LOGIN_PATH)
                .as(TokenDto.class);
    }

    protected Response requestContacts(ContactDtoLombok contact, String token) {
        return given()
                .body(contact)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .put(BASE_URL + GET_ALL_CONTACTS_PATH)
                .thenReturn();
    }
}
