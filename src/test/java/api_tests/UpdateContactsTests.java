package api_tests;

import config.ContactController;
import dto.ContactDtoLombok;
import dto.TokenDto;
import dto.UserDto;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static helper.RandomUtils.*;


import static helper.PropertiesReader.getProperty;

public class UpdateContactsTests extends ContactController {
    ContactDtoLombok contact = new ContactDtoLombok();
    TokenDto tokenDto;

    @BeforeClass
    public void loginPositiveTest()
    {
        UserDto user = new UserDto(getProperty("data.properties","email")
                ,getProperty("data.properties","password"));

        Assert.assertEquals(requestRegLogin(user,LOGIN_PATH).getStatusCode(),200);
    }
    @Test
    public void updateContactPositiveTest()
    {
        ContactDtoLombok contactNew = ContactDtoLombok.builder()
                .id(contact.getId())
                .name(generateString(5)).
                lastName(generateString(10)).
                phone(generatePhone(10)).
                email(generateEmail(12)).
                address(generateString(20)).
                description(generateString(10))
                .build();
        Assert.assertEquals(requestContacts(contactNew,tokenDto.getToken()).getStatusCode(),200);


    }
}
