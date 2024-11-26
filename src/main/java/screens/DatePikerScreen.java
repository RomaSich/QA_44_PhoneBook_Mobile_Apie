package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;

public class DatePikerScreen extends BaseScreen{

    public DatePikerScreen(AppiumDriver<AndroidElement> driver) {
        super(driver);
    }

    @FindBy(id = "com.sheygam.contactapp:id/dateBtn")
    AndroidElement btnChangeDate;
    @FindBy(id = "android:id/prev")
    AndroidElement btnPrevMonth;
    @FindBy(id = "android:id/next")
    AndroidElement btnNextMonth;
    @FindBy(id = "android:id/date_picker_header_year")
    AndroidElement btnPickerYear;
    @FindBy(id = "android:id/button1")
    AndroidElement btnOk;
    @FindBy(id="com.sheygam.contactapp:id/dateTxt")
    AndroidElement pikerDateScreen;

    public boolean validatePikerDate(String expectedDate) {
pause(2);
        String actualDate = getSelectedDate();
        System.out.println("Actual date: "+actualDate+ " | Expected date: "+expectedDate);
        return actualDate.equals(expectedDate);
    }
    public String getSelectedDate()
    {
       return pikerDateScreen.getText();
    }

    public void typeDate(String date)
    {
        clickWait(btnChangeDate,3);
        String [] arrayDate = date.split(" ");
        String day = arrayDate[0];
        String month = arrayDate[1].toUpperCase();
        int year = Integer.parseInt(arrayDate[2]);
        LocalDate localDate = LocalDate.now();
        if(localDate.getYear()!= year)
        {
            clickWait(btnPickerYear,3);
            driver.findElement(By.xpath("//*[@text='"+year+"']")).click();
        }pause(3);
        String currentMonth;
        do {
            currentMonth = driver.findElement(By.id("android:id/date_picker_header_date"))
                    .getText().split(" ")[1];
            System.out.println("Month passed to getMonthNumber: '" + month +" --->  "+ currentMonth.toUpperCase());
            if (!currentMonth.equalsIgnoreCase(month)) {
                if (shouldMoveNext(currentMonth, month))
                {
                    clickWait(btnNextMonth,3);
                }else {
                    clickWait(btnPrevMonth,3);
                }
            }
        }while (!currentMonth.equalsIgnoreCase(month));

        driver.findElement(By.xpath("//*[@text='" + day + "']")).click();
        btnOk.click();
        System.out.println("Correct Date ---> "+date);
    }
    private boolean shouldMoveNext(String currentMonth, String targetMonth) {
        int currentMonthNumber = getMonthNumber(currentMonth);
        int targetMonthNumber = getMonthNumber(targetMonth);

        return currentMonthNumber < targetMonthNumber; // true, если целевой месяц позже текущего
    }

    private int getMonthNumber(String month) {
        switch (month.toUpperCase()) {
            case "JAN": return 1;
            case "FEB": return 2;
            case "MART": return 3;
            case "APR": return 4;
            case "MAY": return 5;
            case "JUNE": return 6;
            case "JULY": return 7;
            case "AUG": return 8;
            case "SEP": return 9;
            case "OCT": return 10;
            case "NOV": return 11;
            case "DEC": return 12;
            default:
                throw new IllegalArgumentException("Invalid month name: " + month);
        }
    }

}
