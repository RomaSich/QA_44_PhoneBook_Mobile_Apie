package screens;

import dto.ContactDtoLombok;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

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
    @FindBy(xpath = "//*[@text = 'Logout']")
    AndroidElement btnLogout;
    @FindBy(xpath = "//*[@text = 'Date picker']")
    AndroidElement btnDataPicker;
    @FindBy(xpath = "//android.widget.ImageView[@content-desc='More options']")
    AndroidElement btnMoreOptions;

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
        pause(3);
        int xLeftUpCorner = firstElementContactsList.getLocation().getX();
        int yLeftUpCorner = firstElementContactsList.getLocation().getY();
        int widthElement = firstElementContactsList.getSize().getWidth();
        int heightElement = firstElementContactsList.getSize().getHeight();
        TouchAction<?> touchAction = new TouchAction<>(driver);
        touchAction.longPress(PointOption.point(xLeftUpCorner+widthElement*5/6,yLeftUpCorner+heightElement/2))
                .moveTo(PointOption.point(xLeftUpCorner+widthElement/6,yLeftUpCorner+heightElement/2))
                .release().perform();
    }
    public boolean validateUIListContact(ContactDtoLombok contact)
    {
        String nameFamily = contact.getName()+" "+contact.getLastName();
        boolean flagEqualsNameFamily = false;


        while (true) {
            List<AndroidElement> listContactOnScreen = new ArrayList<>();
            listContactOnScreen = driver.findElements(By.xpath("//*[@resource-id='com.sheygam.contactapp:id/rowName']"));
            System.out.println(listContactOnScreen);
            for (AndroidElement e : listContactOnScreen) {
                System.out.println(e.getText());
                if (e.getText().equals(nameFamily)) {
                    flagEqualsNameFamily = true;
                    return true;
                }
                }
            if (!canScrollUp()){
                break;
            }
        scrollUp();
        }
        return flagEqualsNameFamily;
    }

    private boolean canScrollUp() {
        List<AndroidElement> oldElements = driver.findElements(By.xpath("//*[@resource-id='com.sheygam.contactapp:id/rowName']"));
        scrollUp();
        List<AndroidElement> newElements = driver.findElements(By.xpath("//*[@resource-id='com.sheygam.contactapp:id/rowName']"));
        return !newElements.equals(oldElements);
    }

    private void scrollUp() {
       int height = driver.manage().window().getSize().getHeight();
        TouchAction<?> touchAction = new TouchAction<>(driver);
        touchAction.longPress(PointOption.point(10,height/8*7))
                .moveTo(PointOption.point(10,height/8))
                .release().perform();
    }
    public void logout() {
        clickWait(btnMoreOptions, 3);
        clickWait(btnLogout, 3);
    }
    public void clickToDatePiker()
    {
        clickWait(btnMoreOptions,3);
        clickWait(btnDataPicker,3);
    }
}


