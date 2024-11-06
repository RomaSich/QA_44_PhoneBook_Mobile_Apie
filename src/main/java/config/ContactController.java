package config;

import dto.ContactDtoLombok;
import dto.TokenDto;

import io.restassured.specification.RedirectSpecification;
import org.testng.annotations.BeforeSuite;
import interfaces.BaseApi;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ContactController implements BaseApi {
protected TokenDto tokenDto;
private RedirectSpecification redirectSpecification;

    @BeforeSuite
    public void login() {
        AuthenticationController authenticationController = new AuthenticationController();
       tokenDto = authenticationController.requestRegLogin(USER_LOGIN,LOGIN_PATH)
                .as(TokenDto.class);
    }

    protected Response getAddNewContactsRequest(ContactDtoLombok contact, String token) {
        return given()
                .body(contact)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .post(BASE_URL + GET_ALL_CONTACTS_PATH)
                .thenReturn();
    }
    protected Response getUserContactResponse(String token)
    {
        return given()
                .header("Authorization", token)
                .when()
                .get(BASE_URL+GET_ALL_CONTACTS_PATH)
                .thenReturn();
    }
    protected Response updateContactResponse(ContactDtoLombok contact, String token)
    {
        return given()
                .body(contact)
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .put(BASE_URL+GET_ALL_CONTACTS_PATH)
                .thenReturn();
    }
    protected Response deleteContactByIdResponse(String id, String token)
    {
        return given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .pathParam("id",id)
                .delete(BASE_URL+DELETE_CONTACT_BY_ID)
                .thenReturn();
    }

}
