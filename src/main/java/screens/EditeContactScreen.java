package screens;

import dto.ContactDtoLombok;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.support.FindBy;

public class EditeContactScreen extends BaseScreen{

    public EditeContactScreen(AppiumDriver<AndroidElement> driver) {
        super(driver);
    }
    @FindBy(id = "com.sheygam.contactapp:id/inputName")
    AndroidElement inputName;
    @FindBy(id = "com.sheygam.contactapp:id/inputLastName")
    AndroidElement inputLastName;
    @FindBy(id = "com.sheygam.contactapp:id/inputEmail")
    AndroidElement inputEmail;
    @FindBy(id = "com.sheygam.contactapp:id/inputPhone")
    AndroidElement inputPhone;
    @FindBy(id = "com.sheygam.contactapp:id/inputAddress")
    AndroidElement inputAddress;
    @FindBy(id = "com.sheygam.contactapp:id/inputDesc")
    AndroidElement inputDescription;
    @FindBy(id="com.sheygam.contactapp:id/updateBtn")
    AndroidElement btnUpdateContact;

    public void typeEditeForm(ContactDtoLombok contact)
    {

        inputName.clear();
        inputName.sendKeys(contact.getName());
        inputLastName.clear();
        inputLastName.sendKeys(contact.getLastName());
        inputEmail.clear();
        inputEmail.sendKeys(contact.getEmail());
        inputPhone.clear();
        inputPhone.sendKeys(contact.getPhone());
        inputAddress.clear();
        inputAddress.sendKeys(contact.getAddress());
        inputDescription.clear();
        inputDescription.sendKeys(contact.getDescription());

    }

    public void clickBtnUpdateContact() {

        btnUpdateContact.click();
    }
}
