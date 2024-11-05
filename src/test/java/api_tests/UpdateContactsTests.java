package api_tests;

import config.ContactController;
import dto.ContactDtoLombok;
import org.testng.Assert;

import org.testng.annotations.Test;
import static helper.RandomUtils.*;


import static helper.PropertiesReader.getProperty;

public class UpdateContactsTests extends ContactController {
    ContactDtoLombok contact = new ContactDtoLombok();
    @Test
    public void updateContactPositiveTest()
    {
        ContactDtoLombok newContact = ContactDtoLombok.builder()
                .id(contact.getId())
                .name(generateString(5)).
                lastName(generateString(10)).
                phone(generatePhone(10)).
                email(generateEmail(12)).
                address(generateString(20)).
                description(generateString(10))
                .build();
        Assert.assertEquals(requestContacts(newContact,tokenDto.getToken()).getStatusCode(),200);


    }
}
