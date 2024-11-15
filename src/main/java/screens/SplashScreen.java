package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import jdk.jshell.spi.ExecutionControl;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SplashScreen extends BaseScreen{
    public SplashScreen(AppiumDriver<AndroidElement> driver) {
        super(driver);
    }
    @FindBy(id = "com.sheygam.contactapp:id/version_text")
    AndroidElement versionApp;

    public boolean validateVersion() {
        return textInElementPresent(versionApp, "Version 1.0.0", 5);
    }

    public void goToAuthScreen() {
        pause(10);
    }

    public void goToAuthScreen(int time) {
        try {
            new WebDriverWait(driver, time)
                    .until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//*[@text='Authentication']")));
        }catch (TimeoutException e){
            e.printStackTrace();
            System.out.println("element Authentication not find");
        }

    }

    public boolean validateSplashScreenToDisappear(long expectedTime) {

        long startTime = System.currentTimeMillis();
        long endTime = 0;
        if(
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.invisibilityOf(versionApp)))
        endTime = System.currentTimeMillis();
        long splashDuration = endTime-startTime;
        if(splashDuration<=expectedTime)
            return true;
        else return false;

    }
}