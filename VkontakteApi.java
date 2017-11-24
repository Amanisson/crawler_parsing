import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.sql.SQLException;

public class VkontakteApi {

    public void getWallPosts(int id) throws IOException, SQLException, ClassNotFoundException {

        SeleniumDriver seldrv = new SeleniumDriver();
        DataBase db = new DataBase();

        String access_token = seldrv.getConnection();

        String methodGetWall = "https://api.vk.com/method/wall.get?" +
                "owner_id=" + id +
                "&count=2"+
                "&access_token=" + access_token +
                "&v=5.69";

        System.setProperty("webdriver.chrome.driver", "/home/amanisson/SoftWare/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get(methodGetWall);

        WebElement postInfo = driver.findElement(By.tagName("body"));
        System.out.println(postInfo);
        String postInfoText = postInfo.getText();

        db.savePostsInBD(postInfoText);

        /*
        String path = "/home/amanisson/SoftWare/buffer.json";
        Files.write(Paths.get(path), postInfoText.getBytes(), StandardOpenOption.APPEND);
        File file = new File("/home/amanisson/SoftWare/buffer.json");
        FileWriter fwr = new FileWriter(file);
        fwr.write(postInfoText);
        */

    }

}
