import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class DataBase {

    private Connection connect = null;
    PreparedStatement preparedStatement = null;

    public int insertIntoDB(List<News> list) throws SQLException, ClassNotFoundException {

        int status = 0;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/parsing", "root", "sigidu28");

            preparedStatement = connect.prepareStatement("INSERT INTO rbk_news values (?)");

            Iterator<News> iterator = list.iterator();
            while (iterator.hasNext()){
                News n = iterator.next();
                preparedStatement.setString(1, n.getNews());
                preparedStatement.addBatch();
            }

            preparedStatement.execute();



        } catch (Exception e){
            throw e;
        } finally {
            try {
                if (connect != null) {
                    connect.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        return status;
    }

}
