
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumDriver {

    private String myEmail = "***************";
    private String myPass = "************";
    private String CLIENT_SECRET = "********************";

    public String getConnection(){

        String getAuthUrl = "https://oauth.vk.com/authorize?"+
                "client_id=6233942&"+
                "display=page&"+
                "redirect_uri=https://oauth.vk.com/blank.html&"+
                "scope=offline,wall&"+
                "response_type=code&" +
                "v=5.69";

        System.setProperty("webdriver.chrome.driver", "/home/*******/*********/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get(getAuthUrl);

        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys(myEmail);

        WebElement pass = driver.findElement(By.name("pass"));
        pass.sendKeys(myPass);

        WebElement buttonEnter = driver.findElement(By.id("install_allow"));
        buttonEnter.click();

        String currentUrl = driver.getCurrentUrl();
        //System.out.println(currentUrl);

        String code = currentUrl.substring(37);
        System.out.println("code = " + code);

        String getTokenUrl = "https://oauth.vk.com/access_token?" +
                "client_id=**********" +
                "&client_secret="+ CLIENT_SECRET +
                "&redirect_uri=https://oauth.vk.com/blank.html" +
                "&code=" + code;

        driver.get(getTokenUrl);

        WebElement getToken = driver.findElement(By.tagName("body"));
        String token = getToken.getText();

        JsonObject jso = new Gson().fromJson(token, JsonObject.class);
        String access_token = jso.get("access_token").toString();

        access_token = access_token.substring(1, access_token.length() - 1); //Вырезать ковычки
        System.out.println("access_token = " + access_token);

        return  access_token;
    }

}
