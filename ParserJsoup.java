
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParserJsoup {

    void parse() throws IOException, SQLException, ClassNotFoundException {

        DataBase db = new DataBase();

        List<News> newsList = new ArrayList<>();
        String url_rbk = "https://www.rbc.ru";

        Document doc = Jsoup.connect(url_rbk).get();
        Elements posts = doc.select("span.news-feed__item__title");

        for(Element post: posts){
            String news = post.text();
            newsList.add(new News(news));
        }

        newsList.forEach(System.out::println);

        db.insertIntoDB(newsList);
    }
}


class News {
    private String news;

    public News(String news){
        this.news = news;
    }

    public String getNews(){

        return news;
    }

    public void setNews(String news){

        this.news = news;
    }


    @Override
    public String toString() {
        return "[" +
                "news = " + news +
                                    "]";
    }
}