package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactsScreen extends BaseScreen{

        public ContactsScreen(AppiumDriver<AndroidElement> driver) {
            super(driver);
        }

    @FindBy(xpath = "//*[@class='android.widget.TextView']")
    AndroidElement headerContactsScreen;



    public boolean validateHeader() {

        return textInElementPresent(headerContactsScreen,"Contact list", 5);
    }
}
