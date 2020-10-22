package steve.step_definitions;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Integer.parseInt;

public class ecs {
    private static final String CHROME_DRIVER_PATH = "C:\\Projects\\ecs-qa\\src\\main\\resources\\drivers\\chromedriver.exe";
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000/");

        WebElement el;

        el = driver.findElement(By.xpath(".//span[contains(text(), 'Render the Challenge')]"));
        el.click();

        List<WebElement> arrayCells = new ArrayList<>();

        String[] rowIndex = {"1", "2", "3"};

        for (String rowNumber : rowIndex) {
            String insideEl = "array-item-" + rowNumber + "-0";

            arrayCells = driver.findElements(By.xpath(".//td[@data-test-id = '" + insideEl + "'" + "]//ancestor::tr//descendant::td"));

            boolean found = false;
            int shift = 0;
            int index = (arrayCells.size() - 1) / 2;
            String answer;

            if (arrayCells.size() % 2 == 1) {
                int leftTotal = 0;
                int rightTotal = 0;

                while (!((index + shift == 0) || (index - shift == 0 || found))) {
                    leftTotal = countSide("left", index, arrayCells, shift);
                    rightTotal = countSide("right", index, arrayCells, shift);

                    if (leftTotal - rightTotal == 0) {
                        found = true;
                    } else if (leftTotal - rightTotal > 0) {
                        shift--;
                    } else {
                        shift++;
                    }
                }

            } else {
                System.out.println("The array should have an odd number of values for this test");
            }

            if (found) {
                answer = Integer.toString(index + shift);
            } else {
                answer = null;
            }

            insideEl = "submit-" + rowNumber;
            el = driver.findElement(By.xpath(".//input[@data-test-id = '" + insideEl + "'" + "]"));
            el.sendKeys(answer);
        }

        el = driver.findElement(By.xpath(".//input[@data-test-id = 'submit-4']"));
        el.sendKeys("Steve McDonald");

        el = driver.findElement(By.xpath(".//span[contains(text(), 'Submit Answers')]"));
        el.click();
    }

    public static int countSide(String sideName, int noOfElements, List<WebElement> arrayCells, int shift) {
        int total = 0;

        if (sideName.equalsIgnoreCase("left")) {
            for(int i=0; i<noOfElements + shift; i++) {
                total += parseInt(arrayCells.get(i).getText());
            }
        } else {
            int endIndex = arrayCells.size()-1;
            for(int i=endIndex; i>endIndex-noOfElements + shift; i--) {
                total += parseInt(arrayCells.get(i).getText());
            }
        }
        return total;
    }

}