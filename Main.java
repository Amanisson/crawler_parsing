import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

        DataBase db = new DataBase();
        VkontakteApi vk = new VkontakteApi();

        //vk.getWallPosts(76289806);

        //---------------------------------------------
        int[] arr = db.checkUsersFromDB();

        for (int i = 0; i < arr.length; i++)
        {
            vk.getWallPosts(arr[i]);
        }

        db.updateLastDate();
        //---------------------------------------------

    }

}
