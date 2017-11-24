import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataBase {

    private Connection connect = null;
    PreparedStatement preparedStatement = null;

    public int savePostsInBD(String pattern) throws FileNotFoundException, SQLException, ClassNotFoundException {
        int status = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/crawler", "root", "sigidu28");

            preparedStatement = connect.prepareStatement("INSERT INTO posts values (?, ?, ?, ?, ?, ? )");

            //JsonParser parser = new JsonParser();
            //Object obj = parser.parse(new FileReader("/home/amanisson/SoftWare/buffer.json"));
            JsonObject jsonObject = new Gson().fromJson(pattern, JsonObject.class);
            JsonObject response  = (JsonObject) jsonObject.get("response");
            JsonArray items = response.getAsJsonArray("items");
            //System.out.println(items);

            String items_str = String.valueOf(items);
            items_str = items_str.substring(1, items_str.length() - 1);
            //System.out.println(items_str);

            //JsonObject jso = new Gson().fromJson(items_str, JsonObject.class);
            Gson gson = new Gson();
            StringReader reader = new StringReader(items_str);
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.setLenient(true);
            JsonParser parser = new JsonParser();
            JsonObject jso = (JsonObject) parser.parse(jsonReader);

            String id = jso.get("id").toString();                   //System.out.println(id);
            String from_id = jso.get("from_id").toString();         //System.out.println(from_id);
            String owner_id = jso.get("owner_id").toString();       //System.out.println(owner_id);
            String date = jso.get("date").toString();               //System.out.println(date);
            String post_type = jso.get("post_type").toString();     //System.out.println(post_type);
            String text = jso.get("text").toString();               //System.out.println(text);

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, from_id);
            preparedStatement.setString(3, owner_id);
            preparedStatement.setString(4, date);
            preparedStatement.setString(5, post_type);
            preparedStatement.setString(6, text);

            status = preparedStatement.executeUpdate();

        } catch (Exception e) {
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


    public int[] checkUsersFromDB() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/crawler", "root", "sigidu28");

        String query = "SELECT userId FROM users";
        Statement st = connect.createStatement();
        ResultSet rs = st.executeQuery(query);

        ArrayList<String> list= new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString("userId"));
        }

        String[] resStr = new String[list.size()];
        resStr = list.toArray(resStr);
        st.close();

        int[] resInt = new int[resStr.length];

        for (int i =0; i < resStr.length; i++){
            resInt[i] = Integer.parseInt(resStr[i]);
        }

        /*
        for(int i =0; i<resInt.length; i++){
            System.out.println(resInt[i]);      }
        */

        return resInt;
    }


    public void updateLastDate() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/crawler", "root", "sigidu28");

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date currentDate = new Date();

        String query = "UPDATE users SET last_date = '" + currentDate + "'";
        Statement st = connect.createStatement();
        int rs = st.executeUpdate(query);

        st.close();
    }
}
