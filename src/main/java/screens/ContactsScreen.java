package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.support.FindBy;

public class ContactsScreen extends BaseScreen{

        public ContactsScreen(AppiumDriver<AndroidElement> driver) {
            super(driver);
        }

    @FindBy(xpath = "//*[@class='android.widget.TextView']")
    AndroidElement headerContactsScreen;
    @FindBy(id = "com.sheygam.contactapp:id/add_contact_btn")
    AndroidElement btnAddNewContact;
    @FindBy(xpath = "/hierarchy/android.widget.Toast")
    AndroidElement popUpMessage;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/rowContainer']")
    AndroidElement firstElementContactsList;
    @FindBy(id= "android:id/button1")
    AndroidElement popUpBtnYes;

    public boolean validateHeader() {

        return textInElementPresent(headerContactsScreen,"Contact list", 5);
    }
    public void clickBtnAddNewContact()
    {
        clickWait(btnAddNewContact,5);
    }
    public boolean validatePopMessage(String message)
    {
        return textInElementPresent(popUpMessage, message,5);

    }

public void deleteContact(){
        pause(3);

int xLeftUpCorner = firstElementContactsList.getLocation().getX();
int yLeftUpCorner = firstElementContactsList.getLocation().getY();
int widthElement = firstElementContactsList.getSize().getWidth();
int heightElement = firstElementContactsList.getSize().getHeight();

    TouchAction<?> touchAction = new TouchAction(driver);
    touchAction.longPress(PointOption.point(widthElement/6,yLeftUpCorner+heightElement/2))
            .moveTo(PointOption.point(widthElement/6*5,yLeftUpCorner+heightElement/2))
            .release().perform();
}
public void clickBtnYes()
{
    popUpBtnYes.click();
}

    public void updateContact()
    {
        pause(2);
        int xLeftUpCorner = firstElementContactsList.getLocation().getX();
        int yLeftUpCorner = firstElementContactsList.getLocation().getY();
        int widthElement = firstElementContactsList.getSize().getWidth();
        int heightElement = firstElementContactsList.getSize().getHeight();
        TouchAction<?> touchAction = new TouchAction<>(driver);
        touchAction.longPress(PointOption.point(xLeftUpCorner+widthElement*5/6,yLeftUpCorner+heightElement/2))
                .moveTo(PointOption.point(xLeftUpCorner+widthElement/6,yLeftUpCorner+heightElement/2))
                .release().perform();
        pause(2);
    }
}


